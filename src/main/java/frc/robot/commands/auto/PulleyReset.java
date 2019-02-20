
package frc.robot.commands.auto;

import edu.wpi.first.wpilibj.command.Command;

import frc.robot.consoles.Logger;
import frc.robot.Robot;
import frc.robot.Robot.ClimbMode;;


// This command uses the gyro to synchronize the Tank and the Pulley in lifting the robot above the platform
public class PulleyReset extends Command {

    public PulleyReset() {
        Logger.setup("Constructing Command: TankPulleyReset...");

        requires(Robot.robotPulley);
    }

    @Override
    protected void initialize() {
        Logger.action("Initializing Command: TankPulleyReset...");

        Robot.robotPulley.resetPulleyPosition();
    }

    @Override
    protected void execute() {
        
    }

    // This will finish when the Pulley reaches its encoded target
    @Override
    protected boolean isFinished() {
        boolean positionMet = Robot.robotPulley.isPulleyPositionResetMet();
        return positionMet;
    }

    @Override
    protected void end() {
        Logger.ending("Ending Command: TankPulleyReset...");

        Robot.robotPulley.stop();

        // After the Pulley has reached its encoded target, the next climb task to 
        Robot.robotClimbMode = ClimbMode.ARM;
    }

    @Override
    protected void interrupted() {
        Logger.ending("Interrupting Command: TankPulleyReset...");

        Robot.robotTank.stop();
        Robot.robotPulley.stop();
    }

}
