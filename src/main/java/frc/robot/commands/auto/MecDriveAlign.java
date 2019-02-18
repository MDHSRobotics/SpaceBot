
package frc.robot.commands.auto;

import edu.wpi.first.wpilibj.command.Command;

import frc.robot.helpers.Logger;
import frc.robot.Devices;
import frc.robot.OI;
import frc.robot.Robot;


// Automatically control the MecDrive to align the Robot with the gyro, and the line seen by the vision system
public class MecDriveAlign extends Command {

    private final double X_SPEED = .3;

    private int m_targetAngle = 0;

    public MecDriveAlign() {
        Logger.debug("Constructing Command: MecDriveAlignHatch...");

        // Declare subsystem dependencies
        requires(Robot.robotMecDriver);
    }

    @Override
    protected void initialize() {
        Logger.debug("Initializing Command: MecDriveAlignHatch...");

        m_targetAngle = OI.getDpadAngle();
    }

    @Override
    protected void execute() {
        if (m_targetAngle == -1) return;

        double angle = Devices.imuMecDrive.getAngleZ();
        double speed = m_targetAngle - angle;
        Robot.robotMecDriver.pivot(speed);

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
    }

    // We're finished when the line looks straight and is centered enough (or a line is not detected)
    @Override
    protected boolean isFinished() {
        if (m_targetAngle == -1) return true;

        boolean straight = Robot.robotMecDriver.isAlignedWithGyro(m_targetAngle);
        if (!straight) return false;

        boolean detected = Robot.robotLineDetectorFront.lineDetected();
        if (!detected) return true;

        boolean centered = Robot.robotLineDetectorFront.isCentered();
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
