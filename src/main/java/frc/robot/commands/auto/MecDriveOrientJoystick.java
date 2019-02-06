
package frc.robot.commands.auto;

import edu.wpi.first.wpilibj.command.Command;

import frc.robot.helpers.Logger;
import frc.robot.Robot;


// This command stops the mecanum drive, and flips the joystick Y orientation
public class MecDriveOrientJoystick extends Command {

    public MecDriveOrientJoystick() {
        Logger.debug("Constructing MecDriveOrientJoystick...");

        // Declare subsystem dependencies
        requires(Robot.robotMecDriver);
    }

    @Override
    protected void initialize() {
        Logger.debug("Initializing MecDriveOrientJoystick...");

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
        Logger.debug("Ending MecDriveOrientJoystick...");

        Robot.robotMecDriver.stop();
    }

    @Override
    protected void interrupted() {
        Logger.debug("Interrupting MecDriveOrientJoystick...");

        Robot.robotMecDriver.stop();
    }

}
