package frc.robot.commands.interactive;

import edu.wpi.first.wpilibj.command.Command;

import frc.robot.consoles.Logger;
import frc.robot.subsystems.Arm.ArmPosition;
import frc.robot.OI;
import frc.robot.Robot;


// This command is activated by a button hold, and lowers the arm beyond the full position
public class ArmLowerMore extends Command {

    public ArmLowerMore() {
        Logger.setup("Constructing Command: ArmLowerMore...");

        requires(Robot.robotArm);
    }

    @Override
    protected void initialize() {
        Logger.action("Initializing Command: ArmLowerMore...");
    }

    @Override
    protected void execute() {
        int position = Robot.robotArm.getPosition();
        int velocity = Robot.robotArm.getVelocity();
        double speed = OI.getArmLowerMoreSpeed();
        Logger.info("ArmLowerMore -> Position: " + position + "; Velocity: " + velocity + "; Speed: " + speed);
        Robot.robotArm.lowerMore(speed);
    }

    // This command continues until interrupted
    @Override
    protected boolean isFinished() {
        return Robot.robotArm.isMorePositionMet();
    }

    @Override
    protected void end() {
        Logger.ending("Ending Command: ArmLowerMore...");

        Robot.robotArm.stop();

        Robot.robotArm.currentArmPosition = ArmPosition.MORE;
    }

    @Override
    protected void interrupted() {
        Logger.ending("Interrupting Command: ArmLowerMore...");

        Robot.robotArm.stop();
    }

}
