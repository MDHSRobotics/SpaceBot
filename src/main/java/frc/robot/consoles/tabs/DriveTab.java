
package frc.robot.consoles.tabs;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.shuffleboard.*;

import frc.robot.commands.auto.MecDriveForward;
import frc.robot.Devices;
import frc.robot.Robot;


// The Shuffleboard Drive Tab
public class DriveTab {

    // Tab, layout, and widget objects
    private ShuffleboardTab m_tab;
    private ComplexWidget m_mecDriverWidget;
    private SimpleWidget m_targetDistanceWidget;
    private ComplexWidget m_driveForwardCmdWidget;
    private ComplexWidget m_mecDriveWidget;

    // Updatable entries, either by user or under program control
    private NetworkTableEntry m_driveTargetDistance;

    // Constructor
    public DriveTab() {
        m_tab = Shuffleboard.getTab("Drive");

        m_mecDriverWidget = m_tab.add("Mec Driver Subsystem", Robot.robotMecDriver);
        m_mecDriverWidget.withPosition(0, 0);
        m_mecDriverWidget.withSize(2, 1);

        // Target Distance for Drive Distance command
        m_targetDistanceWidget = m_tab.add("Target Distance", 2.0);
        m_targetDistanceWidget.withPosition(0, 1);
        m_targetDistanceWidget.withWidget(BuiltInWidgets.kTextView);
        m_driveTargetDistance = m_targetDistanceWidget.getEntry();

        m_driveForwardCmdWidget = m_tab.add("Drive Forward", new MecDriveForward());
        m_driveForwardCmdWidget.withPosition(0, 2);
        m_driveForwardCmdWidget.withSize(2, 1);

        m_mecDriveWidget = m_tab.add("Mecanum Drive", Devices.mecDrive);
        m_mecDriveWidget.withPosition(3, 1);
        m_mecDriveWidget.withSize(4, 3);
    }

    // This will be called in the robotPeriodic
    public void update() {
        m_driveTargetDistance = m_targetDistanceWidget.getEntry();
    }

    // -------------------
    // Access Methods
    // -------------------
    public double getTargetDriveDistance() {
        return m_driveTargetDistance.getDouble(1.0);
    }

}
