
package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;

import frc.robot.helpers.Logger;
import frc.robot.Robot;


// This command pushes the LiftDrive forward
public class AutoLiftDrivePush extends Command {

    public AutoLiftDrivePush() {
        Logger.debug("Constructing AutoLiftDrivePush");

        requires(Robot.robotLiftDriver);
    }

    @Override
    protected void initialize() {
        Logger.debug("Initializing AutoLiftDrivePush...");
    }

    @Override
    protected void execute() {
        // TODO: This speed value needs to be in a private member variable,
        // possibly in the subsystem class if it never changes per command.
        Robot.robotLiftDriver.push(.3);
    }

    // TODO: Is this command continually started by holding a joystick or xbox controller button?
    // Or does it keep going until it hits a limit? Or until interrupted?
    @Override
    protected boolean isFinished() {
        return true;
    }

    @Override
    protected void end() {
        Logger.debug("Ending AutoLiftDrivePush");

        Robot.robotLiftDriver.stop();
    }

    @Override
    protected void interrupted() {
        // TODO: Implement this. You need to be able to interrupt this command.
    }

}
