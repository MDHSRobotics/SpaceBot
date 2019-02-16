
package frc.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;

import frc.robot.commands.idle.TankStop;
import frc.robot.commands.interactive.TankSpin;
import frc.robot.helpers.Logger;
import frc.robot.Devices;


// Tank subsystem for climbing
public class Tank extends Subsystem {

    private boolean m_talonsAreConnected = false;
    private double m_secondsFromNeutralToFull = 1.0;
    private int m_timeoutMS = 10;

    public Tank() {
        Logger.debug("Constructing Subsystem: Tank...");

        m_talonsAreConnected = Devices.isConnected(Devices.talonSrxTank);
        Devices.talonSrxTank.configOpenloopRamp(m_secondsFromNeutralToFull, m_timeoutMS);
    }

    @Override
    public void initDefaultCommand() {
        Logger.debug("Initializing Tank DefaultCommand -> TankStop...");
        setDefaultCommand(new TankSpin());
    }

    // Stop the Tank motor
    public void stop() {
        if (m_talonsAreConnected) {
            Devices.talonSrxTank.stopMotor();
        }
    }

    // Spins the Tank motor to climb
    public void spin(double speed) {
        if (m_talonsAreConnected) {
            Devices.talonSrxTank.set(speed);
        }
    }

}
