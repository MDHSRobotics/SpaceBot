
package frc.robot.commands.auto;

import edu.wpi.first.wpilibj.command.Command;

import frc.robot.helpers.Logger;
import frc.robot.subsystems.Arm.ArmPosition;
import frc.robot.Robot;
import frc.robot.Robot.ClimbMode;


// This command is activated by a button and resets the Arm to its starting position
public class ArmResetPosition extends Command {

    public ArmResetPosition() {
        Logger.debug("Constructing Command: ArmResetPosition...");

        requires(Robot.robotArm);
    }

    @Override
    protected void initialize() {
        Logger.debug("Initializing Command: ArmResetPosition...");
    }

    @Override
    protected void execute() {
        Robot.robotArm.resetPosition();
    }

    // This command finishes when the "reset" position is reached
    @Override
    protected boolean isFinished() {
        boolean positionMet = Robot.robotArm.isPositionResetMet();
        return positionMet;
    }

    @Override
    protected void end() {
        Logger.debug("Ending Command: ArmResetPosition...");

        Robot.robotArm.stop();

        Robot.robotArm.currentArmPosition = ArmPosition.START;
        // The first climb task is to move the Arm
        Robot.robotClimbMode = ClimbMode.ARM;
    }

    @Override
    protected void interrupted() {
        Logger.debug("Interrupting Command: ArmResetPosition...");

        Robot.robotArm.stop();
    }

}
