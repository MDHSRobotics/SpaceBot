package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.SensorCollection;
import edu.wpi.first.wpilibj.command.Subsystem;

import frc.robot.commands.idle.ArmStop;
import frc.robot.consoles.Logger;
import frc.robot.helpers.EncoderConstants;
import frc.robot.Devices;


// Subsystem to move the Arm down into two different set positions,
// plus a little extra controlled by the user.
public class Arm extends Subsystem {

    public enum ArmPosition {
        START, HALF, FULL, MORE
    }

    // The public property to determine the Arm state
    public ArmPosition currentArmPosition = ArmPosition.START;

    // Encoder constants
 
    private final double GEARBOX_RATIO = 81;
    private final double ROTATION_HALF_DEGREES = 45;
    private final double ROTATION_FULL_DEGREES = 90;
    private final double TARGET_POSITION_HALF = (ROTATION_HALF_DEGREES/360)*GEARBOX_RATIO*EncoderConstants.REDLIN_ENCODER_TPR;	
    private final double TARGET_POSITION_FULL = (ROTATION_FULL_DEGREES/360)*GEARBOX_RATIO*EncoderConstants.REDLIN_ENCODER_TPR;
    private final double TARGET_POSITION_RESET = 0;
    private final boolean SENSOR_PHASE = false; // So that Talon does not report sensor out of phase
    private final boolean MOTOR_INVERT = false; // Which direction you want to be positive; this does not affect motor invert
    private final double POSITION_TOLERANCE = 0;

    // The Talon connection state, to prevent watchdog warnings during testing
    private boolean m_talonsAreConnected = false;

    public Arm() {
        Logger.setup("Constructing Subsystem: Arm...");

        if (!m_talonsAreConnected) {
            Logger.error("Arm talons not all connected! Disabling Arm...");
        }
        else {
            Devices.talonSrxArm.configFactoryDefault();

            Devices.talonSrxArm.configPeakCurrentDuration(40, 20);
            Devices.talonSrxArm.configPeakCurrentLimit(11, 20);
            Devices.talonSrxArm.configContinuousCurrentLimit(10, 20);

            Devices.talonSrxArm.configNominalOutputForward(0);
            Devices.talonSrxArm.configNominalOutputReverse(0);
            Devices.talonSrxArm.configPeakOutputForward(0.3);
            Devices.talonSrxArm.configPeakOutputReverse(-0.3);

            Devices.talonSrxArm.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, EncoderConstants.PID_LOOP_PRIMARY, EncoderConstants.TIMEOUT_MS);
            Devices.talonSrxArm.setSensorPhase(SENSOR_PHASE);
            Devices.talonSrxArm.setInverted(MOTOR_INVERT);
            Devices.talonSrxArm.configAllowableClosedloopError(0, EncoderConstants.PID_LOOP_PRIMARY, EncoderConstants.TIMEOUT_MS);

            Devices.talonSrxArm.config_kF(EncoderConstants.PID_LOOP_PRIMARY, 0.0, EncoderConstants.TIMEOUT_MS);
            Devices.talonSrxArm.config_kP(EncoderConstants.PID_LOOP_PRIMARY, 0.06, EncoderConstants.TIMEOUT_MS);
            Devices.talonSrxArm.config_kI(EncoderConstants.PID_LOOP_PRIMARY, 0.0, EncoderConstants.TIMEOUT_MS);
            Devices.talonSrxArm.config_kD(EncoderConstants.PID_LOOP_PRIMARY, 0.0, EncoderConstants.TIMEOUT_MS);

            // Reset Encoder Position 
            Devices.talonSrxArm.setSelectedSensorPosition(0, 0, 20);
            SensorCollection sensorCol = Devices.talonSrxArm.getSensorCollection();
            int absolutePosition = sensorCol.getPulseWidthPosition();
            absolutePosition &= 0xFFF;
            if (SENSOR_PHASE) absolutePosition *= -1;
            if (MOTOR_INVERT) absolutePosition *= -1;
            // Set the quadrature (relative) sensor to match absolute
            Devices.talonSrxArm.setSelectedSensorPosition(absolutePosition, EncoderConstants.PID_LOOP_PRIMARY, EncoderConstants.TIMEOUT_MS);
            
            Devices.talonSrxArm.configMotionAcceleration(1500, 20);
            Devices.talonSrxArm.configMotionCruiseVelocity(4000, 20);
        }
    }

    @Override
    public void initDefaultCommand() {
        Logger.setup("Initializing Arm DefaultCommand -> ArmStop...");

        setDefaultCommand(new ArmStop());
    }

    // Stop the Arm motor
    public void stop() {
        if (!m_talonsAreConnected) return;
        Devices.talonSrxArm.stopMotor();
    }

    // Reset the Arm to its starting position
    public void resetPosition() {
        if (!m_talonsAreConnected) return;
        Logger.info("Arm -> Reset Position: " + TARGET_POSITION_RESET);
        Devices.talonSrxArm.set(ControlMode.Position, TARGET_POSITION_RESET);
    }

    // Lowers the Arm to the encoded "half" position
    public void lowerHalf() {
        if (!m_talonsAreConnected) return;
        double targetPositionUnits = TARGET_POSITION_HALF * EncoderConstants.REDLIN_ENCODER_TPR;
        Logger.info("Arm -> Target Lower Half Position: " + targetPositionUnits);
        Devices.talonSrxArm.set(ControlMode.Position, targetPositionUnits);
    }

    // Lowers the Arm to the encoded "full" position
    public void lowerFull() {
        if (!m_talonsAreConnected) return;
        double targetPositionUnits = TARGET_POSITION_FULL * EncoderConstants.REDLIN_ENCODER_TPR;
        Logger.info("Arm -> Target Lower Full Position: " + targetPositionUnits);
        Devices.talonSrxArm.set(ControlMode.Position, targetPositionUnits);
    }

    // Lowers the Arm beyond the full encoded position, based on given speed
    // TODO: Use encoders to set a limit? There will also be a physical stop
    public void lowerMore(double speed) {
        if (m_talonsAreConnected) {
            Devices.talonSrxArm.set(speed);
        }
    }

    // Get the current motor velocity
    public int getVelocity() {
        if (!m_talonsAreConnected) return 0;
        return Devices.talonSrxArm.getSelectedSensorVelocity();
    }

    // Get the current motor position
    public int getPosition() {
        if (!m_talonsAreConnected) return 0;
        return Devices.talonSrxArm.getSelectedSensorPosition();
    }

    // Return whether or not the motor has reached the encoded "half" position
    public boolean isPositionHalfMet() {
        if (!m_talonsAreConnected) return true;
        int currentPosition = getPosition();
        double targetPositionUnits = TARGET_POSITION_HALF * EncoderConstants.REDLIN_ENCODER_TPR;
        return Math.abs(currentPosition - targetPositionUnits) < POSITION_TOLERANCE;
    }

    // Return whether or not the motor has reached the encoded "full" position
    public boolean isPositionFullMet() {
        if (!m_talonsAreConnected) return true;
        int currentPosition = getPosition();
        double targetPositionUnits = TARGET_POSITION_FULL * EncoderConstants.REDLIN_ENCODER_TPR;
        return Math.abs(currentPosition - targetPositionUnits) < POSITION_TOLERANCE;
    }

    // Return whether or not the motor has reached the encoded "reset" position
    public boolean isArmPositionResetMet() {
        if (!m_talonsAreConnected) return true;
        int currentPosition = getPosition();
        return (Math.abs(currentPosition) < POSITION_TOLERANCE);
    }

}
