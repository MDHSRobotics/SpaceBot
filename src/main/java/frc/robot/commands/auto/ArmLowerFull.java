package frc.robot.commands.auto;

import edu.wpi.first.wpilibj.command.Command;

import frc.robot.helpers.Logger;
import frc.robot.subsystems.Arm.ArmPosition;
import frc.robot.Robot;


// This command is activated by a button and lowers the arm to the full position
public class ArmLowerFull extends Command {

    public ArmLowerFull() {
        Logger.debug("Constructing Command: ArmLowerFull...");

        requires(Robot.robotArm);
    }

    @Override
    protected void initialize() {
        Logger.debug("Initializing Command: ArmLowerFull...");
    }

    @Override
    protected void execute() {
        Robot.robotArm.lowerFull();
    }
 
    // This command finishes when the "full" position is reached
    @Override
    protected boolean isFinished() {
        boolean positionMet = Robot.robotArm.isPositionFullMet();
        if (positionMet) Robot.robotArm.currentArmPosition = ArmPosition.FULL;
        return positionMet;
    }

    @Override
    protected void end() {
        Logger.debug("Ending Command: ArmLowerFull...");

        Robot.robotArm.stop();
    }

    @Override
    protected void interrupted() {
        Logger.debug("Interrupting Command: ArmLowerFull...");

        Robot.robotArm.stop();
    }

}
