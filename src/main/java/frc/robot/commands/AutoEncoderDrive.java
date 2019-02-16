
package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.helpers.Logger;
import frc.robot.Robot;


// This command auto drives with encoders
public class AutoEncoderDrive extends Command {

    public AutoEncoderDrive() {
        Logger.debug("Constructing AutoEncoderDrive...");

        // Declare subsystem dependencies
        requires(Robot.robotEncodedDriver);
    }

    @Override
    protected void initialize() {
        Logger.debug("Initializing AutoEncoderDrive...");

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
        Logger.debug("Ending AutoEncoderDrive...");

        Robot.robotEncodedDriver.stop();
    }

    @Override
    protected void interrupted() {
        Logger.debug("Interrupting AutoEncoderDrive...");

        Robot.robotEncodedDriver.stop();
    }

}
