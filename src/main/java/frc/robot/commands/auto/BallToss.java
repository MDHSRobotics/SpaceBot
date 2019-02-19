
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
        Logger.info("BallToss -> Position: " + Robot.robotBaller.getPosition());
        Logger.info("BallToss -> Velocity: " +  Robot.robotBaller.getVelocity());
    }

    // This command is finished when the ball has been fully tossed
    @Override
    protected boolean isFinished() {
        // TODO: If this command never finishes, we can never use it in a CommandGroup to automate complex actions
        // Also, it will spam the log with Position and Velocity long after the action is complete
        // return false;
        return Robot.robotHatcher.isPositionMet();
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
