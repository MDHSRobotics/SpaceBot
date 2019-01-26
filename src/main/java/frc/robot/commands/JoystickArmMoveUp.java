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


// This command uses 1 button to move the arm up
public class JoystickArmMoveUp extends Command {
  public JoystickArmMoveUp() {
    Logger.debug("Constructing JoystickArmMoveUp...");

    // Declare subsystem dependencies
    requires(Robot.robotArm);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    Logger.debug("Initializing JoystickArmMoveDown...");
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    Robot.robotArm.arms(.3);
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return false;
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    Logger.debug("Ending JoystickArmMoveUp...");
    Robot.robotArm.stop();
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
    Logger.debug("Interrupting JoystickArmMoveUp...");
    Robot.robotArm.stop();
  }
}