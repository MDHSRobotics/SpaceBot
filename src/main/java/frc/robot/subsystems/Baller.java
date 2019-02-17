
package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.SensorCollection;
import edu.wpi.first.wpilibj.command.Subsystem;

import frc.robot.commands.idle.BallerStop;
import frc.robot.helpers.EncoderConstants;
import frc.robot.helpers.Logger;
import frc.robot.Devices;


// Baller subsystem, for holding and tossing cargo balls
public class Baller extends Subsystem {

    private boolean m_talonsAreConnected = false;

    private double m_targetRotations = 3.73; //Calculation: 4.44 -- TODO: Why is this comment out of sink with the value, but not so on Hatcher?
    private double m_targetPositionUnits;	
    private double m_stopThreshold = 10;
    // Choose so that Talon does not report sensor out of phase
    private boolean m_sensorPhase = false;
    // Choose based on what direction you want to be positive, this does not affect motor invert
    private boolean m_motorInvert = false;

    // The public property to determine the Baller state
    public boolean ballIsTossed = false;

    // Constructor
    public Baller() {
        Logger.debug("Constructing Subsystem: Baller...");

        m_talonsAreConnected = Devices.isConnected(Devices.talonSrxBaller);
        if (m_talonsAreConnected) {
            Devices.talonSrxBaller.configFactoryDefault();

            Devices.talonSrxBaller.configPeakCurrentDuration(40, 20);
            Devices.talonSrxBaller.configPeakCurrentLimit(11, 20);
            Devices.talonSrxBaller.configContinuousCurrentLimit(10, 20);

            Devices.talonSrxBaller.configNominalOutputForward(0);
            Devices.talonSrxBaller.configNominalOutputReverse(0);
            Devices.talonSrxBaller.configPeakOutputForward(0.3);
            Devices.talonSrxBaller.configPeakOutputReverse(-0.3);

            Devices.talonSrxBaller.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, EncoderConstants.kPIDLoopPrimary, EncoderConstants.kTimeoutMs);
            Devices.talonSrxBaller.setSensorPhase(m_sensorPhase);
            Devices.talonSrxBaller.setInverted(m_motorInvert);
            Devices.talonSrxBaller.configAllowableClosedloopError(0, EncoderConstants.kPIDLoopPrimary, EncoderConstants.kTimeoutMs);

            Devices.talonSrxBaller.config_kF(EncoderConstants.kPIDLoopPrimary, 0.0, EncoderConstants.kTimeoutMs);
            Devices.talonSrxBaller.config_kP(EncoderConstants.kPIDLoopPrimary, 0.06, EncoderConstants.kTimeoutMs);
            Devices.talonSrxBaller.config_kI(EncoderConstants.kPIDLoopPrimary, 0.0, EncoderConstants.kTimeoutMs);
            Devices.talonSrxBaller.config_kD(EncoderConstants.kPIDLoopPrimary, 0.0, EncoderConstants.kTimeoutMs);

            // Reset Encoder Position 
            Devices.talonSrxBaller.setSelectedSensorPosition(0, 0, 20);
            SensorCollection sensorCol = Devices.talonSrxBaller.getSensorCollection();
            int absolutePosition = sensorCol.getPulseWidthPosition();
            absolutePosition &= 0xFFF;
            if (m_sensorPhase)	absolutePosition *= -1;
            if (m_motorInvert)	absolutePosition *= -1;
            // Set the quadrature (relative) sensor to match absolute
            Devices.talonSrxBaller.setSelectedSensorPosition(absolutePosition, EncoderConstants.kPIDLoopPrimary, EncoderConstants.kTimeoutMs);
            
            Devices.talonSrxBaller.configMotionAcceleration(1500, 20);
            Devices.talonSrxBaller.configMotionCruiseVelocity(4000, 20);
        }
    }

    @Override
    public void initDefaultCommand() {
        Logger.debug("Initializing Baller DefaultCommand -> BallerStop...");

        setDefaultCommand(new BallerStop());
    }

    // Stop the Baller motor
    public void stop() {
        if (m_talonsAreConnected) {
            Devices.talonSrxBaller.stopMotor();
        }
    }

    // Move the Baller flipper to toss the ball
    public void tossBall() {
        if (m_talonsAreConnected) {
            Devices.talonSrxBaller.setSelectedSensorPosition(0, 0, 20);
            m_targetPositionUnits = m_targetRotations * EncoderConstants.kRedlineEncoderTpr;
            Logger.debug("Baller -> Target Toss Position: " + m_targetPositionUnits);
            Devices.talonSrxBaller.set(ControlMode.Position, m_targetPositionUnits);
        }
    }

    // Move the Baller flipper back to the hold position
    public void holdBall() {
        if (m_talonsAreConnected) {
            Devices.talonSrxBaller.setSelectedSensorPosition(0, 0, 20);
            m_targetPositionUnits = -(m_targetRotations * EncoderConstants.kRedlineEncoderTpr);
            Logger.debug("Baller -> Target Hold Position: " + m_targetPositionUnits);
            Devices.talonSrxBaller.set(ControlMode.Position, m_targetPositionUnits);
        }
    }

    // Get the current Baller flipper motor velocity
    public int getVelocity() {
        return Devices.talonSrxBaller.getSelectedSensorVelocity();
    }

    // Get the current Baller flipper motor position
    public int getPosition() {
        return Devices.talonSrxBaller.getSelectedSensorPosition();
    }

    // Return whether or not the motor has reached the encoded position
    public boolean isPositionMet() {
        if (!m_talonsAreConnected) return true;
        int currentPosition = getPosition();
        m_targetPositionUnits = m_targetRotations * EncoderConstants.kRedlineEncoderTpr;
        return (Math.abs((Math.abs(currentPosition) - m_targetPositionUnits)) < 600);
    }

    // Toggle the ballIsTossed state
    public void toggleBallTossed() {
        ballIsTossed = !ballIsTossed;
    }

    //---------//
    // Testing //
    //---------//

    public void driveStatic() {
        if (m_talonsAreConnected) {
            Devices.talonSrxBaller.set(0.2);
        }
    }

}
