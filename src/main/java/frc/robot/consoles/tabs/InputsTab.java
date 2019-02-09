
package frc.robot.consoles.tabs;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.shuffleboard.*;

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

    // Constructor
    public InputsTab() {
        m_tab = Shuffleboard.getTab("Inputs");

        // Y Dead Zone for the Joystick
        m_yDeadZoneWidget = m_tab.add("Y Dead Zone", Brain.yDeadZoneDefault);
        m_yDeadZoneWidget.withPosition(0, 0);
        m_yDeadZoneWidget.withWidget(BuiltInWidgets.kTextView);
        Brain.yDeadZoneEntry = m_yDeadZoneWidget.getEntry();

        // X Dead Zone for the Joystick
        m_xDeadZoneWidget = m_tab.add("X Dead Zone", Brain.xDeadZoneDefault);
        m_xDeadZoneWidget.withPosition(0, 1);
        m_xDeadZoneWidget.withWidget(BuiltInWidgets.kTextView);
        Brain.xDeadZoneEntry = m_xDeadZoneWidget.getEntry();

        // Z Dead Zone for the Joystick
        m_zDeadZoneWidget = m_tab.add("Z Dead Zone", Brain.zDeadZoneDefault);
        m_zDeadZoneWidget.withPosition(0, 2);
        m_zDeadZoneWidget.withWidget(BuiltInWidgets.kTextView);
        Brain.zDeadZoneEntry = m_zDeadZoneWidget.getEntry();

        // Y Sensitivity for the Joystick
        m_ySensitivityWidget = m_tab.add("Y Sensitivity", Brain.ySensitivityDefault);
        m_ySensitivityWidget.withPosition(1, 0);
        m_ySensitivityWidget.withWidget(BuiltInWidgets.kTextView);
        Brain.ySensitivityEntry = m_ySensitivityWidget.getEntry();

        // X Sensitivity for the Joystick
        m_xSensitivityWidget = m_tab.add("X Sensitivity", Brain.xSensitivityDefault);
        m_xSensitivityWidget.withPosition(1, 1);
        m_xSensitivityWidget.withWidget(BuiltInWidgets.kTextView);
        Brain.xSensitivityEntry = m_xSensitivityWidget.getEntry();

        // Z Sensitivity for the Joystick
        m_zSensitivityWidget = m_tab.add("Z Sensitivity", Brain.zSensitivityDefault);
        m_zSensitivityWidget.withPosition(1, 2);
        m_zSensitivityWidget.withWidget(BuiltInWidgets.kTextView);
        Brain.zSensitivityEntry = m_zSensitivityWidget.getEntry();
    }

    // This will be called in the robotPeriodic
    public void update() {
        NetworkTableEntry yDeadZoneEntry = m_yDeadZoneWidget.getEntry();
        double yDeadZone = yDeadZoneEntry.getDouble(Brain.yDeadZoneDefault);
        Brain.yDeadZoneEntry.setDouble(yDeadZone);

        NetworkTableEntry xDeadZoneEntry = m_xDeadZoneWidget.getEntry();
        double xDeadZone = xDeadZoneEntry.getDouble(Brain.xDeadZoneDefault);
        Brain.xDeadZoneEntry.setDouble(xDeadZone);

        NetworkTableEntry zDeadZoneEntry = m_zDeadZoneWidget.getEntry();
        double zDeadZone = zDeadZoneEntry.getDouble(Brain.zDeadZoneDefault);
        Brain.zDeadZoneEntry.setDouble(zDeadZone);

        NetworkTableEntry ySensitivityEntry = m_ySensitivityWidget.getEntry();
        double ySensitivity = ySensitivityEntry.getDouble(Brain.ySensitivityDefault);
        Brain.ySensitivityEntry.setDouble(ySensitivity);

        NetworkTableEntry xSensitivityEntry = m_xSensitivityWidget.getEntry();
        double xSensitivity = xSensitivityEntry.getDouble(Brain.xSensitivityDefault);
        Brain.xSensitivityEntry.setDouble(xSensitivity);

        NetworkTableEntry zSensitivityEntry = m_zSensitivityWidget.getEntry();
        double zSensitivity = zSensitivityEntry.getDouble(Brain.zSensitivityDefault);
        Brain.zSensitivityEntry.setDouble(zSensitivity);
    }

}
