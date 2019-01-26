/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.helpers.Logger;
import frc.robot.Robot;


public class JoystickPulleyUp extends Command {
  private double m_speed = 0.5;
  
    public JoystickPulleyUp() {
        Logger.debug("Constructing JoystickPulleyUp...");

        // Declare subsystem dependencies
        requires(Robot.robotClimbPulley);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
      Logger.debug("Initializing JoystickPulleyUp");
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
      Robot.robotClimbPulley.lift(m_speed);
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return true;
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    Logger.debug("Ending JoystickPulleyUp...");

    Robot.robotClimbPulley.stop();
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
    Logger.debug("Interrupted JoystickPulleUp...");

    Robot.robotClimbPulley.stop();
  }
}