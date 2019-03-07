
package frc.robot.commands.auto;

import edu.wpi.first.wpilibj.command.Command;

import frc.robot.consoles.Logger;
import frc.robot.Robot;


// This command moves the Baller to hold the cargo ball until it is ready to be tossed
public class BallHold extends Command {

    public BallHold() {
        Logger.setup("Constructing Command: BallHold...");

        // Declare subsystem dependencies
        requires(Robot.robotBaller);
    }

    @Override
    protected void initialize() {
        Logger.action("Initializing Command: BallHold...");

        Robot.robotBaller.holdBall();
    }

    @Override
    protected void execute() {
        // TODO: Comment out Logger output once this is determined to be working reliably.
        //       Excess logging during executes can slow things down and spam the log.
        int position = Robot.robotBaller.getPosition();
        int velocity = Robot.robotBaller.getVelocity();
        Logger.info("BallHold -> Position: " + position + "; Velocity: " + velocity);
    }

    // This command is finished when the ball has been fully blocked
    @Override
    protected boolean isFinished() {
        return Robot.robotBaller.isHoldPositionMet();
    }

    @Override
    protected void end() {
        Logger.ending("Ending Command: BallHold...");

        Robot.robotBaller.stop();
    }

    @Override
    protected void interrupted() {
        Logger.ending("Interrupting Command: BallHold...");

        Robot.robotBaller.stop();
    }

}
