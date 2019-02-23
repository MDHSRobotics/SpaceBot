
package frc.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;

import frc.robot.commands.interactive.MecDriveCartesian;
import frc.robot.consoles.Logger;
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
    private double ALIGN_ANGLE_THRESHOLD = 10;

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
        if (m_talonsAreConnected) {
            Devices.mecDrive.stopMotor();
        }
        else {
            Devices.mecDrive.feed();
        }
    }

    // Strafe at the given speed
    public void strafe(double speed) {
        if (m_talonsAreConnected) {
            Devices.mecDrive.driveCartesian(speed, 0, 0);
        }
        else {
            Devices.mecDrive.feed();
        }
    }

    // Drive straight at the given speed
    public void driveStraight(double speed) {
        if (m_talonsAreConnected) {
            Devices.mecDrive.driveCartesian(0, speed, 0);
        }
        else {
            Devices.mecDrive.feed();
        }
    }

    // Rotate at the given speed
    public void rotate(double speed) {
        if (m_talonsAreConnected) {
            Devices.mecDrive.driveCartesian(0, 0, speed);
        }
        else {
            Devices.mecDrive.feed();
        }
    }

    // Drive using the cartesian method, using the current control orientation
    public void driveCartesian(double ySpeed, double xSpeed, double zRotation) {
        DriveOrientation orientation = Brain.getDriveOrientation();
        driveCartesian(ySpeed, xSpeed, zRotation, orientation);
    }

    // Drive using the cartesian method, using the given control orientation
    public void driveCartesian(double ySpeed, double xSpeed, double zRotation, DriveOrientation orientation) {
        if (m_talonsAreConnected) {
            if (orientation == DriveOrientation.ROBOT) {
                // Logger.info("Cartesian Movement: " + ySpeed + ", " + xSpeed + ", " + zRotation);
                Devices.mecDrive.driveCartesian(ySpeed, xSpeed, zRotation);
            }
            else if (orientation == DriveOrientation.FIELD) {
                double gyroAngle = Devices.imuMecDrive.getAngleZ();
                // Logger.info("Cartesian Movement: " + ySpeed + ", " + xSpeed + ", " + zRotation + ", " + gyroAngle);
                Devices.mecDrive.driveCartesian(ySpeed, xSpeed, zRotation, gyroAngle);
            }
        }
        else {
            Devices.mecDrive.feed();
        }
    }

    // Drive using the polar method
    public void drivePolar(double magnitude, double angle, double rotation) {
        if (m_talonsAreConnected) {
            // Logger.info("Polar Movement: " + magnitude + ", " + angle + ", " + rotation);
            Devices.mecDrive.drivePolar(magnitude, angle, rotation);
        }
        else {
            Devices.mecDrive.feed();
        }
    }

    // Returns true if the gyro Z angle matches the target angle within the ALIGN_ANGLE_THRESHOLD
    public boolean isAlignedWithGyro(int targetAngle) {
        double angle = Devices.imuMecDrive.getYaw();
        double difference = Math.abs(targetAngle - angle);
        if (difference > 180) difference = 360 - difference;
        boolean aligned = (difference <= ALIGN_ANGLE_THRESHOLD);
        return aligned;
    }

}
