
package frc.robot.commands.interactive;

import edu.wpi.first.wpilibj.command.Command;

import frc.robot.consoles.Logger;
import frc.robot.Robot;


// Tests the MecDrive Slow Forward
public class MecDriveSlowForward extends Command {

    public MecDriveSlowForward() {
        Logger.setup("Constructing Command: MecDriveSlowForward...");

        // Declare subsystem dependencies
        requires(Robot.robotMecDriver);
    }

    @Override
    protected void initialize() {
        Logger.action("Initializing Command: MecDriveSlowForward...");
    }

    @Override
    protected void execute() {
        Robot.robotMecDriver.driveStraight(.2);
    }

    // This command continues until interrupted
    @Override
    protected boolean isFinished() {
        return false;
    }

    @Override
    protected void end() {
        Logger.ending("Ending Command: MecDriveSlowForward...");

        Robot.robotMecDriver.stop();
    }

    @Override
    protected void interrupted() {
        System.out.println("--");
        Logger.ending("Interrupting Command: MecDriveSlowForward...");

        Robot.robotMecDriver.stop();
    }

}
