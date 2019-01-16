package org.usfirst.frc.team4141.robot.subsystems;

import org.usfirst.frc.team4141.MDRobotBase.MDRobotBase;
import org.usfirst.frc.team4141.MDRobotBase.MDSubsystem;
import org.usfirst.frc.team4141.MDRobotBase.config.ConfigSetting;
import org.usfirst.frc.team4141.robot.commands.CargoCommand;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.SpeedController;

public class CargoSubsystem extends MDSubsystem {
	
	private SpeedController cargoSpeedController;
	public static String motor1="cargoSpeedController";
	private double governor = 1.0;
	

	// ------------------------------------------------ //
	
	/**
	 * This method houses the configuring of a SpeedController as a fail-safe to check 
	 * that the SpeedController is connected and ready to be used. If the SpeedController
	 * is not connected the Robot will not enable.
	 *  
	 * @return true if the SpeedController is found, false if not.
	 */
	
	public MDSubsystem configure(){
		super.configure();
		
		if(getMotors()==null 
				|| !getMotors().containsKey(motor1))
			throw new IllegalArgumentException("Invalid motor configuration for cargo System.");
		cargoSpeedController = (SpeedController)(getMotors().get(motor1));
		
	return this;
}
	
	/**
	 * The constructor is used to hold the parameters robot and name.
	 *  
	 * @param robot the default name used after the MDRobotBase in the constructor
	 * @param name the default name used after the string in the constructor
	 */
	public CargoSubsystem(MDRobotBase robot, String name) {
		super(robot, name);
	}

	public void moveFromAxis(Joystick xbox){
		double axisValue = xbox.getRawAxis(6);
		double moveSpeed = axisValue*governor;
		cargoSpeedController.set(moveSpeed);
	
	}
	
	public void autoMove(double power){
			cargoSpeedController.set(power);
		
	}
	
	public void stop(){
		cargoSpeedController.set(0);
		// doesn't stop motor when OI has whenPressed
		
	}
	
	@Override
	protected void initDefaultCommand() {
		setDefaultCommand(new CargoCommand(this.getRobot()));
	}

	@Override
	protected void setUp() {
		if(getConfigSettings().containsKey("governor")) governor = getConfigSettings().get("governor").getDouble();
		
	}

	/**
	 * This method allows us to change the values of the variable on the fly, 
	 * without going and re-deploying the code every time we want to change the value.
	 * Instead we can now do it with the new and improved MDConsole.
	 */
	@Override
	public void settingChangeListener(ConfigSetting changedSetting) {
		if(changedSetting.getName().equals("governor")) governor = changedSetting.getDouble();

	}

	/**
	 * This method holds the default command used in a subsystem.
	 * Which is not being used in this subsystem. 
	 */

}
