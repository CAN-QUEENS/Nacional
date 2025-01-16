package org.firstinspires.ftc.teamcode.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;
import org.openftc.easyopencv.OpenCvPipeline;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Scalar;
import org.opencv.core.MatOfPoint;
import org.opencv.imgproc.Imgproc;
import java.util.List;

@TeleOp(name = "Color Selection TeleOp with Contours")
public class ColorDetector extends LinearOpMode {
    OpenCvCamera camera;
    private String selectedColor = "RED";

    @Override
    public void runOpMode() {
        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier(
                "cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        camera = OpenCvCameraFactory.getInstance().createWebcam(
                hardwareMap.get(WebcamName.class, "Webcam 1"), cameraMonitorViewId);

        MultiColorDetectionPipeline pipeline = new MultiColorDetectionPipeline();
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
            if (gamepad1.a) {
                selectedColor = "RED";
            } else if (gamepad1.b) {
                selectedColor = "BLUE";
            } else if (gamepad1.y) {
                selectedColor = "YELLOW";
            }

            pipeline.setSelectedColor(selectedColor);

            telemetry.addData("Selected Color", selectedColor);
            telemetry.update();
        }
    }

    static class MultiColorDetectionPipeline extends OpenCvPipeline {
        private String selectedColor = "RED";

        public void setSelectedColor(String color) {
            this.selectedColor = color;
        }

        @Override
        public Mat processFrame(Mat input) {
            // Inicializar matrices
            Mat hsvImage = new Mat();
            Mat mask = new Mat();

            // Convertir la imagen a espacio de color HSV
            Imgproc.cvtColor(input, hsvImage, Imgproc.COLOR_RGB2HSV);

            // Rango de colores para los tres colores seleccionados
            Scalar lowerBound = new Scalar(0, 0, 0);
            Scalar upperBound = new Scalar(0, 0, 0);

            switch (selectedColor) {
                case "RED":
                    lowerBound = new Scalar(0, 100, 100);
                    upperBound = new Scalar(10, 255, 255);
                    Core.inRange(hsvImage, lowerBound, upperBound, mask);

                    // Red tiene dos rangos debido al espectro HSV
                    Scalar lowerBound2 = new Scalar(160, 100, 100);
                    Scalar upperBound2 = new Scalar(179, 255, 255);
                    Mat mask2 = new Mat();
                    Core.inRange(hsvImage, lowerBound2, upperBound2, mask2);
                    Core.add(mask, mask2, mask);
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

            // Buscar contornos en la máscara
            List<MatOfPoint> contours = new java.util.ArrayList<>();
            Mat hierarchy = new Mat();
            Imgproc.findContours(mask, contours, hierarchy, Imgproc.RETR_EXTERNAL, Imgproc.CHAIN_APPROX_SIMPLE);

            // Dibujar los contornos en la imagen original
            Imgproc.drawContours(input, contours, -1, new Scalar(255, 0, 0), 2);  // Contornos en color rojo

            // Liberar las matrices después de usarlas
            hsvImage.release();
            mask.release();
            hierarchy.release();

            // Devolver la imagen con los contornos dibujados
            return input;
        }
    }
}