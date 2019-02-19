
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
        Logger.info("BallHold -> Position: " + Robot.robotBaller.getPosition());
        Logger.info("BallHold -> Velocity: " +  Robot.robotBaller.getVelocity());
    }

    // This command is finished when the ball has been fully blocked
    @Override
    protected boolean isFinished() {
        // TODO: If this command never finishes, we can never use it in a CommandGroup to automate complex actions
        // Also, it will spam the log with Position and Velocity long after the action is complete
        // return false;
        return Robot.robotHatcher.isPositionMet();
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
