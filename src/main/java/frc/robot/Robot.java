
package frc.robot;

import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import frc.robot.commands.auto.*;
import frc.robot.commands.idle.*;
import frc.robot.consoles.*;
import frc.robot.helpers.Logger;
import frc.robot.subsystems.*;
import frc.robot.vision.CameraTester;
import frc.robot.vision.LineDetector;


/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {

    // Subsystems
    public static MecDriver robotMecDriver;
    public static Lighter robotLighter;

    public static Hatcher robotHatcher;
    public static Baller robotBaller;

    public static Arm robotArm;
    public static Tank robotTank;
    public static Pulley robotPulley;
    public static Pusher robotPusher;

    // Vision
    public static UsbCamera robotCameraSight;
    public static UsbCamera robotCameraLineHatch;
    public static UsbCamera robotCameraLineBall;
    public static UsbCamera robotCameraLineLeft;
    public static UsbCamera robotCameraLineRight;
    public static LineDetector robotLineDetectorHatch;
    public static LineDetector robotLineDetectorBall;
    public static LineDetector robotLineDetectorLeft;
    public static LineDetector robotLineDetectorRight;
    public static int camResolutionWidth = 320;
	public static int camResolutionHeight = 240;

    // OI
    public static OI robotOI;

    // Consoles
    public static Shuffler shuffler = new Shuffler();
    public static SendableChooser<Command> autoCommandChooser;
    private Command m_autoCmd;

    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    @Override
    public void robotInit() {
        Logger.debug("Initializing Robot...");

        // Instantiate subsystem singletons FIRST
        robotMecDriver = new MecDriver();
        robotLighter = new Lighter();

        robotHatcher = new Hatcher();
        robotBaller = new Baller();

        robotArm = new Arm();
        robotTank = new Tank();
        robotPulley = new Pulley();
        robotPusher = new Pusher();

        // Instantiate the OI singleton AFTER all the subsystems
        robotOI = new OI();

        // Instantiate auto commands
        Logger.debug("Adding AutoModes to SmartDashboard...");
        autoCommandChooser = new SendableChooser<>();

        autoCommandChooser.setDefaultOption("MecDrive - Stop", new MecDriverStop());
        autoCommandChooser.addOption("MecDrive - Forward", new MecDriveForward());
        autoCommandChooser.addOption("MecDrive - Turn Right", new MecDriveTurnRight());
        autoCommandChooser.addOption("MecDrive - Align Hatch", new MecDriveAlignHatch());

        // Add the commands to the SmartDashboard
        SmartDashboard.putData("AutoMode", autoCommandChooser);
        // Intialize the shuffler
        shuffler.initialize();

        // Test camera connections
        boolean cam0connected = CameraTester.testConnection(0);
        boolean cam1connected = CameraTester.testConnection(1);
        boolean cam2connected = CameraTester.testConnection(2);
        boolean cam3connected = CameraTester.testConnection(3);
        boolean cam4connected = CameraTester.testConnection(4);

        // Capture connected cameras
        if (cam0connected) robotCameraSight = CameraTester.captureCamera(0, camResolutionWidth, camResolutionHeight);
        if (cam1connected) robotCameraLineHatch = CameraTester.captureCamera(1, camResolutionWidth, camResolutionHeight);
        if (cam2connected) robotCameraLineBall = CameraTester.captureCamera(2, camResolutionWidth, camResolutionHeight);
        if (cam3connected) robotCameraLineLeft = CameraTester.captureCamera(3, camResolutionWidth, camResolutionHeight);
        if (cam4connected) robotCameraLineRight = CameraTester.captureCamera(4, camResolutionWidth, camResolutionHeight);

        // Instantiate Line Detector singletons
        robotLineDetectorHatch = new LineDetector(robotCameraLineHatch);
        robotLineDetectorBall = new LineDetector(robotCameraLineBall);
        robotLineDetectorLeft = new LineDetector(robotCameraLineLeft);
        robotLineDetectorRight = new LineDetector(robotCameraLineRight);
    }

    /**
     * This function is called every robot packet, no matter the mode. Use
     * this for items like diagnostics that you want ran during disabled,
     * autonomous, teleoperated and test.
     *
     * <p>This runs after the mode specific periodic functions, but before
     * LiveWindow and SmartDashboard integrated updating.
     */
    @Override
    public void robotPeriodic() {
        shuffler.update();
    }

    /**
     * This function is called once each time the robot enters Disabled mode.
     * You can use it to reset any subsystem information you want to clear when
     * the robot is disabled.
     */
    @Override
    public void disabledInit() {
        Logger.debug("Disabling Robot...");
    }

    @Override
    public void disabledPeriodic() {
        Scheduler.getInstance().run();
    }

    /**
     * This autonomous (along with the chooser code above) shows how to select
     * between different autonomous modes using the dashboard. The sendable
     * chooser code works with the Java SmartDashboard. If you prefer the
     * LabVIEW Dashboard, remove all of the chooser code and uncomment the
     * getString code to get the auto name from the text box below the Gyro
     *
     * <p>You can add additional auto modes by adding additional commands to the
     * chooser code above (like the commented example) or additional comparisons
     * to the switch structure below with additional strings & commands.
     */
    @Override
    public void autonomousInit() {
        Logger.debug("Initializing Autonomous...");

        m_autoCmd = autoCommandChooser.getSelected();

        /*
        String autoSelected = SmartDashboard.getString("Auto Selector", "Default");
        switch(autoSelected) {
            case "My Auto":
                autonomousCommand = new MyAutoCommand();
                break;
            case "Default Auto":
            default:
                autonomousCommand = new ExampleCommand();
                break;
        }
        */

        // Schedule the autonomous command
        if (m_autoCmd != null) {
            m_autoCmd.start();
        }
    }

    /**
     * This function is called periodically during autonomous.
     */
    @Override
    public void autonomousPeriodic() {
        Scheduler.getInstance().run();
    }

    @Override
    public void teleopInit() {
        Logger.debug("Initializing Teleop...");

        // This makes sure that the autonomous stops running when
        // teleop starts running. If you want the autonomous to
        // continue until interrupted by another command, remove
        // this line or comment it out.
        if (m_autoCmd != null) {
            m_autoCmd.cancel();
        }
    }

    /**
     * This function is called periodically during operator control.
     */
    @Override
    public void teleopPeriodic() {
        Scheduler.getInstance().run();
    }

    /**
     * This function is called periodically during test mode.
     */
    @Override
    public void testPeriodic() {
    }

}
