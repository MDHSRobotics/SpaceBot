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


public class IdlePulley extends Command {
  public IdlePulley() {
    Logger.debug("Constucting IdlePulley...");

        //Declare subsysted dependencies
        requires(Robot.robotClimbPulley);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    Logger.debug("Initializing IdlePulley...");
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    Robot.robotClimbPulley.stop();
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return false;
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    Logger.debug("Ending IdlePulley...");

    Robot.robotClimbPulley.stop();
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
    Logger.debug("Interrupting IdlePulley...");

    Robot.robotClimbPulley.stop();
}
  }

