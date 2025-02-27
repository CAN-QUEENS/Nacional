package org.firstinspires.ftc.teamcode.vision.robot;

import com.qualcomm.robotcore.hardware.HardwareMap;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;
import org.openftc.easyopencv.OpenCvPipeline;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;

public class RobotVisionColor {
    private OpenCvCamera webcam;
    private String detectedColor = "Unknown";

    public void initialize(HardwareMap hardwareMap) {
        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier(
                "cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        webcam = OpenCvCameraFactory
                .getInstance()
                .createWebcam(hardwareMap.get(WebcamName.class, "Webcam 1"), cameraMonitorViewId);
        webcam.setPipeline(new CenterColorPipeline());

        webcam.openCameraDeviceAsync(new OpenCvCamera.AsyncCameraOpenListener() {
            @Override
            public void onOpened() {
                webcam.startStreaming(320, 240, OpenCvCameraRotation.UPRIGHT);
            }

            @Override
            public void onError(int errorCode) {}
        });
    }

    public String getDetectedColor() {
        return detectedColor;
    }

    private class CenterColorPipeline extends OpenCvPipeline {
        Mat hsv = new Mat();
        Mat maskRed = new Mat();
        Mat maskBlue = new Mat();
        Mat maskYellow = new Mat();
        Scalar lowerRed = new Scalar(0, 100, 100);
        Scalar upperRed = new Scalar(10, 255, 255);
        Scalar lowerBlue = new Scalar(110, 100, 100);
        Scalar upperBlue = new Scalar(130, 255, 255);
        Scalar lowerYellow = new Scalar(20, 100, 100);
        Scalar upperYellow = new Scalar(30, 255, 255);

        @Override
        public Mat processFrame(Mat input) {
            Imgproc.cvtColor(input, hsv, Imgproc.COLOR_RGB2HSV);

            Rect centerRect = new Rect(input.width() / 2 - 20, input.height() / 2 - 20, 40, 40);
            Mat centerMat = hsv.submat(centerRect);

            Core.inRange(centerMat, lowerRed, upperRed, maskRed);
            Core.inRange(centerMat, lowerBlue, upperBlue, maskBlue);
            Core.inRange(centerMat, lowerYellow, upperYellow, maskYellow);

            double redValue = Core.mean(maskRed).val[0];
            double blueValue = Core.mean(maskBlue).val[0];
            double yellowValue = Core.mean(maskYellow).val[0];

            if (redValue > blueValue && redValue > yellowValue) {
                detectedColor = "Red";
            } else if (blueValue > redValue && blueValue > yellowValue) {
                detectedColor = "Blue";
            } else if (yellowValue > redValue && yellowValue > blueValue) {
                detectedColor = "Yellow";
            } else {
                detectedColor = "Unknown";
            }

            return input;
        }
    }
}