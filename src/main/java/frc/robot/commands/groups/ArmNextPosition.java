/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.groups;
import frc.robot.Robot;
import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.commands.auto.ArmLowerHalf;
import frc.robot.commands.auto.ArmLowerFull;
import frc.robot.commands.auto.ArmLowerMore;
import frc.robot.commands.idle.ArmStop;

public class ArmNextPosition extends CommandGroup {

    public ArmNextPosition() {
        switch(Robot.robotArm.currentArmPosition) {
            case START:
                addSequential(new ArmLowerHalf());
                break;

            case HALF:
                addSequential(new ArmLowerFull());
                break;

            case FULL:
                addSequential(new ArmLowerMore());
                break;

            case MORE:
                addSequential(new ArmStop());
                break;
        }
    }
}
