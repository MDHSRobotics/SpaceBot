
package frc.robot.commands.auto;

import edu.wpi.first.wpilibj.command.Command;

import frc.robot.helpers.Logger;
import frc.robot.Robot;


// This command closes the Hatcher claw to release the hatch
public class HatchRelease extends Command {

    public HatchRelease() {
        Logger.debug("Constructing Command: HatchRelease...");

         // Declare subsystem dependencies
         requires(Robot.robotHatcher);
    }

    @Override
    protected void initialize() {
        Logger.debug("Initializing Command: HatchRelease...");

        Robot.robotHatcher.releaseHatch();
    }

    @Override
    protected void execute() {
        Logger.debug("Position: " + Robot.robotHatcher.getPosition());
        Logger.debug("Velocity: " + Robot.robotHatcher.getVelocity());
    }

    // This command is finished when the Hatch is released
    @Override
    protected boolean isFinished() {
        return Robot.robotHatcher.isPositionMet();
    }

    @Override
    protected void end() {
        Logger.debug("Ending Command: HatchRelease...");

        Robot.robotHatcher.stop();
    }

    @Override
    protected void interrupted() {
        Logger.debug("Interrupting Command: HatchRelease...");

        Robot.robotHatcher.stop();
    }

}
