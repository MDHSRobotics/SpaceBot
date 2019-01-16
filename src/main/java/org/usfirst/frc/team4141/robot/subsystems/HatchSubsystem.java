package org.usfirst.frc.team4141.robot.subsystems;

import org.usfirst.frc.team4141.MDRobotBase.MDRobotBase;
import org.usfirst.frc.team4141.MDRobotBase.MDSubsystem;
import org.usfirst.frc.team4141.MDRobotBase.config.ConfigSetting;
import org.usfirst.frc.team4141.robot.commands.HatchCommand;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.SpeedController;

public class HatchSubsystem extends MDSubsystem {

	private SpeedController hatchSpeedController;
	public static String motor1 = "hatchSpeedController";
	private double governor = 1.0;

	/**
	 * The constructor creates an instance of the CargoSubsystem and sets its robot and name.
	 */
	public HatchSubsystem(MDRobotBase robot, String name) {
		super(robot, name);
	}

	/**
	 * This method houses the configuring of a SpeedController as a fail-safe to check 
	 * that the SpeedController is connected and ready to be used. If the SpeedController
	 * is not connected the Robot will not enable (this appears to be a lie -- need to test.)
	 * 
	 * @return this
	 */
	public MDSubsystem configure() {
		super.configure();
		
		if (getMotors() == null || !getMotors().containsKey(motor1)) {
			throw new IllegalArgumentException("Invalid motor configuration for Hatch System.");
		}
		hatchSpeedController = (SpeedController)(getMotors().get(motor1));

		return this;
	}

	public void moveFromAxis(Joystick xbox) {
		double axisValue = xbox.getRawAxis(5);
		double moveSpeed = axisValue*governor;
		hatchSpeedController.set(moveSpeed);
	}
	
	public void autoMove(double power) {
		hatchSpeedController.set(power);
	}

	public void stop() {
		hatchSpeedController.set(0);
		// doesn't stop motor when OI has whenPressed
	}

	protected void initDefaultCommand() {
		setDefaultCommand(new HatchCommand(this.getRobot()));
	}

	@Override
	protected void setUp() {
		if (getConfigSettings().containsKey("governor")) {
			governor = getConfigSettings().get("governor").getDouble();
		}
	}

	/**
	 * This method allows us to change the values of the variable on the fly, 
	 * without going and re-deploying the code every time we want to change the value.
	 * Instead we can now do it with the new and improved MDConsole.
	 */
	@Override
	public void settingChangeListener(ConfigSetting changedSetting) {
		if (changedSetting.getName().equals("governor")) {
			governor = changedSetting.getDouble();
		}
	}

}
