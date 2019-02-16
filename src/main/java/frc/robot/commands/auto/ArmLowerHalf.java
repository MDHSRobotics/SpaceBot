package frc.robot.commands.auto;

import edu.wpi.first.wpilibj.command.Command;

import frc.robot.subsystems.Arm.ArmPosition;
import frc.robot.helpers.Logger;
import frc.robot.Robot;


// This command is activated by a button and lowers the arm to the half position
public class ArmLowerHalf extends Command {

    public ArmLowerHalf() {
        Logger.debug("Constructing Command: ArmLowerHalf...");
        requires(Robot.robotArm);
    }

    @Override
    protected void initialize() {
        Logger.debug("Initializing Command: ArmLowerHalf...");
    }

    @Override
    protected void execute() {
        Robot.robotArm.lowerHalf();
    }

    // This command continues for a certain period of time
    // Will be replaced be encoder logic
    @Override
    protected boolean isFinished() {
        if(Robot.robotArm.isPositionHalfMet()){
            Robot.robotArm.currentArmPosition = ArmPosition.HALF;
            return true;
        } 
        return false;
    }

    @Override
    protected void end() {
        Logger.debug("Ending Command: ArmLowerHalf...");

        Robot.robotArm.stop();
    }

    @Override
    protected void interrupted() {
        Logger.debug("Interrupting Command: ArmLowerHalf...");

        Robot.robotArm.stop();
    }

}
