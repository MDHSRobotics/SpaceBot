
package frc.robot;

import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import frc.robot.commands.auto.*;
import frc.robot.commands.idle.*;
import frc.robot.commands.instant.*;
import frc.robot.consoles.*;
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

    // Robot States
    public enum GameMode {
        DELIVERY, CLIMB
    }

    public enum DeliveryMode {
        GET_HATCH, ATTACH_HATCH, GET_BALL, TOSS_BALL
    }

    public enum ClimbMode {
        ARM, LIFT, CLIMB
    }

    public enum PulleyMode {
        DOWN, UP
    }

    // Game Mode is used to activate/deactivate the Climb Xbox Controller
    public static GameMode robotGameMode = GameMode.DELIVERY;
    // Delivery Mode is used to control vision processing actions, as well as xbox controller activation
    // TODO: We need to implement ways to set the Robot DeliveryMode, either manually, or automatically, or a combination
    // TODO: Determine the best default. What's the first action the Robot will take during Sandstorm?
    public static DeliveryMode robotDeliveryMode = DeliveryMode.GET_HATCH;
    // Climb Mode tells the climb commands which system needs to be activated next
    public static ClimbMode robotClimbMode = ClimbMode.ARM;

    // Core Classes
    public static Logger robotLogger;
    public static Devices robotDevices;

    // Subsystems
    public static MecDriver robotMecDriver;
    public static Lighter robotLighter;

    public static Hatcher robotHatcher;
    public static Baller robotBaller;

    public static Arm robotArm;
    public static Tank robotTank;
    public static Pulley robotPulley;

    // Vision
    public static UsbCamera robotCameraSight;
    public static UsbCamera robotCameraLineFront;
    public static UsbCamera robotCameraLineLeft;
    public static UsbCamera robotCameraLineRight;
    public static LineDetector robotLineDetectorFront;
    public static LineDetector robotLineDetectorLeft;
    public static LineDetector robotLineDetectorRight;
    public static int camResolutionWidth = 320;
	public static int camResolutionHeight = 240;

    // Consoles
    public static SendableChooser<Command> autoCommandChooser;
    private Command m_autoCmd;
    public static Shuffler robotShuffler = new Shuffler();

    // OI
    public static OI robotOI;

    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    @Override
    public void robotInit() {
        // Instantiate the Logger FIRST to support ANSI on Windows 10
        robotLogger = new Logger();
        Logger.setup("Initializing Robot...");

        // Instantiate Devices SECOND
        robotDevices = new Devices();

        // Pre-intialize the Shuffler THIRD
        robotShuffler.preInitialize();

        // Instantiate subsystem singletons FOURTH
        robotMecDriver = new MecDriver();
        robotLighter = new Lighter();

        robotHatcher = new Hatcher();
        robotBaller = new Baller();

        robotArm = new Arm();
        robotTank = new Tank();
        robotPulley = new Pulley();

        // Test camera connections
        boolean cam0connected = CameraTester.testConnection(0);
        boolean cam1connected = CameraTester.testConnection(1);
        boolean cam2connected = CameraTester.testConnection(2);
        boolean cam3connected = CameraTester.testConnection(3);

        // Capture connected cameras
        if (cam0connected) robotCameraSight = CameraTester.captureCamera(0, camResolutionWidth, camResolutionHeight);
        if (cam1connected) robotCameraLineFront = CameraTester.captureCamera(1, camResolutionWidth, camResolutionHeight);
        if (cam2connected) robotCameraLineLeft = CameraTester.captureCamera(2, camResolutionWidth, camResolutionHeight);
        if (cam3connected) robotCameraLineRight = CameraTester.captureCamera(2, camResolutionWidth, camResolutionHeight);

        // Instantiate Line Detector singletons
        robotLineDetectorFront = new LineDetector(robotCameraLineFront);
        robotLineDetectorLeft = new LineDetector(robotCameraLineLeft);
        robotLineDetectorRight = new LineDetector(robotCameraLineRight);

        // Add the commands to the SmartDashboard
        Logger.setup("Adding AutoModes to SmartDashboard...");
        autoCommandChooser = new SendableChooser<>();

        autoCommandChooser.setDefaultOption("MecDrive - Stop", new MecDriverStop());
        autoCommandChooser.addOption("MecDrive - Forward", new MecDriveForward());
        autoCommandChooser.addOption("MecDrive - Turn Right", new MecDriveTurnRight());
        autoCommandChooser.addOption("MecDrive - Toggle Orientation", new MecDriveToggleOrientation());

        SmartDashboard.putData("AutoMode", autoCommandChooser);

        // Intialize and configure the shuffler, and instantiate OI, in that order, LAST
        robotShuffler.initialize();
        robotShuffler.configure();
        robotOI = new OI();
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
        robotShuffler.update();
    }

    /**
     * This function is called once each time the robot enters Disabled mode.
     * You can use it to reset any subsystem information you want to clear when
     * the robot is disabled.
     */
    @Override
    public void disabledInit() {
        Logger.ending("Disabling Robot...");
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
        Logger.action("Initializing Autonomous...");

        m_autoCmd = autoCommandChooser.getSelected();

        /*
        String autoSelected = SmartDashboard.getString("Auto Selector", "Default");
        switch (autoSelected) {
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
        Logger.action("Initializing Teleop...");

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
