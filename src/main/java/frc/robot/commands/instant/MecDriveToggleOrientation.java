
package frc.robot.commands.instant;

import edu.wpi.first.wpilibj.command.Command;

import frc.robot.helpers.Logger;
import frc.robot.Robot;


// This command stops the mecanum drive, and toggles the control orientation
public class MecDriveToggleOrientation extends Command {

    public MecDriveToggleOrientation() {
        Logger.debug("Constructing Command: MecDriveToggleOrientation...");

        // Declare subsystem dependencies
        requires(Robot.robotMecDriver);
    }

    @Override
    protected void initialize() {
        Logger.debug("Initializing Command: MecDriveToggleOrientation...");

        Robot.robotMecDriver.stop();
    }

    @Override
    protected void execute() {
        Robot.robotMecDriver.toggleDriveOrientation();
    }

    // This command finishes immediately
    @Override
    protected boolean isFinished() {
        return true;
    }

    @Override
    protected void end() {
        Logger.debug("Ending Command: MecDriveToggleOrientation...");

        Robot.robotMecDriver.stop();
    }

    @Override
    protected void interrupted() {
        Logger.debug("Interrupting Command: MecDriveToggleOrientation...");

        Robot.robotMecDriver.stop();
    }

}
