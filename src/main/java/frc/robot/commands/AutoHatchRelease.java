
package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;

import frc.robot.helpers.Logger;
import frc.robot.Robot;


// This command opens the Hatcher claw to release the hatch
public class AutoHatchRelease extends Command {

    public AutoHatchRelease() {
        Logger.debug("Constructing AutoHatchRelease...");

         // Declare subsystem dependencies
         requires(Robot.robotHatcher);
    }

    @Override
    protected void initialize() {
        Logger.debug("Constructing AutoHatchRelease...");
    }

    @Override
    protected void execute() {
        Robot.robotHatcher.release();
    }

    // This command is finished when the Hatch is released
    @Override
    protected boolean isFinished() {
        boolean isReleased = Robot.robotHatcher.isReleased();
        return isReleased;
    }

    @Override
    protected void end() {
        Logger.debug("Ending AutoHatchRelease...");

        Robot.robotHatcher.stop();
    }

    @Override
    protected void interrupted() {
        Logger.debug("Interrupted AutoHatchRelease...");

        Robot.robotHatcher.stop();
    }

}
