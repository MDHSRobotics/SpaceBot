
package frc.robot.commands.idle;

import edu.wpi.first.wpilibj.command.Command;

import frc.robot.helpers.Logger;
import frc.robot.Robot;


// This command stops the Encoded Driver motors
public class EncodedDriverStop extends Command {

    public EncodedDriverStop() {
        Logger.debug("Constructing EncodedDriverStop...");

        // Declare subsystem dependencies
        requires(Robot.robotEncoderDrive);
    }

    @Override
    protected void initialize() {
        Logger.debug("Initializing EncodedDriverStop...");
    }

    @Override
    protected void execute() {
        Robot.robotEncoderDrive.stop();
    }

    // This command continues until interrupted
    @Override
    protected boolean isFinished() {
        return false;
    }

    @Override
    protected void end() {
        Logger.debug("Ending EncodedDriverStop...");

        Robot.robotEncoderDrive.stop();
    }

    @Override
    protected void interrupted() {
        Logger.debug("Interrupting EncodedDriverStop...");

        Robot.robotEncoderDrive.stop();
    }

}
