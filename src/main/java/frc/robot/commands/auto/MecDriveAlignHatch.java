
package frc.robot.commands.auto;

import edu.wpi.first.wpilibj.command.Command;

import frc.robot.helpers.Logger;
import frc.robot.Robot;


// Automatically control the MecDrive to align the Hatcher with a line seen by the vision system.
public class MecDriveAlignHatch extends Command {

    private final double Z_SPEED = .3;
    private final double X_SPEED = .3;

    public MecDriveAlignHatch() {
        Logger.debug("Constructing Command: MecDriveAlignHatch...");

        // Declare subsystem dependencies
        requires(Robot.robotMecDriver);
    }

    @Override
    protected void initialize() {
        Logger.debug("Initializing Command: MecDriveAlignHatch...");
    }

    @Override
    protected void execute() {
        boolean detected = Robot.robotLineDetectorFront.lineDetected();
        if (!detected) {
            Logger.debug("Line not detected!");
            return;
        }

        boolean centered = Robot.robotLineDetectorFront.isCentered();
        if (!centered) {
            double x = Robot.robotLineDetectorFront.getCorrectedX();
            Logger.debug("Strafe pixels to correct: " + x);
            double xSpeed = X_SPEED;
            if (x > 0) {
                xSpeed = -xSpeed;
            }
            Robot.robotMecDriver.strafe(xSpeed);
            return;
        }

        boolean straight = Robot.robotLineDetectorFront.isStraight();
        if (!straight) {
            double z = Robot.robotLineDetectorFront.getCorrectedZ();
            Logger.debug("Pivot angle to correct: " + z);
            double zSpeed = Z_SPEED;
            if (z > 0) {
                zSpeed = -zSpeed;
            }
            Robot.robotMecDriver.pivot(zSpeed);
            return;
        }
    }

    // We're finished when the line looks straight and is centered enough (or a line is not detected)
    @Override
    protected boolean isFinished() {
        boolean detected = Robot.robotLineDetectorFront.lineDetected();
        if (!detected) return true;

        boolean centered = Robot.robotLineDetectorFront.isCentered();
        if (!centered) return false;

        boolean straight = Robot.robotLineDetectorFront.isStraight();
        if (!straight) return false;

        return true;
    }

    @Override
    protected void end() {
        Logger.debug("Ending Command: MecDriveAlignHatch...");

        Robot.robotMecDriver.stop();
    }

    @Override
    protected void interrupted() {
        Logger.debug("Interrupting Command: MecDriveAlignHatch...");

        Robot.robotMecDriver.stop();
    }

}
