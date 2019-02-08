
package frc.robot.commands.xbox;

import edu.wpi.first.wpilibj.command.Command;

import frc.robot.helpers.Logger;
import frc.robot.Robot;


// This command is called repeatedly while the joystick button is held to lift the robot with the pulley
public class PulleyLift extends Command {
    
    public PulleyLift() {
        Logger.debug("Constructing Command: PulleyLift...");

        // Declare subsystem dependencies
        requires(Robot.robotPulley);
    }

    @Override
    protected void initialize() {
        Logger.debug("Initializing Command: PulleyLift");
    }

    @Override
    protected void execute() {
        Robot.robotPulley.lift();
    }

    // This command finishes immediately, because it is repeatedly scheduled by the button hold
    @Override
    protected boolean isFinished() {
        return true;
    }

    @Override
    protected void end() {
        Logger.debug("Ending Command: PulleyLift...");

        Robot.robotPulley.stop();
    }

    @Override
    protected void interrupted() {
        Logger.debug("Interrupted Command: PulleyLift...");

        Robot.robotPulley.stop();
    }

}
