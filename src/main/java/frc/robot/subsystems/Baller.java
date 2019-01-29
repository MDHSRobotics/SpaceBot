/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;

import frc.robot.commands.IdleBaller;
import frc.robot.helpers.Logger;
import frc.robot.Devices;


// Cargo ball subsystem
public class Baller extends Subsystem {

    private double m_secondsFromNeutralToFull = 1.0;
    private int m_timeoutMS = 10;
    private double m_speed = 0.2; 

    public Baller() {
        Logger.debug("Constructing Baller...");

        Devices.talonSrxBaller.configOpenloopRamp(m_secondsFromNeutralToFull, m_timeoutMS);
    }

    @Override
    public void initDefaultCommand() {
        Logger.debug("Initializing Baller default command...");

        setDefaultCommand(new IdleBaller());
    }

    // Stop all the drive motors
    public void stop() {
        Devices.talonSrxBaller.stopMotor();
    }

    // Deploys the ball
    public void deploy() {
        Devices.talonSrxBaller.set(m_speed);
    }

    // Blocks the ball
    public void block() {
        Devices.talonSrxBaller.set(-m_speed);    
    }

    // Determines if the Ball Deployed limit switch is active
    public boolean isDeployed() {
        boolean hitLimit = Devices.limitSwitchBallDeployed.get();
        if (hitLimit) {
            Logger.debug("Ball has been deployed!");
        }
        return hitLimit;
    }

    // Determines if the Ball Blocked limit switch is active
    public boolean isBlocked() {
        boolean hitLimit = Devices.limitSwitchBallBlocked.get();
        if (hitLimit) {
            Logger.debug("Ball is now blocked!");
        }
        return hitLimit;
    }

}
