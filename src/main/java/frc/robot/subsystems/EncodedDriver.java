
package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.SensorCollection;
import edu.wpi.first.wpilibj.command.Subsystem;

import frc.robot.commands.idle.EncodedDriverStop;
import frc.robot.helpers.EncoderConstants;
import frc.robot.helpers.Logger;
import frc.robot.Devices;


public class EncodedDriver extends Subsystem {

    // Encoder constants
    private final boolean SENSOR_PHASE = false; // So that Talon does not report sensor out of phase
    private final boolean MOTOR_INVERT = false; // Which direction you want to be positive; this does not affect motor invert

    // The Talon connection state, to prevent watchdog warnings during testing
    private boolean m_talonsAreConnected = false;

    public EncodedDriver() {
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
            if (SENSOR_PHASE) absolutePosition *= -1;
            if (MOTOR_INVERT) absolutePosition *= -1;

            // Config TalonSRX encoder     
            Devices.talonSrxMecWheelRearRight.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Absolute,
                                                                           EncoderConstants.PID_LOOP_PRIMARY,
                                                                           EncoderConstants.TIMEOUT_MS);
            Devices.talonSrxMecWheelRearRight.setSensorPhase(false);
            Devices.talonSrxMecWheelRearRight.setInverted(false);
            Devices.talonSrxMecWheelRearRight.configNominalOutputForward(0, EncoderConstants.TIMEOUT_MS);
            Devices.talonSrxMecWheelRearRight.configNominalOutputReverse(0, EncoderConstants.TIMEOUT_MS);
            Devices.talonSrxMecWheelRearRight.configPeakOutputForward(1, EncoderConstants.TIMEOUT_MS);
            Devices.talonSrxMecWheelRearRight.configPeakOutputReverse(-1, EncoderConstants.TIMEOUT_MS);
            Devices.talonSrxMecWheelRearRight.configAllowableClosedloopError(EncoderConstants.PID_LOOP_PRIMARY, 0, EncoderConstants.TIMEOUT_MS);

            Devices.talonSrxMecWheelRearRight.config_kF(EncoderConstants.PID_LOOP_PRIMARY, 0.0, EncoderConstants.TIMEOUT_MS);
            Devices.talonSrxMecWheelRearRight.config_kP(EncoderConstants.PID_LOOP_PRIMARY, 0.15, EncoderConstants.TIMEOUT_MS);
            Devices.talonSrxMecWheelRearRight.config_kI(EncoderConstants.PID_LOOP_PRIMARY, 0.001, EncoderConstants.TIMEOUT_MS);
            Devices.talonSrxMecWheelRearRight.config_kD(EncoderConstants.PID_LOOP_PRIMARY, 1.5, EncoderConstants.TIMEOUT_MS);

            Devices.talonSrxMecWheelRearRight.setSelectedSensorPosition(absolutePosition, EncoderConstants.PID_LOOP_PRIMARY, EncoderConstants.TIMEOUT_MS);
        }
    }

    @Override
    public void initDefaultCommand() {
        Logger.debug("Initializing EncoderDrive DefaultCommand -> IdleEncoderDrive...");

        setDefaultCommand(new EncodedDriverStop());
    }

    // Stop all the drive motors
    public void stop() {
        if (!m_talonsAreConnected) return;
        Devices.talonSrxMecWheelRearRight.stopMotor();
    }

    // Rotates the motor for a certain amount of raw encoder units
    // One rotations equals 4096 raw units
    public void driveRotations(double rotations) {
        if (!m_talonsAreConnected) return;
        double tickCount = rotations * EncoderConstants.REDLIN_ENCODER_TPR;
        Devices.talonSrxMecWheelRearRight.set(ControlMode.MotionMagic, tickCount);
    }

    public int getPosition() {
        return Devices.talonSrxMecWheelRearRight.getSelectedSensorPosition(EncoderConstants.PID_LOOP_PRIMARY);
    }

    public int getVelocity(){
        return Devices.talonSrxMecWheelRearRight.getSelectedSensorVelocity(EncoderConstants.PID_LOOP_PRIMARY);
    }

    public boolean isPositionMet() {
        if (!m_talonsAreConnected) return true;
        SensorCollection sensorCol = Devices.talonSrxMecWheelRearRight.getSensorCollection();
        int velocity = sensorCol.getPulseWidthVelocity();
        return (velocity == 0);
    }

    // Resets the encoder position
    public void resetEncoderPosition() {
        if (!m_talonsAreConnected) return;
        Devices.talonSrxMecWheelRearRight.setSelectedSensorPosition(0, EncoderConstants.PID_LOOP_PRIMARY, EncoderConstants.TIMEOUT_MS);
    }

}
