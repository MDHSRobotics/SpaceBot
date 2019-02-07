
package frc.robot.commands.idle;

import edu.wpi.first.wpilibj.command.Command;

import frc.robot.helpers.Logger;
import frc.robot.Robot;


// This command stops the Hatcher motor
public class HatcherStop extends Command {

    public HatcherStop() {
        Logger.debug("Constructing Command: HatcherStop...");

        // Declare subsystem dependencies
        requires(Robot.robotHatcher);
    }

    @Override
    protected void initialize() {
        Logger.debug("Initializing Command: HatcherStop...");
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
        Logger.debug("Ending Command: HatcherStop...");

        Robot.robotHatcher.stop();
    }

    @Override
    protected void interrupted() {
        Logger.debug("Interrupting Command: HatcherStop...");

        Robot.robotHatcher.stop();
    }

}
