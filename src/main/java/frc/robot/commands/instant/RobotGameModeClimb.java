
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

        Robot.robotGameMode = Robot.GameMode.CLIMB;
        Logger.info("Robot Game Mode is now CLIMB");

        if(Robot.robotClimbMode == Robot.ClimbMode.HAB2){
            Robot.robotClimbMode = Robot.ClimbMode.HAB3;
            Logger.info("Robot Climb Mode is now Climb HAB3");
        }
        else if(Robot.robotClimbMode == Robot.ClimbMode.HAB3){
            Robot.robotClimbMode = Robot.ClimbMode.HAB2;
            Logger.info("Robot Climb Mode is now Climb HAB2");
        }
    }

}
