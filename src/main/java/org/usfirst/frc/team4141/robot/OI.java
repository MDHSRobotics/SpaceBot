package org.usfirst.frc.team4141.robot;

import org.usfirst.frc.team4141.MDRobotBase.ConsoleOI;
import org.usfirst.frc.team4141.MDRobotBase.MDJoystick;
import org.usfirst.frc.team4141.MDRobotBase.MDRobotBase;
import org.usfirst.frc.team4141.MDRobotBase.OIBase;
import org.usfirst.frc.team4141.MDRobotBase.RioHID;
import org.usfirst.frc.team4141.robot.autocommands.DriveDistanceCommand;
import org.usfirst.frc.team4141.robot.autocommands.TurnCommand;
import org.usfirst.frc.team4141.robot.commands.MDPrintCommand;
import org.usfirst.frc.team4141.robot.commands.ToggleOrientationCommand;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 * 
 * We use devices including but not limited to:
 * 	Xbox Controller
 * 	Flight Simulator Controller
 * 
 */
public class OI extends OIBase {

	public OI(MDRobotBase robot) {
		super(robot);
		System.out.println("OI created");
	}

	/**
	 * A robot should have 1 or more operator interfaces (OI).
	 * Typically, a robot will have at least 1 joystick and 1 console (MDConsole in our instance).
	 * We control what buttons are mapped in both the console and the joystick.
	 * We configure the joysticks below.
	 */	
	public void configureOI() {
		// Joysticks
		MDJoystick jstick = new MDJoystick(getRobot(), "joystick", 0);
		jstick.whenPressed("5", 5, new ToggleOrientationCommand(getRobot(), "ToggleOrientationCommand"));
		jstick.whenPressed("8", 8, new DriveDistanceCommand(getRobot(), "DriveDistanceCommand", 10.0 , 1.0));
		jstick.whenPressed("7", 7, new TurnCommand(getRobot(), "TurnCommand", 90.0 , 1.0));
		jstick.configure();
		add(jstick);

		MDJoystick xbox = new MDJoystick(getRobot(), "xbox", 1);
		xbox.configure();
		add(xbox);

		// Configure the RioHID.
		// Attach a command to the user button on the RoboRIO.
		RioHID rhid = new RioHID(getRobot());
		rhid.whileHeld(new MDPrintCommand(getRobot(), "ExampleCommand1", "ExampleCommand1 message"));
		rhid.configure();
		add(rhid);

		// Configure the MDConsole "Joystick".
		// We add commands here that will show up as buttons in the MDConsole.
		ConsoleOI coi = new ConsoleOI(getRobot());
		coi.configure();
		add(coi);
	}

}
