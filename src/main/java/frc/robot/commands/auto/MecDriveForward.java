
package frc.robot.commands.auto;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.Timer;

import frc.robot.helpers.Logger;
import frc.robot.Robot;


// This command drives the MecDrive forward until is reaches its target
public class MecDriveForward extends Command {

    // Constants
    private final double VELOCITY_AT_FULL_POWER = 11.5; // Velocity (feet/second) at full power - THIS IS A GUESS - CHECK IT!!
    private final static double DEFAULT_TARGET_DISTANCE_IN_FEET = 1; // Default desired distance to travel (in feet) - NOTE: Negative means move backwards
    private final static double DEFAULT_POWER = .1; // Default power setting for drive: 0.0 to +1.0

    // Private Member Variables
    private double m_targetDistanceInFeet; // Desired distance to travel (in feet) - NOTE: Negative means move backwards
    private double m_power; // Power setting for drive: 0.0 to +1.0
    private double m_distanceTraveled; // Distanced traveled thus far (in feet)
    private double m_velocity; // Velocity (feet/second) at current power setting
    private double m_elapsedTime; // Time (in seconds) that this command has executed
    private boolean m_movingForward; // True if moving forward; False if moving backward
	private Timer m_timer; // Timer for this command
    private int m_counter; // Counter for the timer

    // Constructors
    public MecDriveForward() {
        this(DEFAULT_TARGET_DISTANCE_IN_FEET, DEFAULT_POWER);
    }

    public MecDriveForward(double targetDistanceInFeet, double power) {
        Logger.debug("Constructing Command: MecDriveForward...");

        // Declare subsystem dependencies
        requires(Robot.robotMecDriver);

        m_targetDistanceInFeet = targetDistanceInFeet;
        // Use sign of distance to determine whether moving forward or backward
		m_movingForward = (targetDistanceInFeet > 0);
        m_power = power;
        // Scale velocity at full power by the current power (which is between 0 and 1.0)
		m_velocity = m_power * VELOCITY_AT_FULL_POWER;
		m_distanceTraveled = 0;
		m_elapsedTime = 0;
		m_timer = new Timer();
    }

    @Override
    protected void initialize() {
        Logger.debug("Initializing Command: MecDriveForward...");

		m_counter = 0;
		m_distanceTraveled = 0;
		m_elapsedTime = 0;
		m_timer.reset();
		m_timer.start();
		Logger.debug("Target = " + m_targetDistanceInFeet + " feet" + "; Power = " + m_power);
    }

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

    @Override
    protected void end() {
        Logger.debug("Ending Command: MecDriveForward...");

        Robot.robotMecDriver.stop();

        m_timer.stop();
        Logger.debug("Target = " + m_targetDistanceInFeet + " feet; Actual = " + m_distanceTraveled + " feet; Elapsed time = " + m_elapsedTime + " seconds");
    }

    @Override
    protected void interrupted() {
        Logger.debug("Interrupting Command: MecDriveForward...");

        Robot.robotMecDriver.stop();

        m_timer.stop();
        Logger.debug("Target = " + m_targetDistanceInFeet + " feet; Actual = " + m_distanceTraveled + " feet; Elapsed time = " + m_elapsedTime + " seconds");
    }

}
