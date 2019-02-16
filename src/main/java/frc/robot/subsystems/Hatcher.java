
package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.SensorCollection;
import edu.wpi.first.wpilibj.command.Subsystem;

import frc.robot.commands.idle.HatcherStop;
import frc.robot.helpers.EncoderConstants;
import frc.robot.helpers.Logger;
import frc.robot.Devices;


// Hatcher subsystem, for grabbing and releasing hatches
public class Hatcher extends Subsystem {

    private boolean m_talonsAreConnected = false;

    // TODO: Be explicit. Are these public or private? Make them private unless they need to be public. Name them accordingly.
    double targetRotations = 0.8;
    double targetPositionUnits;	
    static final double k_stopThreshold = 10;

    // Choose so that Talon does not report sensor out of phase
    public static boolean kSensorPhase = false;
    
    // Choose based on what direction you want to be positive, this does not affect motor invert
    public static boolean kMotorInvert = true;

    public boolean hatchToggle = false;

    // Constructor
    public Hatcher() {
        Logger.debug("Constructing Subsystem: Hatcher...");

        m_talonsAreConnected = Devices.isConnected(Devices.talonSrxHatcher);
        if (m_talonsAreConnected) {
            Devices.talonSrxHatcher.configFactoryDefault();

            Devices.talonSrxHatcher.configPeakCurrentDuration(40, 20);
            Devices.talonSrxHatcher.configPeakCurrentLimit(11, 20);
            Devices.talonSrxHatcher.configContinuousCurrentLimit(10, 20);

            Devices.talonSrxHatcher.configNominalOutputForward(0);
            Devices.talonSrxHatcher.configNominalOutputReverse(0);
            Devices.talonSrxHatcher.configPeakOutputForward(0.5);
            Devices.talonSrxHatcher.configPeakOutputReverse(-0.5);

            Devices.talonSrxHatcher.configMotionAcceleration(6000, 20);
            Devices.talonSrxHatcher.configMotionCruiseVelocity(15000, 20);

            // Config TalonSRX Redline encoder
            Devices.talonSrxHatcher.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, EncoderConstants.kPIDLoopPrimary, EncoderConstants.kTimeoutMs);
            Devices.talonSrxHatcher.setSensorPhase(kSensorPhase);
            Devices.talonSrxHatcher.setInverted(kMotorInvert);
            Devices.talonSrxHatcher.configAllowableClosedloopError(0, EncoderConstants.kPIDLoopPrimary, EncoderConstants.kTimeoutMs);

            Devices.talonSrxHatcher.config_kF(EncoderConstants.kPIDLoopPrimary, 0.0, EncoderConstants.kTimeoutMs);
            Devices.talonSrxHatcher.config_kP(EncoderConstants.kPIDLoopPrimary, 0.06, EncoderConstants.kTimeoutMs);
            Devices.talonSrxHatcher.config_kI(EncoderConstants.kPIDLoopPrimary, 0.0, EncoderConstants.kTimeoutMs);
            Devices.talonSrxHatcher.config_kD(EncoderConstants.kPIDLoopPrimary, 0.0, EncoderConstants.kTimeoutMs);

            // Reset Encoder Position 
            Devices.talonSrxHatcher.setSelectedSensorPosition(0, 0, 20);
            SensorCollection sensorCol = Devices.talonSrxHatcher.getSensorCollection();
            int absolutePosition = sensorCol.getPulseWidthPosition();
            absolutePosition &= 0xFFF;
            if (kSensorPhase) absolutePosition *= -1;
            if (kMotorInvert) absolutePosition *= -1;
            // Set the quadrature (relative) sensor to match absolute
            Devices.talonSrxHatcher.setSelectedSensorPosition(absolutePosition, EncoderConstants.kPIDLoopPrimary, EncoderConstants.kTimeoutMs);
        }
    }

    @Override
    public void initDefaultCommand() {
        Logger.debug("Initializing Hatcher DefaultCommand -> HatcherStop...");

        setDefaultCommand(new HatcherStop());
    }

    // Stop all the drive motors
    public void stop() {
        if (m_talonsAreConnected) {
            Devices.talonSrxHatcher.stopMotor();
        }
    }

    public int getPosition() {
        if (m_talonsAreConnected) {
            return Devices.talonSrxHatcher.getSelectedSensorPosition();
        }
        else {
            // TODO: Is this a good error return value?
            return -1;
        }
    }

    public int getVelocity() {
        if (m_talonsAreConnected) {
            return Devices.talonSrxHatcher.getSelectedSensorPosition();
        }
        else {
            // TODO: Is this a good error return value?
            return -1;
        }
    }

    public void resetEncoderPosition() {
        if (m_talonsAreConnected) {
            Devices.talonSrxHatcher.setSelectedSensorPosition(0, EncoderConstants.kPIDLoopPrimary, EncoderConstants.kTimeoutMs);
        }
    }

    public void driveStatic() {
        if (m_talonsAreConnected) {
            Devices.talonSrxHatcher.set(0.2);
        }
    }

    public void clawOpen() {
        if (m_talonsAreConnected) {
            Devices.talonSrxHatcher.setSelectedSensorPosition(0, 0, 20);
            targetPositionUnits = targetRotations * EncoderConstants.kRedlineEncoderTpr;

            Devices.talonSrxHatcher.set(ControlMode.MotionMagic, targetPositionUnits);
            Logger.debug("Target Open Position: " + targetPositionUnits);
        }
    }

    public void clawClose() {
        if (m_talonsAreConnected) {
            Devices.talonSrxHatcher.setSelectedSensorPosition(0, 0, 20);
            targetPositionUnits = -(targetRotations * EncoderConstants.kRedlineEncoderTpr);
            Devices.talonSrxHatcher.set(ControlMode.MotionMagic, targetPositionUnits);
            Logger.debug("Target Close Position: " + targetPositionUnits);
        }
    }

    // TODO: Get and Set methods are a little strange here. The set is actually a toggle, and get just returns a public member variable. Let's discuss.
    public void setHatchToggle() {
        hatchToggle = !hatchToggle;
    }

    public boolean getHatchToggle() {
        return hatchToggle;
    }

    public boolean isStopped() {
        double currentVelocity = Devices.talonSrxHatcher.getSelectedSensorVelocity();
        Logger.debug("Position: " + currentVelocity);
        return (Math.abs(currentVelocity) < k_stopThreshold);
    }

    public boolean isPositionMet() {
        int currentPosition = Devices.talonSrxHatcher.getSelectedSensorPosition();

        targetPositionUnits = targetRotations * EncoderConstants.kRedlineEncoderTpr;
        Logger.debug("Position: " + currentPosition);

        return (Math.abs((Math.abs(currentPosition) - targetPositionUnits)) < 600);
    }

}