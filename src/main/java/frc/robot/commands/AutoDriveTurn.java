/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.Timer;
import frc.robot.helpers.Logger;
// Don't import Devices; Commands use OI and control Robot subsystems, but they don't access any raw devices directly
import frc.robot.Robot;

public class AutoDriveTurn extends Command {

    // Angular velocity (degrees/second) at full power - THIS IS A GUESS - CHECK IT!!
    private static double angularVelocityAtFullPower = 48.0;
    // Default target angle to rotate in degrees (positive is clock-wise)
    private static double m_defaultTargetAngle = 15;
    // Default power setting for drive: 0.0 to +1.0
    private static double m_defaultPower = .1;

    // Target angle to rotate in degrees (positive is clock-wise)
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

    // Constructor
    public AutoDriveTurn() {
        this(m_defaultTargetAngle, m_defaultPower);
    }

    public AutoDriveTurn(double targetAngle, double power) {
        Logger.debug("Constructing AutoDriveTurn...");

        // Declare subsystem dependencies
        requires(Robot.robotMecDriver);

		m_turningRight = (targetAngle > 0); // Use sign of targetAngle to determine whether turning right or left
		m_targetAngle = targetAngle;
		m_power = power;
		m_currentAngle = 0;
		m_elapsedTime = 0;
		m_timer = new Timer();
		m_angularVelocity = m_power * angularVelocityAtFullPower; // Scale velocity at full power by the current power (which is between 0 and 1.0)
		if (!m_turningRight) m_angularVelocity *= (-1.0); // Negate angular velocity if turning to the left (i.e. negative angle)
    }

    // Called just before this Command runs the first time
    @Override
    protected void initialize() {
        Logger.debug("Initializing AutoDriveTurn...");

		m_timer.reset();
		m_timer.start();
		m_elapsedTime = 0;
        m_counter = 0; 
		Logger.debug("Target = " + m_targetAngle + " degrees" + "; Power = " + m_power);
    }

    // Called repeatedly when this Command is scheduled to run
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
			Logger.debug("Executing AutoDriveTurn: Current angle = " + m_currentAngle + " degress; Elapsed time = " + m_elapsedTime + " seconds");
			m_counter = 0;
		}
    }

    // Make this return true when this Command no longer needs to run execute()
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

    // Called once after isFinished returns true
    @Override
    protected void end() {
        Logger.debug("Ending AutoDriveTurn...");

        Robot.robotMecDriver.stop();

        m_timer.stop();
        Logger.debug("Target = " + m_targetAngle + " degrees; Actual = " + m_currentAngle + " desgrees; Elapsed time = " + m_elapsedTime + " seconds");
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    @Override
    protected void interrupted() {
        Logger.debug("Interrupting AutoDriveTurn...");

        Robot.robotMecDriver.stop();

        m_timer.stop();
        Logger.debug("Target = " + m_targetAngle + " degrees; Actual = " + m_currentAngle + " desgrees; Elapsed time = " + m_elapsedTime + " seconds");
    }

}
