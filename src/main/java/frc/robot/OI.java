/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import frc.robot.commands.*;
import frc.robot.helpers.*;


/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {

    // Constructor
    public OI() {
        Logger.debug("Constructing OI...");

        // Bind the joystick buttons to specific commands
        Devices.jstickBtn5.whenPressed(new IdleDriveOrientJoystick());
        Devices.jstickBtn7.whenPressed(new AutoDriveTurn());
        Devices.jstickBtn8.whenPressed(new AutoDriveDistance());
        Devices.jstickBtn9.whenPressed(new AutoHatchGrab());
        Devices.jstickBtn10.whenPressed(new AutoBallDeploy());
        Devices.jstickBtn11.whenPressed(new AutoArmLower());
        Devices.jstickBtn12.whenPressed(new AutoArmRaise());
    }

    // Determines the cartesian movement (magnitude, angle, rotation) from the current joystick position
    public static CartesianMovement getCartesianMovementFromJoystick(Boolean isFlipped) {
        double xSpeed = Devices.jstick.getX();
        double ySpeed = -Devices.jstick.getY();
        double zRotation = Devices.jstick.getZ();
        double gyroAngle = 0.0;

        if (isFlipped) {
            ySpeed = -ySpeed;
        }

        CartesianMovement move = new CartesianMovement();
        move.xSpeed = xSpeed;
        move.ySpeed = ySpeed;
        move.zRotation = zRotation;
        move.gyroAngle = gyroAngle;

        return move;
    }

    // Determines the polar movement (magnitude, angle, rotation) from the current joystick position
    public static PolarMovement getPolarMovementFromJoystick(Boolean isFlipped) {
        double xSpeed = Devices.jstick.getX();
        double ySpeed = -Devices.jstick.getY();
        double zRotation = Devices.jstick.getZ();

        if (isFlipped) {
            ySpeed = -ySpeed;
        }

        PolarMovement move = new PolarMovement();
        move.magnitude = PolarMovement.calculateMagnitude(xSpeed, ySpeed);
        move.angle = PolarMovement.calculateAngle(xSpeed, ySpeed);
        move.rotation = zRotation;

        return move;
    }

}
