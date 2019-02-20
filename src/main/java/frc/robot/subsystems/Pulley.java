
package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import edu.wpi.first.wpilibj.command.Subsystem;

import frc.robot.commands.idle.PulleyStop;
import frc.robot.consoles.Logger;
import frc.robot.Devices;


// Pulley subsystem for lifting the robot onto the platform
public class Pulley extends Subsystem {

    public enum PulleyPosition {
        DOWN, UP
    }

    // The public property to determine the Pulley state
    public PulleyPosition currentPulleyPosition = PulleyPosition.DOWN;

    // Motor constants
    private final double SECONDS_FROM_NEUTRAL_TO_FULL = 0;
    private final int TIMEOUT_MS = 10;
    private final double PULLEY_SPEED = .5;

    // Encoder constants
    private final double POSITION_TOLERANCE = 0;
    private final double TARGET_POSITION_RESET = 0;
    private final boolean SENSOR_PHASE = false; // So that Talon does not report sensor out of phase
    private final boolean MOTOR_INVERT = false; // Which direction you want to be positive; this does not affect motor invert

    // The Talon connection state, to prevent watchdog warnings during testing
    private boolean m_talonsAreConnected = false;

    public Pulley() {
        Logger.setup("Constructing Subsystem: Pulley...");

        m_talonsAreConnected = Devices.isConnected(Devices.talonSrxPulleyMaster);
        if (m_talonsAreConnected) {
            Devices.talonSrxPulleyMaster.configOpenloopRamp(SECONDS_FROM_NEUTRAL_TO_FULL, TIMEOUT_MS);
        }
    }

    @Override
    public void initDefaultCommand() {
        Logger.setup("Initializing Pulley DefaultCommand -> PulleyStop...");

        setDefaultCommand(new PulleyStop());
    }

    // Stop the Pulley motor
    public void stop() {
        if (!m_talonsAreConnected) return;
        Devices.talonSrxPulleyMaster.stopMotor();
    }

    // Run the motor to lift the pulley
    public void lift() {
        if (!m_talonsAreConnected) return;
        Devices.talonSrxPulleyMaster.set(PULLEY_SPEED);
    }

     // Run the motor to lower the pulley
     public void lower() {
        if (!m_talonsAreConnected) return;
        Devices.talonSrxPulleyMaster.set(-PULLEY_SPEED);
    }

    // Get the current motor position
    public int getPosition() {
        if (!m_talonsAreConnected) return 0;
        return Devices.talonSrxPulleyMaster.getSelectedSensorPosition();
    }

    public void resetPulleyPosition() {
        if (!m_talonsAreConnected) return;
        Logger.info("Pulley -> Reset Position: " + TARGET_POSITION_RESET);
        Devices.talonSrxArm.set(ControlMode.Position, TARGET_POSITION_RESET);
    }

    public boolean isPulleyPositionResetMet() {
            if (!m_talonsAreConnected) return true;
            int currentPosition = getPosition();
            return (Math.abs(currentPosition) < POSITION_TOLERANCE);
    }
    
}
