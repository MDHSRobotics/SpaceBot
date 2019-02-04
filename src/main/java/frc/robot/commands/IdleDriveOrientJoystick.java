
package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;

import frc.robot.helpers.Logger;
import frc.robot.Robot;


// This command idles the drive, and flips the joystick Y orientation
public class IdleDriveOrientJoystick extends Command {

    public IdleDriveOrientJoystick() {
        Logger.debug("Constructing IdleDriveOrientJoystick...");

        // Declare subsystem dependencies
        requires(Robot.robotMecDriver);
    }

    @Override
    protected void initialize() {
        Logger.debug("Initializing IdleDriveOrientJoystick...");

        Robot.robotMecDriver.stop();
    }

    @Override
    protected void execute() {
        Robot.robotMecDriver.flipControlOrientation();
    }

    // This command finishes immediately
    @Override
    protected boolean isFinished() {
        return true;
    }

    @Override
    protected void end() {
        Logger.debug("Ending IdleDriveOrientJoystick...");

        Robot.robotMecDriver.stop();
    }

    @Override
    protected void interrupted() {
        Logger.debug("Interrupting IdleDriveOrientJoystick...");

        Robot.robotMecDriver.stop();
    }

}
