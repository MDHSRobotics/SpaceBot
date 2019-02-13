
package frc.robot.commands.interactive;

import edu.wpi.first.wpilibj.command.Command;

import frc.robot.helpers.Logger;
import frc.robot.Robot;


// This command is called repeatedly while the joystick button is held to lower the robot with the pulley
public class PulleyLower extends Command {
    
    public PulleyLower() {
        Logger.debug("Constructing Command: PulleyLower...");

        // Declare subsystem dependencies
        requires(Robot.robotPulley);
    }

    @Override
    protected void initialize() {
        Logger.debug("Initializing Command: PulleyLower");
    }

    @Override
    protected void execute() {
        Robot.robotPulley.lower();
    }

    // This command finishes immediately, because it is repeatedly scheduled by the button hold
    @Override
    protected boolean isFinished() {
        return true;
    }

    @Override
    protected void end() {
        Logger.debug("Ending Command: PulleyLower...");

        Robot.robotPulley.stop();
    }

    @Override
    protected void interrupted() {
        Logger.debug("Interrupted Command: PulleyLower...");

        Robot.robotPulley.stop();
    }

}
