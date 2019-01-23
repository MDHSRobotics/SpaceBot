/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.Devices;
import frc.robot.helpers.Logger;

public class Hatcher extends Subsystem {

    private double m_secondsFromNeutralToFull = 1.0;
    private int m_timeoutMS = 10;

    public Hatcher() {
        Logger.debug("Constructing Hatcher...");
        Devices.talonSrxHatch.configOpenloopRamp(m_secondsFromNeutralToFull, m_timeoutMS);
    }

    @Override
    public void initDefaultCommand() {
        Logger.debug("Initializing Hatcher default command...");
    }

    // Stop all the drive motors
    public void stop() {
        Devices.talonSrxHatch.stopMotor();
    }

    //moves the hatch in a + or - direction based on speed
    public void claw(double speed) {
        Devices.talonSrxHatch.set(speed);
    }
}
