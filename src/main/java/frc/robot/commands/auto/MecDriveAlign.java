
package frc.robot.commands.auto;

import edu.wpi.first.wpilibj.command.Command;

import frc.robot.consoles.Logger;
import frc.robot.OI;
import frc.robot.Robot;


// Automatically control the MecDrive to align the Robot with the gyro, and the line seen by the vision system
public class MecDriveAlign extends Command {

    private int m_targetAngle = -1;

    public MecDriveAlign() {
        Logger.setup("Constructing Command: MecDriveAlign...");

        // Declare subsystem dependencies
        requires(Robot.robotMecDriver);
    }

    @Override
    protected void initialize() {
        System.out.println("--");
        Logger.action("Initializing Command: MecDriveAlign...");

        m_targetAngle = OI.getDpadAngleForGyro();
    }

    @Override
    protected void execute() {
        if (m_targetAngle == -1) {
            // TODO: This happens sometimes, so find a more reliable way to get the button that was pressed
            Logger.warning("MecDriveAlign -> Missed the DPad button press!");
            Robot.robotMecDriver.stop();
            return;
        }

        Robot.robotMecDriver.driveAlign(m_targetAngle);
    }

    // We're finished when the line looks straight and is centered enough (or a line is not detected)
    @Override
    protected boolean isFinished() {
        if (m_targetAngle == -1) return true;

        // TODO: Change this with the change to OI, to support whileHeld activation
        boolean aligned = Robot.robotMecDriver.isAligned(m_targetAngle);
        return aligned;
    }

    @Override
    protected void end() {
        Logger.ending("Ending Command: MecDriveAlign...");

        Robot.robotMecDriver.stop();
    }

    @Override
    protected void interrupted() {
        Logger.ending("Interrupting Command: MecDriveAlign...");

        Robot.robotMecDriver.stop();
    }

}
