
package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;

import frc.robot.helpers.Logger;
import frc.robot.Robot;


// This command idles the LiftDriver motor
public class IdleLiftDriver extends Command {

    public IdleLiftDriver() {
        Logger.debug("Constructing IdleLiftDriver...");

        // Declare subsystem dependencies
        requires(Robot.robotLiftDriver);
    }

    @Override
    protected void initialize() {
        Logger.debug("Initializing IdleLiftDriver...");
    }

    @Override
    protected void execute() {
        Robot.robotLiftDriver.stop();
    }

    // This command continues until interrupted
    @Override
    protected boolean isFinished() {
        return false;
    }

    @Override
    protected void end() {
        Logger.debug("Ending IdleLiftDriver...");

        Robot.robotLiftDriver.stop();
    }

    @Override
    protected void interrupted() {
        Logger.debug("Interrupting IdleLiftDriver...");

        Robot.robotLiftDriver.stop();
    }

}
