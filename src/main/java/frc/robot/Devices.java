
package frc.robot;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.drive.MecanumDrive;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Relay;


// This class contains singletons for human interface devices and robot components, and mappings for each
public class Devices {

    // Joysticks
    public static Joystick jstick = new Joystick(0);
    public static JoystickButton jstickBtn1 = new JoystickButton(jstick, 1);
    public static JoystickButton jstickBtn2 = new JoystickButton(jstick, 2);
    public static JoystickButton jstickBtn3 = new JoystickButton(jstick, 3);
    public static JoystickButton jstickBtn4 = new JoystickButton(jstick, 4);
    public static JoystickButton jstickBtn5 = new JoystickButton(jstick, 5);
    public static JoystickButton jstickBtn6 = new JoystickButton(jstick, 6);
    public static JoystickButton jstickBtn7 = new JoystickButton(jstick, 7);
    public static JoystickButton jstickBtn8 = new JoystickButton(jstick, 8);
    public static JoystickButton jstickBtn9 = new JoystickButton(jstick, 9);
    public static JoystickButton jstickBtn10 = new JoystickButton(jstick, 10);
    public static JoystickButton jstickBtn11 = new JoystickButton(jstick, 11);
    public static JoystickButton jstickBtn12 = new JoystickButton(jstick, 12);

    // Xbox Controllers
    public static XboxController xbox = new XboxController(1);

    // Relays
    public static Relay lighterRelay = new Relay(1);

    // Speed Controllers
    public static WPI_TalonSRX talonSrxWheelFrontRight = new WPI_TalonSRX(1); // 1 motor
    public static WPI_TalonSRX talonSrxWheelFrontLeft = new WPI_TalonSRX(2); // 1 motor
    public static WPI_TalonSRX talonSrxWheelRearRight = new WPI_TalonSRX(3); // 1 motor
    public static WPI_TalonSRX talonSrxWheelRearLeft = new WPI_TalonSRX(4); // 1 motor

    public static WPI_TalonSRX talonSrxHatcher = new WPI_TalonSRX(5); // 1 motor
    public static WPI_TalonSRX talonSrxBaller = new WPI_TalonSRX(6); // 1 motor

    public static WPI_TalonSRX talonSrxClimbArm = new WPI_TalonSRX(7); // 1 motor
    public static WPI_TalonSRX talonSrxClimbTank = new WPI_TalonSRX(8); // 1 motor
    public static WPI_TalonSRX talonSrxLiftPulley = new WPI_TalonSRX(9); // 4 motors
    public static WPI_TalonSRX talonSrxLiftWheel = new WPI_TalonSRX(10); // 1 motor

    // Limit Switches
    public static DigitalInput limitSwitchHatchGrabbed  = new DigitalInput(1); // TODO: Replace this with encoders
    public static DigitalInput limitSwitchHatchReleased = new DigitalInput(2); // TODO: Replace this with encoders
    public static DigitalInput limitSwitchBallBlocked = new DigitalInput(3);
    public static DigitalInput limitSwitchBallTossed = new DigitalInput(4);

    // Drives
    public static MecanumDrive mecDrive = new MecanumDrive(talonSrxWheelFrontLeft, talonSrxWheelRearLeft, talonSrxWheelFrontRight, talonSrxWheelRearRight);

}
