
package frc.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;

import frc.robot.commands.idle.HatcherStop;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
// import com.ctre.phoenix.motorcontrol.ControlMode;
import frc.robot.helpers.Logger;
import frc.robot.helpers.Constants;
import frc.robot.Devices;


// Hatcher subsystem, for grabbing and releasing hatches
public class Hatcher extends Subsystem {

    private boolean m_talonsAreConnected = false;
    private double m_secondsFromNeutralToFull = 1.0;
    private int m_timeoutMS = 10;
    private double m_speed = 0.2; 

    public Hatcher() {
        Logger.debug("Constructing Subsystem: Hatcher...");

        m_talonsAreConnected = Devices.isConnected(Devices.talonSrxHatcher);
        Devices.talonSrxHatcher.configOpenloopRamp(m_secondsFromNeutralToFull, m_timeoutMS);
        Devices.talonSrxHatch.configFactoryDefault();

        Devices.talonSrxHatch.configPeakCurrentDuration(40, 20);
        Devices.talonSrxHatch.configPeakCurrentLimit(11, 20);
        Devices.talonSrxHatch.configContinuousCurrentLimit(10, 20);

        Devices.talonSrxHatch.configNominalOutputForward(0);
        Devices.talonSrxHatch.configNominalOutputReverse(0);
        Devices.talonSrxHatch.configPeakOutputForward(0.3);
        Devices.talonSrxHatch.configPeakOutputReverse(-0.3);

        //Config TalonSRX Redline encoder     
        Devices.talonSrxHatch.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, Constants.kPIDLoopPrimary, Constants.kTimeoutMs);
        Devices.talonSrxHatch.setSensorPhase(true);
        Devices.talonSrxHatch.setInverted(true);
        Devices.talonSrxHatch.configAllowableClosedloopError(Constants.kPIDLoopPrimary, 0, Constants.kTimeoutMs);

        Devices.talonSrxHatch.config_kF(Constants.kPIDLoopPrimary, 0.0, Constants.kTimeoutMs);
        Devices.talonSrxHatch.config_kP(Constants.kPIDLoopPrimary, 0.2, Constants.kTimeoutMs);
        Devices.talonSrxHatch.config_kI(Constants.kPIDLoopPrimary, 0.0, Constants.kTimeoutMs);
        Devices.talonSrxHatch.config_kD(Constants.kPIDLoopPrimary, 0.0, Constants.kTimeoutMs);	

    }

    @Override
    public void initDefaultCommand() {
        Logger.debug("Initializing Hatcher DefaultCommand -> HatcherStop...");

        setDefaultCommand(new HatcherStop());
    }

    // Stop all the drive motors
    public void stop() {
        if (m_talonsAreConnected) {
            Devices.talonSrxHatcher.stopMotor();
        }
    }

    // Opens or closes the Hatcher claw based on speed
    public void clawOpen() {

       // Devices.talonSrxHatch.set(ControlMode.Position, 27); //temporary tick
        Devices.talonSrxHatch.set(ControlMode.Position, 1024); //temporary tick
    }

    public int getPosition(){
        return Devices.talonSrxHatch.getSelectedSensorPosition(); 
    }

    public int getVelocity(){
        return Devices.talonSrxHatch.getSelectedSensorVelocity();
    }

    public void resetEncoderPosition(){
        Devices.talonSrxHatch.setSelectedSensorPosition(0, Constants.kPIDLoopPrimary, Constants.kTimeoutMs);
    }

    public void joystickMove(double joystickValue){
        Devices.talonSrxHatch.set(ControlMode.PercentOutput, joystickValue);
        
    }

    public void driveStatic(){
        Devices.talonSrxHatch.set(-0.2);
    }
}
