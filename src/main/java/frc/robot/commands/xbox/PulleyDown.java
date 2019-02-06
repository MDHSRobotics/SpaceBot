
package frc.robot.commands.xbox;

import edu.wpi.first.wpilibj.command.Command;

import frc.robot.helpers.Logger;
import frc.robot.Robot;


// This command is called repeatedly while the joystick button is held to lower the pulley
public class PulleyDown extends Command {

    private double m_speed = -0.5;
    
    public PulleyDown() {
        Logger.debug("Constructing PulleyDown...");

        // Declare subsystem dependencies
        requires(Robot.robotPulley);
    }

    @Override
    protected void initialize() {
        Logger.debug("Initializing PulleyDown");
    }

    @Override
    protected void execute() {
        Robot.robotPulley.lift(m_speed);
    }

    // This command finishes immediately
    @Override
    protected boolean isFinished() {
        return true;
    }

    @Override
    protected void end() {
        Logger.debug("Ending PulleyDown...");

        Robot.robotPulley.stop();
    }

    @Override
    protected void interrupted() {
        Logger.debug("Interrupted PulleyDown...");

        Robot.robotPulley.stop();
    }

}
