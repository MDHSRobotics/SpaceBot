
package frc.robot.commands.idle;

import edu.wpi.first.wpilibj.command.Command;

import frc.robot.helpers.Logger;
import frc.robot.Robot;


// This command stops the Pulley motor
public class PulleyStop extends Command {

    public PulleyStop() {
        Logger.debug("Constucting Command: PulleyStop...");

        // Declare subsystem dependencies
        requires(Robot.robotPulley);
    }

    @Override
    protected void initialize() {
        Logger.debug("Initializing Command: PulleyStop...");
    }

    @Override
    protected void execute() {
        Robot.robotPulley.stop();
    }

    // This command continues until interrupted
    @Override
    protected boolean isFinished() {
        return false;
    }

    @Override
    protected void end() {
        Logger.debug("Ending Command: PulleyStop...");

        Robot.robotPulley.stop();
    }

    @Override
    protected void interrupted() {
        Logger.debug("Interrupting Command: PulleyStop...");

        Robot.robotPulley.stop();
    }

}
