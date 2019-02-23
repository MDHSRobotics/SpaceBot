
package frc.robot.commands.auto;

import edu.wpi.first.wpilibj.command.Command;

import frc.robot.consoles.Logger;
import frc.robot.subsystems.Arm.ArmPosition;
import frc.robot.Robot;
import frc.robot.Robot.ClimbMode;


// This command is activated by a button and lowers the arm to the half position
public class ArmLowerHalf extends Command {

    public ArmLowerHalf() {
        Logger.setup("Constructing Command: ArmLowerHalf...");

        requires(Robot.robotArm);
    }

    @Override
    protected void initialize() {
        Logger.action("Initializing Command: ArmLowerHalf...");

        Robot.robotArm.lowerHalf();
    }

    @Override
    protected void execute() {
        int position = Robot.robotArm.getPosition();
        int velocity = Robot.robotArm.getVelocity();
        Logger.info("ArmLowerHalf -> Position: " + position + "; Velocity: " + velocity);
    }

    // This command finishes when the "half" position is reached
    @Override
    protected boolean isFinished() {
        return Robot.robotArm.isHalfPositionMet();
    }

    @Override
    protected void end() {
        Logger.ending("Ending Command: ArmLowerHalf...");

        Robot.robotArm.stop();

        Logger.info("Arm Position is now HALF");
        Robot.robotArm.currentArmPosition = ArmPosition.HALF;

        // After the Arm is in the "half" position, the next climb task is to lift the robot up
        Logger.info("Climb Mode is now LIFT");
        Robot.robotClimbMode = ClimbMode.LIFT;
    }

    @Override
    protected void interrupted() {
        Logger.ending("Interrupting Command: ArmLowerHalf...");

        Robot.robotArm.stop();
    }

}
