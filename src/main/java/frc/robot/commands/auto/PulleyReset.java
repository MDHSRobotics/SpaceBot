
package frc.robot.commands.auto;

import edu.wpi.first.wpilibj.command.Command;

import frc.robot.consoles.Logger;
import frc.robot.Robot;


// This command uses the gyro to synchronize the Tank and the Pulley in lifting the robot above the platform
public class PulleyReset extends Command {

    public PulleyReset() {
        Logger.setup("Constructing Command: TankPulleyReset...");

        requires(Robot.robotPulley);
    }

    @Override
    protected void initialize() {
        Logger.action("Initializing Command: TankPulleyReset...");

        Robot.robotPulley.resetPosition();
    }

    @Override
    protected void execute() {
        int position = Robot.robotPulley.getPosition();
        int velocity = Robot.robotPulley.getVelocity();
        Logger.info("PulleyReset -> Position: " + position + "; Velocity: " + velocity);
    }

    // This will finish when the Pulley reaches its encoded target
    @Override
    protected boolean isFinished() {
        return Robot.robotPulley.isPositionResetMet();
    }

    @Override
    protected void end() {
        Logger.ending("Ending Command: TankPulleyReset...");

        Robot.robotPulley.stop();
    }

    @Override
    protected void interrupted() {
        Logger.ending("Interrupting Command: TankPulleyReset...");

        Robot.robotTank.stop();
        Robot.robotPulley.stop();
    }

}
