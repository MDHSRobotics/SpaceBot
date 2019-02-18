
package frc.robot.commands.auto;

import edu.wpi.first.wpilibj.command.Command;

import frc.robot.helpers.Logger;
import frc.robot.subsystems.Arm.ArmPosition;
import frc.robot.Robot;
import frc.robot.Robot.ClimbMode;


// This command is activated by a button and lowers the arm to the half position
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

    // This command finishes when the "half" position is reached
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
        // After the Arm is in the "start" position, the next climb task is to lower arm to half
        Robot.robotClimbMode = ClimbMode.ARM;
    }

    @Override
    protected void interrupted() {
        Logger.debug("Interrupting Command: ArmResetPosition...");

        Robot.robotArm.stop();
    }

}
