
package frc.robot.commands.reactive;

import edu.wpi.first.wpilibj.command.Command;

import frc.robot.consoles.Logger;
import frc.robot.Robot;


// This command opens the Hatcher claw via encoder to grab the hatch, and keeps it there
public class HatchClawOpen extends Command {

    public HatchClawOpen() {
        Logger.setup("Constructing Command: HatchClawOpen...");

         // Declare subsystem dependencies
         requires(Robot.robotHatcher);
    }

    @Override
    protected void initialize() {
        Logger.action("Initializing Command: HatchClawOpen...");

        // Set the encoded position
        // TODO: I believe we need an openClaw() method on the Hatcher subsystem
        Robot.robotHatcher.openClaw();
    }

    @Override
    protected void execute() {
        // int position = Robot.robotHatcher.getPosition();
        // int velocity = Robot.robotHatcher.getVelocity();
        // Logger.info("HatchClawOpen -> Position: " + position + "; Velocity: " + velocity);
    }

    // This command is continues until interrupted
    @Override
    protected boolean isFinished() {
        return false;
    }

    @Override
    protected void end() {
        Logger.ending("Ending Command: HatchClawOpen...");
    }

    @Override
    protected void interrupted() {
        Logger.ending("Interrupting Command: HatchClawOpen...");
    }

}
