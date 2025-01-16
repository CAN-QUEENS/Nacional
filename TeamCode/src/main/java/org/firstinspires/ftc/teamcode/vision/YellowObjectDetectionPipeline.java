package org.firstinspires.ftc.teamcode.vision;
// Importación y definición de la clase YellowObjectDetectionPipeline
import org.opencv.core.Mat;
import org.opencv.core.Scalar;
import org.opencv.core.MatOfPoint;
import org.opencv.imgproc.Imgproc;
import org.opencv.core.Core;
import org.openftc.easyopencv.OpenCvPipeline;

import java.util.List;
 class YellowObjectDetectionPipeline extends OpenCvPipeline {
    private double objectCenterX = -1;

    @Override
    public Mat processFrame(Mat input) {
        // Convertir la imagen de RGB a HSV
        Mat hsvImage = new Mat();
        Imgproc.cvtColor(input, hsvImage, Imgproc.COLOR_RGB2HSV);

        // Definir el rango de color amarillo en el espacio HSV
        Scalar lowerBound = new Scalar(20, 100, 100); // Rango de amarillo
        Scalar upperBound = new Scalar(30, 255, 255);
        Mat mask = new Mat();
        Core.inRange(hsvImage, lowerBound, upperBound, mask);

        // Buscar contornos en la máscara
        List<MatOfPoint> contours = new java.util.ArrayList<>();
        Mat hierarchy = new Mat();
        Imgproc.findContours(mask, contours, hierarchy, Imgproc.RETR_EXTERNAL, Imgproc.CHAIN_APPROX_SIMPLE);

        if (!contours.isEmpty()) {
            MatOfPoint largestContour = contours.get(0);
            double maxArea = Imgproc.contourArea(largestContour);
            for (int i = 1; i < contours.size(); i++) {
                double area = Imgproc.contourArea(contours.get(i));
                if (area > maxArea) {
                    largestContour = contours.get(i);
                    maxArea = area;
                }
            }

            if (largestContour.rows() > 0) {
                org.opencv.core.Rect boundingRect = Imgproc.boundingRect(largestContour);
                objectCenterX = boundingRect.x + boundingRect.width / 2;
            }
        }

        // Liberar las matrices para evitar fugas de memoria
        hsvImage.release();
        mask.release();
        hierarchy.release();
        for (MatOfPoint contour : contours) {
            contour.release();
        }

        // Devolver la imagen procesada
        return input;
    }

    public double getObjectCenterX() {
        return objectCenterX;
    }
}