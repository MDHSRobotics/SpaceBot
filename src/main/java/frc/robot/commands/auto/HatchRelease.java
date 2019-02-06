
package frc.robot.commands.auto;

import edu.wpi.first.wpilibj.command.Command;

import frc.robot.helpers.Logger;
import frc.robot.Robot;


// This command closes the Hatcher claw to release the hatch
public class HatchRelease extends Command {

    public HatchRelease() {
        Logger.debug("Constructing HatchRelease...");

         // Declare subsystem dependencies
         requires(Robot.robotHatcher);
    }

    @Override
    protected void initialize() {
        Logger.debug("Constructing HatchRelease...");
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
        Logger.debug("Ending HatchRelease...");

        Robot.robotHatcher.stop();
    }

    @Override
    protected void interrupted() {
        Logger.debug("Interrupted HatchRelease...");

        Robot.robotHatcher.stop();
    }

}
