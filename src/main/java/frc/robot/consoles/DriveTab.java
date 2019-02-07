
package frc.robot.consoles;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.shuffleboard.*;

import frc.robot.commands.auto.MecDriveForward;
import frc.robot.Devices;
import frc.robot.Robot;


// The Shuffleboard Drive Tab
public class DriveTab {

    private ShuffleboardTab driveTab;

    // Updatable entries, either by user or under program control
    private NetworkTableEntry drive_targetDistanceEntry;

    // Constructor
    public DriveTab() {

        driveTab = Shuffleboard.getTab("Drive");

        ComplexWidget mecDriverWdiget = driveTab.add("Mec Driver Subsystem", Robot.robotMecDriver);
        mecDriverWdiget.withSize(2, 1);
        mecDriverWdiget.withPosition(0, 0);

        // Target Distance for Drive Distance command
        SimpleWidget targetDistanceWidget = driveTab.add("Target Distance", 2.0);
        targetDistanceWidget.withPosition(0, 1);
        targetDistanceWidget.withWidget(BuiltInWidgets.kTextView);
        drive_targetDistanceEntry = targetDistanceWidget.getEntry();

        ComplexWidget driveForwardCmdWidget = driveTab.add("Drive Forward", new MecDriveForward());
        driveForwardCmdWidget.withSize(2, 1);
        driveForwardCmdWidget.withPosition(0, 2);

        ComplexWidget mecDriveWidget = driveTab.add("Mecanum Drive", Devices.mecDrive);
        mecDriveWidget.withSize(4, 3);
        mecDriveWidget.withPosition(3, 1);
    }

    public void update() {

    }

    // -------------------
    // Access Methods
    // -------------------
    public double getTargetDriveDistance() {
        return drive_targetDistanceEntry.getDouble(1.0);
    }

}
