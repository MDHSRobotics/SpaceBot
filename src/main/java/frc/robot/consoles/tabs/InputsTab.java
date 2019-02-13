
package frc.robot.consoles.tabs;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.shuffleboard.*;

import frc.robot.consoles.Logging;
import frc.robot.Brain;


// The Shuffleboard Inputs Tab
public class InputsTab {

    // Tab, layout, and widget objects
    private ShuffleboardTab m_tab;
    private SimpleWidget m_yDeadZoneWidget;
    private SimpleWidget m_xDeadZoneWidget;
    private SimpleWidget m_zDeadZoneWidget;
    private SimpleWidget m_ySensitivityWidget;
    private SimpleWidget m_xSensitivityWidget;
    private SimpleWidget m_zSensitivityWidget;
    private SimpleWidget m_driveOrientationWidget;

    // Constructor
    public InputsTab() {
        Logging.logTrivial("Constructing InputsTab...");

        m_tab = Shuffleboard.getTab("Inputs");
    }

    // Create Brain Widgets
    public void preInitialize() {
        // Y Dead Zone for the Joystick
        m_yDeadZoneWidget = m_tab.add("Y Dead Zone", Brain.yDeadZoneDefault);
        Brain.yDeadZoneEntry = m_yDeadZoneWidget.getEntry();

        // X Dead Zone for the Joystick
        m_xDeadZoneWidget = m_tab.add("X Dead Zone", Brain.xDeadZoneDefault);
        Brain.xDeadZoneEntry = m_xDeadZoneWidget.getEntry();

        // Z Dead Zone for the Joystick
        m_zDeadZoneWidget = m_tab.add("Z Dead Zone", Brain.zDeadZoneDefault);
        Brain.zDeadZoneEntry = m_zDeadZoneWidget.getEntry();

        // Y Sensitivity for the Joystick
        m_ySensitivityWidget = m_tab.add("Y Sensitivity", Brain.ySensitivityDefault);
        Brain.ySensitivityEntry = m_ySensitivityWidget.getEntry();

        // X Sensitivity for the Joystick
        m_xSensitivityWidget = m_tab.add("X Sensitivity", Brain.xSensitivityDefault);
        Brain.xSensitivityEntry = m_xSensitivityWidget.getEntry();

        // Z Sensitivity for the Joystick
        m_zSensitivityWidget = m_tab.add("Z Sensitivity", Brain.zSensitivityDefault);
        Brain.zSensitivityEntry = m_zSensitivityWidget.getEntry();

        // Control Orientation for the Joystick
        m_driveOrientationWidget = m_tab.add("Drive Orientation", Brain.driveOrientationDefault.toString());
        Brain.driveOrientationEntry = m_driveOrientationWidget.getEntry();
    }

    // Create all other Widgets
    public void initialize() {
    }

    // Configure all Widgets
    public void configure() {
        m_yDeadZoneWidget.withPosition(0, 0);
        m_yDeadZoneWidget.withWidget(BuiltInWidgets.kTextView);

        m_xDeadZoneWidget.withPosition(0, 1);
        m_xDeadZoneWidget.withWidget(BuiltInWidgets.kTextView);

        m_zDeadZoneWidget.withPosition(0, 2);
        m_zDeadZoneWidget.withWidget(BuiltInWidgets.kTextView);

        m_ySensitivityWidget.withPosition(1, 0);
        m_ySensitivityWidget.withWidget(BuiltInWidgets.kTextView);

        m_xSensitivityWidget.withPosition(1, 1);
        m_xSensitivityWidget.withWidget(BuiltInWidgets.kTextView);

        m_zSensitivityWidget.withPosition(1, 2);
        m_zSensitivityWidget.withWidget(BuiltInWidgets.kTextView);

        m_driveOrientationWidget.withPosition(2, 0);
        //m_driveOrientationWidget.withWidget(BuiltInWidgets.kSplitButtonChooser);
    }

    // This will be called in the robotPeriodic
    public void update() {
        NetworkTableEntry yDeadZoneEntry = m_yDeadZoneWidget.getEntry();
        Brain.setYdeadZone(yDeadZoneEntry);

        NetworkTableEntry xDeadZoneEntry = m_xDeadZoneWidget.getEntry();
        Brain.setXdeadZone(xDeadZoneEntry);

        NetworkTableEntry zDeadZoneEntry = m_zDeadZoneWidget.getEntry();
        Brain.setZdeadZone(zDeadZoneEntry);

        NetworkTableEntry ySensitivityEntry = m_ySensitivityWidget.getEntry();
        Brain.setYsensitivity(ySensitivityEntry);

        NetworkTableEntry xSensitivityEntry = m_xSensitivityWidget.getEntry();
        Brain.setXsensitivity(xSensitivityEntry);

        NetworkTableEntry zSensitivityEntry = m_zSensitivityWidget.getEntry();
        Brain.setZsensitivity(zSensitivityEntry);

        NetworkTableEntry driveOrientationEntry = m_driveOrientationWidget.getEntry();
        Brain.setDriveOrientation(driveOrientationEntry);
    }

}
