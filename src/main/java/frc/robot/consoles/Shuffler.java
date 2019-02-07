
package frc.robot.consoles;


// Class that wraps all of the interaction with the Shuffleboard

// All decisions about content and layout of the Shuffleboard are consolidated in this file
// to make it easier to change things rather than having to look throughout all of the 
// classes for subsystems, commands, etc.

// The Shuffler class knows about the subsystems, commands, etc.  but generally not vice versa
public class Shuffler {

    // Tabs
    private MainTab m_mainTab;
    private DriveTab m_driveTab;
    private SubsystemsTab m_subsystemsTab;
    private DebugTab m_debugTab;

    public Shuffler() {
    }

    public void initialize() {
        Logging.logTrivial("Initializing Shuffler...");

        setupSmartdashboard();

        m_mainTab = new MainTab();
        m_driveTab = new DriveTab();
        m_subsystemsTab = new SubsystemsTab();
        m_debugTab = new DebugTab();
    }

    public void update() {
        m_mainTab.update();
        m_driveTab.update();
        m_subsystemsTab.update();
        m_debugTab.update();
    }

    // This is for stuff that can't be displayed easily in custom Shuffleboard tabs
    // Will end up on the SmartDashboard tab
    private void setupSmartdashboard() {

        // SmartDashboard.putData("Command Scheduler",Scheduler.getInstance());

    }

}
