
package frc.robot.commands.idle;

import edu.wpi.first.wpilibj.command.Command;

import frc.robot.helpers.Logger;
import frc.robot.Robot;


// This command stops the Baller motor
public class BallerStop extends Command {

    public BallerStop() {
        Logger.debug("Constructing BallerStop...");

        // Declare subsystem dependencies
        requires(Robot.robotBaller);
    }

    @Override
    protected void initialize() {
        Logger.debug("Initializing BallerStop...");
    }

    @Override
    protected void execute() {
        Robot.robotBaller.stop();
    }

    // This command continues until interrupted
    @Override
    protected boolean isFinished() {
        return false;
    }

    @Override
    protected void end() {
        Logger.debug("Ending BallerStop...");

        Robot.robotBaller.stop();
    }

    @Override
    protected void interrupted() {
        Logger.debug("Interrupting BallerStop...");

        Robot.robotBaller.stop();
    }

}
