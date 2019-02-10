
package frc.robot.commands.auto;

import edu.wpi.first.wpilibj.command.Command;

import frc.robot.helpers.Logger;
import frc.robot.Robot;


// This command spins the Tank
public class TankSpin extends Command {

    private double m_spin = .5;

    public TankSpin() {
        Logger.debug("Constructing Command: TankSpin...");

        // Declare subsystem dependencies
        requires(Robot.robotTank);
    }

    @Override
    protected void initialize() {
        Logger.debug("Initializing Command: TankSpin...");
    }

    @Override
    protected void execute() {
        Robot.robotTank.spin(m_spin);
    }

    @Override
    protected boolean isFinished() {
        // TODO: should this not continue until something interrupts it?
        // Or is it controlled by a held joystick or xbox controller button?

        return true;
    }

    @Override
    protected void end() {
        Logger.debug("Ending Command: TankSpin...");

        Robot.robotTank.stop();
    }

    @Override
    protected void interrupted() {
        Logger.debug("Interrupted Command: TankSpin...");

        Robot.robotTank.stop();
    }

}
