
package frc.robot.commands.reactive;

import edu.wpi.first.wpilibj.command.Command;

import frc.robot.consoles.Logger;
import frc.robot.OI;
import frc.robot.Robot;


// This command lowers the Arm via encoder, and keeps it there
public class ArmManual extends Command {

    public ArmManual() {
        Logger.setup("Constructing Command: ArmManual...");

        requires(Robot.robotArm);
    }

    @Override
    protected void initialize() {
        Logger.action("Initializing Command: ArmManual...");

    }

    @Override
    protected void execute() {
        Robot.robotArm.manualControl(OI.getArmLowerSpeed());
    }

    // This continues until interrupted
    @Override
    protected boolean isFinished() {
        return false;
    }

    @Override
    protected void end() {
        Logger.ending("Ending Command: ArmManual...");
    }

    @Override
    protected void interrupted() {
        Logger.ending("Interrupting Command: ArmManual...");
    }

}
