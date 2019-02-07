
package frc.robot.commands.auto;

import edu.wpi.first.wpilibj.command.Command;

import frc.robot.helpers.Logger;
import frc.robot.Robot;


// This command holds to cargo ball until it is ready to be tossed
public class BallHold extends Command {

    public BallHold() {
        Logger.debug("Constructing Command: BallHold...");

        // Declare subsystem dependencies
        requires(Robot.robotBaller);
    }

    @Override
    protected void initialize() {
        Logger.debug("Initializing Command: BallHold...");
    }

    @Override
    protected void execute() {
        Robot.robotBaller.holdBall();
    }

    // This command is finished when the ball has been fully blocked
    @Override
    protected boolean isFinished() {
        boolean isHeld = Robot.robotBaller.isHeld();
        return isHeld;
    }

    @Override
    protected void end() {
        Logger.debug("Ending Command: BallHold...");

        Robot.robotBaller.stop();
    }

    @Override
    protected void interrupted() {
        Logger.debug("Interrupted Command: BallHold...");

        Robot.robotBaller.stop();
    }

}
