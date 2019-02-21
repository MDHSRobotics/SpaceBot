
package frc.robot.commands.instant;

import edu.wpi.first.wpilibj.command.InstantCommand;

import frc.robot.consoles.Logger;
import frc.robot.Robot;


// This command sets the Game Mode to DELIVERY
public class RobotGameModeDelivery extends InstantCommand {

    public RobotGameModeDelivery() {
        super();
        Logger.setup("Constructing InstantCommand: RobotGameModeDelivery...");
    }

    @Override
    protected void initialize() {
        System.out.println("--");
        Logger.action("Initializing InstantCommand: RobotGameModeDelivery...");

        Logger.info("Robot Game Mode is now DELIVERY");
        Robot.robotGameMode = Robot.GameMode.DELIVERY;
    }

}
