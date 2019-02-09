
package frc.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.commands.joystick.MecDriveCartesian;
import frc.robot.helpers.Logger;
import frc.robot.Brain;
import frc.robot.Devices;


// Mecanum driver subsystem
public class MecDriver extends Subsystem {

    public enum ControlOrientation {
        ROBOT, FIELD
    }

    public boolean joystickOrientationFlipped = false;
    public ControlOrientation controlOrientation = Brain.getControlOrientation();

    private double m_secondsFromNeutralToFull = 1.0;
    private int m_timeoutMS = 10;

    // Constructor
    public MecDriver() {
        Logger.debug("Constructing Subsystem: MecDriver...");

        // Configure wheel speed controllers
        Devices.talonSrxMecWheelFrontLeft.configOpenloopRamp(m_secondsFromNeutralToFull, m_timeoutMS);
        Devices.talonSrxMecWheelRearLeft.configOpenloopRamp(m_secondsFromNeutralToFull, m_timeoutMS);
        Devices.talonSrxMecWheelFrontRight.configOpenloopRamp(m_secondsFromNeutralToFull, m_timeoutMS);
        Devices.talonSrxMecWheelRearRight.configOpenloopRamp(m_secondsFromNeutralToFull, m_timeoutMS);
    }

    // Initialize Default Command
    @Override
    public void initDefaultCommand() {
        Logger.debug("Initializing MecDriver DefaultCommand -> MecDriveCartesian...");

        setDefaultCommand(new MecDriveCartesian());
    }

    // Flip the control direction of the joystick in Y
    public Boolean flipJoystickOrientation() {
        Logger.debug("Toggling MecDriver joystick orientation...");

        joystickOrientationFlipped = !joystickOrientationFlipped;

        if (joystickOrientationFlipped) {
            Logger.debug("MecDriver joystick orientation is now flipped.");
        }
        else {
            Logger.debug("MecDriver joystick orientation is now standard (not flipped).");
        }

        return joystickOrientationFlipped;
    }

    // Toggle the control orientation for the mecanum drive
    public ControlOrientation toggleControlOrientation() {
        Logger.debug("Toggling MecDriver control orientation...");

        if (controlOrientation == ControlOrientation.FIELD) {
            controlOrientation = ControlOrientation.ROBOT;
            Logger.debug("MecDriver control orientation is now ROBOT.");
        } else if (controlOrientation == ControlOrientation.ROBOT) {
            controlOrientation = ControlOrientation.FIELD;
            Logger.debug("MecDriver control orientation is now FIELD.");
        }

        return controlOrientation;
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
        driveCartesian(ySpeed, xSpeed, zRotation, controlOrientation);
    }

    // Drive using the cartesian method, using the given control orientation
    public void driveCartesian(double ySpeed, double xSpeed, double zRotation, ControlOrientation orientation) {
        if (orientation == ControlOrientation.ROBOT) {
            Logger.debug("Cartesian Movement: " + ySpeed + ", " + xSpeed + ", " + zRotation);
            Devices.mecDrive.driveCartesian(ySpeed, xSpeed, -zRotation);
        }
        else if (orientation == ControlOrientation.FIELD) {
            double gyroAngle = Devices.imuMecDrive.getAngleZ();
            Logger.debug("Cartesian Movement: " + ySpeed + ", " + xSpeed + ", " + zRotation + ", " + gyroAngle);
            Devices.mecDrive.driveCartesian(ySpeed, xSpeed, -zRotation, gyroAngle);
        }
    }

    // Drive using the polar method
    public void drivePolar(double magnitude, double angle, double rotation) {
        Logger.debug("Polar Movement: " + magnitude + ", " + angle + ", " + rotation);
        Devices.mecDrive.drivePolar(magnitude, angle, rotation);
    }

}
