
package frc.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;

import frc.robot.commands.joystick.MecDriveCartesian;
import frc.robot.helpers.Logger;
import frc.robot.Devices;


// Mecanum driver subsystem
public class MecDriver extends Subsystem {

    public enum DeliveryMode {
        HATCH, BALL
    }

    public DeliveryMode currentMode = DeliveryMode.HATCH;
    public boolean isControlOrientationFlipped = false;

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
        Logger.debug("Initializing MecDriver DefaultCommand -> JoystickDrivePolar...");

        setDefaultCommand(new MecDriveCartesian());
    }

    // Flip the control direction of the joystick in Y
    public Boolean flipControlOrientation() {
        Logger.debug("Toggling MecDriver control orientation...");

        isControlOrientationFlipped = !isControlOrientationFlipped;

        if (isControlOrientationFlipped) {
            Logger.debug("MecDriver control orientation is now flipped.");
        }
        else {
            Logger.debug("MecDriver control orientation is now standard (not flipped).");
        }

        return isControlOrientationFlipped;
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

    // Drive using the cartesian method, using robot orientation
    public void driveCartesian(double ySpeed, double xSpeed, double zRotation) {
        Logger.debug("Cartesian Movement: " + ySpeed + ", " + xSpeed + ", " + zRotation);

        Devices.mecDrive.driveCartesian(xSpeed, ySpeed, zRotation);
    }

    // Drive using the cartesian method, using field orientation
    public void driveCartesian(double ySpeed, double xSpeed, double zRotation, double gyroAngle) {
        Logger.debug("Cartesian Movement: " + ySpeed + ", " + xSpeed + ", " + zRotation + ", " + gyroAngle);

        Devices.mecDrive.driveCartesian(xSpeed, ySpeed, -zRotation, gyroAngle);
    }

    // Drive using the polar method
    public void drivePolar(double magnitude, double angle, double rotation) {
        Logger.debug("Polar Movement: " + magnitude + ", " + angle + ", " + rotation);

        Devices.mecDrive.drivePolar(magnitude, angle, rotation);
    }

}
