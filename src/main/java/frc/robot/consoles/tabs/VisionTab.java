
package frc.robot.consoles.tabs;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.shuffleboard.*;

import frc.robot.consoles.ShuffleLogger;
import frc.robot.Brain;


// The Shuffleboard Inputs Tab
public class VisionTab {

    // Tab, layout, and widget objects
    private ShuffleboardTab m_tab;

    // Line Pipeline - HSV Thresholds
    private SimpleWidget m_hueMinWidget;
    private SimpleWidget m_hueMaxWidget;
    private SimpleWidget m_saturationMinWidget;
    private SimpleWidget m_saturationMaxWidget;
    private SimpleWidget m_valueMinWidget;
    private SimpleWidget m_valueMaxWidget;

    // Line Detection - Front Camera
    private SimpleWidget m_frontAreaWidget;
    private SimpleWidget m_frontAngleWidget;
    private SimpleWidget m_frontXcenterWidget;
    private SimpleWidget m_frontYcenterWidget;

    // Line Detection - Left Camera
    private SimpleWidget m_leftAreaWidget;
    private SimpleWidget m_leftAngleWidget;
    private SimpleWidget m_leftXcenterWidget;
    private SimpleWidget m_leftYcenterWidget;

    // Line Detection - Right Camera
    private SimpleWidget m_rightAreaWidget;
    private SimpleWidget m_rightAngleWidget;
    private SimpleWidget m_rightXcenterWidget;
    private SimpleWidget m_rightYcenterWidget;

    // Constructor
    public VisionTab() {
        ShuffleLogger.logTrivial("Constructing VisionTab...");

        m_tab = Shuffleboard.getTab("Vision");
    }

    // Create Brain Widgets
    public void preInitialize() {
        // Hue Thresholds
        m_hueMinWidget = m_tab.add("Hue Minimum", Brain.hueMinDefault);
        Brain.hueMinEntry = m_hueMinWidget.getEntry();

        m_hueMaxWidget = m_tab.add("Hue Maximum", Brain.hueMaxDefault);
        Brain.hueMaxEntry = m_hueMaxWidget.getEntry();

        // Saturation Thresholds
        m_saturationMinWidget = m_tab.add("Saturation Minimum", Brain.saturationMinDefault);
        Brain.saturationMinEntry = m_saturationMinWidget.getEntry();

        m_saturationMaxWidget = m_tab.add("Saturation Maximum", Brain.saturationMaxDefault);
        Brain.saturationMaxEntry = m_saturationMaxWidget.getEntry();

        // Value Thresholds
        m_valueMinWidget = m_tab.add("Value Minimum", Brain.valueMinDefault);
        Brain.valueMinEntry = m_valueMinWidget.getEntry();

        m_valueMaxWidget = m_tab.add("Value Maximum", Brain.valueMaxDefault);
        Brain.valueMaxEntry = m_valueMaxWidget.getEntry();

        // Front Camera
        m_frontAreaWidget = m_tab.add("Front Line Area", Brain.frontLineAreaDefault);
        Brain.frontLineAreaEntry = m_frontAreaWidget.getEntry();

        m_frontAngleWidget = m_tab.add("Front Line Angle", Brain.frontLineAngleDefault);
        Brain.frontLineAngleEntry = m_frontAngleWidget.getEntry();

        m_frontXcenterWidget = m_tab.add("Front Line Center X", Brain.frontLineXcenterDefault);
        Brain.frontLineXcenterEntry = m_frontXcenterWidget.getEntry();

        m_frontYcenterWidget = m_tab.add("Front Line Center Y", Brain.frontLineYcenterDefault);
        Brain.frontLineYcenterEntry = m_frontYcenterWidget.getEntry();

        // Left Camera
        m_leftAreaWidget = m_tab.add("Left Line Area", Brain.leftLineAreaDefault);
        Brain.leftLineAreaEntry = m_leftAreaWidget.getEntry();

        m_leftAngleWidget = m_tab.add("Left Line Angle", Brain.leftLineAngleDefault);
        Brain.leftLineAngleEntry = m_leftAngleWidget.getEntry();

        m_leftXcenterWidget = m_tab.add("Left Line Center X", Brain.leftLineXcenterDefault);
        Brain.leftLineXcenterEntry = m_leftXcenterWidget.getEntry();

        m_leftYcenterWidget = m_tab.add("Left Line Center Y", Brain.leftLineYcenterDefault);
        Brain.leftLineYcenterEntry = m_leftYcenterWidget.getEntry();

        // Right Camera
        m_rightAreaWidget = m_tab.add("Right Line Area", Brain.rightLineAreaDefault);
        Brain.rightLineAreaEntry = m_rightAreaWidget.getEntry();

        m_rightAngleWidget = m_tab.add("Right Line Angle", Brain.rightLineAngleDefault);
        Brain.rightLineAngleEntry = m_rightAngleWidget.getEntry();

        m_rightXcenterWidget = m_tab.add("Right Line Center X", Brain.rightLineXcenterDefault);
        Brain.rightLineXcenterEntry = m_rightXcenterWidget.getEntry();

        m_rightYcenterWidget = m_tab.add("Right Line Center Y", Brain.rightLineYcenterDefault);
        Brain.rightLineYcenterEntry = m_rightYcenterWidget.getEntry();
    }

    // Create all other Widgets
    public void initialize() {
    }

