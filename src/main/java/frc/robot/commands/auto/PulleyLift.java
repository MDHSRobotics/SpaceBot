
package frc.robot.commands.auto;

import edu.wpi.first.wpilibj.command.Command;

import frc.robot.consoles.Logger;
import frc.robot.OI;
import frc.robot.Robot;


// This command lifts the Pulley via encoder, and keeps it there, with the user option to manually override
public class PulleyLift extends Command {

    public PulleyLift() {
        Logger.setup("Constructing Command: PulleyLift...");

        requires(Robot.robotPulley);
    }

    @Override
    protected void initialize() {
        Logger.action("Initializing Command: PulleyLift...");

        // Set the encoded position
        Robot.robotPulley.lift();
    }

    @Override
    protected void execute() {
        // TODO: You are getting the input from the controller twice,
        //       which is slower, and also could give you different results.
        //       Instead, get the value and put it into a variable, and use that.
        //       (This is good practice anyway, as it forces you to name the value,
        //       which serves as documentation via code, and is easier for others to read and understand.)
        if (OI.getPulleyLiftSpeed() != 0) {
            // TODO: When this happens, set a state on the Pulley subsystem to indicate it's now under manual control.
            //       Check that state first, and if it's manual, don't check that the input is not zero, just use the value.
            Robot.robotPulley.setSpeed(OI.getPulleyLiftSpeed());
            // TODO: I know I said otherwise previously, but now I do think it might be better to have this start a new command instead.
        }

        // TODO: Comment out Logger output once this is determined to be working reliably.
        //       Excess logging during executes can slow things down and spam the log.
        int position = Robot.robotPulley.getPosition();
        int velocity = Robot.robotPulley.getVelocity();
        Logger.info("PulleyUp -> Position: " + position + "; Velocity: " + velocity);
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
