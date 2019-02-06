
package frc.robot.commands.idle;

import edu.wpi.first.wpilibj.command.Command;

import frc.robot.helpers.Logger;
import frc.robot.Robot;


// This command stops the Pusher motor
public class PusherStop extends Command {

    public PusherStop() {
        Logger.debug("Constructing Command: PusherStop...");

        // Declare subsystem dependencies
        requires(Robot.robotPusher);
    }

    @Override
    protected void initialize() {
        Logger.debug("Initializing Command: PusherStop...");
    }

    @Override
    protected void execute() {
        Robot.robotPusher.stop();
    }

    // This command continues until interrupted
    @Override
    protected boolean isFinished() {
        return false;
    }

    @Override
    protected void end() {
        Logger.debug("Ending Command: PusherStop...");

        Robot.robotPusher.stop();
    }

    @Override
    protected void interrupted() {
        Logger.debug("Interrupting Command: PusherStop...");

        Robot.robotPusher.stop();
    }

}
