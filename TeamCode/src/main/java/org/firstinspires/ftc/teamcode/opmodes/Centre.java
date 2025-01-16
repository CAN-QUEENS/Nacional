package org.firstinspires.ftc.teamcode.opmodes;

import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.PoseVelocity2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.teamcode.MecanumDrive;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;
import org.openftc.easyopencv.OpenCvPipeline;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;

import java.util.List;

@Autonomous(name = "CENTRE")
public class Centre extends LinearOpMode {

    OpenCvCamera camera;
    private YellowObjectDetectionPipeline pipeline;
    MecanumDrive drive = new MecanumDrive(hardwareMap, new Pose2d(0, 0, 0));

    private static final double TURN_GAIN = 0.005; // Ajuste de giro
    private static final double DRIVE_GAIN = 0.005; // Ajuste de avance/retroceso
    private static final int FRAME_CENTER_X = 320; // Centro del frame (horizontal)
    private static final double TOLERANCE = 20; // Tolerancia para considerar centrado

    @Override
    public void runOpMode() {
        // Inicializar la cámara y la detección de color
        initCamera();

        // Inicializar el drive de Road Runner

        telemetry.addData(">", "Press Play to start");
        telemetry.update();

        waitForStart();

        while (opModeIsActive()) {
            double objectCenterX = pipeline.getObjectCenterX();
            if (objectCenterX != -1) {
                double error = objectCenterX - FRAME_CENTER_X;

                if (Math.abs(error) > TOLERANCE) {
                    // Ajustar el movimiento del robot basándose en el error
                    double turnPower = error * TURN_GAIN;
                    drive.setDrivePowers(new PoseVelocity2d(
                            new Vector2d(0, 0), -turnPower
                    ));
                } else {
                    // Avanzar si el objeto está centrado
                    drive.setDrivePowers(new PoseVelocity2d(
                            new Vector2d(DRIVE_GAIN, 0), 0
                    ));
                }

                telemetry.addData("Object Center X", objectCenterX);
                telemetry.addData("Error", error);
            } else {
                // Detener el robot si no se detecta el objeto
                drive.setDrivePowers(new PoseVelocity2d(
                        new Vector2d(0, 0), 0
                ));
                telemetry.addData("Object", "Not found");
            }
            telemetry.update();
        }
    }

    private void initCamera() {
        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier(
                "cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        camera = OpenCvCameraFactory.getInstance().createWebcam(
                hardwareMap.get(WebcamName.class, "Webcam 1"), cameraMonitorViewId);

        pipeline = new YellowObjectDetectionPipeline();
        camera.setPipeline(pipeline);

        camera.openCameraDeviceAsync(new OpenCvCamera.AsyncCameraOpenListener() {
            @Override
            public void onOpened() {
                camera.startStreaming(640, 480, OpenCvCameraRotation.UPRIGHT);
            }

            @Override
            public void onError(int errorCode) {
                telemetry.addData("Error", "Camera could not be opened: " + errorCode);
                telemetry.update();
            }
        });
    }

    // Pipeline para detectar el color amarillo
    static class YellowObjectDetectionPipeline extends OpenCvPipeline {
        private double objectCenterX = -1;

        @Override
        public Mat processFrame(Mat input) {
            Mat hsvImage = new Mat();
            Imgproc.cvtColor(input, hsvImage, Imgproc.COLOR_RGB2HSV);

            Scalar lowerBound = new Scalar(20, 100, 100); // Rango de amarillo
            Scalar upperBound = new Scalar(30, 255, 255);
            Mat mask = new Mat();
            Core.inRange(hsvImage, lowerBound, upperBound, mask);

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

            hsvImage.release();
            mask.release();
            hierarchy.release();

            return input;
        }

        public double getObjectCenterX() {
            return objectCenterX;
        }
    }
}