    // Configure all Widgets
    public void configure() {
        // Hue Thresholds
        m_hueMinWidget.withPosition(0, 0);
        m_hueMinWidget.withWidget(BuiltInWidgets.kTextView);

        m_hueMaxWidget.withPosition(1, 0);
        m_hueMaxWidget.withWidget(BuiltInWidgets.kTextView);
        
        // Saturation Thresholds
        m_saturationMinWidget.withPosition(0, 1);
        m_saturationMinWidget.withWidget(BuiltInWidgets.kTextView);

        m_saturationMaxWidget.withPosition(1, 1);
        m_saturationMaxWidget.withWidget(BuiltInWidgets.kTextView);

        // Value Thresholds
        m_valueMinWidget.withPosition(0, 2);
        m_valueMinWidget.withWidget(BuiltInWidgets.kTextView);

        m_valueMaxWidget.withPosition(1, 2);
        m_valueMaxWidget.withWidget(BuiltInWidgets.kTextView);

        // Front Camera
        m_frontAreaWidget.withPosition(0, 3);
        m_frontAreaWidget.withWidget(BuiltInWidgets.kTextView);

        m_frontAngleWidget.withPosition(1, 3);
        m_frontAngleWidget.withWidget(BuiltInWidgets.kTextView);

        m_frontXcenterWidget.withPosition(2, 3);
        m_frontXcenterWidget.withWidget(BuiltInWidgets.kTextView);

        m_frontYcenterWidget.withPosition(3, 3);
        m_frontYcenterWidget.withWidget(BuiltInWidgets.kTextView);

        // Left Camera
        m_leftAreaWidget.withPosition(0, 4);
        m_leftAreaWidget.withWidget(BuiltInWidgets.kTextView);

        m_leftAngleWidget.withPosition(1, 4);
        m_leftAngleWidget.withWidget(BuiltInWidgets.kTextView);

        m_leftXcenterWidget.withPosition(2, 4);
        m_leftXcenterWidget.withWidget(BuiltInWidgets.kTextView);

        m_leftYcenterWidget.withPosition(3, 4);
        m_leftYcenterWidget.withWidget(BuiltInWidgets.kTextView);

        // Right Camera
        m_rightAreaWidget.withPosition(0, 5);
        m_rightAreaWidget.withWidget(BuiltInWidgets.kTextView);

        m_rightAngleWidget.withPosition(1, 5);
        m_rightAngleWidget.withWidget(BuiltInWidgets.kTextView);

        m_rightXcenterWidget.withPosition(2, 5);
        m_rightXcenterWidget.withWidget(BuiltInWidgets.kTextView);

        m_rightYcenterWidget.withPosition(3, 5);
        m_rightYcenterWidget.withWidget(BuiltInWidgets.kTextView);
    }

    // This will be called in the robotPeriodic
    public void update() {
        // Hue Thresholds
        NetworkTableEntry hueMinEntry = m_hueMinWidget.getEntry();
        Brain.setHueMin(hueMinEntry);

        NetworkTableEntry hueMaxEntry = m_hueMaxWidget.getEntry();
        Brain.setHueMax(hueMaxEntry);

        // Saturation Thresholds
        NetworkTableEntry saturationMinEntry = m_saturationMinWidget.getEntry();
        Brain.setSaturationMin(saturationMinEntry);

        NetworkTableEntry saturationMaxEntry = m_saturationMaxWidget.getEntry();
        Brain.setSaturationMax(saturationMaxEntry);

        // Value Thresholds
        NetworkTableEntry valueMinEntry = m_valueMinWidget.getEntry();
        Brain.setValueMin(valueMinEntry);

        NetworkTableEntry valueMaxEntry = m_valueMaxWidget.getEntry();
        Brain.setValueMax(valueMaxEntry);

        // Front Camera
        NetworkTableEntry frontAreaEntry = m_frontAreaWidget.getEntry();
        Brain.setFrontLineArea(frontAreaEntry);

        NetworkTableEntry frontAngleEntry = m_frontAngleWidget.getEntry();
        Brain.setFrontLineAngle(frontAngleEntry);

        NetworkTableEntry frontXcenterEntry = m_frontXcenterWidget.getEntry();
        Brain.setFrontLineXcenter(frontXcenterEntry);

        NetworkTableEntry frontYcenterEntry = m_frontYcenterWidget.getEntry();
        Brain.setFrontLineYcenter(frontYcenterEntry);

        // Left Camera
        NetworkTableEntry leftAreaEntry = m_leftAreaWidget.getEntry();
        Brain.setLeftLineArea(leftAreaEntry);

        NetworkTableEntry leftAngleEntry = m_leftAngleWidget.getEntry();
        Brain.setLeftLineAngle(leftAngleEntry);

        NetworkTableEntry leftXcenterEntry = m_leftXcenterWidget.getEntry();
        Brain.setLeftLineXcenter(leftXcenterEntry);

        NetworkTableEntry leftYcenterEntry = m_leftYcenterWidget.getEntry();
        Brain.setLeftLineYcenter(leftYcenterEntry);

        // Right Camera
        NetworkTableEntry rightAreaEntry = m_rightAreaWidget.getEntry();
        Brain.setRightLineArea(rightAreaEntry);

        NetworkTableEntry rightAngleEntry = m_rightAngleWidget.getEntry();
        Brain.setRightLineAngle(rightAngleEntry);

        NetworkTableEntry rightXcenterEntry = m_rightXcenterWidget.getEntry();
        Brain.setRightLineXcenter(rightXcenterEntry);

        NetworkTableEntry rightYcenterEntry = m_rightYcenterWidget.getEntry();
        Brain.setRightLineYcenter(rightYcenterEntry);
    }

}
