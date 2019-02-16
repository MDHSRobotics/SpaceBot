package frc.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;

import frc.robot.commands.idle.ArmStop;
import frc.robot.Devices;
import frc.robot.helpers.EncoderConstants;
import frc.robot.helpers.Logger;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.SensorCollection;


// Subsystem to move the Arm down into two different set positions,
// plus a little extra controlled by the user.
public class Arm extends Subsystem {

    public enum ArmPosition {
        START, HALF, FULL, MORE
    }

    //encoder variables
    private double targetPositionUnits;
    private double m_targetRotationHalf = 0.125;	
    private double m_targetRotationFull = 0.25;
    private boolean m_talonsAreConnected = false;

    // Choose so that Talon does not report sensor out of phase
    public static boolean kSensorPhase = false;
    // Choose based on what direction you want to be positive, this does not affect motor invert
    public static boolean kMotorInvert = false;
    public ArmPosition currentArmPosition = ArmPosition.START;


    // Constructor
    public Arm() {
        Logger.debug("Contructing Subsystem: Arm...");

        m_talonsAreConnected = Devices.isConnected(Devices.talonSrxArm);

        if (m_talonsAreConnected) {
            Devices.talonSrxArm.configFactoryDefault();

            Devices.talonSrxArm.configPeakCurrentDuration(40, 20);
            Devices.talonSrxArm.configPeakCurrentLimit(11, 20);
            Devices.talonSrxArm.configContinuousCurrentLimit(10, 20);

            Devices.talonSrxArm.configNominalOutputForward(0);
            Devices.talonSrxArm.configNominalOutputReverse(0);
            Devices.talonSrxArm.configPeakOutputForward(0.3);
            Devices.talonSrxArm.configPeakOutputReverse(-0.3);

            Devices.talonSrxArm.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, EncoderConstants.kPIDLoopPrimary, EncoderConstants.kTimeoutMs);
            Devices.talonSrxArm.setSensorPhase(kSensorPhase);
            Devices.talonSrxArm.setInverted(kMotorInvert);
            Devices.talonSrxArm.configAllowableClosedloopError(0, EncoderConstants.kPIDLoopPrimary, EncoderConstants.kTimeoutMs);

            Devices.talonSrxArm.config_kF(EncoderConstants.kPIDLoopPrimary, 0.0, EncoderConstants.kTimeoutMs);
            Devices.talonSrxArm.config_kP(EncoderConstants.kPIDLoopPrimary, 0.06, EncoderConstants.kTimeoutMs);
            Devices.talonSrxArm.config_kI(EncoderConstants.kPIDLoopPrimary, 0.0, EncoderConstants.kTimeoutMs);
            Devices.talonSrxArm.config_kD(EncoderConstants.kPIDLoopPrimary, 0.0, EncoderConstants.kTimeoutMs);

            //Reset Encoder Position 
            Devices.talonSrxArm.setSelectedSensorPosition(0, 0, 20);
            SensorCollection sensorCol = Devices.talonSrxArm.getSensorCollection();
            int absolutePosition = sensorCol.getPulseWidthPosition();
            absolutePosition &= 0xFFF;
            if (kSensorPhase)	absolutePosition *= -1;
            if (kMotorInvert)	absolutePosition *= -1;
            // Set the quadrature (relative) sensor to match absolute
            Devices.talonSrxArm.setSelectedSensorPosition(absolutePosition, EncoderConstants.kPIDLoopPrimary, EncoderConstants.kTimeoutMs);
            
            Devices.talonSrxArm.configMotionAcceleration(1500, 20);
            Devices.talonSrxArm.configMotionCruiseVelocity(4000, 20);
        }
    }

    @Override
    public void initDefaultCommand() {
        Logger.debug("Initializing Arm DefaultCommand -> ArmStop...");

        setDefaultCommand(new ArmStop());
    }

    // Stop the Arm motor
    public void stop() {
        if (m_talonsAreConnected) {
            Devices.talonSrxArm.stopMotor();
        }
    }

    public int getPosition() {
        if (!m_talonsAreConnected) return -1; // TODO: Is there a better return value?
        return Devices.talonSrxArm.getSelectedSensorPosition();
    }

    public boolean isPositionHalfMet() {
        int currentPosition = getPosition();
        if (currentPosition == -1) return true;
        targetPositionUnits = m_targetRotationHalf * EncoderConstants.kRedlineEncoderTpr;
        return (Math.abs((Math.abs(currentPosition) - targetPositionUnits)) < 512);
    }

    public boolean isPositionFullMet() {
        int currentPosition = getPosition();
        if (currentPosition == -1) return true;
        targetPositionUnits = m_targetRotationFull * EncoderConstants.kRedlineEncoderTpr;
        return (Math.abs((Math.abs(currentPosition) - targetPositionUnits)) < 800);
    }

    // Raises or lowers the Arm based on given speed
    public void lowerHalf() {
        if (m_talonsAreConnected) {
            Devices.talonSrxArm.setSelectedSensorPosition(0, 0, 20);
            targetPositionUnits = m_targetRotationHalf * EncoderConstants.kRedlineEncoderTpr;
            Logger.debug("Arm -> Target Lower Half Position: " + targetPositionUnits);
            Devices.talonSrxArm.set(ControlMode.Position, targetPositionUnits);
        }
    }

    public void lowerFull() {
        if (m_talonsAreConnected) {
            targetPositionUnits = m_targetRotationFull * EncoderConstants.kRedlineEncoderTpr;
            Logger.debug("Arm -> Target Lower Full Position: " + targetPositionUnits);
            Devices.talonSrxArm.set(ControlMode.Position, targetPositionUnits);
        }
    }

    public void lowerMore(double speed) {
        if (m_talonsAreConnected) {
            Devices.talonSrxArm.set(speed);
        }
    }

}
