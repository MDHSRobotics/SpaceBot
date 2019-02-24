
package frc.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;

import frc.robot.commands.interactive.MecDriveCartesian;
import frc.robot.consoles.Logger;
import frc.robot.sensors.Gyro;
import frc.robot.sensors.Vision;
import frc.robot.Brain;
import frc.robot.Devices;


// Mecanum driver subsystem
public class MecDriver extends Subsystem {

    public enum DriveOrientation {
        ROBOT, FIELD
    }

    // The direction of forward/backward via the controller
    public boolean controlStickDirectionFlipped = false;

    // Motor constants
    private final double SECONDS_FROM_NEUTRAL_TO_FULL = 0;
    private final int TIMEOUT_MS = 10;

    // Alignment Constants
    private double ALIGN_SPEED_SENSITIVITY = .5;
    private double ALIGN_SPEED_MINIMUM = .1;

    // The Talon connection state, to prevent watchdog warnings during testing
    private boolean m_talonsAreConnected = false;

    public MecDriver() {
        Logger.setup("Constructing Subsystem: MecDriver...");

        // Configure wheel speed controllers
        boolean talonFrontLeftIsConnected = Devices.isConnected(Devices.talonSrxMecWheelFrontLeft);
        boolean talonRearLeftIsConnected = Devices.isConnected(Devices.talonSrxMecWheelRearLeft);
        boolean talonFrontRightIsConnected = Devices.isConnected(Devices.talonSrxMecWheelFrontRight);
        boolean talonRearRightIsConnected = Devices.isConnected(Devices.talonSrxMecWheelRearRight);
        m_talonsAreConnected = (talonFrontLeftIsConnected &&
                                talonRearLeftIsConnected && 
                                talonFrontRightIsConnected && 
                                talonRearRightIsConnected);

        if (!m_talonsAreConnected) {
            Logger.error("MecDriver talons not all connected! Disabling MecDriver...");
        }
        else {
            Devices.talonSrxMecWheelFrontLeft.configOpenloopRamp(SECONDS_FROM_NEUTRAL_TO_FULL, TIMEOUT_MS);
            Devices.talonSrxMecWheelRearLeft.configOpenloopRamp(SECONDS_FROM_NEUTRAL_TO_FULL, TIMEOUT_MS);
            Devices.talonSrxMecWheelFrontRight.configOpenloopRamp(SECONDS_FROM_NEUTRAL_TO_FULL, TIMEOUT_MS);
            Devices.talonSrxMecWheelRearRight.configOpenloopRamp(SECONDS_FROM_NEUTRAL_TO_FULL, TIMEOUT_MS);
        }
    }

    // Initialize Default Command
    @Override
    public void initDefaultCommand() {
        Logger.setup("Initializing MecDriver DefaultCommand -> MecDriveCartesian...");

        setDefaultCommand(new MecDriveCartesian());
    }

    // Flip the control direction of the joystick in Y (or Y Left for Xbox thumbsticks)
    public Boolean flipControlStickDirection() {
        Logger.action("Toggling MecDriver control stick direction...");

        controlStickDirectionFlipped = !controlStickDirectionFlipped;

        if (controlStickDirectionFlipped) {
            Logger.info("MecDriver control stick direction is now flipped.");
        }
        else {
            Logger.info("MecDriver control stick direction is now standard (not flipped).");
        }

        return controlStickDirectionFlipped;
    }

    // Toggle the drive orientation for the mecanum drive
    public DriveOrientation toggleDriveOrientation() {
        Logger.action("Toggling MecDriver drive orientation...");

        DriveOrientation orientation = Brain.getDriveOrientation();
        if (orientation == DriveOrientation.FIELD) {
            orientation = DriveOrientation.ROBOT;
            Logger.info("MecDriver drive orientation is now ROBOT.");
        }
        else if (orientation == DriveOrientation.ROBOT) {
            orientation = DriveOrientation.FIELD;
            Logger.info("MecDriver drive orientation is now FIELD.");
        }
        Brain.setDriveOrientation(orientation);

        return orientation;
    }

    // Stop all the drive motors
    public void stop() {
        if (!m_talonsAreConnected) {
            Devices.mecDrive.feed();
            return;
        }

        Devices.mecDrive.stopMotor();
    }

