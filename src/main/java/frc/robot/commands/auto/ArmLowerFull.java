package frc.robot.commands.auto;

import edu.wpi.first.wpilibj.command.Command;

import frc.robot.helpers.Logger;
import frc.robot.subsystems.Arm.ArmPosition;
import frc.robot.Robot;
import frc.robot.Robot.ClimbMode;


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
        return positionMet;
    }

    @Override
    protected void end() {
        Logger.debug("Ending Command: ArmLowerFull...");

        Robot.robotArm.stop();

        Robot.robotArm.currentArmPosition = ArmPosition.FULL;
        // After the Arm is in the "full" position, the next climb task is give the user control over the Arm and Tank
        Robot.robotClimbMode = ClimbMode.CLIMB;
    }

    @Override
    protected void interrupted() {
        Logger.debug("Interrupting Command: ArmLowerFull...");

        Robot.robotArm.stop();
    }

}
