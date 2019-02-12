
package frc.robot.consoles.tabs;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.shuffleboard.*;

import frc.robot.consoles.Logging;
import frc.robot.Brain;


// The Shuffleboard Inputs Tab
public class VisionTab {

    // Tab, layout, and widget objects
    private ShuffleboardTab m_tab;
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
        m_areaWidget.withPosition(0, 0);
        m_areaWidget.withWidget(BuiltInWidgets.kTextView);

        m_angleWidget.withPosition(0, 1);
        m_angleWidget.withWidget(BuiltInWidgets.kTextView);

        m_xCenterWidget.withPosition(0, 2);
        m_xCenterWidget.withWidget(BuiltInWidgets.kTextView);

        m_yCenterWidget.withPosition(0, 3);
        m_yCenterWidget.withWidget(BuiltInWidgets.kTextView);
    }

    // This will be called in the robotPeriodic
    public void update() {
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
