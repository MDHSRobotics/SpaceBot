
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

        // TODO: Need to establish a dead zone for the joystick

        // Bind the joystick buttons to specific commands
        Devices.jstickBtn1.whenPressed(new AutoMecDriveLine());
        Devices.jstickBtn3.whenPressed(new AutoHatchGrab());
        Devices.jstickBtn4.whenPressed(new AutoHatchRelease());
        Devices.jstickBtn5.whenPressed(new AutoBallBlock());
        Devices.jstickBtn6.whenPressed(new AutoBallToss());

        // TODO: The lift and climb commands should be activated via the xbox controller
        Devices.jstickBtn7.whenPressed(new AutoClimbArmLower());
        Devices.jstickBtn8.whileHeld(new JoystickPulleyUp());
        Devices.jstickBtn9.whileHeld(new JoystickPulleyDown());
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
