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
import java.util.List;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Scalar;
import org.opencv.core.MatOfPoint;
import org.opencv.imgproc.Imgproc;

@Autonomous(name = "CENTRE")
public class Centre extends LinearOpMode {
    OpenCvCamera camera;
    private YellowObjectDetectionPipeline pipeline;
    private double speedFactor = 0.5;  // Factor de velocidad para el movimiento del robot
    private double tolerance = 20;  // Tolerancia para el error

    @Override
    public void runOpMode() {
        MecanumDrive drive = new MecanumDrive(hardwareMap, new Pose2d(0,0,0));

        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        camera = OpenCvCameraFactory.getInstance().createWebcam(hardwareMap.get(WebcamName.class, "Webcam 1"), cameraMonitorViewId);

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

        waitForStart();

        while (opModeIsActive()) {
            // Obtener la posición del objeto amarillo
            double objectCenterX = pipeline.getObjectCenterX();

            // Calcular el error en el centro (si el centro está muy a la izquierda o derecha)
            double error = objectCenterX - 320; // 320 es el centro horizontal de la cámara (640x480)

            // Mover el robot basado en el error
            moveRobotToCenter(drive, error);

            // Telemetría
            telemetry.addData("Object Center X", objectCenterX);
            telemetry.addData("Error", error);
            telemetry.update();
        }
    }

    private void moveRobotToCenter(MecanumDrive drive, double error) {
        double x = 0;
        double y = 0;
        double rx = 0;

        // Si el objeto está desplazado a la izquierda o derecha, mover el robot
        if (Math.abs(error) > tolerance) {
            // El objeto está fuera del centro
            if (error > 0) {
                // Mover hacia la izquierda (si el objeto está a la derecha)
                x = -speedFactor; // Movimiento a la izquierda
            } else {
                // Mover hacia la derecha (si el objeto está a la izquierda)
                x = speedFactor; // Movimiento a la derecha
            }
        } else {
            // El objeto está suficientemente centrado, el robot puede avanzar
            y = speedFactor; // Mover hacia adelante
        }

        // Crear el PoseVelocity2d para controlar el movimiento
        drive.setDrivePowers(new PoseVelocity2d(
                new Vector2d(y, -x), -rx
        ));
    }

    // Pipeline para detectar el color amarillo
    static class YellowObjectDetectionPipeline extends OpenCvPipeline {
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

            // Si encontramos contornos, encontrar el contorno más grande
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

                // Dibujar el contorno más grande en la imagen
                Imgproc.drawContours(input, contours, -1, new Scalar(255, 0, 0), 2);

                // Calcular el centro del contorno más grande
                if (largestContour != null && largestContour.rows() > 0) {
                    // Obtener el rectángulo delimitador del contorno
                    org.opencv.core.Rect boundingRect = Imgproc.boundingRect(largestContour);
                    objectCenterX = boundingRect.x + boundingRect.width / 2;
                }
            }

            // Liberar matrices
            hsvImage.release();
            mask.release();
            hierarchy.release();

            // Devolver la imagen con los contornos dibujados
            return input;
        }

        public double getObjectCenterX() {
            return objectCenterX;
        }
    }
}