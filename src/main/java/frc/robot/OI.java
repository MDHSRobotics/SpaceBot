
package frc.robot;

import frc.robot.commands.auto.*;
//import frc.robot.commands.xbox.*;
import frc.robot.helpers.*;


/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {

    // TODO: Also consider adding a "debouncer" for the buttons

    private static double m_deadzoneY = .05;
    private static double m_deadzoneX = .05;
    private static double m_deadzoneZ = .05;

    // Constructor
    public OI() {
        Logger.debug("Constructing OI...");

        // Bind the joystick buttons to specific commands
        Devices.jstickBtn1.whenPressed(new MecDriveAlignHatch());
        Devices.jstickBtn3.whenPressed(new HatchGrab());
        Devices.jstickBtn4.whenPressed(new HatchRelease());
        Devices.jstickBtn5.whenPressed(new BallHold());
        Devices.jstickBtn6.whenPressed(new BallToss());

        // Bind the xbox buttons to specific commands
        // Devices.xboxBtn1.whenPressed(new ArmLowerHalf());
        // Devices.xboxBtn2.whenPressed(new ArmLowerFull());
        // Devices.xboxBtn3.whenPressed(new ArmLowerMore());
        // Devices.xboxBtn4.whileHeld(new PulleyUp());
        // Devices.xboxBtn5.whileHeld(new PulleyDown());
    }

    // Determines the cartesian movement (forward/backward speed, side to side speed, rotation speed) from the current joystick position
    public static CartesianMovement getCartesianMovementFromJoystick(boolean isFlipped) {
        double ySpeed = -Devices.jstick.getY(); // Forward & backward, flipped
        double xSpeed = Devices.jstick.getX(); // Side to side
        double zRotation = Devices.jstick.getZ(); // Rotate

        // User-determined flipping of forward/backward orientation
        if (isFlipped) {
            ySpeed = -ySpeed;
        }

        // Deadzones
        if (Math.abs(ySpeed) < m_deadzoneY) ySpeed = 0;
        if (Math.abs(xSpeed) < m_deadzoneX) xSpeed = 0;
        if (Math.abs(zRotation) < m_deadzoneZ) zRotation = 0;

        CartesianMovement move = new CartesianMovement();
        move.ySpeed = ySpeed;
        move.xSpeed = xSpeed;
        move.zRotation = zRotation;

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
