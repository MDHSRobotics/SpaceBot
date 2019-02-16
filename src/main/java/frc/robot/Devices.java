
package frc.robot;

import com.analog.adis16448.frc.ADIS16448_IMU;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.drive.MecanumDrive;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.XboxController;


// This class contains singleton constants, for human interface devices and robot components, and mappings for each
public class Devices {

    // Joysticks
    public static final Joystick jstick = new Joystick(0);
    public static final JoystickButton jstickBtn1 = new JoystickButton(jstick, 1); // Trigger
    public static final JoystickButton jstickBtn2 = new JoystickButton(jstick, 2);
    public static final JoystickButton jstickBtn3 = new JoystickButton(jstick, 3);
    public static final JoystickButton jstickBtn4 = new JoystickButton(jstick, 4);
    public static final JoystickButton jstickBtn5 = new JoystickButton(jstick, 5);
    public static final JoystickButton jstickBtn6 = new JoystickButton(jstick, 6);
    public static final JoystickButton jstickBtn7 = new JoystickButton(jstick, 7);
    public static final JoystickButton jstickBtn8 = new JoystickButton(jstick, 8);
    public static final JoystickButton jstickBtn9 = new JoystickButton(jstick, 9);
    public static final JoystickButton jstickBtn10 = new JoystickButton(jstick, 10);
    public static final JoystickButton jstickBtn11 = new JoystickButton(jstick, 11);
    public static final JoystickButton jstickBtn12 = new JoystickButton(jstick, 12);

    // Xbox Controllers
    public static final XboxController driveXbox = new XboxController(0);
    public static final JoystickButton driveXboxBtn1 = new JoystickButton(driveXbox, 1); // A
    public static final JoystickButton driveXboxBtn2 = new JoystickButton(driveXbox, 2); // B
    public static final JoystickButton driveXboxBtn3 = new JoystickButton(driveXbox, 3); // X
    public static final JoystickButton driveXboxBtn4 = new JoystickButton(driveXbox, 4); // Y
    public static final JoystickButton driveXboxBtn5 = new JoystickButton(driveXbox, 5); // Bumper Left
    public static final JoystickButton driveXboxBtn6 = new JoystickButton(driveXbox, 6); // Bumper Right
    public static final JoystickButton driveXboxBtn7 = new JoystickButton(driveXbox, 7); // Back
    public static final JoystickButton driveXboxBtn8 = new JoystickButton(driveXbox, 8); // Start
    public static final JoystickButton driveXboxBtn9 = new JoystickButton(driveXbox, 9); // Stick Left
    public static final JoystickButton driveXboxBtn10 = new JoystickButton(driveXbox, 10); // Stick Right

    public static final XboxController controlXbox = new XboxController(1);
    public static final JoystickButton controlXboxBtn1 = new JoystickButton(controlXbox, 1); // A
    public static final JoystickButton controlXboxBtn2 = new JoystickButton(controlXbox, 2); // B
    public static final JoystickButton controlXboxBtn3 = new JoystickButton(controlXbox, 3); // X
    public static final JoystickButton controlXboxBtn4 = new JoystickButton(controlXbox, 4); // Y
    public static final JoystickButton controlXboxBtn5 = new JoystickButton(controlXbox, 5); // Bumper Left
    public static final JoystickButton controlXboxBtn6 = new JoystickButton(controlXbox, 6); // Bumper Right
    public static final JoystickButton controlXboxBtn7 = new JoystickButton(controlXbox, 7); // Back
    public static final JoystickButton controlXboxBtn8 = new JoystickButton(controlXbox, 8); // Start
    public static final JoystickButton controlXboxBtn9 = new JoystickButton(controlXbox, 9); // Stick Left
    public static final JoystickButton controlXboxBtn10 = new JoystickButton(controlXbox, 10); // Stick Right

    // Relays
    public static final Relay lighterRelay = new Relay(1);

    // Speed Controllers
    public static final WPI_TalonSRX talonSrxMecWheelFrontRight = new WPI_TalonSRX(1); // 1 motor
    public static final WPI_TalonSRX talonSrxMecWheelFrontLeft = new WPI_TalonSRX(2); // 1 motor
    public static final WPI_TalonSRX talonSrxMecWheelRearRight = new WPI_TalonSRX(3); // 1 motor
    public static final WPI_TalonSRX talonSrxMecWheelRearLeft = new WPI_TalonSRX(4); // 1 motor

    public static final WPI_TalonSRX talonSrxHatcher = new WPI_TalonSRX(9); // 1 motor
    public static final WPI_TalonSRX talonSrxBaller = new WPI_TalonSRX(10); // 1 motor

    public static final WPI_TalonSRX talonSrxArm = new WPI_TalonSRX(7); // 1 motor
    public static final WPI_TalonSRX talonSrxTank = new WPI_TalonSRX(8); // 1 motor
    public static final WPI_TalonSRX talonSrxPulley = new WPI_TalonSRX(5); // 4 motors
    public static final WPI_TalonSRX talonSrxPusher = new WPI_TalonSRX(6); // 1 motor

    // Drives
    public static final MecanumDrive mecDrive = new MecanumDrive(talonSrxMecWheelFrontLeft,
                                                                 talonSrxMecWheelRearLeft,
                                                                 talonSrxMecWheelFrontRight,
                                                                 talonSrxMecWheelRearRight);
                                               
    // Gyros
    public static final ADIS16448_IMU imuMecDrive = new ADIS16448_IMU();

    // Determines if the Talon SRX is connected
    public static boolean isConnected(WPI_TalonSRX talon) {
        int firmVer = talon.getFirmwareVersion();
        boolean connected = (firmVer != -1);
        return connected;
    }

}
