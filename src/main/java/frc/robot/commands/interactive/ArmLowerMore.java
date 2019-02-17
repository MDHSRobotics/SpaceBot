package frc.robot.commands.interactive;

import edu.wpi.first.wpilibj.command.Command;

import frc.robot.helpers.Logger;
import frc.robot.subsystems.Arm.ArmPosition;
import frc.robot.OI;
import frc.robot.Robot;


// This command is activated by a button hold, and lowers the arm beyond the full position
public class ArmLowerMore extends Command {

    public ArmLowerMore() {
        Logger.debug("Constructing Command: ArmLowerMore...");

        requires(Robot.robotArm);
    }

    @Override
    protected void initialize() {
        Logger.debug("Initializing Command: ArmLowerMore...");
    }

    @Override
    protected void execute() {
        double pos = OI.getControlXboxAxisY();
        Robot.robotArm.lowerMore(pos);
    }

    // This command continues until interrupted
    @Override
    protected boolean isFinished() {
        return false;
    }

    @Override
    protected void end() {
        Logger.debug("Ending Command: ArmLowerMore...");

        Robot.robotArm.stop();
    }

    @Override
    protected void interrupted() {
        Logger.debug("Interrupting Command: ArmLowerMore...");

        Robot.robotArm.stop();
        Robot.robotArm.currentArmPosition = ArmPosition.HALF;
    }

}
