
package frc.robot.consoles.tabs;

import edu.wpi.first.wpilibj.shuffleboard.*;
import edu.wpi.first.wpilibj.DriverStation;
import java.util.Map;

import frc.robot.consoles.Logging;
import frc.robot.Brain;
import frc.robot.Robot;


// The Shuffleboard Main Tab
public class MainTab {

    // Tab, layout, and widget objects
    private ShuffleboardTab m_tab;
    private ComplexWidget m_autoCmdWidget;
    private SimpleWidget m_matchTimeWidget;
    private SimpleWidget m_lineDetectedWidget;

    // Constructor
    public MainTab() {
        Logging.logTrivial("Constructing MainTab...");

        m_tab = Shuffleboard.getTab("Main");
    }

    // Create Brain Widgets
    public void preInitialize() {
        // Match Time
        m_matchTimeWidget = m_tab.add("Match Time", Brain.matchTimeDefault);
        Brain.matchTimeEntry = m_matchTimeWidget.getEntry();

        // Line Detected
        m_lineDetectedWidget = m_tab.add("Line Detected", Brain.lineDetectedDefault);
        Brain.lineDetectedEntry = m_lineDetectedWidget.getEntry();
    }

    // Create all other Widgets
    public void initialize() {
        // Autonomous Command
        m_autoCmdWidget = m_tab.add("Auto Command", Robot.autoCommandChooser);
    }

    // Configure all Widgets
    public void configure() {
        m_autoCmdWidget.withPosition(0, 0);
        m_autoCmdWidget.withSize(2, 1);

        m_matchTimeWidget.withPosition(2, 0);
        m_matchTimeWidget.withWidget(BuiltInWidgets.kDial);
        m_matchTimeWidget.withProperties(Map.of("min", -1, "max", 135)); // this property setting isn't working

        m_lineDetectedWidget.withPosition(3, 0);
    }

    // This will be called in the robotPeriodic
    public void update() {
        // Match time
        DriverStation ds = DriverStation.getInstance();
        double matchTime = ds.getMatchTime();
        Brain.matchTimeEntry.setDouble(matchTime);

        // Line Detector
        boolean detected = Robot.robotLineDetectorHatch.lineDetected();
        Brain.lineDetectedEntry.setBoolean(detected);
    }

}
