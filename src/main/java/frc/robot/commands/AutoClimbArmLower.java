/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.Timer;
import frc.robot.helpers.Logger;
// Don't import Devices; Commands use OI and control Robot subsystems, but they don't access any raw devices directly
import frc.robot.Robot;


// This command is activated by a button and lowers the arm until interrupted
public class AutoClimbArmLower extends Command {
    // Power setting for drive: 0.0 to +1.0
    private double m_power;
    //This is a temporary code that uses the timer until we  have the encoder working
    // Timer for this command
    private Timer m_timer;
    // Target duration for the timer
    private int m_target;
    

    public AutoClimbArmLower(double power) {
        Logger.debug("Constructing AutoClimbArmLower...");

        m_power = power;
        m_timer = new Timer();
        
    }

    // Called just before this Command runs the first time
    @Override
    protected void initialize() {
        Logger.debug("Initializing AutoClimbArmLower...");

        m_target = 2; //seconds
        m_timer.reset();
        m_timer.start();
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    protected void execute() {
        Robot.robotClimbArm.move(m_power);
    }

    // This command continues until it is interrupted
    @Override
    protected boolean isFinished() {
        double elapsedTime = m_timer.get();
        return (elapsedTime >= m_target);
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
