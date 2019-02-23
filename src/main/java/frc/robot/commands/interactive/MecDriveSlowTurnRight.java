
package frc.robot.commands.interactive;

import edu.wpi.first.wpilibj.command.Command;

import frc.robot.consoles.Logger;
import frc.robot.Robot;


// Tests the MecDrive Slow Turn Right
public class MecDriveSlowTurnRight extends Command {

    public MecDriveSlowTurnRight() {
        Logger.setup("Constructing Command: MecDriveSlowTurnRight...");

        // Declare subsystem dependencies
        requires(Robot.robotMecDriver);
    }

    @Override
    protected void initialize() {
        Logger.action("Initializing Command: MecDriveSlowTurnRight...");
    }

    @Override
    protected void execute() {
        Robot.robotMecDriver.rotate(.2);
    }

    // This command continues until interrupted
    @Override
    protected boolean isFinished() {
        return false;
    }

    @Override
    protected void end() {
        Logger.ending("Ending Command: MecDriveSlowTurnRight...");

        Robot.robotMecDriver.stop();
    }

    @Override
    protected void interrupted() {
        System.out.println("--");
        Logger.ending("Interrupting Command: MecDriveSlowTurnRight...");

        Robot.robotMecDriver.stop();
    }

}
