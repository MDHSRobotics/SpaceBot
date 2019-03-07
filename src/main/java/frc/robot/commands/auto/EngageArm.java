
package frc.robot.commands.auto;

import edu.wpi.first.wpilibj.command.Command;

import frc.robot.consoles.Logger;
import frc.robot.OI;
import frc.robot.Robot;


// This command is activated by a button and lowers the arm to the half position
public class EngageArm extends Command {

    public EngageArm() {
        Logger.setup("Constructing Command: EngageArm...");

        requires(Robot.robotArm);
    }

    @Override
    protected void initialize() {
        Logger.action("Initializing Command: EngageArm...");

        Robot.robotArm.lowerFull();
    }

    @Override
    protected void execute() {
        if(OI.getArmJoystick() != 0){

            Robot.robotArm.manualControl(OI.getArmJoystick());
        }
        Logger.info("ArmAuto -> Position: " + Robot.robotArm.getPosition() + "; Velocity: " + Robot.robotArm.getVelocity());
    }

    // This command finishes when the "half" position is reached
    @Override
    protected boolean isFinished() {
        return Robot.robotArm.isFullPositionMet();
        //return false;
    }

    @Override
    protected void end() {
        Logger.ending("Ending Command: EngageArm...");

        Robot.robotArm.stop();
    }

    @Override
    protected void interrupted() {
        Logger.ending("Interrupting Command: ArmAuto...");

        Robot.robotArm.stop();
    }

}
