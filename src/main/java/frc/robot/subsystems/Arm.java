package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.SensorCollection;
import edu.wpi.first.wpilibj.command.Subsystem;

import frc.robot.commands.idle.ArmStop;
import frc.robot.consoles.Logger;
import frc.robot.helpers.TalonConstants;
import frc.robot.Devices;


// Subsystem to move the Arm down into two different set positions,
// plus a little extra controlled by the user.
public class Arm extends Subsystem {

    public enum ArmMode {
        AUTO, MANUAL
    }

    // The public property to determine the Arm state
    public ArmMode armMode   = ArmMode.AUTO;

    // Encoder constants
    private final double GEAR_RATIO = 81;

    // TODO: test to find the correct degree measures
    private final double FULL_ROTATION_DEGREE = 90;

    private final double RESET_POSITION = 0;
    private final double FULL_POSITION = (FULL_ROTATION_DEGREE / 360) * GEAR_RATIO * TalonConstants.REDLIN_ENCODER_TPR;
    private final double POSITION_TOLERANCE = 0; // TODO: test to see what tolerance works

    private final boolean SENSOR_PHASE = false; // So that Talon does not report sensor out of phase
    private final boolean MOTOR_INVERT = false; // Which direction you want to be positive; this does not affect motor invert

    // The Talon connection state, to prevent watchdog warnings during testing
    private boolean m_talonsAreConnected = false;


    public Arm() {
        Logger.setup("Constructing Subsystem: Arm...");

        if (!m_talonsAreConnected) {
            Logger.error("Arm talons not all connected! Disabling Arm...");
        }
        else {
            Devices.talonSrxArm.configFactoryDefault();

            Devices.talonSrxArm.configPeakCurrentDuration(TalonConstants.PEAK_CURRENT_DURATION, TalonConstants.TIMEOUT_MS);
            Devices.talonSrxArm.configPeakCurrentLimit(TalonConstants.PEAK_CURRENT_AMPS, TalonConstants.TIMEOUT_MS);
            Devices.talonSrxArm.configContinuousCurrentLimit(TalonConstants.CONTINUOUS_CURRENT_LIMIT, TalonConstants.TIMEOUT_MS);

            Devices.talonSrxArm.configNominalOutputForward(TalonConstants.NOMINAL_OUTPUT_FORWARD);
            Devices.talonSrxArm.configNominalOutputReverse(TalonConstants.NOMINAL_OUTPUT_REVERSE);
            Devices.talonSrxArm.configPeakOutputForward(0.3);
            Devices.talonSrxArm.configPeakOutputReverse(-0.3);

            Devices.talonSrxArm.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, TalonConstants.PID_LOOP_PRIMARY, TalonConstants.TIMEOUT_MS);
            Devices.talonSrxArm.setSensorPhase(SENSOR_PHASE);
            Devices.talonSrxArm.setInverted(MOTOR_INVERT);
            Devices.talonSrxArm.configAllowableClosedloopError(TalonConstants.PID_LOOP_PRIMARY, 0, TalonConstants.TIMEOUT_MS);

            Devices.talonSrxArm.config_kF(TalonConstants.PID_LOOP_PRIMARY, 0.0, TalonConstants.TIMEOUT_MS);
            Devices.talonSrxArm.config_kP(TalonConstants.PID_LOOP_PRIMARY, 0.06, TalonConstants.TIMEOUT_MS);
            Devices.talonSrxArm.config_kI(TalonConstants.PID_LOOP_PRIMARY, 0.0, TalonConstants.TIMEOUT_MS);
            Devices.talonSrxArm.config_kD(TalonConstants.PID_LOOP_PRIMARY, 0.0, TalonConstants.TIMEOUT_MS);

            // Reset Encoder Position 
            Devices.talonSrxArm.setSelectedSensorPosition(0, TalonConstants.PID_SLOT_0, TalonConstants.TIMEOUT_MS);
            SensorCollection sensorCol = Devices.talonSrxArm.getSensorCollection();
            int absolutePosition = sensorCol.getPulseWidthPosition();
            absolutePosition &= 0xFFF;
            if (SENSOR_PHASE) absolutePosition *= -1;
            if (MOTOR_INVERT) absolutePosition *= -1;
            // Set the quadrature (relative) sensor to match absolute
            Devices.talonSrxArm.setSelectedSensorPosition(absolutePosition, TalonConstants.PID_LOOP_PRIMARY, TalonConstants.TIMEOUT_MS);
            
            Devices.talonSrxArm.configMotionAcceleration(1500, TalonConstants.TIMEOUT_MS);
            Devices.talonSrxArm.configMotionCruiseVelocity(4000, TalonConstants.TIMEOUT_MS);
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
        Logger.info("Arm -> Reset Position: " + RESET_POSITION);
        Devices.talonSrxArm.set(ControlMode.Position, RESET_POSITION);
    }

    // Lowers the Arm to the encoded "full" position
    public void lowerFull() {
        if (!m_talonsAreConnected) return;
        double targetPositionUnits = FULL_POSITION * TalonConstants.REDLIN_ENCODER_TPR;
        Logger.info("Arm -> Target Lower Full Position: " + targetPositionUnits);
        Devices.talonSrxArm.set(ControlMode.Position, targetPositionUnits);
    }

    public void manualControl(double jStickValue){
        Devices.talonSrxArm.set(jStickValue);
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

    // Return whether or not the motor has reached the encoded "reset" position
    public boolean isResetPositionMet() {
        if (!m_talonsAreConnected) return true;
        int currentPosition = getPosition();
        return (Math.abs(currentPosition) < POSITION_TOLERANCE);
    }

    // Return whether or not the motor has reached the encoded "full" position
    public boolean isFullPositionMet() {
        if (!m_talonsAreConnected) return true;
        int currentPosition = getPosition();
        double targetPositionUnits = FULL_POSITION * TalonConstants.REDLIN_ENCODER_TPR;
        return Math.abs(currentPosition - targetPositionUnits) < POSITION_TOLERANCE;
    }

}
