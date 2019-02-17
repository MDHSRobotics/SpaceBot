
package frc.robot.commands.instant;

import edu.wpi.first.wpilibj.command.InstantCommand;

import frc.robot.commands.auto.HatchGrab;
import frc.robot.commands.auto.HatchRelease;
import frc.robot.helpers.Logger;
import frc.robot.Robot;


// Toggles the position of the Hatcher
public class HatcherTogglePosition extends InstantCommand {

    private HatchGrab m_hatchGrabCmd;
    private HatchRelease m_hatchReleaseCmd;

    public HatcherTogglePosition() {
        super();
        Logger.debug("Constructing InstantCommand: HatcherTogglePosition...");

        m_hatchGrabCmd = new HatchGrab();
        m_hatchReleaseCmd = new HatchRelease();
    }

    @Override
    protected void initialize() {
        Logger.debug("Initializing InstantCommand: HatcherTogglePosition...");

        if (Robot.robotHatcher.isHatchGrabbed) {
            m_hatchGrabCmd.start();
        }
        else {
            m_hatchReleaseCmd.start();
        }
        // TODO: Should this happen here, or in the isFinished() methods for each command?
        Robot.robotHatcher.toggleHatchGrabbed();
    }

}
