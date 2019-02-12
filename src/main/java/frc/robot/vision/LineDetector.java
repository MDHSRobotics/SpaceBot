
package frc.robot.vision;

import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.vision.VisionThread;
import java.util.ArrayList;
import org.opencv.core.*;
import org.opencv.imgproc.Imgproc;

import frc.robot.Brain;
import frc.robot.Robot;
import frc.robot.helpers.Logger;


public class LineDetector {

    private enum Quadrant {
        UPPERLEFT, UPPERRIGHT, LOWERLEFT, LOWERRIGHT;
    }

    private double m_minimumArea = (Robot.camResolutionHeight / 3) ^ 2;

    private double m_targetAngle = 90;
    private double m_targetCenterX = Robot.camResolutionWidth / 2;

    private double m_angleThreshold = 10;
    private double m_centerXThreshold = Robot.camResolutionWidth / 64;

    private VisionThread m_visionThread;

    // Constructor
    public LineDetector(UsbCamera lineCam) {
        Logger.debug("Constructing Vision: LineDetector...");

        if (lineCam != null) {
            intitialize(lineCam);
        }
    }

    public void intitialize(UsbCamera lineCam) {
        Logger.debug("Intitializing Vision: LineDetector...");

        LinePipeline linePipe = new LinePipeline();
        m_visionThread = new VisionThread(lineCam, linePipe, pipeline -> {
            ArrayList<MatOfPoint> output = pipeline.filterContoursOutput();
            int outputSize = output.size();
            // We can only work with one contour
            if (outputSize == 1) {
                Logger.debug("One contour identified, checking minimum size...");
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

                    // Add the values to NetworkTables via the Brain
                    Brain.setHatchLineArea(area);
                    Brain.setHatchLineAngle(angle);
                    Brain.setHatchLineXcenter(centerX);
                    Brain.setHatchLineYcenter(centerY);

                    Logger.debug("Line Detected! Pull joystick trigger to align robot!");
                }
            }
            else {
                // We can't work with these contours, so set everything to default
                Brain.setHatchLineArea(Brain.hatchLineAreaDefault);
                Brain.setHatchLineAngle(Brain.hatchLineAngleDefault);
                Brain.setHatchLineXcenter(Brain.hatchLineXcenterDefault);
                Brain.setHatchLineYcenter(Brain.hatchLineYcenterDefault);

                // TODO: consider checking all the contours, and if only one meets the minimum area requirements, use that
            }
        });
        Logger.debug("Starting LineDetector Thread...");
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

    public boolean lineDetected() {
        double area = Brain.getHatchLineArea();
        boolean detected = lineDetected(area);
        return detected;
    }

    public boolean lineDetected(double area) {
        boolean detected = area >= m_minimumArea;
        return detected;
    }

    public boolean isStraight() {
        double angle = Brain.getHatchLineAngle();
        boolean straight = isStraight(angle);
        return straight;
    }

    public boolean isStraight(double angle) {
        //boolean straight = (m_targetAngle - m_angleThreshold <= angle && angle <= m_targetAngle + m_angleThreshold);
        boolean straight = (m_targetAngle - m_angleThreshold <= angle || angle <= -m_targetAngle + m_angleThreshold); 
        return straight;
    }

    public boolean isCentered() {
        double centerX = Brain.getHatchLineXcenter();
        boolean centered = isCentered(centerX);
        return centered;
    }

    public boolean isCentered(double centerX) {
        boolean centered = (m_targetCenterX - m_centerXThreshold <= centerX && centerX <= m_targetCenterX + m_centerXThreshold);
        return centered;
    }

    public double getCorrectedZ() {
        double angle = Brain.getHatchLineAngle();
        return m_targetAngle - angle;
    }

    public double getCorrectedX() {
        double centerX = Brain.getHatchLineXcenter();
        return m_targetCenterX - centerX;
    }

}
