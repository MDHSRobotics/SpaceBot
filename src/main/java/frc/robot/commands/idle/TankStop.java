
package frc.robot.commands.idle;

import edu.wpi.first.wpilibj.command.Command;

import frc.robot.helpers.Logger;
import frc.robot.Robot;


// This command stops the Tank motor
public class TankStop extends Command {

    public TankStop() {
        Logger.debug("Constructing Command: TankStop...");

        // Declare subsystem dependencies
        requires(Robot.robotTank);
    }

    @Override
    protected void initialize() {
        Logger.debug("Initializing Command: TankStop...");
    }

    @Override
    protected void execute() {
        Robot.robotTank.stop();
    }

    // This command continues until interrupted
    @Override
    protected boolean isFinished() {
        return false;
    }

    @Override
    protected void end() {
        Logger.debug("Ending Command: TankStop...");

        Robot.robotTank.stop();
    }

    @Override
    protected void interrupted() {
        Logger.debug("Interrupting Command: TankStop...");

        Robot.robotTank.stop();
    }

}
