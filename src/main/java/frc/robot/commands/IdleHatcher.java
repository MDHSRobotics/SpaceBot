
package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;

import frc.robot.helpers.Logger;
import frc.robot.Robot;


// This command idles the Hatcher motor
public class IdleHatcher extends Command {

    public IdleHatcher() {
        Logger.debug("Constructing IdleHatcher...");

        // Declare subsystem dependencies
        requires(Robot.robotHatcher);
    }

    @Override
    protected void initialize() {
        Logger.debug("Initializing IdleHatcher...");
    }

    @Override
    protected void execute() {
        Robot.robotHatcher.stop();
    }

    // This command continues until interrupted
    @Override
    protected boolean isFinished() {
        return false;
    }

    @Override
    protected void end() {
        Logger.debug("Ending IdleHatcher...");

        Robot.robotHatcher.stop();
    }

    @Override
    protected void interrupted() {
        Logger.debug("Interrupting IdleHatcher...");

        Robot.robotHatcher.stop();
    }

}
