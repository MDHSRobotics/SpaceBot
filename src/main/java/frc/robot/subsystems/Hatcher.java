/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.commands.IdleHatch;
import frc.robot.helpers.Logger;
// Don't import OI; Subsystems control robot devices, they don't access HIDs -- commands do that
import frc.robot.Devices;

// Hatch subsystem
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

        IdleHatch defaultCmd = new IdleHatch();
        setDefaultCommand(defaultCmd);
    }

    // Stop all the drive motors
    public void stop() {
        Devices.talonSrxHatch.stopMotor();
    }

    // Opens or closes the Hatcher claw based on speed
    public void claw(double speed) {
        Devices.talonSrxHatch.set(speed);
    }

}
