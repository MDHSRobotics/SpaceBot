
package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.SensorCollection;
import edu.wpi.first.wpilibj.command.Subsystem;

import frc.robot.commands.idle.BallerStop;
import frc.robot.helpers.EncoderConstants;
import frc.robot.helpers.Logger;
import frc.robot.Devices;


// Cargo ball subsystem
public class Baller extends Subsystem {

    private boolean m_talonsAreConnected = false;

    // TODO: Be explicit. Are these public or private? Make them private unless they need to be public. Name them accordingly.
    double targetRotations = 3.73;
    double targetPositionUnits;	
    private static final double k_stopThreshold = 10;
    boolean ballToggle = false;

    // Choose so that Talon does not report sensor out of phase
    public static boolean kSensorPhase = false;

    // Choose based on what direction you want to be positive, this does not affect motor invert
    public static boolean kMotorInvert = false;

    // Constructor
    public Baller() {
        Logger.debug("Constructing Subsystem: Baller...");

        m_talonsAreConnected = Devices.isConnected(Devices.talonSrxBaller);
        if (m_talonsAreConnected) {
            Devices.talonSrxBaller.configFactoryDefault();

            Devices.talonSrxBaller.configPeakCurrentDuration(40, 20);
            Devices.talonSrxBaller.configPeakCurrentLimit(11, 20);
            Devices.talonSrxBaller.configContinuousCurrentLimit(10, 20);

            Devices.talonSrxBaller.configNominalOutputForward(0);
            Devices.talonSrxBaller.configNominalOutputReverse(0);
            Devices.talonSrxBaller.configPeakOutputForward(0.3);
            Devices.talonSrxBaller.configPeakOutputReverse(-0.3);

            Devices.talonSrxBaller.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, EncoderConstants.kPIDLoopPrimary, EncoderConstants.kTimeoutMs);
            Devices.talonSrxBaller.setSensorPhase(kSensorPhase);
            Devices.talonSrxBaller.setInverted(kMotorInvert);
            Devices.talonSrxBaller.configAllowableClosedloopError(0, EncoderConstants.kPIDLoopPrimary, EncoderConstants.kTimeoutMs);

            Devices.talonSrxBaller.config_kF(EncoderConstants.kPIDLoopPrimary, 0.0, EncoderConstants.kTimeoutMs);
            Devices.talonSrxBaller.config_kP(EncoderConstants.kPIDLoopPrimary, 0.06, EncoderConstants.kTimeoutMs);
            Devices.talonSrxBaller.config_kI(EncoderConstants.kPIDLoopPrimary, 0.0, EncoderConstants.kTimeoutMs);
            Devices.talonSrxBaller.config_kD(EncoderConstants.kPIDLoopPrimary, 0.0, EncoderConstants.kTimeoutMs);

            //Reset Encoder Position 
            Devices.talonSrxBaller.setSelectedSensorPosition(0, 0, 20);
            SensorCollection sensorCol = Devices.talonSrxBaller.getSensorCollection();
            int absolutePosition = sensorCol.getPulseWidthPosition();
            absolutePosition &= 0xFFF;
            if (kSensorPhase)	absolutePosition *= -1;
            if (kMotorInvert)	absolutePosition *= -1;
            // Set the quadrature (relative) sensor to match absolute
            Devices.talonSrxBaller.setSelectedSensorPosition(absolutePosition, EncoderConstants.kPIDLoopPrimary, EncoderConstants.kTimeoutMs);
            
            Devices.talonSrxBaller.configMotionAcceleration(1500, 20);
            Devices.talonSrxBaller.configMotionCruiseVelocity(4000, 20);
        }
    }

    @Override
    public void initDefaultCommand() {
        Logger.debug("Initializing Baller DefaultCommand -> BallerStop...");

        setDefaultCommand(new BallerStop());
    }

    // Stop all the drive motors
    public void stop() {
        if (m_talonsAreConnected) {
            Devices.talonSrxBaller.stopMotor();
        }
    }

    public int getPosition() {
        if (m_talonsAreConnected) {
            return Devices.talonSrxBaller.getSelectedSensorPosition();
        }
        else {
            // TODO: Is this a good error return value?
            return -1;
        }
    }

    public int getVelocity() {
        if (m_talonsAreConnected) {
            return Devices.talonSrxBaller.getSelectedSensorVelocity();
        }
        else {
            // TODO: Is this a good error return value?
            return -1;
        }
    }

    public void resetEncoderPosition() {
        if (m_talonsAreConnected) {
            Devices.talonSrxBaller.setSelectedSensorPosition(0, EncoderConstants.kPIDLoopPrimary, EncoderConstants.kTimeoutMs);
        }
    }

    public void ballRaise() {
        if (m_talonsAreConnected) {
            Devices.talonSrxBaller.setSelectedSensorPosition(0, 0, 20);
            targetPositionUnits = targetRotations * EncoderConstants.kRedlineEncoderTpr;

            Devices.talonSrxBaller.set(ControlMode.Position, targetPositionUnits);
            Logger.debug("Target Position: " + targetPositionUnits);
        }
    }

    public void ballClose() {
        if (m_talonsAreConnected) {
            Devices.talonSrxBaller.setSelectedSensorPosition(0, 0, 20);
            targetPositionUnits = -(targetRotations * EncoderConstants.kRedlineEncoderTpr);

            Devices.talonSrxBaller.set(ControlMode.Position, targetPositionUnits);
            Logger.debug("Target Position: " + targetPositionUnits);
        }
    }

    // TODO: Get and Set methods are a little strange here. The set is actually a toggle, and get just returns a public member variable. Let's discuss.
    public void setBallToggle() {
        ballToggle = !ballToggle;
    }

    public boolean getBallToggle() {
        return ballToggle;
    }

    public boolean isStopped() {
        if (m_talonsAreConnected) {
            double currentVelocity = Devices.talonSrxBaller.getSelectedSensorVelocity();
            Logger.debug("Position: " + currentVelocity);
            return (Math.abs(currentVelocity) < k_stopThreshold);
        }
        else {
            // TODO: Is this a good error return value?
            return true;
        }
    }

    public boolean isPositionMet() {
        if (m_talonsAreConnected) {
            int currentPosition = Devices.talonSrxBaller.getSelectedSensorPosition();

            targetPositionUnits = targetRotations * EncoderConstants.kRedlineEncoderTpr;
            Logger.debug("Position: " + currentPosition);

            return (Math.abs((Math.abs(currentPosition) - targetPositionUnits)) < 600);
        }
        else {
            // TODO: Is this a good error return value?
            return true;
        }
    }

}
