package org.firstinspires.ftc.teamcode.opmodes.disable;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.acmerobotics.dashboard.FtcDashboard;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.teamcode.vision.ColorDetector;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;

@Disabled
@TeleOp(name = "Color Selection TeleOp with Contours")
public class Detection extends LinearOpMode {
    OpenCvCamera camera;
    private String selectedColor = "RED";

    @Override
    public void runOpMode() {
        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier(
                "cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        camera = OpenCvCameraFactory.getInstance().createWebcam(
                hardwareMap.get(WebcamName.class, "Webcam 1"), cameraMonitorViewId);

        ColorDetector pipeline = new ColorDetector();
        camera.setPipeline(pipeline);

        // Asíncrona para abrir la cámara
        camera.openCameraDeviceAsync(new OpenCvCamera.AsyncCameraOpenListener() {
            @Override
            public void onOpened() {
                // Configura la cámara para comenzar a transmitir a 640x480 y en orientación vertical
                camera.startStreaming(640, 480, OpenCvCameraRotation.UPRIGHT);
            }

            @Override
            public void onError(int errorCode) {
                telemetry.addData("Error", "Camera could not be opened: " + errorCode);
                telemetry.update();
            }
        });

        FtcDashboard dashboard = FtcDashboard.getInstance();
        FtcDashboard.getInstance().startCameraStream(camera, 60);

        waitForStart();

        while (opModeIsActive()) {
            // Selección del color
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
}