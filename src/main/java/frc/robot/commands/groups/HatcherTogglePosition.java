
package frc.robot.commands.groups;

import edu.wpi.first.wpilibj.command.CommandGroup;

import frc.robot.commands.auto.HatchGrab;
import frc.robot.commands.auto.HatchRelease;
import frc.robot.Robot;


public class HatcherTogglePosition extends CommandGroup {

    public HatcherTogglePosition() {
        if (Robot.robotHatcher.hatchIsGrabbed) {
            addSequential(new HatchRelease());
        }
        else {
            addSequential(new HatchGrab());
        }
        Robot.robotHatcher.toggleHatchGrabbed();
    }

}
