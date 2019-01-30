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


// This command is activated by a button and lowers the arm until interrupted
public class AutoClimbArmLower extends Command {

    // TODO: This speed is the same as the AutoClimbArmRaise command. One of these should be negative.
    private double m_speed = 0.3;

    public AutoClimbArmLower() {
        Logger.debug("Constructing AutoClimbArmLower...");

        // Declare subsystem dependencies
        requires(Robot.robotClimbArm);
    }

    // Called just before this Command runs the first time
    @Override
    protected void initialize() {
        Logger.debug("Initializing AutoClimbArmLower...");
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    protected void execute() {
        Robot.robotClimbArm.move(m_speed);
    }

    // This command continues until it is interrupted
    @Override
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    @Override
    protected void end() {
        Logger.debug("Ending AutoClimbArmLower...");

        Robot.robotClimbArm.stop();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    @Override
    protected void interrupted() {
        Logger.debug("Interrupting AutoClimbArmLower...");

        Robot.robotClimbArm.stop();
    }

}
