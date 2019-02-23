
package frc.robot.commands.auto;

import edu.wpi.first.wpilibj.command.Command;

import frc.robot.consoles.Logger;
import frc.robot.Robot;
import frc.robot.Robot.ClimbMode;


// This command uses the gyro to synchronize the Tank and the Pulley in lifting the robot above the platform
public class TankPulleyLift extends Command {

    double offsetAngle;

    public TankPulleyLift() {
        Logger.setup("Constructing Command: TankPulleyLift...");

        requires(Robot.robotTank);
        requires(Robot.robotPulley);
    }

    @Override
    protected void initialize() {
        Logger.action("Initializing Command: TankPulleyLift...");
    }

    @Override
    protected void execute() {
        // TODO: Use the gyro and encoders to synchronize the Tank and Pulley in lifting the robot above the platform without falling over
        offsetAngle = Robot.robotPulley.getRobotPitch();
        Robot.robotPulley.levelRobot(offsetAngle);
    
    }

    // This will finish when the Pulley reaches its encoded target
    @Override
    protected boolean isFinished() {
        return Robot.robotPulley.isEndPositionMet();
    }

    @Override
    protected void end() {
        Logger.ending("Ending Command: TankPulleyLift...");

        Robot.robotTank.stop();
        Robot.robotPulley.stop();

        // After the Pulley has reached its encoded target, the next climb task is lower the Arm to the "full" position
        Logger.info("Climb Mode is now ARM");
        Robot.robotClimbMode = ClimbMode.ARM;
    }

    @Override
    protected void interrupted() {
        Logger.ending("Interrupting Command: TankPulleyLift...");

        Robot.robotTank.stop();
        Robot.robotPulley.stop();
    }

}
