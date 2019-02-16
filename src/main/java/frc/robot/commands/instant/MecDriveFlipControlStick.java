
package frc.robot.commands.instant;

import edu.wpi.first.wpilibj.command.Command;

import frc.robot.helpers.Logger;
import frc.robot.Robot;


// This command stops the mecanum drive, and flips the joystick or left thumbstick in Y
public class MecDriveFlipControlStick extends Command {

    public MecDriveFlipControlStick() {
        Logger.debug("Constructing Command: MecDriveFlipControlStick...");

        // Declare subsystem dependencies
        requires(Robot.robotMecDriver);
    }

    @Override
    protected void initialize() {
        Logger.debug("Initializing Command: MecDriveFlipControlStick...");

        Robot.robotMecDriver.stop();
    }

    @Override
    protected void execute() {
        Robot.robotMecDriver.flipControlStickDirection();
    }

    // This command finishes immediately
    @Override
    protected boolean isFinished() {
        return true;
    }

    @Override
    protected void end() {
        Logger.debug("Ending Command: MecDriveFlipControlStick...");

        Robot.robotMecDriver.stop();
    }

    @Override
    protected void interrupted() {
        Logger.debug("Interrupting Command: MecDriveFlipControlStick...");

        Robot.robotMecDriver.stop();
    }

}
