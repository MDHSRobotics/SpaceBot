
package frc.robot.commands.instant;

import edu.wpi.first.wpilibj.command.InstantCommand;

import frc.robot.helpers.Logger;
import frc.robot.Robot;


// This command sets the Game Mode to Climb
public class RobotGameModeClimb extends InstantCommand {

    public RobotGameModeClimb() {
        Logger.debug("Constructing InstantCommand: RobotGameModeClimb...");
    }

    @Override
    protected void initialize() {
        Logger.debug("Initializing InstantCommand: RobotGameModeClimb...");

        Logger.debug("Robot Game Mode is now CLIMB");
        Robot.robotGameMode = Robot.GameMode.CLIMB;
    }

}
