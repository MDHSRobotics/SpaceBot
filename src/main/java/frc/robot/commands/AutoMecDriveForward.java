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

public class AutoMecDriveForward extends Command {

	// Velocity (feet/second) at full power - THIS IS A GUESS - CHECK IT!!
    private static double m_velocityAtFullPower = 11.5;
    // Default desired distance to travel (in feet) - NOTE: Negative means move backwards
    private static double m_defaultTargetDistanceInFeet = 1;
    // Default power setting for drive: 0.0 to +1.0
    private static double m_defaultPower = .1;

    // Desired distance to travel (in feet) - NOTE: Negative means move backwards
    private double m_targetDistanceInFeet;
     // Power setting for drive: 0.0 to +1.0
    private double m_power;
    // Distanced traveled thus far (in feet)
    private double m_distanceTraveled;
    // Velocity (feet/second) at current power setting
    private double m_velocity;
    // Time (in seconds) that this command has executed
    private double m_elapsedTime;
    // True if moving forward; False if moving backward
    private boolean m_movingForward;
    // Timer for this command
	private Timer m_timer;
	// Counter for the timer
    private int m_counter;

    // Constructors
    public AutoMecDriveForward() {
        this(m_defaultTargetDistanceInFeet, m_defaultPower);
    }

    public AutoMecDriveForward(double targetDistanceInFeet, double power) {
        Logger.debug("Constructing AutoDriveDistance...");

        // Declare subsystem dependencies
        requires(Robot.robotMecDriver);

        m_targetDistanceInFeet = targetDistanceInFeet;
        // Use sign of distance to determine whether moving forward or backward
		m_movingForward = (targetDistanceInFeet > 0);
        m_power = power;
        // Scale velocity at full power by the current power (which is between 0 and 1.0)
		m_velocity = m_power * m_velocityAtFullPower;
		m_distanceTraveled = 0;
		m_elapsedTime = 0;
		m_timer = new Timer();
    }

    // Called just before this Command runs the first time
    @Override
    protected void initialize() {
        Logger.debug("Initializing AutoDriveDistance...");

		m_counter = 0;
		m_distanceTraveled = 0;
		m_elapsedTime = 0;
		m_timer.reset();
		m_timer.start();
		Logger.debug("Target = " + m_targetDistanceInFeet + " feet" + "; Power = " + m_power);
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    protected void execute() {
        // Keep robot moving in the requested direction
        double power = m_power;
        if (!m_movingForward) {
            power = -power;
        }
        Robot.robotMecDriver.driveStraight(power);

        // Return number of seconds since the timer was started
        m_elapsedTime = m_timer.get();
        // Distance traveled (feet) = elapsed time (seconds) * velocity (feet per second)
		m_distanceTraveled = m_elapsedTime * m_velocity;
		
		if (++m_counter >= 50) {
			Logger.debug("Distance traveled = " + m_distanceTraveled + " feet; Elapsed time = " + m_elapsedTime + " seconds");
			m_counter = 0;
		}
    }

    // The command is finished when the target distance is estimated to have been reached
    // TODO: This should be determined by an encoder
    @Override
    protected boolean isFinished() {
        return (m_distanceTraveled >= m_targetDistanceInFeet);
    }

    // Called once after isFinished returns true
    @Override
    protected void end() {
        Logger.debug("Ending AutoDriveDistance...");

        Robot.robotMecDriver.stop();

        m_timer.stop();
        Logger.debug("Target = " + m_targetDistanceInFeet + " feet; Actual = " + m_distanceTraveled + " feet; Elapsed time = " + m_elapsedTime + " seconds");
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    @Override
    protected void interrupted() {
        Logger.debug("Interrupted AutoDriveDistance...");

        Robot.robotMecDriver.stop();

        m_timer.stop();
        Logger.debug("Target = " + m_targetDistanceInFeet + " feet; Actual = " + m_distanceTraveled + " feet; Elapsed time = " + m_elapsedTime + " seconds");
    }

}
