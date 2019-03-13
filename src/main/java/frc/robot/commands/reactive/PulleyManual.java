
package frc.robot.commands.reactive;

import edu.wpi.first.wpilibj.command.Command;

import frc.robot.consoles.Logger;
import frc.robot.OI;
import frc.robot.Robot;


// This command lifts the Pulley via encoder, and keeps it there, with the user option to manually override
public class PulleyManual extends Command {

    public PulleyManual() {
        Logger.setup("Constructing Command: PulleyManual...");

        requires(Robot.robotPulley);
    }

    @Override
    protected void initialize() {
        Logger.action("Initializing Command: PulleyManual...");
    }

    @Override
    protected void execute() {
        // int position = Robot.robotPulley.getPosition();
        // int velocity = Robot.robotPulley.getVelocity();
        // Logger.info("PulleyUp -> Position: " + position + "; Velocity: " + velocity);
        Robot.robotPulley.setSpeed(OI.getPulleyLiftSpeed());

    }

    // This will finish when the Pulley reaches its encoded "lift" position
    @Override
    protected boolean isFinished() {
        return false;
    }

    @Override
    protected void end() {
        Logger.ending("Ending Command: PulleyLift...");
    }

    @Override
    protected void interrupted() {
        Logger.ending("Interrupting Command: PulleyLift...");
    }

}
