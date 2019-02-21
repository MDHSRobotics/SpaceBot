
package frc.robot.commands.auto;

import edu.wpi.first.wpilibj.command.Command;

import frc.robot.consoles.Logger;
import frc.robot.subsystems.Arm.ArmPosition;
import frc.robot.Robot;
import frc.robot.Robot.ClimbMode;


// This command is activated by a button and resets the Arm to its starting position
public class ArmReset extends Command {

    public ArmReset() {
        Logger.setup("Constructing Command: ArmReset...");

        requires(Robot.robotArm);
    }

    @Override
    protected void initialize() {
        Logger.action("Initializing Command: ArmReset...");

        Robot.robotArm.resetPosition();
    }

    @Override
    protected void execute() {
        int position = Robot.robotArm.getPosition();
        int velocity = Robot.robotArm.getVelocity();
        Logger.info("ArmReset -> Position: " + position + "; Velocity: " + velocity);
    }

    // This command finishes when the "reset" position is reached
    @Override
    protected boolean isFinished() {
        return Robot.robotArm.isArmPositionResetMet();
    }

    @Override
    protected void end() {
        Logger.ending("Ending Command: ArmReset...");

        Robot.robotArm.stop();

        Logger.info("Arm position is now START");
        Robot.robotArm.currentArmPosition = ArmPosition.START;

        // The first climb task is to move the Arm
        Logger.info("Climb Mode is now ARM");
        Robot.robotClimbMode = ClimbMode.ARM;
    }

    @Override
    protected void interrupted() {
        Logger.ending("Interrupting Command: ArmReset...");

        Robot.robotArm.stop();
    }

}
