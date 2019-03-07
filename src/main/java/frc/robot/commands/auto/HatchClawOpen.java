
package frc.robot.commands.auto;

import edu.wpi.first.wpilibj.command.Command;

import frc.robot.consoles.Logger;
import frc.robot.Robot;


// This command opens the Hatcher claw to grab the hatch
public class HatchClawOpen extends Command {

    public HatchClawOpen() {
        Logger.setup("Constructing Command: HatchGrab...");

         // Declare subsystem dependencies
         requires(Robot.robotHatcher);
    }

    @Override
    protected void initialize() {
        Logger.action("Initializing Command: HatchGrab...");

        // Set the encoded position
        Robot.robotHatcher.closeClaw();
    }

    @Override
    protected void execute() {
        // TODO: Comment out Logger output once this is determined to be working reliably.
        //       Excess logging during executes can slow things down and spam the log.
        int position = Robot.robotHatcher.getPosition();
        int velocity = Robot.robotHatcher.getVelocity();
        Logger.info("HatchGrab -> Position: " + position + "; Velocity: " + velocity);
    }

    // This command is finished when the hatch claw is closed
    @Override
    protected boolean isFinished() {
        return Robot.robotHatcher.isClosePositionMet();
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
