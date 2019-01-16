package org.usfirst.frc.team4141.robot.commands;

import org.usfirst.frc.team4141.MDRobotBase.MDCommand;
import org.usfirst.frc.team4141.MDRobotBase.MDJoystick;
import org.usfirst.frc.team4141.MDRobotBase.MDRobotBase;
import org.usfirst.frc.team4141.robot.subsystems.HatchSubsystem;

public class HatchCommand extends MDCommand {
	
	private HatchSubsystem hatchSubsystem;
	
	// ------------------------------------------------ //
	
	/**
	 * The constructor is used to hold the parameters robot and name.
	 * Within the constructor is a fail-safe to check that the hatchSubsystem
	 * is connected and ready to be used. If the hatchSubsystem is not connected 
	 * the Robot will not enable.
	 *  
	 * @param robot the default name used after the MDRobotBase in the constructor
	 * @return true if the hatchSubsystem is found, false if not.
	 */
	public HatchCommand(MDRobotBase robot) {
		super(robot, "HatchCommand");

		hatchSubsystem = (HatchSubsystem)getRobot().getSubsystem("hatchSubsystem"); 
		requires(hatchSubsystem);
	}

	// ------------------------------------------------ //
	
	private MDJoystick axis = null;
	
	protected void initialize() {
			super.initialize();
			axis = getRobot().getOi().getJoysticks().get("xbox");
			System.out.println("initializing HatchCommand");
			
		}
	
	/**
	 * @return false because the command never ends by itself.
	 */
	protected boolean isFinished() {
		return false;
	}
	
	protected void execute() {
		if (hatchSubsystem!=null)hatchSubsystem.moveFromAxis(axis);
//		log(Level.DEBUG,"execute()","Moving hatch arms");
	}
	
	/**
	 * This signifies the end of the command by stopping the ropeSubsystem motors
	 * due to the halt of input by the driver.
	 */
	@Override
		protected void end() {
		super.end();
		hatchSubsystem.stop();
			
		}
}
