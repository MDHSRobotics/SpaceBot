
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

    private static double m_deadzoneY = .1;
    private static double m_deadzoneX = .1;
    private static double m_deadzoneZ = .1;

    private static double m_sensitivityY = .5;
    private static double m_sensitivityX = .5;
    private static double m_sensitivityZ = .5;

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
        JoystickPosition pos = getJoystickPosition(isFlipped);

        CartesianMovement move = new CartesianMovement(pos.xPosition, pos.yPosition, pos.zPosition);
        return move;
    }

    // Determines the polar movement (magnitude, angle, rotation) from the current joystick position
    public static PolarMovement getPolarMovementFromJoystick(Boolean isFlipped) {
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
        if (Math.abs(y) <= m_deadzoneY) y = 0;
        if (Math.abs(x) <= m_deadzoneX) x = 0;
        if (Math.abs(z) <= m_deadzoneZ) z = 0;

        if (y > 0) y = y - m_deadzoneY;
        if (y < 0) y = y + m_deadzoneY;
        if (x > 0) x = x - m_deadzoneX;
        if (x < 0) x = x + m_deadzoneX;
        if (z > 0) z = z - m_deadzoneZ;
        if (z < 0) z = z + m_deadzoneZ;

        // Sensitivity
        y = y * m_sensitivityY;
        x = x * m_sensitivityX;
        z = z * m_sensitivityZ;

        JoystickPosition pos = new JoystickPosition(y, x, z);
        return pos;
    }

}
