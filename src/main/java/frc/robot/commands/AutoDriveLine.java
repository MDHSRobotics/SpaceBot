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


// Automatically drive to line up on the line seen by the vision system.
public class AutoDriveLine extends Command {

    private double m_magnitude = -.5;

    public AutoDriveLine() {
        Logger.debug("Constructing AutoDriveLine...");

        // Declare subsystem dependencies
        requires(Robot.robotMecDriver);
    }

    // Called just before this Command runs the first time
    @Override
    protected void initialize() {
        Logger.debug("Initializing AutoDriveLine...");
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    protected void execute() {
        double angle = Robot.robotLineDetector.getCurrentAngle();
        Robot.robotMecDriver.drivePolar(m_magnitude, angle, -angle);
    }

    // We're finished when the line looks straight enough
    @Override
    protected boolean isFinished() {
        double angle = Robot.robotLineDetector.getCurrentAngle();
        return (-5 <= angle && angle <= 5);
    }

    // Called once after isFinished returns true
    @Override
    protected void end() {
        Logger.debug("Ending AutoDriveLine...");

        Robot.robotMecDriver.stop();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    @Override
    protected void interrupted() {
        Logger.debug("Interrupting AutoDriveLine...");

        Robot.robotMecDriver.stop();
    }

}
