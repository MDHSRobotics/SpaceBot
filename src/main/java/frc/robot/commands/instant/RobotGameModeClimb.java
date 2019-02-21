
package frc.robot.commands.instant;

import edu.wpi.first.wpilibj.command.InstantCommand;

import frc.robot.consoles.Logger;
import frc.robot.Robot;


// This command sets the Game Mode to Climb
public class RobotGameModeClimb extends InstantCommand {

    public RobotGameModeClimb() {
        super();
        Logger.setup("Constructing InstantCommand: RobotGameModeClimb...");
    }

    @Override
    protected void initialize() {
        System.out.println("--");
        Logger.action("Initializing InstantCommand: RobotGameModeClimb...");

        Logger.info("Robot Game Mode is now CLIMB");
        Robot.robotGameMode = Robot.GameMode.CLIMB;
    }

}
