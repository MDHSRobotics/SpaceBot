
package frc.robot.commands.auto;

import edu.wpi.first.wpilibj.command.Command;

import frc.robot.consoles.Logger;
import frc.robot.Robot;


// This command opens the Hatcher claw to grab the hatch
public class HatchGrab extends Command {

    public HatchGrab() {
        Logger.setup("Constructing Command: HatchGrab...");

         // Declare subsystem dependencies
         requires(Robot.robotHatcher);
    }

    @Override
    protected void initialize() {
        Logger.action("Initializing Command: HatchGrab...");

        Robot.robotHatcher.grabHatch();
    }

    @Override
    protected void execute() {
        Logger.info("HatchGrab -> Position: " + Robot.robotHatcher.getPosition());
        Logger.info("HatchGrab -> Velocity: " + Robot.robotHatcher.getVelocity());
    }

    // This command is finished when the hatch is grabbed
    @Override
    protected boolean isFinished() {
        // TODO: If this command never finishes, we can never use it in a CommandGroup to automate complex actions
        // Also, it will spam the log with Position and Velocity long after the action is complete
        return Robot.robotHatcher.isPositionMet();
        // return false;
    }

    @Override
    protected void end() {
        Logger.ending("Ending Command: HatchGrab...");

        Robot.robotHatcher.stop();
    }

    @Override
    protected void interrupted() {
        Logger.ending("Interrupting Command: HatchGrab...");

        Robot.robotHatcher.stop();
    }

}
