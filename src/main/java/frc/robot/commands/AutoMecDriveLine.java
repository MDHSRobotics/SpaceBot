
package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;

import frc.robot.helpers.Logger;
import frc.robot.Robot;


// Automatically control the MecDrive to align with a line seen by the vision system.
public class AutoMecDriveLine extends Command {

    private double m_zSpeed = .3;
    private double m_xSpeed = .3;

    public AutoMecDriveLine() {
        Logger.debug("Constructing AutoMecDriveLine...");

        // Declare subsystem dependencies
        requires(Robot.robotMecDriver);
    }

    @Override
    protected void initialize() {
        Logger.debug("Initializing AutoMecDriveLine...");
    }

    @Override
    protected void execute() {
        boolean detected = Robot.robotLineDetector.lineDetected();
        if (!detected) {
            Logger.debug("Line not detected!");
            return;
        }

        double angle = Robot.robotLineDetector.getCurrentAngle();
        boolean straight = Robot.robotLineDetector.isStraight(angle);
        if (!straight) {
            double z = Robot.robotLineDetector.getCorrectedZ();
            Logger.debug("Pivot angle to correct: " + z);
            double zSpeed = m_zSpeed;
            if (z > 0) {
                zSpeed = -zSpeed;
            }
            Robot.robotMecDriver.pivot(zSpeed);
            return;
        }

        double centerX = Robot.robotLineDetector.getCurrentCenterX();
        boolean centered = Robot.robotLineDetector.isCentered(centerX);
        if (!centered) {
            double x = Robot.robotLineDetector.getCorrectedX();
            Logger.debug("Strafe pixels to correct: " + x);
            double xSpeed = m_xSpeed;
            if (x > 0) {
                xSpeed = -xSpeed;
            }
            Robot.robotMecDriver.strafe(xSpeed);
            return;
        }
    }

    // We're finished when the line looks straight and is centered enough (or a line is not detected)
    @Override
    protected boolean isFinished() {
        boolean detected = Robot.robotLineDetector.lineDetected();
        if (!detected) return true;

        boolean straight = Robot.robotLineDetector.isStraight();
        if (!straight) return false;

        boolean centered = Robot.robotLineDetector.isCentered();
        if (!centered) return false;

        return true;
    }

    @Override
    protected void end() {
        Logger.debug("Ending AutoMecDriveLine...");

        Robot.robotMecDriver.stop();
    }

    @Override
    protected void interrupted() {
        Logger.debug("Interrupting AutoMecDriveLine...");

        Robot.robotMecDriver.stop();
    }

}
