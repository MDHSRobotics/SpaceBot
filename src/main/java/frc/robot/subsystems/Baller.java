
package frc.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;

import frc.robot.commands.idle.BallerStop;
import frc.robot.helpers.Logger;
import frc.robot.Devices;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import frc.robot.helpers.EncoderConstants;

// Cargo ball subsystem
public class Baller extends Subsystem {
    double targetRotations = 0.5;
    double unitsPerRevolution = 4096;
    double targetPositionUnits;	
    double yAxisValue = Devices.jstick.getY();
	double motorOutput = Devices.talonSrxBaller.getMotorOutputPercent();
    boolean button1Pressed = Devices.jstick.getRawButton(1);

    private boolean m_talonsAreConnected = false;
    private double m_secondsFromNeutralToFull = 1.0;
    private int m_timeoutMS = 10;
    private double m_speed = 0.2; 

      /* choose so that Talon does not report sensor out of phase */
      public static boolean kSensorPhase = false;

      /* choose based on what direction you want to be positive,
          this does not affect motor invert. */
      public static boolean kMotorInvert = false;

    public Baller() {
        Logger.debug("Constructing Subsystem: Baller...");

        m_talonsAreConnected = Devices.isConnected(Devices.talonSrxBaller);
        Devices.talonSrxBaller.configOpenloopRamp(m_secondsFromNeutralToFull, m_timeoutMS);
        Devices.talonSrxBaller.configFactoryDefault();

        Devices.talonSrxBaller.configPeakCurrentDuration(40, 20);
        Devices.talonSrxBaller.configPeakCurrentLimit(11, 20);
        Devices.talonSrxBaller.configContinuousCurrentLimit(10, 20);

        Devices.talonSrxBaller.configNominalOutputForward(0);
        Devices.talonSrxBaller.configNominalOutputReverse(0);
        Devices.talonSrxBaller.configPeakOutputForward(0.5);
        Devices.talonSrxBaller.configPeakOutputReverse(-0.5);

        Devices.talonSrxBaller.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, EncoderConstants.kPIDLoopPrimary, EncoderConstants.kTimeoutMs);
        Devices.talonSrxBaller.setSensorPhase(true);
        Devices.talonSrxBaller.setInverted(false);
        Devices.talonSrxBaller.configAllowableClosedloopError(0, EncoderConstants.kPIDLoopPrimary, EncoderConstants.kTimeoutMs);

        Devices.talonSrxBaller.config_kF(EncoderConstants.kPIDLoopPrimary, 0.0, EncoderConstants.kTimeoutMs);
        Devices.talonSrxBaller.config_kP(EncoderConstants.kPIDLoopPrimary, 0.06, EncoderConstants.kTimeoutMs);
        Devices.talonSrxBaller.config_kI(EncoderConstants.kPIDLoopPrimary, 0.0, EncoderConstants.kTimeoutMs);
        Devices.talonSrxBaller.config_kD(EncoderConstants.kPIDLoopPrimary, 0.0, EncoderConstants.kTimeoutMs);	

        //Reset Encoder Position 
        Devices.talonSrxBaller.setSelectedSensorPosition(0, 0, 20);
        int absolutePosition = Devices.talonSrxBaller.getSensorCollection().getPulseWidthPosition();
        absolutePosition &= 0xFFF;
		if (kSensorPhase)	absolutePosition *= -1;
        if (kMotorInvert)	absolutePosition *= -1;
        // Set the quadrature (relative) sensor to match absolute
		Devices.talonSrxBaller.setSelectedSensorPosition(absolutePosition, EncoderConstants.kPIDLoopPrimary, EncoderConstants.kTimeoutMs);

        //Devices.talonSrxBaller.configOpenloopRamp(m_secondsFromNeutralToFull, m_timeoutMS);
    }

    @Override
    public void initDefaultCommand() {
        Logger.debug("Initializing Baller DefaultCommand -> BallerStop...");

        setDefaultCommand(new BallerStop());
    }

    // Stop all the drive motors
    public void stop() {
        if (m_talonsAreConnected) {
            Devices.talonSrxBaller.stopMotor();
        }
    }

    // Holds the ball
    public void holdBall() {
        if (m_talonsAreConnected) {
            Devices.talonSrxBaller.set(-m_speed);
        }
    }

    public int getPosition(){
        return Devices.talonSrxBaller.getSelectedSensorPosition(); 
    }

    public int getVelocity(){
        return Devices.talonSrxBaller.getSelectedSensorVelocity();
    }

    public void resetEncoderPosition(){
        Devices.talonSrxBaller.setSelectedSensorPosition(0, EncoderConstants.kPIDLoopPrimary, EncoderConstants.kTimeoutMs);
    }

    public void joystickMove(double joystickValue){
        Devices.talonSrxBaller.set(ControlMode.PercentOutput, joystickValue);
        
    }

    public void ballRaise(){
        
        Devices.talonSrxBaller.setSelectedSensorPosition(0, 0, 20);

        // Scale target based on max joystick value - express in sensor units
        targetPositionUnits = targetRotations * unitsPerRevolution;
        Devices.talonSrxBaller.set(ControlMode.Position, targetPositionUnits);
        Logger.debug("Target Position: " + targetPositionUnits);

    
    }

    public void ballClose(){

        // Scale target based on max joystick value - express in sensor units
        Devices.talonSrxBaller.set(ControlMode.Position, 0);
        Logger.debug("Target Position: " + targetPositionUnits);

    }
}
