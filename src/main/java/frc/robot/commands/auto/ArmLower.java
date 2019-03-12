
package frc.robot.commands.auto;

import edu.wpi.first.wpilibj.command.Command;

import frc.robot.consoles.Logger;
import frc.robot.Robot;


// This command lowers the Arm via encoder, and keeps it there
public class ArmLower extends Command {

    public ArmLower() {
        Logger.setup("Constructing Command: ArmLower...");

        requires(Robot.robotArm);
    }

    @Override
    protected void initialize() {
        Logger.action("Initializing Command: ArmLower...");

        // Set the encoded position
        Robot.robotArm.lower();
    }

    @Override
    protected void execute() {
        int position = Robot.robotArm.getPosition();
        // int velocity = Robot.robotArm.getVelocity();
        // Logger.info("ArmLower -> Position: " + position + "; Velocity: " + velocity);

        // TODO: The Pulley needs to start automatically when the Arm contacts the platform
    }

    // This continues until interrupted
    @Override
    protected boolean isFinished() {
        return false;
    }

    @Override
    protected void end() {
        Logger.ending("Ending Command: ArmLower...");
    }

    @Override
    protected void interrupted() {
        Logger.ending("Interrupting Command: ArmLower...");
    }

}