    // Strafe at the given speed
    public void strafe(double speed) {
        if (!m_talonsAreConnected) {
            Devices.mecDrive.feed();
            return;
        }

        Devices.mecDrive.driveCartesian(speed, 0, 0);
    }

    // Drive straight at the given speed
    public void driveStraight(double speed) {
        if (!m_talonsAreConnected) {
            Devices.mecDrive.feed();
            return;
        }

        Devices.mecDrive.driveCartesian(0, speed, 0);
    }

    // Rotate at the given speed
    public void rotate(double speed) {
        if (!m_talonsAreConnected) {
            Devices.mecDrive.feed();
            return;
        }

        Devices.mecDrive.driveCartesian(0, 0, speed);
    }

    // Drive using the cartesian method, using the current control orientation
    public void driveCartesian(double ySpeed, double xSpeed, double zRotation) {
        DriveOrientation orientation = Brain.getDriveOrientation();
        driveCartesian(ySpeed, xSpeed, zRotation, orientation);
    }

    // Drive using the cartesian method, using the given control orientation
    public void driveCartesian(double ySpeed, double xSpeed, double zRotation, DriveOrientation orientation) {
        if (!m_talonsAreConnected) {
            Devices.mecDrive.feed();
            return;
        }

        if (orientation == DriveOrientation.ROBOT) {
            // Logger.info("Cartesian Movement: " + ySpeed + ", " + xSpeed + ", " + zRotation);
            Devices.mecDrive.driveCartesian(ySpeed, xSpeed, zRotation);
        }
        else if (orientation == DriveOrientation.FIELD) {
            double gyroAngle = Devices.gyro.getYaw();
            // Logger.info("Cartesian Movement: " + ySpeed + ", " + xSpeed + ", " + zRotation + ", " + gyroAngle);
            Devices.mecDrive.driveCartesian(ySpeed, xSpeed, zRotation, gyroAngle);
        }
    }

    // Drive using the polar method
    public void drivePolar(double magnitude, double angle, double rotation) {
        if (!m_talonsAreConnected) {
            Devices.mecDrive.feed();
            return;
        }

        // Logger.info("Polar Movement: " + magnitude + ", " + angle + ", " + rotation);
        Devices.mecDrive.drivePolar(magnitude, angle, rotation);
    }

    // Drive to align to a detected line at the given target angle
    public void driveAlign(double targetAngle) {
        if (!m_talonsAreConnected) {
            Devices.mecDrive.feed();
            return;
        }

        // TODO: See which line is detected, and move based on that, such that the front camera can see the line when initial adjustment is complete

        double zRotation = 0;
        double angle = Devices.gyro.getYaw();
        double correction = targetAngle - angle;
        if (correction > 180) correction = correction - 360;
        if (correction < -180) correction = correction + 360;
        zRotation = (correction / 180) * ALIGN_SPEED_SENSITIVITY;
        if (0 < zRotation && zRotation < ALIGN_SPEED_MINIMUM) zRotation = ALIGN_SPEED_MINIMUM;
        if (0 > zRotation && zRotation > -ALIGN_SPEED_MINIMUM) zRotation = -ALIGN_SPEED_MINIMUM;
        Logger.info("MecDriver -> Target Angle: " + targetAngle + "; Gyro Angle: " + angle + "; Correction: " + correction + "; Z Rotate Speed: " + zRotation);

        double ySpeed = 0;
        if (-45 < correction && correction < 45) {
            boolean detected = Vision.lineDetected();
            if (detected) {
                boolean centered = Vision.isCentered();
                if (!centered) {
                    double imageX = Vision.getCorrectedX();
                    ySpeed = .25;
                    if (imageX < 0) {
                        ySpeed = -ySpeed;
                    }
                    Logger.info("MecDriver -> X pixels to correct: " + imageX + "; Y Strafe: " + ySpeed);
                }
            }
        }

        Devices.mecDrive.driveCartesian(ySpeed, 0, zRotation);
    }

    public boolean isAligned(double targetAngle) {
        boolean straight = Gyro.isYawAligned(targetAngle);
        if (!straight) return false;

        boolean detected = Vision.lineDetected();
        if (!detected) {
            Logger.info("MecDriver -> Robot is straight, but no line detected!");
            return true;
        }

        boolean centered = Vision.isCentered();
        if (!centered) return false;

        Logger.info("MecDriver -> Robot is straight, and centered, fully aligned!");
        return true;
    }

}
