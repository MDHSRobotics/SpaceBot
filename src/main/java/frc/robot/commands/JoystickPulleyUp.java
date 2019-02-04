
package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;

import frc.robot.helpers.Logger;
import frc.robot.Robot;


// This command is called repeatedly while the joystick button is held
// to raise the lift pulley
public class JoystickPulleyUp extends Command {

    private double m_speed = 0.5;
    
    public JoystickPulleyUp() {
        Logger.debug("Constructing JoystickPulleyUp...");

        // Declare subsystem dependencies
        requires(Robot.robotPulley);
    }

    @Override
    protected void initialize() {
        Logger.debug("Initializing JoystickPulleyUp");
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
        Logger.debug("Ending JoystickPulleyUp...");

        Robot.robotPulley.stop();
    }

    @Override
    protected void interrupted() {
        Logger.debug("Interrupted JoystickPulleUp...");

        Robot.robotPulley.stop();
    }
}
