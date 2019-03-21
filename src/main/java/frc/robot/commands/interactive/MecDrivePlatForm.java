
package frc.robot.commands.interactive;

import edu.wpi.first.wpilibj.command.Command;

import frc.robot.consoles.Logger;
import frc.robot.OI;
import frc.robot.Robot;


// This command spins the Tank
public class MecDrivePlatForm extends Command {

    public MecDrivePlatForm() {
        Logger.setup("Constructing Command: MecDrivePlatForm...");

        // Declare subsystem dependencies
        requires(Robot.robotMecDriver);
    }

    @Override
    protected void initialize() {
        Logger.action("Initializing Command: MecDrivePlatForm...");
    }

    @Override
    protected void execute() {
        double speed = OI.getplatformDriveSpeed();
        Robot.robotMecDriver.platformDrive(speed);
    }

    // This command continues until interrupted
    @Override
    protected boolean isFinished() {
        return false;
    }

    @Override
    protected void end() {
        Logger.ending("Ending Command: MecDrivePlatForm...");

        // Robot.robotMecDriver.stop();
    }

    @Override
    protected void interrupted() {
        Logger.ending("Interrupting Command: MecDrivePlatForm...");

        // Robot.robotMecDriver.stop();
    }

}
