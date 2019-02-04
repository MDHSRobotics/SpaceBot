
package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;

import frc.robot.helpers.Logger;
import frc.robot.Robot;


// This command idles the ClimbArm motor
public class IdleClimbArm extends Command {

    public IdleClimbArm() {
        Logger.debug("Constucting IdleClimbArm...");

        // Declare subsystem dependencies
        requires(Robot.robotClimbArm);
    }

    @Override
    protected void initialize() {
        Logger.debug("Initializing IdleClimbArm...");
    }

    @Override
    protected void execute() {
        Robot.robotClimbArm.stop();
    }

    // This command continues until interrupted
    @Override
    protected boolean isFinished() {
        return false;
    }

    @Override
    protected void end() {
        Logger.debug("Ending IdleClimbArm...");

        Robot.robotClimbArm.stop();
    }

    @Override
    protected void interrupted() {
        Logger.debug("Interrupting IdleClimbArm...");

        Robot.robotClimbArm.stop();
    }

}
