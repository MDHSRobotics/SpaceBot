
package frc.robot.commands.reactive;

import edu.wpi.first.wpilibj.command.Command;

import frc.robot.consoles.Logger;
import frc.robot.Robot;


// This command resets the Arm to its starting position via encoder, and keeps it there
public class ArmReset extends Command {

    public ArmReset() {
        Logger.setup("Constructing Command: ArmReset...");

        requires(Robot.robotArm);
    }

    @Override
    protected void initialize() {
        Logger.action("Initializing Command: ArmReset...");

        // Set the encoded position
        Robot.robotArm.resetPosition();
    }

    @Override
    protected void execute() {
        // int position = Robot.robotArm.getPosition();
        // int velocity = Robot.robotArm.getVelocity();
        // Logger.info("ArmReset -> Position: " + position + "; Velocity: " + velocity);
    }

    // This continues until interrupted
    @Override
    protected boolean isFinished() {
        return false;
    }

    @Override
    protected void end() {
        Logger.ending("Ending Command: ArmReset...");
    }

    @Override
    protected void interrupted() {
        Logger.ending("Interrupting Command: ArmReset...");
    }

}
