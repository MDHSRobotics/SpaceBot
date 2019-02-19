
package frc.robot.commands.idle;

import edu.wpi.first.wpilibj.command.Command;

import frc.robot.consoles.Logger;
import frc.robot.Robot;


// This command stops the Arm motor
public class ArmStop extends Command {

    public ArmStop() {
        Logger.setup("Constructing Command: ArmStop...");

        // Declare subsystem dependencies
        requires(Robot.robotArm);
    }

    @Override
    protected void initialize() {
        Logger.action("Initializing Command: ArmStop...");
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
        Logger.ending("Ending Command: ArmStop...");

        Robot.robotArm.stop();
    }

    @Override
    protected void interrupted() {
        Logger.ending("Interrupting Command: ArmStop...");

        Robot.robotArm.stop();
    }

}
