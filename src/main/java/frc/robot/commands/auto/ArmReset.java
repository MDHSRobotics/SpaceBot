
package frc.robot.commands.auto;

import edu.wpi.first.wpilibj.command.Command;

import frc.robot.consoles.Logger;
import frc.robot.Robot;


// This command uses the gyro to synchronize the Tank and the Pulley in lifting the robot above the platform
public class ArmReset extends Command {

    public ArmReset() {
        Logger.setup("Constructing Command: TankPulleyReset...");

        requires(Robot.robotArm);
    }

    @Override
    protected void initialize() {
        Logger.action("Initializing Command: ArmReset...");

        Robot.robotArm.resetPosition();
    }

    @Override
    protected void execute() {
        int position = Robot.robotPulley.getPosition();
        int velocity = Robot.robotPulley.getVelocity();
        Logger.info("ArmReset -> Position: " + position + "; Velocity: " + velocity);
    }

    // This will finish when the Arm reaches its encoded target
    @Override
    protected boolean isFinished() {
        return Robot.robotArm.isResetPositionMet();
    }

    @Override
    protected void end() {
        Logger.ending("Ending Command: ArmReset...");

        Robot.robotPulley.stop();
    }

    @Override
    protected void interrupted() {
        Logger.ending("Interrupting Command: ArmReset...");

        Robot.robotTank.stop();
        Robot.robotPulley.stop();
    }

}
