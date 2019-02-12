
package frc.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;

import frc.robot.commands.interactive.MecDriveCartesian;
import frc.robot.helpers.Logger;
import frc.robot.Brain;
import frc.robot.Devices;


// Mecanum driver subsystem
public class MecDriver extends Subsystem {

    public enum DriveOrientation {
        ROBOT, FIELD
    }

    public boolean controlStickDirectionFlipped = false;

    private double m_secondsFromNeutralToFull = 1.0;
    private int m_timeoutMS = 10;

    // Constructor
    public MecDriver() {
        Logger.debug("Constructing Subsystem: MecDriver...");

        // Configure wheel speed controllers
        if (Devices.isConnected(Devices.talonSrxMecWheelFrontLeft)) {
            Devices.talonSrxMecWheelFrontLeft.configOpenloopRamp(m_secondsFromNeutralToFull, m_timeoutMS);
        }
        if (Devices.isConnected(Devices.talonSrxMecWheelRearLeft)) {
            Devices.talonSrxMecWheelRearLeft.configOpenloopRamp(m_secondsFromNeutralToFull, m_timeoutMS);
        }
        if (Devices.isConnected(Devices.talonSrxMecWheelFrontRight)) {
            Devices.talonSrxMecWheelFrontRight.configOpenloopRamp(m_secondsFromNeutralToFull, m_timeoutMS);
        }
        if (Devices.isConnected(Devices.talonSrxMecWheelRearRight)) {
            Devices.talonSrxMecWheelRearRight.configOpenloopRamp(m_secondsFromNeutralToFull, m_timeoutMS);
        }
    }

    // Initialize Default Command
    @Override
    public void initDefaultCommand() {
        Logger.debug("Initializing MecDriver DefaultCommand -> MecDriveCartesian...");

        setDefaultCommand(new MecDriveCartesian());
    }

    // Flip the control direction of the joystick in Y (or Y Left for Xbox thumbsticks)
    public Boolean flipControlStickDirection() {
        Logger.debug("Toggling MecDriver control stick direction...");

        controlStickDirectionFlipped = !controlStickDirectionFlipped;

        if (controlStickDirectionFlipped) {
            Logger.debug("MecDriver control stick direction is now flipped.");
        }
        else {
            Logger.debug("MecDriver control stick direction is now standard (not flipped).");
        }

        return controlStickDirectionFlipped;
    }

    // Toggle the drive orientation for the mecanum drive
    public DriveOrientation toggleDriveOrientation() {
        Logger.debug("Toggling MecDriver drive orientation...");

        DriveOrientation orientation = Brain.getDriveOrientation();
        if (orientation == DriveOrientation.FIELD) {
            orientation = DriveOrientation.ROBOT;
            Logger.debug("MecDriver drive orientation is now ROBOT.");
        }
        else if (orientation == DriveOrientation.ROBOT) {
            orientation = DriveOrientation.FIELD;
            Logger.debug("MecDriver drive orientation is now FIELD.");
        }
        Brain.setDriveOrientation(orientation);

        return orientation;
    }

    // Stop all the drive motors
    public void stop() {
        Devices.mecDrive.stopMotor();
    }

    // Drive straight at the given speed
    public void driveStraight(double speed) {
        Devices.mecDrive.driveCartesian(speed, 0, 0);
    }

    // Pivot at the given speed
    public void pivot(double speed) {
        Devices.mecDrive.driveCartesian(0, speed, 0);
    }

    // Strafe at the given speed
    public void strafe(double speed) {
        Devices.mecDrive.driveCartesian(0, 0, speed);
    }

    // Drive using the cartesian method, using the current control orientation
    public void driveCartesian(double ySpeed, double xSpeed, double zRotation) {
        DriveOrientation orientation = Brain.getDriveOrientation();
        driveCartesian(ySpeed, xSpeed, zRotation, orientation);
    }

    // Drive using the cartesian method, using the given control orientation
    public void driveCartesian(double ySpeed, double xSpeed, double zRotation, DriveOrientation orientation) {
        if (orientation == DriveOrientation.ROBOT) {
            // Logger.debug("Cartesian Movement: " + ySpeed + ", " + xSpeed + ", " + zRotation);
            Devices.mecDrive.driveCartesian(ySpeed, xSpeed, -zRotation);
        }
        else if (orientation == DriveOrientation.FIELD) {
            double gyroAngle = Devices.imuMecDrive.getAngleZ();
            // Logger.debug("Cartesian Movement: " + ySpeed + ", " + xSpeed + ", " + zRotation + ", " + gyroAngle);
            Devices.mecDrive.driveCartesian(ySpeed, xSpeed, -zRotation, gyroAngle);
        }
    }

    // Drive using the polar method
    public void drivePolar(double magnitude, double angle, double rotation) {
        // Logger.debug("Polar Movement: " + magnitude + ", " + angle + ", " + rotation);
        Devices.mecDrive.drivePolar(magnitude, angle, rotation);
    }

}
