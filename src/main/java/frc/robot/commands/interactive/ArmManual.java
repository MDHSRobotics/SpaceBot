
package frc.robot.commands.interactive;

import edu.wpi.first.wpilibj.command.Command;

import frc.robot.consoles.Logger;
import frc.robot.OI;
import frc.robot.Robot;


// This command lowers the Arm manually
public class ArmManual extends Command {

    public ArmManual() {
        Logger.setup("Constructing Command: ArmManual...");

        // Declare subsystem dependencies
        requires(Robot.robotArm);
    }

    @Override
    protected void initialize() {
        Logger.action("Initializing Command: ArmManual...");
    }

    @Override
    protected void execute() {
        double speed = OI.getArmLowerSpeed();
        Robot.robotArm.setSpeed(speed);
    }

    // This command continues until interrupted
    @Override
    protected boolean isFinished() {
        return false;
    }

    @Override
    protected void end() {
        Logger.ending("Ending Command: ArmManual...");
    }

    @Override
    protected void interrupted() {
        Logger.ending("Interrupting Command: ArmManual...");
    }

}
