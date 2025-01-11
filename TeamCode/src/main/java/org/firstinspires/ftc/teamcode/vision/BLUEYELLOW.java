package org.firstinspires.ftc.teamcode.vision;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;
import org.openftc.easyopencv.OpenCvPipeline;
import org.openftc.easyopencv.OpenCvWebcam;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.MatOfPoint2f;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import org.opencv.core.RotatedRect;

import java.util.ArrayList;
import java.util.List;

@TeleOp(name = "Color Detection Toggle", group = "Concept")
public class BLUEYELLOW extends OpMode {
    private OpenCvWebcam webcam;
    private ColorDetectionPipeline pipeline;
    private boolean detectBlue = true; // Inicia con detección de azul

    @Override
    public void init() {
        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier(
                "cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        webcam = OpenCvCameraFactory.getInstance().createWebcam(
                hardwareMap.get(WebcamName.class, "Webcam 1"), cameraMonitorViewId);

        pipeline = new ColorDetectionPipeline();
        webcam.setPipeline(pipeline);

        webcam.openCameraDeviceAsync(new OpenCvCamera.AsyncCameraOpenListener() {
            @Override
            public void onOpened() {
                webcam.startStreaming(320, 240, OpenCvCameraRotation.UPRIGHT);
            }

            @Override
            public void onError(int errorCode) {
                telemetry.addData("Error", "Camera could not be opened: " + errorCode);
                telemetry.update();
            }
        });
    }

    @Override
    public void loop() {
        if (gamepad1.a) {
            detectBlue = true;
            pipeline.setColorRange(new Scalar(100, 150, 0), new Scalar(140, 255, 255)); // Azul
        } else if (gamepad1.b) {
            detectBlue = false;
            pipeline.setColorRange(new Scalar(20, 100, 100), new Scalar(30, 255, 255)); // Amarillo
        }

        telemetry.addData("Detecting", detectBlue ? "Blue" : "Yellow");
        telemetry.addData("Angle", pipeline.getDetectedAngle());
        telemetry.update();
    }

    // Pipeline para procesar la imagen con diferentes colores
    static class ColorDetectionPipeline extends OpenCvPipeline {
        private Scalar lowerColor;
        private Scalar upperColor;
        private double detectedAngle = 0;

        public void setColorRange(Scalar lower, Scalar upper) {
            lowerColor = lower;
            upperColor = upper;
        }

        @Override
        public Mat processFrame(Mat input) {
            Mat hsvImage = new Mat();
            Imgproc.cvtColor(input, hsvImage, Imgproc.COLOR_RGB2HSV);

            // Crear la máscara
            Mat mask = new Mat();
            Core.inRange(hsvImage, lowerColor, upperColor, mask);

            // Encontrar contornos
            List<MatOfPoint> contours = new ArrayList<>();
            Imgproc.findContours(mask, contours, new Mat(), Imgproc.RETR_TREE, Imgproc.CHAIN_APPROX_SIMPLE);

            // Detectar orientación del primer contorno
            if (!contours.isEmpty()) {
                RotatedRect rotatedRect = Imgproc.minAreaRect(new MatOfPoint2f(contours.get(0).toArray()));
                detectedAngle = rotatedRect.angle;
                Point[] rectPoints = new Point[4];
                rotatedRect.points(rectPoints);
                for (int i = 0; i < 4; i++) {
                    Imgproc.line(input, rectPoints[i], rectPoints[(i + 1) % 4], new Scalar(255, 0, 0), 2);
                }
            }

            return input;
        }

        public double getDetectedAngle() {
            return detectedAngle;
        }
    }
}