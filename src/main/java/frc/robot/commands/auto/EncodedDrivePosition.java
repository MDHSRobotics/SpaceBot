
package frc.robot.commands.auto;

import edu.wpi.first.wpilibj.command.Command;

import frc.robot.helpers.Logger;
import frc.robot.Robot;


// This command auto drives with encoders to a set position
public class EncodedDrivePosition extends Command {

    public EncodedDrivePosition() {
        Logger.debug("Constructing Command: EncodedDrivePosition...");

        // Declare subsystem dependencies
        requires(Robot.robotEncodedDriver);
    }

    @Override
    protected void initialize() {
        Logger.debug("Initializing Command: EncodedDrivePosition...");

        Robot.robotEncodedDriver.resetEncoderPosition();
    }

    @Override
    protected void execute() {
        Robot.robotEncodedDriver.driveRotations(4096);
        // Logger.debug("Position: " + Robot.robotEncoderDrive.getPosition());
        // Logger.debug("Velocity: " + Robot.robotEncoderDrive.getVelocity());
    }

    @Override
    protected boolean isFinished() {
        // boolean isPositionMet = EncodedDriver.isPositionMet();
        // if (isPositionMet) {
        //     return true;
        // }
        // else {
            return false;
        // }
    }

    @Override
    protected void end() {
        Logger.debug("Ending EncodedDrivePosition...");

        Robot.robotEncodedDriver.stop();
    }

    @Override
    protected void interrupted() {
        Logger.debug("Interrupting EncodedDrivePosition...");

        Robot.robotEncodedDriver.stop();
    }

}
