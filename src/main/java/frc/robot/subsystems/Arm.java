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

    // Position constants
    // TODO: The constants that might change from the test robot to the competition robot need to be added to Shuffleboard
    private final double GEAR_RATIO = 81;
    // TODO: test to find the correct degree measures
    private final double ROTATION_DEGREE = 90; // Amount of degrees the arm will lower/raise
    private final double ROTATION_COUNT_GS = ROTATION_DEGREE / 360; // Amount of rotations on the gearbox shaft
    private final double ROTATION_COUNT_MS = ROTATION_COUNT_GS * GEAR_RATIO; // Amount of rotations on the motor shaft
    private final double ARM_POSITION = ROTATION_COUNT_MS * TalonConstants.REDLIN_ENCODER_TPR; // Position in ticks to turn ROTATION_DEGREE 
    private final double RESET_POSITION = 0;
    private final double POSITION_TOLERANCE = 100;

    // Encoder constants
    private final boolean SENSOR_PHASE = true; // So that Talon does not report sensor out of phase
    private final boolean MOTOR_INVERT = true; // Which direction you want to be positive; this does not affect motor invert

    // The Talon connection state, to prevent watchdog warnings during testing
    private boolean m_talonsAreConnected = false;


    public Arm() {
        Logger.setup("Constructing Subsystem: Arm...");
        m_talonsAreConnected = Devices.isConnected(Devices.talonSrxArm);
        if (!m_talonsAreConnected) {
            Logger.error("Arm talons not all connected! Disabling Arm...");
        }
        else {
            Devices.talonSrxArm.configFactoryDefault();

            Devices.talonSrxHatcher.configPeakCurrentDuration(TalonConstants.PEAK_AMPERAGE_DURATION, TalonConstants.TIMEOUT_MS);
            Devices.talonSrxHatcher.configPeakCurrentLimit(TalonConstants.PEAK_AMPERAGE, TalonConstants.TIMEOUT_MS);
            Devices.talonSrxHatcher.configContinuousCurrentLimit(TalonConstants.CONTINUOUS_AMPERAGE_LIMIT, TalonConstants.TIMEOUT_MS);

            Devices.talonSrxArm.configNominalOutputForward(0);
            Devices.talonSrxArm.configNominalOutputReverse(0);
            Devices.talonSrxArm.configPeakOutputForward(0.3);
            Devices.talonSrxArm.configPeakOutputReverse(-0.3);

            Devices.talonSrxArm.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, TalonConstants.PID_LOOP_PRIMARY, TalonConstants.TIMEOUT_MS);
            Devices.talonSrxArm.setSensorPhase(SENSOR_PHASE);
            Devices.talonSrxArm.setInverted(MOTOR_INVERT);
            Devices.talonSrxArm.configAllowableClosedloopError(0, TalonConstants.PID_LOOP_PRIMARY, TalonConstants.TIMEOUT_MS);

            Devices.talonSrxArm.config_kF(TalonConstants.PID_LOOP_PRIMARY, 0.0, TalonConstants.TIMEOUT_MS);
            Devices.talonSrxArm.config_kP(TalonConstants.PID_LOOP_PRIMARY, 0.045, TalonConstants.TIMEOUT_MS);
            Devices.talonSrxArm.config_kI(TalonConstants.PID_LOOP_PRIMARY, 0.0, TalonConstants.TIMEOUT_MS);
            Devices.talonSrxArm.config_kD(TalonConstants.PID_LOOP_PRIMARY, 0.0, TalonConstants.TIMEOUT_MS);

            // Reset Encoder Position 
            Devices.talonSrxArm.setSelectedSensorPosition(0, 0, TalonConstants.TIMEOUT_MS);
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
        //if (!m_talonsAreConnected) return;
        Logger.info("Arm -> Reset Position: " + RESET_POSITION);
        Devices.talonSrxArm.set(ControlMode.Position, RESET_POSITION);
    }

    // Lowers the Arm to the encoded "full" position
    public void lower() {
        if (!m_talonsAreConnected) return;
        // Devices.talonSrxArm.setSelectedSensorPosition(0, 0, 20);
        Logger.info("Arm -> Target Lower Full Position: " + ARM_POSITION);
        Devices.talonSrxArm.set(ControlMode.Position, ARM_POSITION);
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

}
