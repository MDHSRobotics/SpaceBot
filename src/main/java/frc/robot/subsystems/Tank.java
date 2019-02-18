
package frc.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;

import frc.robot.commands.interactive.TankSpin;
import frc.robot.helpers.Logger;
import frc.robot.Devices;


// Tank subsystem for climbing
public class Tank extends Subsystem {

    // Motor constants
    private final double SECONDS_FROM_NEUTRAL_TO_FULL = 0;
    private final int TIMEOUT_MS = 10;

    // The Talon connection state, to prevent watchdog warnings during testing
    private boolean m_talonsAreConnected = false;

    public Tank() {
        Logger.debug("Constructing Subsystem: Tank...");

        m_talonsAreConnected = Devices.isConnected(Devices.talonSrxTank);
        if (m_talonsAreConnected) {
            Devices.talonSrxTank.configOpenloopRamp(SECONDS_FROM_NEUTRAL_TO_FULL, TIMEOUT_MS);
        }
    }

    @Override
    public void initDefaultCommand() {
        Logger.debug("Initializing Tank DefaultCommand -> TankSpin...");

        setDefaultCommand(new TankSpin());
    }

    // Stop the Tank motor
    public void stop() {
        if (!m_talonsAreConnected) return;
        Devices.talonSrxTank.stopMotor();
    }

    // Spins the Tank motor to climb
    public void spin(double speed) {
        if (!m_talonsAreConnected) return;
        Devices.talonSrxTank.set(speed);
    }

}
