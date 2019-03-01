
package frc.robot.commands.auto;

import edu.wpi.first.wpilibj.command.Command;

import frc.robot.consoles.Logger;
import frc.robot.OI;
import frc.robot.Robot;
import frc.robot.Robot.ClimbMode;


// This command uses the gyro to synchronize the Tank and the Pulley in lifting the robot above the platform
public class EngagePulley extends Command {

    double offsetAngle;

    public EngagePulley() {
        Logger.setup("Constructing Command: EngagePulley...");

        requires(Robot.robotPulley);
    }

    @Override
    protected void initialize() {
        Logger.action("Initializing Command: EngagePulley...");
        Robot.robotPulley.pulleyUp();
    }

    @Override
    protected void execute() {
        if(OI.getPulleyJoystick() != 0){
            Robot.robotPulley.manualControl(OI.getPulleyJoystick());
        }

        int position = Robot.robotPulley.getPosition();
        int velocity = Robot.robotPulley.getVelocity();
        Logger.info("PulleyUp -> Position: " + position + "; Velocity: " + velocity);
    }

    // This will finish when the Pulley reaches its encoded target
    @Override
    protected boolean isFinished() {
        return Robot.robotPulley.isEndPositionMet();
    }

    @Override
    protected void end() {
        Logger.ending("Ending Command: EngagePulley...");
        Robot.robotPulley.stop();
    }

    @Override
    protected void interrupted() {
        Logger.ending("Interrupting Command: EngagePulley...");

        Robot.robotPulley.stop();
    }

}
