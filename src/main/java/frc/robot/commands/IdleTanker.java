
package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;

import frc.robot.helpers.Logger;
import frc.robot.Robot;


// This command idles the Tanker motor
public class IdleTanker extends Command {

    public IdleTanker() {
        Logger.debug("Constructing IdleTanker...");

        // Declare subsystem dependencies
        requires(Robot.robotTanker);
    }

    @Override
    protected void initialize() {
        Logger.debug("Initializing IdleTanker...");
    }

    @Override
    protected void execute() {
        Robot.robotTanker.stop();
    }

    // This command continues until interrupted
    @Override
    protected boolean isFinished() {
        return false;
    }

    @Override
    protected void end() {
        Logger.debug("Ending IdleTanker...");

        Robot.robotTanker.stop();
    }

    @Override
    protected void interrupted() {
        Logger.debug("Interrupting IdleTanker...");

        Robot.robotTanker.stop();
    }

}
