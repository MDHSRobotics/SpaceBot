/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.helpers.Logger;
// Don't import Devices; Commands use OI and control Robot subsystems, but they don't access any raw devices directly
import frc.robot.Robot;

//This command idles the Arm motor
public class IdleArm extends Command {

  public IdleArm() {
      Logger.debug("Constucting IdleArm...");

      //Declare subsysted dependencies
      requires(Robot.robotArm);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
      Logger.debug("Initializing IdleArm...");
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
      Robot.robotArm.stop();
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return false;
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
      Logger.debug("Ending IdleArm...");

      Robot.robotArm.stop();
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
      Logger.debug("Interrupting IdleArm...");

      Robot.robotArm.stop();
  }
}
