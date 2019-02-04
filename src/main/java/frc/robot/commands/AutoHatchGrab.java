
package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;

import frc.robot.helpers.Logger;
import frc.robot.Robot;


// This command opens the Hatcher claw to grab the hatch
public class AutoHatchGrab extends Command {

    public AutoHatchGrab() {
        Logger.debug("Constructing AutoHatchGrab...");

         // Declare subsystem dependencies
         requires(Robot.robotHatcher);
    }

    @Override
    protected void initialize() {
        Logger.debug("Constructing AutoHatchGrab...");
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
        Logger.debug("Ending AutoHatchGrab...");

        Robot.robotHatcher.stop();
    }

    @Override
    protected void interrupted() {
        Logger.debug("Interrupted AutoHatchGrab...");

        Robot.robotHatcher.stop();
    }

}
