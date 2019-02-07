
package frc.robot.commands.auto;

import edu.wpi.first.wpilibj.command.Command;

import frc.robot.helpers.Logger;
import frc.robot.Robot;


// This command opens the Hatcher claw to grab the hatch
public class HatchGrab extends Command {

    public HatchGrab() {
        Logger.debug("Constructing Command: HatchGrab...");

         // Declare subsystem dependencies
         requires(Robot.robotHatcher);
    }

    @Override
    protected void initialize() {
        Logger.debug("Constructing Command: HatchGrab...");
    }

    @Override
    protected void execute() {
        Robot.robotHatcher.grab();
    }

    // This command is finished when the hatch is grabbed
    @Override
    protected boolean isFinished() {
        boolean isGrabbed = Robot.robotHatcher.isGrabbed();
        return isGrabbed;
    }

    @Override
    protected void end() {
        Logger.debug("Ending Command: HatchGrab...");

        Robot.robotHatcher.stop();
    }

    @Override
    protected void interrupted() {
        Logger.debug("Interrupted Command: HatchGrab...");

        Robot.robotHatcher.stop();
    }

}