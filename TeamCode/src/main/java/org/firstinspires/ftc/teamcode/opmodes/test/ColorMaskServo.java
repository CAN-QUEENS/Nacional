package org.firstinspires.ftc.teamcode.opmodes.test;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import org.openftc.easyopencv.*;

import java.util.ArrayList;
import java.util.List;

@TeleOp(name="ColorMaskServo", group="Iterative Opmode")
public class ColorMaskServo extends OpMode {
    private OpenCvCamera camera;
    private Servo servo;
    private static final double SERVO_MIN = 0.0;
    private static final double SERVO_MAX = 1.0;

    @Override
    public void init() {
        int cameraMonitorViewId = hardwareMap.appContext.getResources()
                .getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());

        camera = OpenCvCameraFactory.getInstance().createWebcam(hardwareMap.get(WebcamName.class, "Webcam 1"), cameraMonitorViewId);
        servo = hardwareMap.get(Servo.class, "servo");

        camera.setPipeline(new ColorDetectionPipeline());

        // Corrección del error al abrir la cámara
        camera.openCameraDeviceAsync(new OpenCvCamera.AsyncCameraOpenListener() {
            @Override
            public void onOpened() {
                camera.startStreaming(320, 240, OpenCvCameraRotation.UPRIGHT);
            }

            @Override
            public void onError(int errorCode) {
                telemetry.addData("Error al abrir la cámara", errorCode);
            }
        });
    }

    @Override
    public void loop() {
        telemetry.update();
    }

    class ColorDetectionPipeline extends OpenCvPipeline {
        @Override
        public Mat processFrame(Mat input) {
            Mat hsv = new Mat();
            Imgproc.cvtColor(input, hsv, Imgproc.COLOR_RGB2HSV);

            Scalar lowerBound = new Scalar(20, 100, 100); // Amarillo
            Scalar upperBound = new Scalar(30, 255, 255);
            Mat mask = new Mat();
            Core.inRange(hsv, lowerBound, upperBound, mask);

            List<MatOfPoint> contours = new ArrayList<>();
            Mat hierarchy = new Mat();
            Imgproc.findContours(mask, contours, hierarchy, Imgproc.RETR_EXTERNAL, Imgproc.CHAIN_APPROX_SIMPLE);

            double largestArea = 0;
            Rect largestRect = null;

            for (MatOfPoint contour : contours) {
                Rect rect = Imgproc.boundingRect(contour);
                double area = rect.area();
                if (area > largestArea) {
                    largestArea = area;
                    largestRect = rect;
                }
            }

            if (largestRect != null) {
                Point center = new Point(largestRect.x + largestRect.width / 2.0, largestRect.y + largestRect.height / 2.0);
                double rotation = (center.x / input.cols()) * (SERVO_MAX - SERVO_MIN) + SERVO_MIN;
                rotation = Math.max(SERVO_MIN, Math.min(SERVO_MAX, rotation));
                servo.setPosition(rotation);
            }

            return mask;
        }
    }
}