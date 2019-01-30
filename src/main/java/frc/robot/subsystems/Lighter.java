/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.helpers.Logger;
import frc.robot.commands.AutoLighterOff;

// Lighter Subsystem
public class Lighter extends Subsystem {

    public Lighter() {
        Logger.debug("Constructing Lighter...");

    }

    @Override
    public void initDefaultCommand() {
        Logger.debug("Initializing Lighter default command...");

        setDefaultCommand(new AutoLighterOff());
    }


    public boolean isOn() {
    return true;
    }

    public boolean isOff() {
    return false;
    }

}