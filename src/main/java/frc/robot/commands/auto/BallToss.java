
package frc.robot.commands.auto;

import edu.wpi.first.wpilibj.command.Command;

import frc.robot.helpers.Logger;
import frc.robot.Robot;


// This command tosses to cargo ball into the scoring area
public class BallToss extends Command {

    public BallToss() {
        Logger.debug("Constructing BallToss...");

        // Declare subsystem dependencies
        requires(Robot.robotBaller);
    }

    @Override
    protected void initialize() {
        Logger.debug("Initializing BallToss...");
    }

    @Override
    protected void execute() {
        Robot.robotBaller.tossBall();
    }

    // This command is finished when the ball has been fully tossed
    @Override
    protected boolean isFinished() {
        boolean isTossed = Robot.robotBaller.isTossed();
        return isTossed;
    }

    @Override
    protected void end() {
        Logger.debug("Ending BallToss...");

        Robot.robotBaller.stop();
    }

    @Override
    protected void interrupted() {
        Logger.debug("Interrupted BallToss...");

        Robot.robotBaller.stop();
    }

}
