
package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;

import frc.robot.helpers.Logger;
import frc.robot.Robot;


// This command tosses to cargo ball into the scoring area
public class AutoBallToss extends Command {

    public AutoBallToss() {
        Logger.debug("Constructing AutoBallToss...");

        // Declare subsystem dependencies
        requires(Robot.robotBaller);
    }

    @Override
    protected void initialize() {
        Logger.debug("Initializing AutoBallToss...");
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
        Logger.debug("Ending AutoBallToss...");

        Robot.robotBaller.stop();
    }

    @Override
    protected void interrupted() {
        Logger.debug("Interrupted AutoBallToss...");

        Robot.robotBaller.stop();
    }

}
