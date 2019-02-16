
package frc.robot.commands.auto;

import edu.wpi.first.wpilibj.command.Command;

import frc.robot.helpers.Logger;
import frc.robot.Robot;


// Automatically control the MecDrive to align the Hatcher with a line seen by the vision system.
public class MecDriveAlignHatch extends Command {

    private double m_zSpeed = .3;
    private double m_xSpeed = .3;

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
        boolean detected = Robot.robotLineDetectorHatch.lineDetected();
        if (!detected) {
            Logger.debug("Line not detected!");
            return;
        }

        boolean centered = Robot.robotLineDetectorHatch.isCentered();
        if (!centered) {
            double x = Robot.robotLineDetectorHatch.getCorrectedX();
            Logger.debug("Strafe pixels to correct: " + x);
            double xSpeed = m_xSpeed;
            if (x > 0) {
                xSpeed = -xSpeed;
            }
            Robot.robotMecDriver.strafe(xSpeed);
            return;
        }

        boolean straight = Robot.robotLineDetectorHatch.isStraight();
        if (!straight) {
            double z = Robot.robotLineDetectorHatch.getCorrectedZ();
            Logger.debug("Pivot angle to correct: " + z);
            double zSpeed = m_zSpeed;
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
        boolean detected = Robot.robotLineDetectorHatch.lineDetected();
        if (!detected) return true;

        boolean straight = Robot.robotLineDetectorHatch.isStraight();
        if (!straight) return false;

        boolean centered = Robot.robotLineDetectorHatch.isCentered();
        if (!centered) return false;

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
