
package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;

import frc.robot.helpers.Logger;
import frc.robot.Robot;


// This command idles the Baller motor
public class IdleBaller extends Command {

    public IdleBaller() {
        Logger.debug("Constructing IdleBaller...");

        // Declare subsystem dependencies
        requires(Robot.robotBaller);
    }

    @Override
    protected void initialize() {
        Logger.debug("Initializing IdleBaller...");
    }

    @Override
    protected void execute() {
        Robot.robotBaller.stop();
    }

    // This command continues until interrupted
    @Override
    protected boolean isFinished() {
        return false;
    }

    @Override
    protected void end() {
        Logger.debug("Ending IdleBaller...");

        Robot.robotBaller.stop();
    }

    @Override
    protected void interrupted() {
        Logger.debug("Interrupting IdleBaller...");

        Robot.robotBaller.stop();
    }

}
