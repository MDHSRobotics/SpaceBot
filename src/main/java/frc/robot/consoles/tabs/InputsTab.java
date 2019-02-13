
package frc.robot.consoles.tabs;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.shuffleboard.*;

import frc.robot.consoles.Logging;
import frc.robot.Brain;


// The Shuffleboard Inputs Tab
public class InputsTab {

    // Tab, layout, and widget objects
    private ShuffleboardTab m_tab;

    // Joystick
    private SimpleWidget m_yDeadZoneWidget;
    private SimpleWidget m_xDeadZoneWidget;
    private SimpleWidget m_zDeadZoneWidget;
    private SimpleWidget m_ySensitivityWidget;
    private SimpleWidget m_xSensitivityWidget;
    private SimpleWidget m_zSensitivityWidget;
    
    // Thumbsticks
    private SimpleWidget m_yLeftDeadZoneWidget;
    private SimpleWidget m_xLeftDeadZoneWidget;
    private SimpleWidget m_yRightDeadZoneWidget;
    private SimpleWidget m_xRightDeadZoneWidget;
    private SimpleWidget m_yLeftSensitivityWidget;
    private SimpleWidget m_xLeftSensitivityWidget;
    private SimpleWidget m_yRightSensitivityWidget;
    private SimpleWidget m_xRightSensitivityWidget;

    // Mecanum Drive
    private SimpleWidget m_driveOrientationWidget;

    // Constructor
    public InputsTab() {
        Logging.logTrivial("Constructing InputsTab...");

        m_tab = Shuffleboard.getTab("Inputs");
    }

    // Create Brain Widgets
    public void preInitialize() {
        // Joystick
        m_yDeadZoneWidget = m_tab.add("Y Dead Zone", Brain.yDeadZoneDefault);
        Brain.yDeadZoneEntry = m_yDeadZoneWidget.getEntry();

        m_xDeadZoneWidget = m_tab.add("X Dead Zone", Brain.xDeadZoneDefault);
        Brain.xDeadZoneEntry = m_xDeadZoneWidget.getEntry();

        m_zDeadZoneWidget = m_tab.add("Z Dead Zone", Brain.zDeadZoneDefault);
        Brain.zDeadZoneEntry = m_zDeadZoneWidget.getEntry();

        m_ySensitivityWidget = m_tab.add("Y Sensitivity", Brain.ySensitivityDefault);
        Brain.ySensitivityEntry = m_ySensitivityWidget.getEntry();

        m_xSensitivityWidget = m_tab.add("X Sensitivity", Brain.xSensitivityDefault);
        Brain.xSensitivityEntry = m_xSensitivityWidget.getEntry();

        m_zSensitivityWidget = m_tab.add("Z Sensitivity", Brain.zSensitivityDefault);
        Brain.zSensitivityEntry = m_zSensitivityWidget.getEntry();

        // Thumbsticks
        m_yLeftDeadZoneWidget = m_tab.add("Y Left Dead Zone", Brain.yLeftDeadZoneDefault);
        Brain.yLeftDeadZoneEntry = m_yLeftDeadZoneWidget.getEntry();

        m_xLeftDeadZoneWidget = m_tab.add("X Left Dead Zone", Brain.xLeftDeadZoneDefault);
        Brain.xLeftDeadZoneEntry = m_xLeftDeadZoneWidget.getEntry();

        m_yRightDeadZoneWidget = m_tab.add("Y Right Dead Zone", Brain.yRightDeadZoneDefault);
        Brain.yRightDeadZoneEntry = m_yRightDeadZoneWidget.getEntry();

        m_xRightDeadZoneWidget = m_tab.add("X Right Dead Zone", Brain.xRightDeadZoneDefault);
        Brain.xRightDeadZoneEntry = m_xRightDeadZoneWidget.getEntry();

        m_yLeftSensitivityWidget = m_tab.add("Y Left Sensitivity", Brain.yLeftSensitivityDefault);
        Brain.yLeftSensitivityEntry = m_yLeftSensitivityWidget.getEntry();

        m_xLeftSensitivityWidget = m_tab.add("X Left Sensitivity", Brain.xLeftSensitivityDefault);
        Brain.xLeftSensitivityEntry = m_xLeftSensitivityWidget.getEntry();

        m_yRightSensitivityWidget = m_tab.add("Y Right Sensitivity", Brain.yRightSensitivityDefault);
        Brain.yRightSensitivityEntry = m_yRightSensitivityWidget.getEntry();

        m_xRightSensitivityWidget = m_tab.add("X Right Sensitivity", Brain.xRightSensitivityDefault);
        Brain.xRightSensitivityEntry = m_xRightSensitivityWidget.getEntry();

        // Mecanum Drive
        m_driveOrientationWidget = m_tab.add("Drive Orientation", Brain.driveOrientationDefault.toString());
        Brain.driveOrientationEntry = m_driveOrientationWidget.getEntry();
    }

