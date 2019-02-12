
package frc.robot;

import com.analog.adis16448.frc.ADIS16448_IMU;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.drive.MecanumDrive;
import edu.wpi.first.wpilibj.DigitalInput;
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
    public static final XboxController xbox = new XboxController(1);
    public static final JoystickButton xboxBtn1 = new JoystickButton(xbox, 1); // A
    public static final JoystickButton xboxBtn2 = new JoystickButton(xbox, 2); // B
    public static final JoystickButton xboxBtn3 = new JoystickButton(xbox, 3); // X
    public static final JoystickButton xboxBtn4 = new JoystickButton(xbox, 4); // Y
    public static final JoystickButton xboxBtn5 = new JoystickButton(xbox, 5); // Bumper Left
    public static final JoystickButton xboxBtn6 = new JoystickButton(xbox, 6); // Bumper Right
    public static final JoystickButton xboxBtn7 = new JoystickButton(xbox, 7); // Back
    public static final JoystickButton xboxBtn8 = new JoystickButton(xbox, 8); // Start
    public static final JoystickButton xboxBtn9 = new JoystickButton(xbox, 9); // Stick Left
    public static final JoystickButton xboxBtn10 = new JoystickButton(xbox, 10); // Stick Right

    // Relays
    public static final Relay lighterRelay = new Relay(1);

    // Speed Controllers
    public static final WPI_TalonSRX talonSrxMecWheelFrontRight = new WPI_TalonSRX(1); // 1 motor
    public static final WPI_TalonSRX talonSrxMecWheelFrontLeft = new WPI_TalonSRX(2); // 1 motor
    public static final WPI_TalonSRX talonSrxMecWheelRearRight = new WPI_TalonSRX(3); // 1 motor
    public static final WPI_TalonSRX talonSrxMecWheelRearLeft = new WPI_TalonSRX(4); // 1 motor

    public static final WPI_TalonSRX talonSrxHatcher = new WPI_TalonSRX(5); // 1 motor
    public static final WPI_TalonSRX talonSrxBaller = new WPI_TalonSRX(6); // 1 motor

    public static final WPI_TalonSRX talonSrxArm = new WPI_TalonSRX(7); // 1 motor
    public static final WPI_TalonSRX talonSrxTank = new WPI_TalonSRX(8); // 1 motor
    public static final WPI_TalonSRX talonSrxPulley = new WPI_TalonSRX(9); // 4 motors
    public static final WPI_TalonSRX talonSrxPusher = new WPI_TalonSRX(10); // 1 motor

    // Drives
    public static final MecanumDrive mecDrive = new MecanumDrive(talonSrxMecWheelFrontLeft,
                                                                 talonSrxMecWheelRearLeft,
                                                                 talonSrxMecWheelFrontRight,
                                                                 talonSrxMecWheelRearRight);
                                               
    // Gyros
    public static final ADIS16448_IMU imuMecDrive = new ADIS16448_IMU();
    
    // Limit Switches
    public static final DigitalInput limitSwitchHatchGrabbed  = new DigitalInput(1); // TODO: Replace this with encoders
    public static final DigitalInput limitSwitchHatchReleased = new DigitalInput(2); // TODO: Replace this with encoders
    public static final DigitalInput limitSwitchBallHeld = new DigitalInput(3);
    public static final DigitalInput limitSwitchBallTossed = new DigitalInput(4);


    public static boolean isConnected(WPI_TalonSRX talon) {
        int firmVer = talon.getFirmwareVersion();
        boolean connected = (firmVer != -1);
        if (!connected) {
            talon.disable();
        }
        return connected;
    }

}
