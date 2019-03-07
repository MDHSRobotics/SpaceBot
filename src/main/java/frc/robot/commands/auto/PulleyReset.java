
package frc.robot.commands.auto;

import edu.wpi.first.wpilibj.command.Command;

import frc.robot.consoles.Logger;
import frc.robot.Robot;


// This command resets the Pulley to its starting position
public class PulleyReset extends Command {

    public PulleyReset() {
        Logger.setup("Constructing Command: PulleyReset...");

        requires(Robot.robotPulley);
    }

    @Override
    protected void initialize() {
        Logger.action("Initializing Command: PulleyReset...");

        // Set the encoded position
        Robot.robotPulley.resetPosition();
    }

    @Override
    protected void execute() {
        // TODO: Comment out Logger output once this is determined to be working reliably.
        //       Excess logging during executes can slow things down and spam the log.
        int position = Robot.robotPulley.getPosition();
        int velocity = Robot.robotPulley.getVelocity();
        Logger.info("PulleyReset -> Position: " + position + "; Velocity: " + velocity);
    }

    // This will finish when the Pulley reaches its encoded "reset" position
    @Override
    protected boolean isFinished() {
        return Robot.robotPulley.isResetPositionMet();
    }

    @Override
    protected void end() {
        Logger.ending("Ending Command: PulleyReset...");

        Robot.robotPulley.stop();
    }

    @Override
    protected void interrupted() {
        Logger.ending("Interrupting Command: PulleyReset...");

        Robot.robotPulley.stop();
    }

}
