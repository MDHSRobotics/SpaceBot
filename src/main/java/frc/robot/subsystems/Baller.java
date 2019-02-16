
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

    // TODO: Be explicit. Are these public or private? Make them private unless they need to be public. Name them accordingly.
    double targetRotations = 3.73;
    double targetPositionUnits;	
    private static final double k_stopThreshold = 10;

    // Choose so that Talon does not report sensor out of phase
    public static boolean kSensorPhase = false;

    // Choose based on what direction you want to be positive, this does not affect motor invert
    public static boolean kMotorInvert = false;

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
            Devices.talonSrxBaller.setSensorPhase(kSensorPhase);
            Devices.talonSrxBaller.setInverted(kMotorInvert);
            Devices.talonSrxBaller.configAllowableClosedloopError(0, EncoderConstants.kPIDLoopPrimary, EncoderConstants.kTimeoutMs);

            Devices.talonSrxBaller.config_kF(EncoderConstants.kPIDLoopPrimary, 0.0, EncoderConstants.kTimeoutMs);
            Devices.talonSrxBaller.config_kP(EncoderConstants.kPIDLoopPrimary, 0.06, EncoderConstants.kTimeoutMs);
            Devices.talonSrxBaller.config_kI(EncoderConstants.kPIDLoopPrimary, 0.0, EncoderConstants.kTimeoutMs);
            Devices.talonSrxBaller.config_kD(EncoderConstants.kPIDLoopPrimary, 0.0, EncoderConstants.kTimeoutMs);

            //Reset Encoder Position 
            Devices.talonSrxBaller.setSelectedSensorPosition(0, 0, 20);
            SensorCollection sensorCol = Devices.talonSrxBaller.getSensorCollection();
            int absolutePosition = sensorCol.getPulseWidthPosition();
            absolutePosition &= 0xFFF;
            if (kSensorPhase)	absolutePosition *= -1;
            if (kMotorInvert)	absolutePosition *= -1;
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
            targetPositionUnits = targetRotations * EncoderConstants.kRedlineEncoderTpr;
            Logger.debug("Baller -> Target Toss Position: " + targetPositionUnits);
            Devices.talonSrxBaller.set(ControlMode.Position, targetPositionUnits);
        }
    }

    // Move the Baller flipper back to the hold position
    public void holdBall() {
        if (m_talonsAreConnected) {
            Devices.talonSrxBaller.setSelectedSensorPosition(0, 0, 20);
            targetPositionUnits = -(targetRotations * EncoderConstants.kRedlineEncoderTpr);
            Logger.debug("Baller -> Target Hold Position: " + targetPositionUnits);
            Devices.talonSrxBaller.set(ControlMode.Position, targetPositionUnits);
        }
    }

    // Get the current Baller flipper motor velocity
    public int getVelocity() {
        if (!m_talonsAreConnected) return -1; // TODO: Is there a better return value?
        return Devices.talonSrxHatcher.getSelectedSensorVelocity();
    }

    // Get the current Baller flipper motor position
    public int getPosition() {
        if (!m_talonsAreConnected) return -1; // TODO: Is there a better return value?
        return Devices.talonSrxBaller.getSelectedSensorPosition();
    }

    public boolean isPositionMet() {
        int currentPosition = getPosition();
        if (currentPosition == -1) return true;
        targetPositionUnits = targetRotations * EncoderConstants.kRedlineEncoderTpr;
        return (Math.abs((Math.abs(currentPosition) - targetPositionUnits)) < 600);
    }

    public void toggleBallTossed() {
        ballIsTossed = !ballIsTossed;
    }

    //--------//
    // Unused //
    //--------//

    public void resetEncoderPosition() {
        if (m_talonsAreConnected) {
            Devices.talonSrxBaller.setSelectedSensorPosition(0, EncoderConstants.kPIDLoopPrimary, EncoderConstants.kTimeoutMs);
        }
    }

    public boolean isStopped() {
        if (!m_talonsAreConnected) return true;
        double currentVelocity = Devices.talonSrxBaller.getSelectedSensorVelocity();
        return (Math.abs(currentVelocity) < k_stopThreshold);
    }

}
