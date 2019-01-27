/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;

import frc.robot.helpers.Logger;
import frc.robot.helpers.PolarMovement;
import frc.robot.OI;
import frc.robot.Robot;


// This command uses the joystick input to drive using the polar method
public class JoystickDrivePolar extends Command {

    // Constructor
    public JoystickDrivePolar() {
        Logger.debug("Constructing JoystickDrivePolar...");

        // Declare subsystem dependencies
        requires(Robot.robotMecDriver);
    }

    // Called just before this Command runs the first time
    @Override
    protected void initialize() {
        Logger.debug("Initializing JoystickDrivePolar...");
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    protected void execute() {
        PolarMovement move = OI.getPolarMovementFromJoystick(Robot.robotMecDriver.isFlipped);
        Robot.robotMecDriver.drivePolar(move.magnitude, move.angle, move.rotation);
    }

    // This command continues to run until it is interrupted
    @Override
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    @Override
    protected void end() {
        Logger.debug("Ending JoystickDrivePolar...");

        Robot.robotMecDriver.stop();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    @Override
    protected void interrupted() {
        Logger.debug("Interrupting JoystickDrivePolar...");

        Robot.robotMecDriver.stop();
    }

}
