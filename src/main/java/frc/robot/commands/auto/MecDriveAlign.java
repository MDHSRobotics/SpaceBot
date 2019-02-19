
package frc.robot.commands.auto;

import edu.wpi.first.wpilibj.command.Command;

import frc.robot.consoles.Logger;
import frc.robot.Devices;
import frc.robot.OI;
import frc.robot.Robot;


// Automatically control the MecDrive to align the Robot with the gyro, and the line seen by the vision system
public class MecDriveAlign extends Command {

    private int m_targetAngle = 0;

    public MecDriveAlign() {
        Logger.setup("Constructing Command: MecDriveAlign...");

        // Declare subsystem dependencies
        requires(Robot.robotMecDriver);
    }

    @Override
    protected void initialize() {
        Logger.action("Initializing Command: MecDriveAlign...");

        m_targetAngle = OI.getDpadAngle();
    }

    @Override
    protected void execute() {
        if (m_targetAngle == -1) {
            Logger.warning("MecDriveAlign -> Missed the DPad button press!");
            Robot.robotMecDriver.stop();
            return;
        }

        double zRotation = 0;
        double angle = Devices.imuMecDrive.getAngleZ();
        double correction = m_targetAngle - angle;
        if (correction < -5 || 5 < correction) {
            if (correction > 180) correction = correction - 360;
            if (correction < -180) correction = correction + 360;
            zRotation = correction/180;
            if (zRotation < .1) zRotation = .1;
            Logger.info("MecDriveAlign -> Target Angle: " + m_targetAngle + "; Gyro Angle: " + angle + "; Correction: " + correction + "; Rotate Speed: " + zRotation);
        }

        double ySpeed = 0;
        if (-45 < correction && correction < 45) {
            boolean detected = Robot.robotLineDetectorFront.lineDetected();
            if (detected) {
                boolean centered = Robot.robotLineDetectorFront.isCentered();
                if (!centered) {
                    double imageX = Robot.robotLineDetectorFront.getCorrectedX();
                    ySpeed = .25;
                    if (imageX < 0) {
                        ySpeed = -ySpeed;
                    }
                    Logger.info("MecDriveAlign -> X pixels to correct: " + imageX + "; Strafe: " + ySpeed);
                }
            }
        }

        // Use the correction values to align to the gyro and line detector
        Robot.robotMecDriver.driveCartesian(ySpeed, 0, zRotation);
    }

    // We're finished when the line looks straight and is centered enough (or a line is not detected)
    @Override
    protected boolean isFinished() {
        if (m_targetAngle == -1) return true;

        boolean straight = Robot.robotMecDriver.isAlignedWithGyro(m_targetAngle);
        if (!straight) return false;

        boolean detected = Robot.robotLineDetectorFront.lineDetected();
        if (!detected) {
            Logger.info("MecDriveAlign -> Line lost!");
            return true;
        }

        boolean centered = Robot.robotLineDetectorFront.isCentered();
        if (!centered) return false;

        Logger.info("MecDriveAlign -> Aligned!");
        return true;
    }

    @Override
    protected void end() {
        Logger.ending("Ending Command: MecDriveAlign...");

        Robot.robotMecDriver.stop();
    }

    @Override
    protected void interrupted() {
        Logger.ending("Interrupting Command: MecDriveAlign...");

        Robot.robotMecDriver.stop();
    }

}
