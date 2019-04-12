
package frc.robot.commands.interactive;

import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Devices;
import frc.robot.Robot;
import frc.robot.consoles.Logger;

// This command uses the joystick input to mecanum drive using the cartesian method
public class MecDriveTank extends Command {

    public MecDriveTank() {
        Logger.setup("Constructing Command: MecDriveTank...");

        // Declare subsystem dependencies
        requires(Robot.robotMecDriver);
    }

    @Override
    protected void initialize() {
        Logger.action("Initializing Command: MecDriveTank...");
    }

    @Override
    protected void execute() {
        // TODO: Oh my goodness, this isn't the place to compensate for motors running backwards (I know this was a rush)
        double xSpeedLeft = -Devices.driveXbox.getY(Hand.kLeft);
        double xSpeedRight = Devices.driveXbox.getY(Hand.kRight);
        // TODO: Commands should get control values from OI, not directly from Devices (again, I know this was a quick hack)
        //CartesianMovement move = OI.getCartesianMovement(Robot.robotMecDriver.controlStickDirectionFlipped);
        Robot.robotMecDriver.driveTank(xSpeedLeft, xSpeedRight);
    }

    // This command continues until interrupted
    @Override
    protected boolean isFinished() {
        return false;
    }

    @Override
    protected void end() {
        Logger.ending("Ending Command: MecDriveTank...");
    }

    @Override
    protected void interrupted() {
        System.out.println("--");
        Logger.ending("Interrupting Command: MecDriveTank...");
    }

}
