
package frc.robot.vision;

import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.vision.VisionThread;
import java.util.ArrayList;
import org.opencv.core.*;
import org.opencv.imgproc.Imgproc;

import frc.robot.consoles.Logger;
import frc.robot.Brain;
import frc.robot.Robot;


public class LineDetector {

    private enum Quadrant {
        UPPERLEFT, UPPERRIGHT, LOWERLEFT, LOWERRIGHT;
    }

    private double m_minimumArea = (Robot.camResolutionHeight / 3) ^ 2;

    private double m_angleTarget = 90;
    private double m_centerXtarget = Robot.camResolutionWidth / 2;

    private double m_angleThreshold = 10;
    private double m_centerXthreshold = Robot.camResolutionWidth / 64;

    private VisionThread m_visionThread;

    // Constructor
    public LineDetector(UsbCamera lineCam) {
        Logger.setup("Constructing Vision: LineDetector...");

        if (lineCam != null) {
            intitialize(lineCam);
        }
    }

    public void intitialize(UsbCamera lineCam) {
        Logger.action("Intitializing Vision: LineDetector...");

        LinePipeline linePipe = new LinePipeline();
        m_visionThread = new VisionThread(lineCam, linePipe, pipeline -> {
            ArrayList<MatOfPoint> output = pipeline.filterContoursOutput();
            int outputSize = output.size();
            // We can only work with one contour
            if (outputSize == 1) {
                Logger.info("One contour identified, checking minimum size...");
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
                    Brain.setFrontLineArea(area);
                    Brain.setFrontLineAngle(angle);
                    Brain.setFrontLineXcenter(centerX);
                    Brain.setFrontLineYcenter(centerY);

                    Logger.info("Line Detected! Press Directional Pad to align robot!");
                }
            }
            else {
                // We can't work with these contours, so set everything to default
                Brain.setFrontLineArea(Brain.frontLineAreaDefault);
                Brain.setFrontLineAngle(Brain.frontLineAngleDefault);
                Brain.setFrontLineXcenter(Brain.frontLineXcenterDefault);
                Brain.setFrontLineYcenter(Brain.frontLineYcenterDefault);

                // TODO: consider checking all the contours, and if only one meets the minimum area requirements, use that
            }
        });
        Logger.action("Starting LineDetector Thread...");
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
        double area = Brain.getFrontLineArea();
        boolean detected = lineDetected(area);
        return detected;
    }

    public boolean lineDetected(double area) {
        boolean detected = area >= m_minimumArea;
        return detected;
    }

    public boolean isStraight() {
        double angle = Brain.getFrontLineAngle();
        boolean straight = isStraight(angle);
        return straight;
    }

    public boolean isStraight(double angle) {
        //boolean straight = (m_targetAngle - m_angleThreshold <= angle && angle <= m_targetAngle + m_angleThreshold);
        boolean straight = (m_angleTarget - m_angleThreshold <= angle || angle <= -m_angleTarget + m_angleThreshold);
        return straight;
    }

    public boolean isCentered() {
        double centerX = Brain.getFrontLineXcenter();
        boolean centered = isCentered(centerX);
        return centered;
    }

    public boolean isCentered(double centerX) {
        boolean centered = (m_centerXtarget - m_centerXthreshold <= centerX && centerX <= m_centerXtarget + m_centerXthreshold);
        return centered;
    }

    public double getCorrectedZ() {
        double angle = Brain.getFrontLineAngle();
        return m_angleTarget + angle;
    }

    public double getCorrectedX() {
        double centerX = Brain.getFrontLineXcenter();
        return m_centerXtarget - centerX;
    }

}
