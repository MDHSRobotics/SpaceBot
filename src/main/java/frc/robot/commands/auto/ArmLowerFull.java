
package frc.robot.commands.auto;

import edu.wpi.first.wpilibj.command.Command;

import frc.robot.consoles.Logger;
import frc.robot.subsystems.Arm.ArmPosition;
import frc.robot.Robot;
import frc.robot.Robot.ClimbMode;


// This command is activated by a button and lowers the arm to the full position
public class ArmLowerFull extends Command {

    public ArmLowerFull() {
        Logger.setup("Constructing Command: ArmLowerFull...");

        requires(Robot.robotArm);
    }

    @Override
    protected void initialize() {
        Logger.action("Initializing Command: ArmLowerFull...");

        Robot.robotArm.lowerFull();
    }

    @Override
    protected void execute() {
        int position = Robot.robotArm.getPosition();
        int velocity = Robot.robotArm.getVelocity();
        Logger.info("ArmLowerFull -> Position: " + position + "; Velocity: " + velocity);
    }
 
    // This command finishes when the "full" position is reached
    @Override
    protected boolean isFinished() {
        return Robot.robotArm.isPositionFullMet();
    }

    @Override
    protected void end() {
        Logger.ending("Ending Command: ArmLowerFull...");

        Robot.robotArm.stop();

        Logger.info("Arm position is now FULL");
        Robot.robotArm.currentArmPosition = ArmPosition.FULL;

        // After the Arm is in the "full" position, the next climb task is give the user control over the Arm and Tank
        Logger.info("Climb Mode is now CLIMB");
        Robot.robotClimbMode = ClimbMode.CLIMB;
    }

    @Override
    protected void interrupted() {
        Logger.ending("Interrupting Command: ArmLowerFull...");

        Robot.robotArm.stop();
    }

}
