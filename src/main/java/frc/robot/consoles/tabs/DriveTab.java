
package frc.robot.consoles.tabs;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.shuffleboard.*;

import frc.robot.commands.test.MecDriveForward;
import frc.robot.consoles.ShuffleLogger;
import frc.robot.Brain;
import frc.robot.Devices;
import frc.robot.Robot;


// The Shuffleboard Drive Tab
public class DriveTab {

    // Tab, layout, and widget objects
    private ShuffleboardTab m_tab;
    private ComplexWidget m_mecDriveWidget;
    private ComplexWidget m_mecDriverWidget;
    private ComplexWidget m_mecDriveForwardCmdWidget;
    private SimpleWidget m_targetDistanceWidget;

    // Create Brain Widgets
    public DriveTab() {
        ShuffleLogger.logCritical("Constructing DriveTab...");

        m_tab = Shuffleboard.getTab("Drive");
    }

    // Create Brain Widgets
    public void preInitialize() {
        // Target Distance for Drive Forward command
        m_targetDistanceWidget = m_tab.add("Target Distance", Brain.driveTargetDistanceDefault);
        Brain.driveTargetDistanceEntry = m_targetDistanceWidget.getEntry();
    }

    // Create all other Widgets
    public void initialize() {
        m_mecDriveWidget = m_tab.add("Mecanum Drive", Devices.mecDrive);
        m_mecDriverWidget = m_tab.add("Mecanum Driver Subsystem", Robot.robotMecDriver);
        m_mecDriveForwardCmdWidget = m_tab.add("Mecanum Drive Forward", new MecDriveForward());
    }

    // Configure all Widgets
    public void configure() {
        m_mecDriverWidget.withPosition(0, 0);
        m_mecDriverWidget.withSize(2, 1);

        m_targetDistanceWidget.withPosition(0, 1);
        m_targetDistanceWidget.withWidget(BuiltInWidgets.kTextView);

        m_mecDriveForwardCmdWidget.withPosition(0, 2);
        m_mecDriveForwardCmdWidget.withSize(2, 1);

        m_mecDriveWidget.withPosition(3, 1);
        m_mecDriveWidget.withSize(4, 3);
    }

    // This will be called in the robotPeriodic
    public void update() {
        NetworkTableEntry targetDistanceEntry = m_targetDistanceWidget.getEntry();
        Brain.setTargetDriveDistance(targetDistanceEntry);
    }

}
