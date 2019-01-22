/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.drive.MecanumDrive;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;

// This class contains singleton human interface devices, robot components, and mappings
public class Devices {

    // Joystick singletons
    public static Joystick jstick = new Joystick(0);
    public static JoystickButton jstickBtn5 = new JoystickButton(jstick, 5);
    public static JoystickButton jstickBtn8 = new JoystickButton(jstick, 8);
    public static JoystickButton jstickBtn7 = new JoystickButton(jstick, 7);

    // Xbox Controller singletons
    public static XboxController xbox = new XboxController(1);

    // Speed Controller singletons
    public static WPI_TalonSRX talonSrxWheelFrontRight = new WPI_TalonSRX(1);
    public static WPI_TalonSRX talonSrxWheelFrontLeft = new WPI_TalonSRX(2);
    public static WPI_TalonSRX talonSrxWheelRearRight = new WPI_TalonSRX(3);
    public static WPI_TalonSRX talonSrxWheelRearLeft = new WPI_TalonSRX(4);

    // Drive singletons
    public static MecanumDrive mecDrive = new MecanumDrive(talonSrxWheelFrontLeft, talonSrxWheelRearLeft, talonSrxWheelFrontRight, talonSrxWheelRearRight);
}
