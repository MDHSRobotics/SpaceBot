
package frc.robot.commands.reactive;

import edu.wpi.first.wpilibj.command.Command;

import frc.robot.consoles.Logger;
import frc.robot.Robot;


// This command resets the Pulley to its starting position
public class PulleyReset extends Command {

    public PulleyReset() {
        Logger.setup("Constructing Command: PulleyReset...");

        requires(Robot.robotPulley);
    }

    @Override
    protected void initialize() {
        Logger.action("Initializing Command: PulleyReset...");

        // Set the encoded position
        Robot.robotPulley.resetPosition();
    }

    @Override
    protected void execute() {
        // int position = Robot.robotPulley.getPosition();
        // int velocity = Robot.robotPulley.getVelocity();
        // Logger.info("PulleyReset -> Position: " + position + "; Velocity: " + velocity);
    }

    // This will finish when the Pulley reaches its encoded "reset" position
    @Override
    protected boolean isFinished() {
        return false;
    }

    @Override
    protected void end() {
        Logger.ending("Ending Command: PulleyReset...");
    }

    @Override
    protected void interrupted() {
        Logger.ending("Interrupting Command: PulleyReset...");
    }

}
