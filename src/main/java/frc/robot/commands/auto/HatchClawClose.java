
package frc.robot.commands.auto;

import edu.wpi.first.wpilibj.command.Command;

import frc.robot.consoles.Logger;
import frc.robot.Robot;


// This command closes the Hatcher claw to release, or in preparating to grab, the hatch
public class HatchClawClose extends Command {

    public HatchClawClose() {
        Logger.setup("Constructing Command: HatchClawClose...");

         // Declare subsystem dependencies
         requires(Robot.robotHatcher);
    }

    @Override
    protected void initialize() {
        Logger.action("Initializing Command: HatchClawClose...");

        Robot.robotHatcher.stop();
    }

    @Override
    protected void execute() {
        // TODO: Comment out Logger output once this is determined to be working reliably.
        //       Excess logging during executes can slow things down and spam the log.
        int position = Robot.robotHatcher.getPosition();
        int velocity = Robot.robotHatcher.getVelocity();
        Logger.info("HatchClawClose -> Position: " + position + "; Velocity: " + velocity);
    }

    // This command currently continues until interrupted
    @Override
    protected boolean isFinished() {
        // TODO: This command never finishes, even though it's coded like an instant command.
        //       Functionally this works, because you're trying to avoid the default command, which actively stops the motor.
        //       Logically, it feels backwards. The default command should be to do nothing ("feed" the motor.)
        //       Only when the motor is trying to hold the claw closed should a different, more active command persist.
        //       Also, as it is right now, this will forever spam the log.
        return false;
    }

    @Override
    protected void end() {
        Logger.ending("Ending Command: HatchClawClose...");

        Robot.robotHatcher.stop();
    }

    @Override
    protected void interrupted() {
        Logger.ending("Interrupting Command: HatchClawClose...");

        Robot.robotHatcher.stop();
    }

}
