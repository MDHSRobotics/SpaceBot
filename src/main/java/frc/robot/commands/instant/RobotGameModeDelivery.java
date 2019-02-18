
package frc.robot.commands.instant;

import edu.wpi.first.wpilibj.command.InstantCommand;

import frc.robot.helpers.Logger;
import frc.robot.Robot;


// This command sets the Game Mode to DELIVERY
public class RobotGameModeDelivery extends InstantCommand {

    public RobotGameModeDelivery() {
        Logger.debug("Constructing InstantCommand: RobotGameModeDelivery...");
    }

    @Override
    protected void initialize() {
        Logger.debug("Initializing InstantCommand: RobotGameModeDelivery...");

        Logger.debug("Robot Game Mode is now DELIVERY");
        Robot.robotGameMode = Robot.GameMode.DELIVERY;
    }

}
