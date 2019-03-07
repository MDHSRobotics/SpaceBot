
package frc.robot.commands.auto;

import edu.wpi.first.wpilibj.command.Command;

import frc.robot.consoles.Logger;
import frc.robot.Robot;


// This command tosses to cargo ball into the scoring area
public class BallToss extends Command {

    public BallToss() {
        Logger.setup("Constructing Command: BallToss...");

        // Declare subsystem dependencies
        requires(Robot.robotBaller);
    }

    @Override
    protected void initialize() {
        Logger.action("Initializing Command: BallToss...");

        Robot.robotBaller.tossBall();
    }

    @Override
    protected void execute() {
        // TODO: Comment out Logger output once this is determined to be working reliably.
        //       Excess logging during executes can slow things down and spam the log.
        int position = Robot.robotBaller.getPosition();
        int velocity = Robot.robotBaller.getVelocity();
        Logger.info("BallToss -> Position: " + position + "; Velocity: " + velocity);
    }

    // This command is finished when the ball has been fully tossed
    @Override
    protected boolean isFinished() {
        return Robot.robotBaller.isTossPositionMet();
    }

    @Override
    protected void end() {
        Logger.ending("Ending Command: BallToss...");

        Robot.robotBaller.stop();
    }

    @Override
    protected void interrupted() {
        Logger.ending("Interrupting Command: BallToss...");

        Robot.robotBaller.stop();
    }

}
