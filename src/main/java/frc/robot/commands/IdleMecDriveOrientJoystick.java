
package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;

import frc.robot.helpers.Logger;
import frc.robot.Robot;


// This command idles the mecanum drive, and flips the joystick Y orientation
public class IdleMecDriveOrientJoystick extends Command {

    public IdleMecDriveOrientJoystick() {
        Logger.debug("Constructing IdleMecDriveOrientJoystick...");

        // Declare subsystem dependencies
        requires(Robot.robotMecDriver);
    }

    @Override
    protected void initialize() {
        Logger.debug("Initializing IdleMecDriveOrientJoystick...");

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
        Logger.debug("Ending IdleMecDriveOrientJoystick...");

        Robot.robotMecDriver.stop();
    }

    @Override
    protected void interrupted() {
        Logger.debug("Interrupting IdleMecDriveOrientJoystick...");

        Robot.robotMecDriver.stop();
    }

}
