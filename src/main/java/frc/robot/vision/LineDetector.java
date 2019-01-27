/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.vision;

import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.vision.VisionThread;
import java.util.ArrayList;
import org.opencv.core.*;
import org.opencv.imgproc.Imgproc;


public class LineDetector {

    private static final int IMG_WIDTH = 320;
	private static final int IMG_HEIGHT = 240;

	private VisionThread m_visionThread;
	private double m_angle = 0.0;

	private final Object m_imgLock = new Object();

    public LineDetector() {
        UsbCamera camera = CameraServer.getInstance().startAutomaticCapture();
        camera.setResolution(IMG_WIDTH, IMG_HEIGHT);

        LinePipeline linePipe = new LinePipeline();
        m_visionThread = new VisionThread(camera, linePipe, pipeline -> {
            ArrayList<MatOfPoint> output = pipeline.filterContoursOutput();
            boolean outputIsEmpty = output.isEmpty();
            if (!outputIsEmpty) {
                MatOfPoint contour = output.get(0);

                // Get the rotated rectangle.
                Point[] points = contour.toArray();
                MatOfPoint2f contour2f = new MatOfPoint2f(points);
                RotatedRect rotRect = Imgproc.minAreaRect(contour2f);

                // Get the rotation angle.
                double angle = rotRect.angle;
                if (rotRect.size.width < rotRect.size.height) {
                  angle = 90 + angle;
                }

                synchronized (m_imgLock) {
                    m_angle = angle;
                }
            }
        });
        m_visionThread.start();
    }

    public double getCurrentAngle() {
        double angle;
        synchronized (m_imgLock) {
            angle = m_angle;
        }
        return angle;
    }

}
