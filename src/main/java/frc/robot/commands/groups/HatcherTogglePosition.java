
package frc.robot.commands.groups;

import edu.wpi.first.wpilibj.command.CommandGroup;

import frc.robot.commands.auto.HatchGrab;
import frc.robot.commands.auto.HatchRelease;
import frc.robot.Robot;
import frc.robot.helpers.Logger;



public class HatcherTogglePosition extends CommandGroup {

    public HatcherTogglePosition() {
        Logger.debug("Constructing Command: HatchTogglePosition...");
    }

    @Override
    protected void initialize() {
        Logger.debug("Initializing Command: HatchTogglePosition...");

        if (Robot.robotHatcher.hatchIsGrabbed) {
            addSequential(new HatchRelease());
        }
        else {
            addSequential(new HatchGrab());
        }
        Robot.robotHatcher.toggleHatchGrabbed();
    }

}
