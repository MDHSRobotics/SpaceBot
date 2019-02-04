
package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;

import frc.robot.helpers.Logger;
import frc.robot.Robot;


// This command blocks to cargo ball until it is ready to be tossed
public class AutoBallBlock extends Command {

    public AutoBallBlock() {
        Logger.debug("Constructing AutoBallBlock...");

        // Declare subsystem dependencies
        requires(Robot.robotBaller);
    }

    @Override
    protected void initialize() {
        Logger.debug("Initializing AutoBallBlock...");
    }

    @Override
    protected void execute() {
        Robot.robotBaller.blockBall();
    }

    // This command is finished when the ball has been fully blocked
    @Override
    protected boolean isFinished() {
        boolean isBlocked = Robot.robotBaller.isBlocked();
        return isBlocked;
    }

    @Override
    protected void end() {
        Logger.debug("Ending AutoBallBlock...");

        Robot.robotBaller.stop();
    }

    @Override
    protected void interrupted() {
        Logger.debug("Interrupted AutoBallBlock...");

        Robot.robotBaller.stop();
    }

}
