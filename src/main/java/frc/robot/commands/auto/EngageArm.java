
package frc.robot.commands.auto;

import edu.wpi.first.wpilibj.command.Command;

import frc.robot.consoles.Logger;
import frc.robot.subsystems.Arm.ArmMode;
import frc.robot.OI;
import frc.robot.Robot;
import frc.robot.Robot.ClimbMode;


// This command is activated by a button and lowers the arm to the half position
public class EngageArm extends Command {

    public EngageArm() {
        Logger.setup("Constructing Command: ArmAuto...");

        requires(Robot.robotArm);
    }

    @Override
    protected void initialize() {
        Logger.action("Initializing Command: ArmAuto...");

        Robot.robotArm.lowerFull();
    }

    @Override
    protected void execute() {
        if(OI.getArmJoystick() != 0){

            Robot.robotArm.manualControl(OI.getArmJoystick());
        }
        int position = Robot.robotArm.getPosition();
        int velocity = Robot.robotArm.getVelocity();
        Logger.info("ArmAuto -> Position: " + position + "; Velocity: " + velocity);
    }

    // This command finishes when the "half" position is reached
    @Override
    protected boolean isFinished() {
        return Robot.robotArm.isFullPositionMet();
    }

    @Override
    protected void end() {
        Logger.ending("Ending Command: ArmAuto...");

        Robot.robotArm.stop();
    }

    @Override
    protected void interrupted() {
        Logger.ending("Interrupting Command: ArmAuto...");

        Robot.robotArm.stop();
    }

}
