
package frc.robot;

import edu.wpi.first.wpilibj.GenericHID.Hand;

import frc.robot.commands.auto.*;
import frc.robot.commands.interactive.*;
import frc.robot.helpers.*;
import frc.robot.Brain;


/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {

    public enum ControlStick {
        JOYSTICK, XBOX
    }

    public static ControlStick activeControlStick = ControlStick.JOYSTICK;

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
        Devices.xboxBtn1.whenPressed(new ArmLowerHalf());
        Devices.xboxBtn2.whenPressed(new ArmLowerFull());
        Devices.xboxBtn3.whenPressed(new ArmLowerMore());
        Devices.xboxBtn4.whileHeld(new PulleyLift());
        Devices.xboxBtn5.whileHeld(new PulleyLower());
        // TODO: Bind the Tank appropriate commands
        // TODO: Bind the Pusher appropriate commands
    }

    //----------------------//
    // Active Control Stick //
    //----------------------//

    // Determines the cartesian movement (forward/backward speed, side to side speed, rotation speed)
    // from the active control stick position(s)
    public static CartesianMovement getCartesianMovement(boolean isYflipped) {
        switch (activeControlStick) {
            case JOYSTICK:
                return getCartesianMovementFromJoystick(isYflipped);
            case XBOX:
                return getCartesianMovementFromThumbsticks(isYflipped);
            default:
                return null;
        }
    }

    // Determines the polar movement (magnitude, angle, rotation)
    // from the active control stick position(s)
    public static PolarMovement getPolarMovement(boolean isYflipped) {
        switch (activeControlStick) {
            case JOYSTICK:
                return getPolarMovementFromJoystick(isYflipped);
            case XBOX:
                return getPolarMovementFromThumbsticks(isYflipped);
            default:
                return null;
        }
    }

    //----------//
    // Joystick //
    //----------//

    // Determines the cartesian movement (forward/backward speed, side to side speed, rotation speed)
    // from the current joystick position
    public static CartesianMovement getCartesianMovementFromJoystick(boolean isYflipped) {
        JoystickPosition pos = getJoystickPosition(isYflipped);
        CartesianMovement move = new CartesianMovement(pos.yPosition, pos.xPosition, pos.zPosition);
        return move;
    }

    // Determines the polar movement (magnitude, angle, rotation)
    // from the current joystick position
    public static PolarMovement getPolarMovementFromJoystick(boolean isYflipped) {
        JoystickPosition pos = getJoystickPosition(isYflipped);
        PolarMovement move = new PolarMovement(pos.xPosition, pos.yPosition, pos.zPosition);
        return move;
    }

    // Gets the joystick position and applies user-determined orientation, deadzones, and sensitivity
    private static JoystickPosition getJoystickPosition(boolean isYflipped) {
        double y = -Devices.jstick.getY(); // Forward & backward, flipped
        double x = Devices.jstick.getX(); // Side to side
        double z = Devices.jstick.getZ(); // Rotate

        // User-determined flipping of forward/backward orientation
        if (isYflipped) {
            y = -y;
        }

        // Deadzones
        double yDeadZone = Brain.getYdeadZone();
        double xDeadZone = Brain.getXdeadZone();
        double zDeadZone = Brain.getZdeadZone();

        if (Math.abs(y) <= yDeadZone) y = 0;
        if (Math.abs(x) <= xDeadZone) x = 0;
        if (Math.abs(z) <= zDeadZone) z = 0;

        if (y > 0) y = y - yDeadZone;
        if (y < 0) y = y + yDeadZone;
        if (x > 0) x = x - xDeadZone;
        if (x < 0) x = x + xDeadZone;
        if (z > 0) z = z - zDeadZone;
        if (z < 0) z = z + zDeadZone;

        // Sensitivity
        double ySensitivity = Brain.getYsensitivity();
        double xSensitivity = Brain.getXsensitivity();
        double zSensitivity = Brain.getXsensitivity();

        y = y * ySensitivity;
        x = x * xSensitivity;
        z = z * zSensitivity;

        JoystickPosition pos = new JoystickPosition(y, x, z);
        return pos;
    }

    //------------------//
    // Xbox Thumbsticks //
    //------------------//

    // Determines the cartesian movement (forward/backward speed, side to side speed, rotation speed)
    // from the current xbox thumbstick positions
    public static CartesianMovement getCartesianMovementFromThumbsticks(boolean isYleftFlipped) {
        ThumbStickPosition pos = getThumbstickPosition(isYleftFlipped);
        CartesianMovement move = new CartesianMovement(pos.yLeftPosition, pos.xLeftPosition, pos.xRightPosition);
        return move;
    }

    // Determines the polar movement (magnitude, angle, rotation)
    // from the current xbox thumbstick positions
    public static PolarMovement getPolarMovementFromThumbsticks(boolean isYleftFlipped) {
        ThumbStickPosition pos = getThumbstickPosition(isYleftFlipped);
        PolarMovement move = new PolarMovement(pos.xLeftPosition, pos.yLeftPosition, pos.xRightPosition);
        return move;
    }

    // Gets the xbox thumbstick positions and applies user-determined orientation, deadzones, and sensitivity
    private static ThumbStickPosition getThumbstickPosition(boolean isYleftFlipped) {
        double yLeft = -Devices.xbox.getY(Hand.kLeft); // Forward & backward, flipped
        double xLeft = Devices.xbox.getX(Hand.kLeft); // Strafe
        double xRight = Devices.xbox.getX(Hand.kRight); // Rotate

        // User-determined flipping of forward/backward orientation
        if (isYleftFlipped) {
            yLeft = -yLeft;
        }

        // Deadzones
        double yLeftDeadZone = Brain.getYleftDeadZone();
        double xLeftDeadZone = Brain.getXleftDeadZone();
        double xRightDeadZone = Brain.getXrightDeadZone();

        if (Math.abs(yLeft) <= yLeftDeadZone) yLeft = 0;
        if (Math.abs(xLeft) <= xLeftDeadZone) xLeft = 0;
        if (Math.abs(xRight) <= xRightDeadZone) xRight = 0;

        if (yLeft > 0) yLeft = yLeft - yLeftDeadZone;
        if (yLeft < 0) yLeft = yLeft + yLeftDeadZone;
        if (xLeft > 0) xLeft = xLeft - xLeftDeadZone;
        if (xLeft < 0) xLeft = xLeft + xLeftDeadZone;
        if (xRight > 0) xRight = xRight - xRightDeadZone;
        if (xRight < 0) xRight = xRight + xRightDeadZone;

        // Sensitivity
        double yLeftSensitivity = Brain.getYleftSensitivity();
        double xLeftSensitivity = Brain.getXleftSensitivity();
        double xRightSensitivity = Brain.getXrightSensitivity();

        yLeft = yLeft * yLeftSensitivity;
        xLeft = xLeft * xLeftSensitivity;
        xRight = xRight * xRightSensitivity;

        ThumbStickPosition pos = new ThumbStickPosition(yLeft, xLeft, xRight);
        return pos;
    }

    // TODO: Also consider adding a "debouncer" for the buttons

}
