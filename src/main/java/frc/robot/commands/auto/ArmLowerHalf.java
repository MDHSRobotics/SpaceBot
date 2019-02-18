
package frc.robot.commands.auto;

import edu.wpi.first.wpilibj.command.Command;

import frc.robot.helpers.Logger;
import frc.robot.subsystems.Arm.ArmPosition;
import frc.robot.Robot;
import frc.robot.Robot.ClimbMode;


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

    // This command finishes when the "half" position is reached
    @Override
    protected boolean isFinished() {
        boolean positionMet = Robot.robotArm.isPositionHalfMet();
        return positionMet;
    }

    @Override
    protected void end() {
        Logger.debug("Ending Command: ArmLowerHalf...");

        Robot.robotArm.stop();

        Robot.robotArm.currentArmPosition = ArmPosition.HALF;
        // After the Arm is in the "half" position, the next climb task is to lift the robot up
        Robot.robotClimbMode = ClimbMode.LIFT;
    }

    @Override
    protected void interrupted() {
        Logger.debug("Interrupting Command: ArmLowerHalf...");

        Robot.robotArm.stop();
    }

}
