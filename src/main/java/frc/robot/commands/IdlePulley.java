
package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;

import frc.robot.helpers.Logger;
import frc.robot.Robot;


// This command idles the Pulley motor
public class IdlePulley extends Command {

    public IdlePulley() {
        Logger.debug("Constucting IdlePulley...");

        // Declare subsystem dependencies
        requires(Robot.robotPulley);
    }

    @Override
    protected void initialize() {
        Logger.debug("Initializing IdlePulley...");
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
        Logger.debug("Ending IdlePulley...");

        Robot.robotPulley.stop();
    }

    @Override
    protected void interrupted() {
        Logger.debug("Interrupting IdlePulley...");

        Robot.robotPulley.stop();
    }

}
