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

    private double m_zSpeed = .3;
    private double m_xSpeed = .3;

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
        boolean detected = Robot.robotLineDetector.lineDetected();
        if (!detected) {
            Logger.debug("Line not detected!");
            return;
        }

        double angle = Robot.robotLineDetector.getCurrentAngle();
        boolean straight = Robot.robotLineDetector.isStraight(angle);
        if (!straight) {
            double z = Robot.robotLineDetector.getCorrectedZ();
            Logger.debug("Pivot angle to correct: " + z);
            double zSpeed = m_zSpeed;
            if (z > 0) {
                zSpeed = -zSpeed;
            }
            Robot.robotMecDriver.pivot(zSpeed);
            return;
        }

        double centerX = Robot.robotLineDetector.getCurrentCenterX();
        boolean centered = Robot.robotLineDetector.isCentered(centerX);
        if (!centered) {
            double x = Robot.robotLineDetector.getCorrectedX();
            Logger.debug("Strafe pixels to correct: " + x);
            double xSpeed = m_xSpeed;
            if (x > 0) {
                xSpeed = -xSpeed;
            }
            Robot.robotMecDriver.strafe(xSpeed);
            return;
        }
    }

    // We're finished when the line looks straight and is centered enough (or a line is not detected)
    @Override
    protected boolean isFinished() {
        boolean detected = Robot.robotLineDetector.lineDetected();
        if (!detected) return true;

        boolean straight = Robot.robotLineDetector.isStraight();
        if (!straight) return false;

        boolean centered = Robot.robotLineDetector.isCentered();
        if (!centered) return false;

        return true;
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
