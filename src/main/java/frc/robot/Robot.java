/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import java.util.concurrent.TimeUnit;

import frc.robot.commands.AutoDriveDistance;
import frc.robot.commands.AutoDriveLine;
import frc.robot.commands.AutoDriveTurn;
import frc.robot.commands.IdleDrive;
import frc.robot.helpers.Logger;
import frc.robot.subsystems.*;
import frc.robot.vision.LineDetector;


/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {

    public static ClimbArm robotClimbArm;
    public static Baller robotBaller;
    public static Hatcher robotHatcher;
    public static MecDriver robotMecDriver;
    public static Tanker robotTanker;
    public static Pulley robotClimbPulley;

    public static UsbCamera robotLineCamera;
    public static LineDetector robotLineDetector;

    public static OI robotOI;
    

    public static final int lineCamResolutionWidth = 320;
	public static final int lineCamResolutionHeight = 240;

    private SendableChooser<Command> m_autoModeChooser;
    private Command m_autoCmd;

    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    @Override
    public void robotInit() {
        Logger.debug("Initializing Robot...");

        // Instantiate subsystem singletons FIRST
        robotClimbArm = new ClimbArm();
        robotBaller = new Baller();
        robotHatcher = new Hatcher();
        robotMecDriver = new MecDriver();


        // Instantiate the OI singleton AFTER all the subsystems
        robotOI = new OI();

        // Instantiate auto commands and add them to the SmartDashboard
        Logger.debug("Adding Auto modes to SmartDashboard...");
        m_autoModeChooser = new SendableChooser<>();

        m_autoModeChooser.setDefaultOption("Idle Drive", new IdleDrive());
        m_autoModeChooser.addOption("Drive Distance", new AutoDriveDistance());
        m_autoModeChooser.addOption("Drive Turn", new AutoDriveTurn());
        m_autoModeChooser.addOption("Drive Line Detect", new AutoDriveLine());

        SmartDashboard.putData("Auto mode", m_autoModeChooser);

        // Initialize camera, if connected
        Logger.debug("Checking for Camera Connection...");
        UsbCamera testCam = new UsbCamera("Test Camera", 0);
        Logger.debug("Waiting 1 second for the USB Camera to connect, if there is one...");
        try {
            TimeUnit.SECONDS.sleep(1);
        }
        catch (InterruptedException e) {
            Logger.debug("Swallowed InterruptedException...?");
        }
        boolean cameraIsConnected = testCam.isConnected();
        testCam.close();
        if (!cameraIsConnected) {
            Logger.debug("No USB Camera Found, Disabling Line Detection...");
            robotLineCamera = null;
        }
        else {
            Logger.debug("Starting Line Camera Capture...");
            CameraServer camServer = CameraServer.getInstance();
            robotLineCamera = camServer.startAutomaticCapture();
            robotLineCamera.setResolution(lineCamResolutionWidth, lineCamResolutionHeight);
        }

        // Instantiate Line Detector singleton
        robotLineDetector = new LineDetector(cameraIsConnected);
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

        m_autoCmd = m_autoModeChooser.getSelected();

        /*
        * String autoSelected = SmartDashboard.getString("Auto Selector",
        * "Default"); switch(autoSelected) { case "My Auto": autonomousCommand
        * = new MyAutoCommand(); break; case "Default Auto": default:
        * autonomousCommand = new ExampleCommand(); break; }
        */

        // schedule the autonomous command (example)
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
