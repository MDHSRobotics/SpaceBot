
package frc.robot.commands.idle;

import edu.wpi.first.wpilibj.command.Command;

import frc.robot.consoles.Logger;
import frc.robot.Robot;


// This command stops the Pulley motor
public class PulleyStop extends Command {

    public PulleyStop() {
        Logger.setup("Constructing Command: PulleyStop...");

        // Declare subsystem dependencies
        requires(Robot.robotPulley);
    }

    @Override
    protected void initialize() {
        Logger.action("Initializing Command: PulleyStop...");
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
        Logger.ending("Ending Command: PulleyStop...");
    }

    @Override
    protected void interrupted() {
        Logger.ending("Interrupting Command: PulleyStop...");
    }

}