    // Create all other Widgets
    public void initialize() {
    }

    // Configure all Widgets
    public void configure() {
        // Joystick
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

        // Thumbsticks
        m_yLeftDeadZoneWidget.withPosition(3, 0);
        m_yLeftDeadZoneWidget.withWidget(BuiltInWidgets.kTextView);

        m_xLeftDeadZoneWidget.withPosition(3, 1);
        m_xLeftDeadZoneWidget.withWidget(BuiltInWidgets.kTextView);

        m_yRightDeadZoneWidget.withPosition(3, 2);
        m_yRightDeadZoneWidget.withWidget(BuiltInWidgets.kTextView);

        m_xRightDeadZoneWidget.withPosition(3, 3);
        m_xRightDeadZoneWidget.withWidget(BuiltInWidgets.kTextView);

        m_ySensitivityWidget.withPosition(4, 0);
        m_ySensitivityWidget.withWidget(BuiltInWidgets.kTextView);

        m_xSensitivityWidget.withPosition(4, 1);
        m_xSensitivityWidget.withWidget(BuiltInWidgets.kTextView);

        m_yRightSensitivityWidget.withPosition(4, 2);
        m_yRightSensitivityWidget.withWidget(BuiltInWidgets.kTextView);

        m_xRightSensitivityWidget.withPosition(4, 3);
        m_xRightSensitivityWidget.withWidget(BuiltInWidgets.kTextView);

        // Mecanum Drive
        m_driveOrientationWidget.withPosition(2, 0);
        //m_driveOrientationWidget.withWidget(BuiltInWidgets.kSplitButtonChooser);
    }

    // This will be called in the robotPeriodic
    public void update() {
        // Joystick
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

        // Thumbsticks
        NetworkTableEntry yLeftDeadZoneEntry = m_yLeftDeadZoneWidget.getEntry();
        Brain.setYleftDeadZone(yLeftDeadZoneEntry);

        NetworkTableEntry xLeftDeadZoneEntry = m_xLeftDeadZoneWidget.getEntry();
        Brain.setXleftDeadZone(xLeftDeadZoneEntry);

        NetworkTableEntry yRightDeadZoneEntry = m_yRightDeadZoneWidget.getEntry();
        Brain.setYrightDeadZone(yRightDeadZoneEntry);

        NetworkTableEntry xRightDeadZoneEntry = m_xRightDeadZoneWidget.getEntry();
        Brain.setXrightDeadZone(xRightDeadZoneEntry);

        NetworkTableEntry yLeftSensitivityEntry = m_yLeftSensitivityWidget.getEntry();
        Brain.setYleftSensitivity(yLeftSensitivityEntry);

        NetworkTableEntry xLeftSensitivityEntry = m_xLeftSensitivityWidget.getEntry();
        Brain.setXleftSensitivity(xLeftSensitivityEntry);

        NetworkTableEntry yRightSensitivityEntry = m_yRightSensitivityWidget.getEntry();
        Brain.setYrightSensitivity(yRightSensitivityEntry);

        NetworkTableEntry xRightSensitivityEntry = m_xRightSensitivityWidget.getEntry();
        Brain.setXrightSensitivity(xRightSensitivityEntry);

        // Mecanum Drive
        NetworkTableEntry driveOrientationEntry = m_driveOrientationWidget.getEntry();
        Brain.setDriveOrientation(driveOrientationEntry);
    }

}
