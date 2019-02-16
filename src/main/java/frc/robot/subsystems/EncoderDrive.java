
package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.SensorCollection;
import edu.wpi.first.wpilibj.command.Subsystem;

import frc.robot.commands.IdleEncoderDrive;
import frc.robot.helpers.EncoderConstants;
import frc.robot.helpers.Logger;
import frc.robot.Devices;


public class EncoderDrive extends Subsystem {

    private boolean m_talonsAreConnected = false;

    // Choose so that Talon does not report sensor out of phase
    public static boolean kSensorPhase = false;

    // Choose based on what direction you want to be positive, this does not affect motor invert
    public static boolean kMotorInvert = false;

    // Constructor
    public EncoderDrive() {
        Logger.debug("Constructing Subsystem: EncoderDrive...");

        boolean talonFrontLeftIsConnected = Devices.isConnected(Devices.talonSrxMecWheelFrontLeft);
        boolean talonRearLeftIsConnected = Devices.isConnected(Devices.talonSrxMecWheelRearLeft);
        boolean talonFrontRightIsConnected = Devices.isConnected(Devices.talonSrxMecWheelFrontRight);
        boolean talonRearRightIsConnected = Devices.isConnected(Devices.talonSrxMecWheelRearRight);
        m_talonsAreConnected = (talonFrontLeftIsConnected &&
                                talonRearLeftIsConnected && 
                                talonFrontRightIsConnected && 
                                talonRearRightIsConnected);

        if (m_talonsAreConnected) {
            SensorCollection sensorCol = Devices.talonSrxMecWheelFrontRight.getSensorCollection();
            int absolutePosition = sensorCol.getPulseWidthPosition();
            // Mask out overflows, keep bottom 12 bits
            absolutePosition &= 0xFFF;
            if (kSensorPhase) absolutePosition *= -1;
            if (kMotorInvert) absolutePosition *= -1;

            //Config TalonSRX encoder     
            Devices.talonSrxMecWheelRearRight.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Absolute,
                                                                           EncoderConstants.kPIDLoopPrimary,
                                                                           EncoderConstants.kTimeoutMs);
            Devices.talonSrxMecWheelRearRight.setSensorPhase(false);
            Devices.talonSrxMecWheelRearRight.setInverted(false);
            Devices.talonSrxMecWheelRearRight.configNominalOutputForward(0, EncoderConstants.kTimeoutMs);
            Devices.talonSrxMecWheelRearRight.configNominalOutputReverse(0, EncoderConstants.kTimeoutMs);
            Devices.talonSrxMecWheelRearRight.configPeakOutputForward(1, EncoderConstants.kTimeoutMs);
            Devices.talonSrxMecWheelRearRight.configPeakOutputReverse(-1, EncoderConstants.kTimeoutMs);
            Devices.talonSrxMecWheelRearRight.configAllowableClosedloopError(EncoderConstants.kPIDLoopPrimary, 0, EncoderConstants.kTimeoutMs);

            Devices.talonSrxMecWheelRearRight.config_kF(EncoderConstants.kPIDLoopPrimary, 0.0, EncoderConstants.kTimeoutMs);
            Devices.talonSrxMecWheelRearRight.config_kP(EncoderConstants.kPIDLoopPrimary, 0.15, EncoderConstants.kTimeoutMs);
            Devices.talonSrxMecWheelRearRight.config_kI(EncoderConstants.kPIDLoopPrimary, 0.001, EncoderConstants.kTimeoutMs);
            Devices.talonSrxMecWheelRearRight.config_kD(EncoderConstants.kPIDLoopPrimary, 1.5, EncoderConstants.kTimeoutMs);

            Devices.talonSrxMecWheelRearRight.setSelectedSensorPosition(absolutePosition, EncoderConstants.kPIDLoopPrimary, EncoderConstants.kTimeoutMs);
        }
    }

    @Override
    public void initDefaultCommand() {
        Logger.debug("Initializing EncoderDrive DefaultCommand -> IdleEncoderDrive...");

        setDefaultCommand(new IdleEncoderDrive());
    }

    // Stop all the drive motors
    public void stop() {
        if (m_talonsAreConnected) {
            Devices.talonSrxMecWheelRearRight.stopMotor();
        }
    }

    // Rotates the motor for a certain amount of raw encoder units
    // One rotations equals 4096 raw units
    public void driveRotations(double rotations) {
        if (m_talonsAreConnected) {
            double tickCount = rotations*EncoderConstants.kRedlineEncoderTpr;
            Devices.talonSrxMecWheelRearRight.set(ControlMode.MotionMagic, tickCount);
        }
    }

    public int getPosition() {
        if (m_talonsAreConnected) {
            return Devices.talonSrxMecWheelRearRight.getSelectedSensorPosition(EncoderConstants.kPIDLoopPrimary);
        }
        else {
            // TODO: Is this a good error return value?
            return -1;
        }
    }

    public int getVelocity(){
        if (m_talonsAreConnected) {
            return Devices.talonSrxMecWheelRearRight.getSelectedSensorVelocity(EncoderConstants.kPIDLoopPrimary);
        }
        else {
            // TODO: Is this a good error return value?
            return -1;
        }
    }

    // Resets the encoder position
    public void resetEncoderPosition() {
        if (m_talonsAreConnected) {
            Devices.talonSrxMecWheelRearRight.setSelectedSensorPosition(0, EncoderConstants.kPIDLoopPrimary, EncoderConstants.kTimeoutMs);
        }
    }

    // TODO: Does this need to be static? If not, change it, and check to see if the talons are connected.
    public static boolean isPositionMet() {
        SensorCollection sensorCol = Devices.talonSrxMecWheelRearRight.getSensorCollection();
        int velocity = sensorCol.getPulseWidthVelocity();
        if (velocity == 0) {
            return true;
        }
        else {
            return false;
        }
    }
}
