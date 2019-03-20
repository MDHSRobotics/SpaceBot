
package frc.robot.consoles.tabs;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.shuffleboard.*;

import frc.robot.consoles.ShuffleLogger;
import frc.robot.Brain;
import frc.robot.Robot;
import frc.robot.consoles.Logger;


// The Shuffleboard Sight Tab
public class EncoderTab {

    // Tab, layout, and widget objects
    private ShuffleboardTab m_tab;

    // Encoder Properties
    private SimpleWidget m_hatchOpenRotationDegreesWidget;
    private SimpleWidget m_hatchCloseRotationDegreesWidget;

    // Constructor
    public EncoderTab() {
        ShuffleLogger.logTrivial("Constructing EncoderTab...");

        m_tab = Shuffleboard.getTab("Encoders");
    }

    // Create Brain Widgets
    public void preInitialize() {
        m_hatchOpenRotationDegreesWidget = m_tab.add("Hatch Open Rotations", Brain.hatchOpenRotationDegreeDefault);
        Brain.hatchOpenRotationDegreeEntry = m_hatchOpenRotationDegreesWidget.getEntry();

        m_hatchCloseRotationDegreesWidget = m_tab.add("Hatch Close Rotations", Brain.hatchCloseRotationDegreeDefault);
        Brain.hatchCloseRotationDegreeEntry = m_hatchCloseRotationDegreesWidget.getEntry();
    }

    // Create all other Widgets
    public void initialize() {
    }

    // Configure all Widgets
    public void configure() {
        m_hatchOpenRotationDegreesWidget.withWidget(BuiltInWidgets.kTextView);
        m_hatchCloseRotationDegreesWidget.withWidget(BuiltInWidgets.kTextView);
    }

    // This will be called in the robotPeriodic
    public void update() {
        NetworkTableEntry hatchOpenRotationDegreeEntry = m_hatchOpenRotationDegreesWidget.getEntry();
            Brain.setHatchOpenRotationDegrees(hatchOpenRotationDegreeEntry);

        NetworkTableEntry hatchCloseRotationDegreeEntry = m_hatchCloseRotationDegreesWidget.getEntry();
            Brain.setHatchCloseRotationDegrees(hatchCloseRotationDegreeEntry);
    }

}
