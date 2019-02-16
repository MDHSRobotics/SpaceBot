
package frc.robot.consoles.tabs;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.shuffleboard.*;

import frc.robot.consoles.Logging;
import frc.robot.Brain;


// The Shuffleboard Inputs Tab
public class VisionTab {

    // Tab, layout, and widget objects
    private ShuffleboardTab m_tab;

    // LinePipeline
    private SimpleWidget m_hueMinWidget;
    private SimpleWidget m_hueMaxWidget;
    private SimpleWidget m_saturationMinWidget;
    private SimpleWidget m_saturationMaxWidget;
    private SimpleWidget m_valueMinWidget;
    private SimpleWidget m_valueMaxWidget;

    // LineDetector
    private SimpleWidget m_areaWidget;
    private SimpleWidget m_angleWidget;
    private SimpleWidget m_xCenterWidget;
    private SimpleWidget m_yCenterWidget;

    // Constructor
    public VisionTab() {
        Logging.logTrivial("Constructing VisionTab...");

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

        // Hatch Camera - Line Area
        m_areaWidget = m_tab.add("Hatch Line Area", Brain.hatchLineAreaDefault);
        Brain.hatchLineAreaEntry = m_areaWidget.getEntry();

        // Hatch Camera - Line Angle
        m_angleWidget = m_tab.add("Hatch Line Angle", Brain.hatchLineAngleDefault);
        Brain.hatchLineAngleEntry = m_angleWidget.getEntry();

        // Hatch Camera - Line Center X
        m_xCenterWidget = m_tab.add("Hatch Line Center X", Brain.hatchLineXcenterDefault);
        Brain.hatchLineXcenterEntry = m_xCenterWidget.getEntry();

        // Hatch Camera - Line Center Y
        m_yCenterWidget = m_tab.add("Hatch Line Center Y", Brain.hatchLineYcenterDefault);
        Brain.hatchLineYcenterEntry = m_yCenterWidget.getEntry();
    }

    // Create all other Widgets
    public void initialize() {
    }

    // Configure all Widgets
    public void configure() {
        m_hueMinWidget.withPosition(0, 0);
        m_hueMinWidget.withWidget(BuiltInWidgets.kTextView);

        m_hueMaxWidget.withPosition(0, 1);
        m_hueMaxWidget.withWidget(BuiltInWidgets.kTextView);
        
        m_saturationMinWidget.withPosition(0, 2);
        m_saturationMinWidget.withWidget(BuiltInWidgets.kTextView);

        m_saturationMaxWidget.withPosition(0, 3);
        m_saturationMaxWidget.withWidget(BuiltInWidgets.kTextView);

        m_valueMinWidget.withPosition(0, 4);
        m_valueMinWidget.withWidget(BuiltInWidgets.kTextView);

        m_valueMaxWidget.withPosition(0, 5);
        m_valueMaxWidget.withWidget(BuiltInWidgets.kTextView);

        m_areaWidget.withPosition(1, 0);
        m_areaWidget.withWidget(BuiltInWidgets.kTextView);

        m_angleWidget.withPosition(1, 1);
        m_angleWidget.withWidget(BuiltInWidgets.kTextView);

        m_xCenterWidget.withPosition(1, 2);
        m_xCenterWidget.withWidget(BuiltInWidgets.kTextView);

        m_yCenterWidget.withPosition(1, 3);
        m_yCenterWidget.withWidget(BuiltInWidgets.kTextView);

    }

    // This will be called in the robotPeriodic
    public void update() {
        NetworkTableEntry hueMinEntry = m_hueMinWidget.getEntry();
        Brain.setHueMin(hueMinEntry);

        NetworkTableEntry hueMaxEntry = m_hueMaxWidget.getEntry();
        Brain.setHueMax(hueMaxEntry);

        NetworkTableEntry saturationMinEntry = m_saturationMinWidget.getEntry();
        Brain.setHueMin(saturationMinEntry);

        NetworkTableEntry saturationMaxEntry = m_saturationMaxWidget.getEntry();
        Brain.setHueMax(saturationMaxEntry);

        NetworkTableEntry valueMinEntry = m_valueMinWidget.getEntry();
        Brain.setHueMin(valueMinEntry);

        NetworkTableEntry valueMaxEntry = m_valueMaxWidget.getEntry();
        Brain.setHueMax(valueMaxEntry);

        NetworkTableEntry areaEntry = m_areaWidget.getEntry();
        Brain.setHatchLineArea(areaEntry);

        NetworkTableEntry angleEntry = m_angleWidget.getEntry();
        Brain.setHatchLineAngle(angleEntry);

        NetworkTableEntry xCenterEntry = m_xCenterWidget.getEntry();
        Brain.setHatchLineXcenter(xCenterEntry);

        NetworkTableEntry yCenterEntry = m_yCenterWidget.getEntry();
        Brain.setHatchLineYcenter(yCenterEntry);
    }

}
