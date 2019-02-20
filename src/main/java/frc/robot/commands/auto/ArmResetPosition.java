
package frc.robot.commands.auto;

import edu.wpi.first.wpilibj.command.Command;

import frc.robot.consoles.Logger;
import frc.robot.subsystems.Arm.ArmPosition;
import frc.robot.Robot;
import frc.robot.Robot.ClimbMode;


// This command is activated by a button and resets the Arm to its starting position
public class ArmResetPosition extends Command {

    public ArmResetPosition() {
        Logger.setup("Constructing Command: ArmResetPosition...");

        requires(Robot.robotArm);
    }

    @Override
    protected void initialize() {
        Logger.action("Initializing Command: ArmResetPosition...");

        Robot.robotArm.resetArmPosition();
    }

    @Override
    protected void execute() {
        
    }

    // This command finishes when the "reset" position is reached
    @Override
    protected boolean isFinished() {
        boolean positionMet = Robot.robotArm.isArmPositionResetMet();
        return positionMet;
    }

    @Override
    protected void end() {
        Logger.ending("Ending Command: ArmResetPosition...");

        Robot.robotArm.stop();

        Robot.robotArm.currentArmPosition = ArmPosition.START;
        // The first climb task is to move the Arm
        Robot.robotClimbMode = ClimbMode.ARM;
    }

    @Override
    protected void interrupted() {
        Logger.ending("Interrupting Command: ArmResetPosition...");

        Robot.robotArm.stop();
    }

}
