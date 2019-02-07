
package frc.robot.commands.auto;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.Timer;

import frc.robot.helpers.Logger;
import frc.robot.Robot;


// This command rotates the MecDrive clockwise until is reaches its target
public class MecDriveTurnRight extends Command {

    // Angular velocity (degrees/second) at full power - THIS IS A GUESS - CHECK IT!!
    private static double angularVelocityAtFullPower = 48.0;
    // Default target angle to rotate in degrees (positive is clockwise)
    private static double m_defaultTargetAngle = 15;
    // Default power setting for drive: 0.0 to +1.0
    private static double m_defaultPower = .1;

    // Target angle to rotate in degrees (positive is clockwise)
    private double m_targetAngle;
    // Power setting for drive: 0.0 to +1.0
    private double m_power;
    // The current angle
    private double m_currentAngle;
    // Time (in seconds) that this command has executed
	private double m_elapsedTime;
    // Angular velocity (degrees/second) at current power setting
    private double m_angularVelocity;
    // True if turning right; False if turning left 
    private boolean m_turningRight;
    // Timer for this command
	private Timer m_timer;
	// Counter for the timer
    private int m_counter;

    // Constructors
    public MecDriveTurnRight() {
        this(m_defaultTargetAngle, m_defaultPower);
    }

    public MecDriveTurnRight(double targetAngle, double power) {
        Logger.debug("Constructing Command: MecDriveTurnRight...");

        // Declare subsystem dependencies
        requires(Robot.robotMecDriver);

        // Use sign of targetAngle to determine whether turning right or left
		m_turningRight = (targetAngle > 0);
		m_targetAngle = targetAngle;
		m_power = power;
		m_currentAngle = 0;
		m_elapsedTime = 0;
        m_timer = new Timer();
        // Scale velocity at full power by the current power (which is between 0 and 1.0)
		m_angularVelocity = m_power * angularVelocityAtFullPower;
		if (!m_turningRight) {
            // Negate angular velocity if turning to the left (i.e. negative angle)
            m_angularVelocity *= (-1.0);
        }
    }

    @Override
    protected void initialize() {
        Logger.debug("Initializing Command: MecDriveTurnRight...");

		m_timer.reset();
		m_timer.start();
		m_elapsedTime = 0;
        m_counter = 0;
		Logger.debug("Target = " + m_targetAngle + " degrees" + "; Power = " + m_power);
    }

    @Override
    protected void execute() {
		// Keep robot moving in the requested direction
		if (m_turningRight) {
			Robot.robotMecDriver.pivot(m_power);
		}
		else {
			Robot.robotMecDriver.pivot(-m_power);
		}

        // Number of seconds since the timer was started
		m_elapsedTime = m_timer.get();
        // Degrees turned (degrees) = elapsed time (seconds) * angular velocity (degrees per second)
		m_currentAngle = m_elapsedTime * m_angularVelocity;
		if (++m_counter >= 50) {
			Logger.debug("Executing Command: MecDriveTurnRight: Current angle = " + m_currentAngle + " degress; Elapsed time = " + m_elapsedTime + " seconds");
			m_counter = 0;
		}
    }

    // This command is finished when the target angle is estimated to have been reached
    // TODO: This should be determined by an encoder
    @Override
    protected boolean isFinished() {
		if (m_targetAngle < 0.) {
			// Negative target angle - that is, counter-clockwise rotation
			return (m_currentAngle <= m_targetAngle);
		}
		else {
			// Positive target angle - that is, clockwise rotation
			return (m_currentAngle >= m_targetAngle);
		}
    }

    @Override
    protected void end() {
        Logger.debug("Ending Command: MecDriveTurnRight...");

        Robot.robotMecDriver.stop();

        m_timer.stop();
        Logger.debug("Target = " + m_targetAngle + " degrees; Actual = " + m_currentAngle + " desgrees; Elapsed time = " + m_elapsedTime + " seconds");
    }

    @Override
    protected void interrupted() {
        Logger.debug("Interrupting Command: MecDriveTurnRight...");

        Robot.robotMecDriver.stop();

        m_timer.stop();
        Logger.debug("Target = " + m_targetAngle + " degrees; Actual = " + m_currentAngle + " desgrees; Elapsed time = " + m_elapsedTime + " seconds");
    }

}
