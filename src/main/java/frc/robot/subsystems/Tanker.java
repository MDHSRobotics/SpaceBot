
package frc.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;

import frc.robot.commands.IdleTanker;
import frc.robot.helpers.Logger;
import frc.robot.Devices;


// Tanker subsystem for climbing
public class Tanker extends Subsystem {

    private double m_secondsFromNeutralToFull = 1.0;
    private int m_timeoutMS = 10;

    public Tanker() {
        Logger.debug("Constructing Tanker...");

        Devices.talonSrxClimbTank.configOpenloopRamp(m_secondsFromNeutralToFull, m_timeoutMS);
    }

    @Override
    public void initDefaultCommand() {
        Logger.debug("Initializing Tanker default command...");

        IdleTanker defaultCmd = new IdleTanker();
        setDefaultCommand(defaultCmd);
    }

    // Stop the tanker motor
    public void stop() {
        Devices.talonSrxClimbTank.stopMotor();
    }

    // Spins the tanker motor to climb
    public void climb(double power) {
        // TODO: name this variable "speed" instead of "power".

        Devices.talonSrxClimbTank.set(power);
    }

}
