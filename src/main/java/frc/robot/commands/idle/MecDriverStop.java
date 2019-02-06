
package frc.robot.commands.idle;

import edu.wpi.first.wpilibj.command.Command;

import frc.robot.helpers.Logger;
import frc.robot.Robot;


// This command stops the MecDriver
public class MecDriverStop extends Command {

    public MecDriverStop() {
        Logger.debug("Constructing MecDriverStop...");

        // Declare subsystem dependencies
        requires(Robot.robotMecDriver);
    }

    @Override
    protected void initialize() {
        Logger.debug("Initializing MecDriverStop...");
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
        Logger.debug("Ending MecDriverStop...");

        Robot.robotMecDriver.stop();
    }

    @Override
    protected void interrupted() {
        Logger.debug("Interrupting MecDriverStop...");

        Robot.robotMecDriver.stop();
    }

}
