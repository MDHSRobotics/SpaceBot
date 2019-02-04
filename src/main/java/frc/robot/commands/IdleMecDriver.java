
package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;

import frc.robot.helpers.Logger;
import frc.robot.Robot;


// This command idles the MecDriver
public class IdleMecDriver extends Command {

    public IdleMecDriver() {
        Logger.debug("Constructing IdleMecDriver...");

        // Declare subsystem dependencies
        requires(Robot.robotMecDriver);
    }

    @Override
    protected void initialize() {
        Logger.debug("Initializing IdleMecDriver...");
    }

    @Override
    protected void execute() {
        Robot.robotMecDriver.stop();
    }

    // This command continues until interrupted
    @Override
    protected boolean isFinished() {
        return false;
    }

    @Override
    protected void end() {
        Logger.debug("Ending IdleMecDriver...");

        Robot.robotMecDriver.stop();
    }

    @Override
    protected void interrupted() {
        Logger.debug("Interrupting IdleMecDriver...");

        Robot.robotMecDriver.stop();
    }

}
