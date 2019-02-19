
package frc.robot.commands.auto;

import edu.wpi.first.wpilibj.command.Command;

import frc.robot.consoles.Logger;
import frc.robot.Robot;


// This command closes the Hatcher claw to release the hatch
public class HatchRelease extends Command {

    public HatchRelease() {
        Logger.setup("Constructing Command: HatchRelease...");

         // Declare subsystem dependencies
         requires(Robot.robotHatcher);
    }

    @Override
    protected void initialize() {
        Logger.action("Initializing Command: HatchRelease...");

        Robot.robotHatcher.releaseHatch();
    }

    @Override
    protected void execute() {
        Logger.info("HatchRelease -> Position: " + Robot.robotHatcher.getPosition());
        Logger.info("HatchRelease -> Velocity: " + Robot.robotHatcher.getVelocity());
    }

    // This command is finished when the Hatch is released
    @Override
    protected boolean isFinished() {
        // TODO: If this command never finishes, we can never use it in a CommandGroup to automate complex actions
        // Also, it will spam the log with Position and Velocity long after the action is complete
        return Robot.robotHatcher.isPositionMet();
        //return false;
    }

    @Override
    protected void end() {
        Logger.ending("Ending Command: HatchRelease...");

        Robot.robotHatcher.stop();
    }

    @Override
    protected void interrupted() {
        Logger.ending("Interrupting Command: HatchRelease...");

        Robot.robotHatcher.stop();
    }

}
