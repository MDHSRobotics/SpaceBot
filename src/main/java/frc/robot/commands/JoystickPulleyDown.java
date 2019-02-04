
package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;

import frc.robot.helpers.Logger;
import frc.robot.Robot;


// This command is called repeatedly while the joystick button is held
// to lower the lift pulley
public class JoystickPulleyDown extends Command {

    private double m_speed = -0.5;
    
    public JoystickPulleyDown() {
        Logger.debug("Constructing JoystickPulleyDown...");

        // Declare subsystem dependencies
        requires(Robot.robotPulley);
    }

    @Override
    protected void initialize() {
        Logger.debug("Initializing JoystickPulleyDown");
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
        Logger.debug("Ending JoystickPulleyDown...");

        Robot.robotPulley.stop();
    }

    @Override
    protected void interrupted() {
        Logger.debug("Interrupted JoystickPulleDown...");

        Robot.robotPulley.stop();
    }

}
