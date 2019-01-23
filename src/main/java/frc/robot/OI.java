/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import frc.robot.commands.AutoBallerGate;
import frc.robot.commands.AutoDriveDistance;
import frc.robot.commands.AutoDriveTurn;
import frc.robot.commands.AutoHatchClaw;
import frc.robot.commands.IdleDriveOrientJoystick;
import frc.robot.helpers.Logger;
import frc.robot.helpers.CartesianMovement;
import frc.robot.helpers.PolarMovement;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {

    // Constructor
    public OI() {
        Logger.debug("Constructing OI...");

        // Bind the joystick buttons to specific commands
        IdleDriveOrientJoystick idleDriveOrientJstickCmd = new IdleDriveOrientJoystick();
        Devices.jstickBtn5.whenPressed(idleDriveOrientJstickCmd);

        AutoDriveDistance autoDriveDistCmd = new AutoDriveDistance();
        Devices.jstickBtn8.whenPressed(autoDriveDistCmd);

        AutoDriveTurn autoDriveTurnCmd = new AutoDriveTurn();
        Devices.jstickBtn7.whenPressed(autoDriveTurnCmd);

        AutoHatchClaw autoHatchClawCmd = new AutoHatchClaw();
        Devices.jstickBtn9.whenPressed(autoHatchClawCmd);

        AutoBallerGate autoBallerGateCmd = new AutoBallerGate();
        Devices.jstickBtn10.whenPressed(autoBallerGateCmd);
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
        // Queston: Why are we not just calling getX() and getY()?
        // This apparently doesn't actually work.
        double rawAxis0 = Devices.jstick.getRawAxis(0);
        double rawAxis1 = Devices.jstick.getRawAxis(1);

        if (isFlipped) {
            rawAxis1 = -rawAxis1;
        }

        PolarMovement move = new PolarMovement();
        move.magnitude = PolarMovement.calculateMagnitude(rawAxis0, rawAxis1);
        move.angle = PolarMovement.calculateAngle(rawAxis0, rawAxis1);
        move.rotation = rawAxis1;

        return move;
    }

}
