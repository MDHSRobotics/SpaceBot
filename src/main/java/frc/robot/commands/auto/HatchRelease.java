
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
        int position = Robot.robotHatcher.getPosition();
        int velocity = Robot.robotHatcher.getVelocity();
        Logger.info("HatchRelease -> Position: " + position + "; Velocity: " + velocity);
    }

    // This command is finished when the Hatch is released
    @Override
    protected boolean isFinished() {
        return Robot.robotHatcher.isReleasePositionMet();
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
