/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.commands.JoystickDriveCartesian;
import frc.robot.commands.JoystickDrivePolar;
import frc.robot.helpers.Logger;
// Don't import OI; Subsystems control robot devices, they don't access HIDs -- commands do that
import frc.robot.Devices;

// Mecanum Driver Subsystem
public class MecDriver extends Subsystem {

    public boolean isFlipped = false;
    public double speed = 0;

    private double m_secondsFromNeutralToFull = 1.0;
    private int m_timeoutMS = 10;

    // Constructor
    public MecDriver() {
        Logger.debug("Constructing MecDriver...");

        // Configure wheel speed controllers
        Devices.talonSrxWheelFrontLeft.configOpenloopRamp(m_secondsFromNeutralToFull, m_timeoutMS);
        Devices.talonSrxWheelRearLeft.configOpenloopRamp(m_secondsFromNeutralToFull, m_timeoutMS);
        Devices.talonSrxWheelFrontRight.configOpenloopRamp(m_secondsFromNeutralToFull, m_timeoutMS);
        Devices.talonSrxWheelRearRight.configOpenloopRamp(m_secondsFromNeutralToFull, m_timeoutMS);
    }

    // Initialize Default Command
    @Override
    public void initDefaultCommand() {
        Logger.debug("Initializing MecDriver default command -> JoystickDrivePolar...");

        JoystickDriveCartesian defaultCmd = new JoystickDriveCartesian();
        setDefaultCommand(defaultCmd);
    }

    // Flip the control direction of the joystick
    public Boolean flip() {
        Logger.debug("Toggling MecDriver control flipping...");

        if (speed != 0) {
            Logger.debug("Cannot flip control without stopping first.");
        }
        else {
            isFlipped = !isFlipped;

            if (isFlipped) {
                Logger.debug("MecDriver control is now flipped.");
            } else {
                Logger.debug("MecDriver control is now standard (not flipped).");
            }
        }

        return isFlipped;
    }

    // Stop all the drive motors
    public void stop() {
        Devices.mecDrive.stopMotor();
        speed = 0;
    }

    // Drive forward at the given speed
    public void driveForward(double speed) {
        Devices.mecDrive.drivePolar(speed, 0, 0);
    }

    // Drive forward at the given speed
    public void pivot(double speed) {
        // TODO: implement this.
        throw new RuntimeException("Not yet implemented!");
    }

    // Drive using the cartesian method
    public void driveCartesian(double ySpeed, double xSpeed, double zRotation, double gyroAngle) {
        Logger.debug("Cartesian Movement: " + ySpeed + ", " + xSpeed + ", " + zRotation + ", " + gyroAngle);
        Devices.mecDrive.driveCartesian(ySpeed, xSpeed, zRotation, gyroAngle);
    }

    // Drive using the polar method
    public void drivePolar(double magnitude, double angle, double rotation) {
        Logger.debug("Polar Movement: " + magnitude + ", " + angle + ", " + rotation);
        Devices.mecDrive.drivePolar(magnitude, angle, rotation);
    }

}
