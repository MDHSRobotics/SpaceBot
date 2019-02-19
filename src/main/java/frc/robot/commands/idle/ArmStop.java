
package frc.robot.commands.idle;

import edu.wpi.first.wpilibj.command.Command;

import frc.robot.helpers.Logger;
import frc.robot.Robot;


// This command stops the Arm motor
public class ArmStop extends Command {

    public ArmStop() {
        Logger.debug("Constructing Command: ArmStop...");

        // Declare subsystem dependencies
        requires(Robot.robotArm);
    }

    @Override
    protected void initialize() {
        Logger.debug("Initializing Command: ArmStop...");
    }

    @Override
    protected void execute() {
        Robot.robotArm.stop();
    }

    // This command continues until interrupted
    @Override
    protected boolean isFinished() {
        return false;
    }

    @Override
    protected void end() {
        Logger.debug("Ending Command: ArmStop...");

        Robot.robotArm.stop();
    }

    @Override
    protected void interrupted() {
        Logger.debug("Interrupting Command: ArmStop...");

        Robot.robotArm.stop();
    }

}
