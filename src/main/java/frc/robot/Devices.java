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
    public static JoystickButton jstickBtn9 = new JoystickButton(jstick, 9);
    public static JoystickButton jstickBtn10 = new JoystickButton(jstick, 10);

    // Xbox Controller singletons
    public static XboxController xbox = new XboxController(1);

    // Speed Controller singletons
    public static WPI_TalonSRX talonSrxWheelFrontRight = new WPI_TalonSRX(1);
    public static WPI_TalonSRX talonSrxWheelFrontLeft = new WPI_TalonSRX(2);
    public static WPI_TalonSRX talonSrxWheelRearRight = new WPI_TalonSRX(3);
    public static WPI_TalonSRX talonSrxWheelRearLeft = new WPI_TalonSRX(4);
    public static WPI_TalonSRX talonSrxHatch = new WPI_TalonSRX(5);
    public static WPI_TalonSRX talonSrxBaller = new WPI_TalonSRX(6);
    public static WPI_TalonSRX talonSrxClimbFoot = new WPI_TalonSRX(7); //4 motors attached 
    public static WPI_TalonSRX talonSrxClimbTank = new WPI_TalonSRX(8); //1 motor attached
    public static WPI_TalonSRX talonSrxClimbArm = new WPI_TalonSRX(9);  //1 motor attached 
                                                                        //1 talonsrx for each micro-subsystem


    // Drive singletons
    public static MecanumDrive mecDrive = new MecanumDrive(talonSrxWheelFrontLeft, talonSrxWheelRearLeft, talonSrxWheelFrontRight, talonSrxWheelRearRight);
}
