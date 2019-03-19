
package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.SensorCollection;

import edu.wpi.first.wpilibj.command.Subsystem;

import frc.robot.commands.reactive.HatchClawClose;
import frc.robot.consoles.Logger;
import frc.robot.helpers.TalonConstants;
import frc.robot.Brain;
import frc.robot.Devices;


// Hatcher subsystem, for grabbing and releasing hatches
public class Hatcher extends Subsystem {

    // The public property to determine the Hatcher's claw state
    public boolean clawIsClosed = false;

    // Position constants
    // TODO: The constants that might change from the test robot to the competition robot need to be added to Shuffleboard
    private final double GEAR_RATIO = 20;
    private final double START_POSITION = 0;
    private final double OPEN_POSITION = 20; // Position in ticks to turn -rotationDegree

    // Encoder constants
    private final boolean SENSOR_PHASE = true; // So that Talon does not report sensor out of phase
    private final boolean MOTOR_INVERT = false; // Which direction you want to be positive; this does not affect motor invert

    // The Talon connection state, to prevent watchdog warnings during testing
    private boolean m_talonsAreConnected = false;

    public Hatcher() {
        Logger.setup("Constructing Subsystem: Hatcher...");

        m_talonsAreConnected = Devices.isConnected(Devices.talonSrxHatcher);
        if (!m_talonsAreConnected) {
            Logger.error("Hatcher talons not all connected! Disabling Hatcher...");
        }
        else {
            Devices.talonSrxHatcher.configFactoryDefault();

            Devices.talonSrxHatcher.configPeakCurrentDuration(TalonConstants.PEAK_AMPERAGE_DURATION, TalonConstants.TIMEOUT_MS);
            Devices.talonSrxHatcher.configPeakCurrentLimit(TalonConstants.PEAK_AMPERAGE, TalonConstants.TIMEOUT_MS);
            Devices.talonSrxHatcher.configContinuousCurrentLimit(TalonConstants.CONTINUOUS_AMPERAGE_LIMIT, TalonConstants.TIMEOUT_MS);

            Devices.talonSrxHatcher.configNominalOutputForward(0);
            Devices.talonSrxHatcher.configNominalOutputReverse(0);
            Devices.talonSrxHatcher.configPeakOutputForward(0.5);
            Devices.talonSrxHatcher.configPeakOutputReverse(-0.5);

            Devices.talonSrxHatcher.configMotionAcceleration(3000, TalonConstants.TIMEOUT_MS);
            Devices.talonSrxHatcher.configMotionCruiseVelocity(8000, TalonConstants.TIMEOUT_MS);

            // Config TalonSRX Redline encoder
            Devices.talonSrxHatcher.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, TalonConstants.PID_LOOP_PRIMARY, TalonConstants.TIMEOUT_MS);
            Devices.talonSrxHatcher.setSensorPhase(SENSOR_PHASE);
            Devices.talonSrxHatcher.setInverted(MOTOR_INVERT);
            Devices.talonSrxHatcher.configAllowableClosedloopError(0, TalonConstants.PID_LOOP_PRIMARY, TalonConstants.TIMEOUT_MS);

            Devices.talonSrxHatcher.config_kF(TalonConstants.PID_LOOP_PRIMARY, 0.0, TalonConstants.TIMEOUT_MS);
            Devices.talonSrxHatcher.config_kP(TalonConstants.PID_LOOP_PRIMARY, 0.32, TalonConstants.TIMEOUT_MS);
            Devices.talonSrxHatcher.config_kI(TalonConstants.PID_LOOP_PRIMARY, 0.0, TalonConstants.TIMEOUT_MS);
            Devices.talonSrxHatcher.config_kD(TalonConstants.PID_LOOP_PRIMARY, 0.0, TalonConstants.TIMEOUT_MS);

            // Initialize current encoder position as zero 
            Devices.talonSrxHatcher.setSelectedSensorPosition(0, TalonConstants.PID_LOOP_PRIMARY, TalonConstants.TIMEOUT_MS);
            SensorCollection sensorCol = Devices.talonSrxHatcher.getSensorCollection();
            int absolutePosition = sensorCol.getPulseWidthPosition();
            absolutePosition &= 0xFFF;
            if (SENSOR_PHASE) absolutePosition *= -1;
            if (MOTOR_INVERT) absolutePosition *= -1;
            // Set the quadrature (relative) sensor to match absolute
            Devices.talonSrxHatcher.setSelectedSensorPosition(absolutePosition, TalonConstants.PID_LOOP_PRIMARY, TalonConstants.TIMEOUT_MS);
        }
    }

    @Override
    public void initDefaultCommand() {
        Logger.setup("Initializing Hatcher DefaultCommand -> HatcherStop...");

        setDefaultCommand(new HatchClawClose());
    }

    // Toggle the clawIsClosed state
    public void toggleClawPosition() {
        clawIsClosed = !clawIsClosed;
    }

    // Stop the Hatcher claw motor
    public void stop() {
        if (!m_talonsAreConnected) return;
        Devices.talonSrxHatcher.stopMotor();
    }

    // Open the Hatcher claw
    public void openClaw() {
        if (!m_talonsAreConnected) return;
        double openPositionDegrees = OPEN_POSITION;
        double openPositionTicks = TalonConstants.translateDegreesToTicks(openPositionDegrees, GEAR_RATIO);
        Logger.info("Hatcher -> Open Position: " + openPositionTicks);
        Devices.talonSrxHatcher.set(ControlMode.MotionMagic, openPositionTicks);
    }

    // Close the Hatcher claw
    public void closeClaw() {
        if (!m_talonsAreConnected) return;
        double closePositionDegrees = Brain.getHatchRotationDegree(); // Amount of degrees the hatch claw will open/close
        double closePositionTicks = TalonConstants.translateDegreesToTicks(closePositionDegrees, GEAR_RATIO);
        Logger.info("Hatcher -> Close Position: " + closePositionTicks);
        Devices.talonSrxHatcher.set(ControlMode.MotionMagic, closePositionTicks);
    }

    // Get the current Hatcher claw motor velocity
    public int getVelocity() {
        if (!m_talonsAreConnected) return 0;
        return Devices.talonSrxHatcher.getSelectedSensorVelocity();
    }

    // Get the current Hatcher claw motor position
    public int getPosition() {
        if (!m_talonsAreConnected) return 0;
        return Devices.talonSrxHatcher.getSelectedSensorPosition();
    }

    //---------//
    // Testing //
    //---------//

    public void testMotor() {
        if (!m_talonsAreConnected) return;
        Devices.talonSrxHatcher.set(0.2);
    }

}