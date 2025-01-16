package org.firstinspires.ftc.teamcode.vision;

import org.openftc.easyopencv.OpenCvPipeline;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import java.util.List;

public class ColorDetector extends OpenCvPipeline {
    private String selectedColor = "RED";

    public void setSelectedColor(String color) {
        this.selectedColor = color;
    }

    @Override
    public Mat processFrame(Mat input) {
        Mat hsvImage = new Mat();
        Mat mask = new Mat();
        Mat mask2 = new Mat(); // Para RED
        Mat hierarchy = new Mat();

        Imgproc.cvtColor(input, hsvImage, Imgproc.COLOR_RGB2HSV);

        Scalar lowerBound = new Scalar(0, 0, 0);
        Scalar upperBound = new Scalar(0, 0, 0);

        switch (selectedColor) {
            case "RED":
                lowerBound = new Scalar(0, 100, 100);
                upperBound = new Scalar(10, 255, 255);
                Core.inRange(hsvImage, lowerBound, upperBound, mask);

                Scalar lowerBound2 = new Scalar(160, 100, 100);
                Scalar upperBound2 = new Scalar(179, 255, 255);
                Core.inRange(hsvImage, lowerBound2, upperBound2, mask2);
                Core.add(mask, mask2, mask);
                mask2.release();
                break;

            case "BLUE":
                lowerBound = new Scalar(100, 100, 100);
                upperBound = new Scalar(140, 255, 255);
                Core.inRange(hsvImage, lowerBound, upperBound, mask);
                break;

            case "YELLOW":
                lowerBound = new Scalar(20, 100, 100);
                upperBound = new Scalar(30, 255, 255);
                Core.inRange(hsvImage, lowerBound, upperBound, mask);
                break;
        }

        List<MatOfPoint> contours = new java.util.ArrayList<>();
        Imgproc.findContours(mask, contours, hierarchy, Imgproc.RETR_EXTERNAL, Imgproc.CHAIN_APPROX_SIMPLE);

        Imgproc.drawContours(input, contours, -1, new Scalar(255, 0, 0), 2);

        hsvImage.release();
        mask.release();
        hierarchy.release();

        for (MatOfPoint contour : contours) {
            contour.release();
        }

        return input;
    }
}