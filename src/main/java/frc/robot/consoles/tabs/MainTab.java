
package frc.robot.consoles.tabs;

import edu.wpi.first.networktables.NetworkTableEntry;

import edu.wpi.first.wpilibj.shuffleboard.*;
import edu.wpi.first.wpilibj.DriverStation;
import java.util.Map;

import frc.robot.Robot;


// The Shuffleboard Main Tab
public class MainTab {

    // Tab, layout, and widget objects
    private ShuffleboardTab m_tab;
    private SimpleWidget m_matchTimeWidget;
    private ComplexWidget m_autoCmdWidget;
    private SimpleWidget m_lineDetectWidget;

    // Updatable entries, either by user or under program control
    private NetworkTableEntry nt_driverMatchTime;

    // Constructor
    public MainTab() {

        m_tab = Shuffleboard.getTab("Main");

        // Autonomous Command
        m_autoCmdWidget = m_tab.add("Auto Command", Robot.autoCommandChooser);
        m_autoCmdWidget.withPosition(0, 0);
        m_autoCmdWidget.withSize(2, 1);

        // Match time
        m_matchTimeWidget = m_tab.add("Match Time", 0.0);
        m_matchTimeWidget.withPosition(2, 0);
        m_matchTimeWidget.withProperties(Map.of("min", -1, "max", 135)); // this property setting isn't working
        m_matchTimeWidget.withWidget(BuiltInWidgets.kDial);
        nt_driverMatchTime = m_matchTimeWidget.getEntry();

        //Line Detector
        boolean detected = Robot.robotLineDetectorHatch.lineDetected();
        m_lineDetectWidget = m_tab.add("Line Detected", detected);
        m_lineDetectWidget.withPosition(3,0);
    }

    // This will be called in the robotPeriodic
    public void update() {
        double matchTime = DriverStation.getInstance().getMatchTime();
        nt_driverMatchTime.setDouble(matchTime);
    }

}
