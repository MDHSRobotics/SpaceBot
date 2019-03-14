
package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.SensorCollection;
import edu.wpi.first.wpilibj.command.Subsystem;

import frc.robot.commands.idle.BallerStop;
import frc.robot.consoles.Logger;
import frc.robot.helpers.TalonConstants;
import frc.robot.Devices;


// Baller subsystem, for holding and tossing cargo balls
public class Baller extends Subsystem {

    // The public property to determine the Baller's flipper state
    public boolean ballIsTossed = false;

    // Position constants
    // TODO: The constants that might change from the test robot to the competition robot need to be added to Shuffleboard
    private final double GEAR_RATIO = 16;
    private final double HOLD_POSITION = 0;

    private final double ROTATION_DEGREE = 120; // Amount of degrees the ball launcher will rotatate up/down
    private final double ROTATION_COUNT_GS = ROTATION_DEGREE / 360; // Amount of rotations on the gearbox shaft
    private final double ROTATION_COUNT_MS = ROTATION_COUNT_GS * GEAR_RATIO; // Amount of rotations on the motor shaft
    private final double TOSS_POSITION = ROTATION_COUNT_MS * TalonConstants.REDLIN_ENCODER_TPR; // Position in ticks to turn ROTATION_DEGREE

    // Encoder constants
    private final boolean SENSOR_PHASE = true; // So that Talon does not report sensor out of phase
    private final boolean MOTOR_INVERT = false; // Which direction you want to be positive; this does not affect motor invert

    // The Talon connection state, to prevent watchdog warnings during testing
    private boolean m_talonsAreConnected = false;

    public Baller() {
        Logger.setup("Constructing Subsystem: Baller...");

        m_talonsAreConnected = Devices.isConnected(Devices.talonSrxBaller);
        if (!m_talonsAreConnected) {
            Logger.error("Baller talons not all connected! Disabling Baller...");
        }
        else {
            Devices.talonSrxBaller.configFactoryDefault();

            Devices.talonSrxHatcher.configPeakCurrentDuration(TalonConstants.PEAK_AMPERAGE_DURATION, TalonConstants.TIMEOUT_MS);
            Devices.talonSrxHatcher.configPeakCurrentLimit(TalonConstants.PEAK_AMPERAGE, TalonConstants.TIMEOUT_MS);
            Devices.talonSrxHatcher.configContinuousCurrentLimit(TalonConstants.CONTINUOUS_AMPERAGE_LIMIT, TalonConstants.TIMEOUT_MS);

            Devices.talonSrxBaller.configNominalOutputForward(0);
            Devices.talonSrxBaller.configNominalOutputReverse(0);
            Devices.talonSrxBaller.configPeakOutputForward(0.55);
            Devices.talonSrxBaller.configPeakOutputReverse(-0.1);

            Devices.talonSrxBaller.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, TalonConstants.PID_LOOP_PRIMARY, TalonConstants.TIMEOUT_MS);
            Devices.talonSrxBaller.setSensorPhase(SENSOR_PHASE);
            Devices.talonSrxBaller.setInverted(MOTOR_INVERT);
            Devices.talonSrxBaller.configAllowableClosedloopError(0, TalonConstants.PID_LOOP_PRIMARY, TalonConstants.TIMEOUT_MS);

            Devices.talonSrxBaller.config_kF(TalonConstants.PID_LOOP_PRIMARY, 0.0, TalonConstants.TIMEOUT_MS);
            Devices.talonSrxBaller.config_kP(TalonConstants.PID_LOOP_PRIMARY, 0.0125, TalonConstants.TIMEOUT_MS);
            Devices.talonSrxBaller.config_kI(TalonConstants.PID_LOOP_PRIMARY, 0.0, TalonConstants.TIMEOUT_MS);
            Devices.talonSrxBaller.config_kD(TalonConstants.PID_LOOP_PRIMARY, 0.1, TalonConstants.TIMEOUT_MS);

            // Reset Encoder Position 
            Devices.talonSrxBaller.setSelectedSensorPosition(0, 0, TalonConstants.TIMEOUT_MS);
            SensorCollection sensorCol = Devices.talonSrxBaller.getSensorCollection();
            int absolutePosition = sensorCol.getPulseWidthPosition();
            absolutePosition &= 0xFFF;
            if (SENSOR_PHASE) absolutePosition *= -1;
            if (MOTOR_INVERT) absolutePosition *= -1;
            // Set the quadrature (relative) sensor to match absolute
            Devices.talonSrxBaller.setSelectedSensorPosition(absolutePosition, TalonConstants.PID_LOOP_PRIMARY, TalonConstants.TIMEOUT_MS);
            
            Devices.talonSrxBaller.configMotionAcceleration(6000, 20);
            Devices.talonSrxBaller.configMotionCruiseVelocity(15000, 20);
        }
    }

    @Override
    public void initDefaultCommand() {
        Logger.setup("Initializing Baller DefaultCommand -> BallerStop...");

        setDefaultCommand(new BallerStop());
    }

    // Toggle the ballIsTossed state
    public void toggleBallFlipperPosition() {
        ballIsTossed = !ballIsTossed;
    }

    // Stop the Baller motor
    public void stop() {
        if (!m_talonsAreConnected) return;
        Devices.talonSrxBaller.stopMotor();
    }

    // Move the Baller flipper back to the hold position
    public void holdBall() {
        if (!m_talonsAreConnected) return;
        Logger.info("Baller -> Hold Position: " + HOLD_POSITION);
        Devices.talonSrxBaller.set(ControlMode.Position, HOLD_POSITION);
    }

    // Move the Baller flipper to toss the ball
    public void tossBall() {
        if (!m_talonsAreConnected) return;
        Logger.info("Baller -> Toss Position: " + TOSS_POSITION);
        Devices.talonSrxBaller.set(ControlMode.Position, TOSS_POSITION);
    }

    // Get the current Baller flipper motor velocity
    public int getVelocity() {
        if (!m_talonsAreConnected) return 0;
        return Devices.talonSrxBaller.getSelectedSensorVelocity();
    }

    // Get the current Baller flipper motor position
    public int getPosition() {
        if (!m_talonsAreConnected) return 0;
        return Devices.talonSrxBaller.getSelectedSensorPosition();
    }

    //---------//
    // Testing //
    //---------//

    public void testMotor() {
        if (!m_talonsAreConnected) return;
        Devices.talonSrxBaller.set(0.2);
    }

}
