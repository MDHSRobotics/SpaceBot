
package frc.robot;

import edu.wpi.first.wpilibj.GenericHID.Hand;

import frc.robot.commands.auto.*;
import frc.robot.commands.xbox.*;
import frc.robot.helpers.*;
import frc.robot.Brain;


/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {

    // TODO: Also consider adding a "debouncer" for the buttons

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

    // Determines the cartesian movement (forward/backward speed, side to side speed, rotation speed) from the current joystick position
    public static CartesianMovement getCartesianMovementFromJoystick(boolean isFlipped) {
        JoystickPosition pos = getJoystickPosition(isFlipped);

        CartesianMovement move = new CartesianMovement(pos.yPosition, pos.xPosition, pos.zPosition);
        return move;
    }

    // Determines the polar movement (magnitude, angle, rotation) from the current joystick position
    public static PolarMovement getPolarMovementFromJoystick(boolean isFlipped) {
        JoystickPosition pos = getJoystickPosition(isFlipped);

        PolarMovement move = new PolarMovement(pos.xPosition, pos.yPosition, pos.zPosition);
        return move;
    }

    

    // Gets the joystick position and applies user-determined orientation, deadzones, and sensitivity
    private static JoystickPosition getJoystickPosition(boolean isFlipped) {
        double y = -Devices.jstick.getY(); // Forward & backward, flipped
        double x = Devices.jstick.getX(); // Side to side
        double z = Devices.jstick.getZ(); // Rotate

        // User-determined flipping of forward/backward orientation
        if (isFlipped) {
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


    public static CartesianMovement getCartesianMovementFromXbox() {
        XboxThumbStickPosition pos = getXboxThumbstickPosition();

        CartesianMovement move = new CartesianMovement(pos.yLeftPosition, pos.xLeftPosition, pos.xRightPosition);
        return move;
    }

    private static XboxThumbStickPosition getXboxThumbstickPosition() {
        double yLeft = -Devices.xbox.getY(Hand.kLeft); // Forward & backward
        double xLeft = Devices.xbox.getX(Hand.kLeft); // Strafe left stick
        double xRight = Devices.xbox.getX(Hand.kRight); // Rotate, right stick


        // Deadzones
        double yDeadZone = Brain.getYdeadZone();
        double xDeadZone = Brain.getXdeadZone();
        double zDeadZone = Brain.getZdeadZone();

        if (Math.abs(yLeft) <= yDeadZone) yLeft = 0;
        if (Math.abs(xLeft) <= xDeadZone) xLeft = 0;
        if (Math.abs(xRight) <= zDeadZone) xRight = 0;

        if (yLeft > 0) yLeft = yLeft - yDeadZone;
        if (yLeft < 0) yLeft = yLeft + yDeadZone;
        if (xLeft > 0) xLeft = xLeft - xDeadZone;
        if (xLeft < 0) xLeft = xLeft + xDeadZone;
        if (xRight > 0) xRight = xRight - zDeadZone;
        if (xRight < 0) xRight = xRight + zDeadZone;

        // Sensitivity
        double yLeftSensitivity = Brain.getYsensitivity();
        double xLeftSensitivity = Brain.getXsensitivity();
        double xRightSensitivity = Brain.getXsensitivity();

        yLeft = yLeft * yLeftSensitivity;
        xLeft = xLeft * xLeftSensitivity;
        xRight = xRight * xRightSensitivity;

        XboxThumbStickPosition pos = new XboxThumbStickPosition(yLeft, xLeft, xRight);

        return pos;
    }


}
