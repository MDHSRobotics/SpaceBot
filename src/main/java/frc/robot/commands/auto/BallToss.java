
package frc.robot.commands.auto;

import edu.wpi.first.wpilibj.command.Command;

import frc.robot.helpers.Logger;
import frc.robot.Robot;


// This command tosses to cargo ball into the scoring area
public class BallToss extends Command {

    public BallToss() {
        Logger.debug("Constructing Command: BallToss...");

        // Declare subsystem dependencies
        requires(Robot.robotBaller);
    }

    @Override
    protected void initialize() {
        Logger.debug("Initializing Command: BallToss...");

        Robot.robotBaller.tossBall();
    }

    @Override
    protected void execute() {
        Logger.debug("Position: " + Robot.robotHatcher.getPosition());
        Logger.debug("Velocity: " +  Robot.robotHatcher.getVelocity());
    }

    // This command is finished when the ball has been fully tossed
    @Override
    protected boolean isFinished() {
        return Robot.robotBaller.isPositionMet();
    }

    @Override
    protected void end() {
        Logger.debug("Ending Command: BallToss...");

        Robot.robotBaller.stop();
    }

    @Override
    protected void interrupted() {
        Logger.debug("Interrupting Command: BallToss...");

        Robot.robotBaller.stop();
    }

}
