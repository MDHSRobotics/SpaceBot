
package frc.robot.vision;

import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.vision.VisionThread;
import java.util.ArrayList;
import org.opencv.core.*;
import org.opencv.imgproc.Imgproc;

import frc.robot.Robot;
import frc.robot.helpers.Logger;


public class LineDetector {

    private enum Quadrant {
        UPPERLEFT, UPPERRIGHT, LOWERLEFT, LOWERRIGHT;
    }

    private double m_minimumArea = (Robot.camResolutionHeight / 3) ^ 2;

    private double m_targetAngle = 0;
    private double m_targetCenterX = Robot.camResolutionWidth / 2;

    private double m_angleThreshold = 10;
    private double m_centerXThreshold = Robot.camResolutionWidth / 64;

    private double m_area = 0;
    private double m_angle = 0;
    private double m_centerX = 0;
    private double m_centerY = 0;

    private VisionThread m_visionThread;
    private final Object m_imgLock = new Object();

    // Constructor
    public LineDetector(UsbCamera lineCam) {
        Logger.debug("Constructing Line Detector...");

        if (lineCam != null) {
            intitialize(lineCam);
        }
    }

    public void intitialize(UsbCamera lineCam) {
        Logger.debug("Intitializing Line Detector...");

        LinePipeline linePipe = new LinePipeline();
        m_visionThread = new VisionThread(lineCam, linePipe, pipeline -> {
            ArrayList<MatOfPoint> output = pipeline.filterContoursOutput();
            int outputSize = output.size();
            // We can only work with one contour
            if (outputSize == 1) {
                MatOfPoint contour = output.get(0);

                // Get the rotated rectangle
                Point[] points = contour.toArray();
                MatOfPoint2f contour2f = new MatOfPoint2f(points);
                RotatedRect rotRect = Imgproc.minAreaRect(contour2f);

                // Get the area of the rotated rectangle
                double area = rotRect.size.area();
                if (area >= m_minimumArea)
                {
                    // Get the center X & Y of the bounding rectangle
                    Rect boundRect = rotRect.boundingRect();
                    double centerX = boundRect.x + (boundRect.width / 2);
                    double centerY = boundRect.y + (boundRect.height / 2);

                    // Get the rotation angle of the rotated rectangle
                    double angle = rotRect.angle;
                    if (rotRect.size.width < rotRect.size.height) {
                        angle = 90 + angle;
                    }
                    Quadrant centerQuad = getQuadrant(centerX, centerY);
                    switch (centerQuad) {
                        case UPPERLEFT:
                            break;
                        case UPPERRIGHT:
                            break;
                        case LOWERLEFT:
                            if (angle > 0) angle = angle - 180;
                            break;
                        case LOWERRIGHT:
                            if (angle < 0) angle = angle + 180;
                            break;
                    }

                    // Set the thread-safe variables for use by outside commands
                    synchronized (m_imgLock) {
                        m_area = area;
                        m_angle = angle;
                        m_centerX = centerX;
                        m_centerY = centerY;
                    }
                }
            }
            else {
                // We can't work with these contours, so set the area to zero
                synchronized (m_imgLock) {
                    m_area = 0;
                }
            }
        });
        Logger.debug("Starting Line Detector Thread...");
        m_visionThread.start();
    }

    private Quadrant getQuadrant(double x, double y) {
        boolean isUpper = (y <= Robot.camResolutionHeight / 2);
        boolean isLeft = (x <= Robot.camResolutionWidth / 2);
        if (isUpper) {
            if (isLeft) {
                return Quadrant.UPPERLEFT;
            }
            else {
                return Quadrant.UPPERRIGHT;
            }
        }
        else {
            if (isLeft) {
                return Quadrant.LOWERLEFT;
            }
            else {
                return Quadrant.LOWERRIGHT;
            }
        }
    }

    public double getCurrentArea() {
        double area;
        synchronized (m_imgLock) {
            area = m_area;
        }
        return area;
    }

    public double getCurrentAngle() {
        double angle;
        synchronized (m_imgLock) {
            angle = m_angle;
        }
        return angle;
    }

    public double getCurrentCenterX() {
        double centerX;
        synchronized (m_imgLock) {
            centerX = m_centerX;
        }
        return centerX;
    }

    public double getCurrentCenterY() {
        double centerY;
        synchronized (m_imgLock) {
            centerY = m_centerY;
        }
        return centerY;
    }

    public boolean lineDetected() {
        double area = getCurrentArea();
        boolean detected = lineDetected(area);
        return detected;
    }

    public boolean lineDetected(double area) {
        boolean detected = area >= m_minimumArea;
        return detected;
    }

    public boolean isStraight() {
        double angle = getCurrentAngle();
        boolean straight = isStraight(angle);
        return straight;
    }

    public boolean isStraight(double angle) {
        boolean straight = (m_targetAngle - m_angleThreshold <= angle && angle <= m_targetAngle + m_angleThreshold);
        return straight;
    }

    public boolean isCentered() {
        double centerX = getCurrentCenterX();
        boolean centered = isCentered(centerX);
        return centered;
    }

    public boolean isCentered(double centerX) {
        boolean centered = (m_targetCenterX - m_centerXThreshold <= centerX && centerX <= m_targetCenterX + m_centerXThreshold);
        return centered;
    }

    public double getCorrectedZ() {
        double angle = getCurrentAngle();
        return m_targetAngle - angle;
    }

    public double getCorrectedX() {
        double centerX = getCurrentCenterX();
        return m_targetCenterX - centerX;
    }

}
