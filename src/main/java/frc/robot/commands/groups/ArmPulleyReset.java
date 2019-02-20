/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.groups;

import edu.wpi.first.wpilibj.command.CommandGroup;

import frc.robot.consoles.Logger;
import frc.robot.commands.auto.ArmResetPosition;
import frc.robot.commands.auto.PulleyReset;

public class ArmPulleyReset extends CommandGroup {

    public ArmPulleyReset() {
        Logger.setup("Constructing Command Group: ArmPulleyReset...");

        addParallel(new ArmResetPosition());
        addParallel(new PulleyReset());
    }


}
