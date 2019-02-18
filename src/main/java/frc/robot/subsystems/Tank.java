
package frc.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;

import frc.robot.commands.interactive.TankSpin;
import frc.robot.helpers.Logger;
import frc.robot.Devices;


// Tank subsystem for climbing
public class Tank extends Subsystem {

    // Motor variables
    private double m_secondsFromNeutralToFull = 0;
    private int m_timeoutMS = 10;

    // The Talon connection state, to prevent watchdog warnings during testing
    private boolean m_talonsAreConnected = false;

    public Tank() {
        Logger.debug("Constructing Subsystem: Tank...");

        m_talonsAreConnected = Devices.isConnected(Devices.talonSrxTank);
        if (m_talonsAreConnected) {
            Devices.talonSrxTank.configOpenloopRamp(m_secondsFromNeutralToFull, m_timeoutMS);
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
