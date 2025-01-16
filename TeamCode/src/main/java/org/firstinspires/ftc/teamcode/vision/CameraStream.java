package org.firstinspires.ftc.teamcode.vision;

import com.acmerobotics.dashboard.FtcDashboard;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;

@Disabled
@TeleOp(name = "CAMERA STREAM Dashboard")
public class CameraStream extends LinearOpMode {
    OpenCvCamera camera;
    FtcDashboard dashboard;

    @Override
    public void runOpMode() {
        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        camera = OpenCvCameraFactory.getInstance().createWebcam(hardwareMap.get(WebcamName.class, "Webcam 1"), cameraMonitorViewId);

        dashboard = FtcDashboard.getInstance();

        // No pipeline for processing, just stream
        camera.setPipeline(new NoOpPipeline());

        camera.openCameraDeviceAsync(new OpenCvCamera.AsyncCameraOpenListener() {
            @Override
            public void onOpened() {
                camera.startStreaming(640, 480, OpenCvCameraRotation.UPRIGHT);
                dashboard.startCameraStream(camera, 0); // Start streaming to the dashboard
            }

            @Override
            public void onError(int errorCode) {
                telemetry.addData("Error", "Failed to open camera: " + errorCode);
                telemetry.update();
            }
        });

        waitForStart();

        // Keep the op mode running to maintain the stream
        while (opModeIsActive()) {
            telemetry.addData("Streaming", "Active");
            telemetry.update();
            // No sleep here to ensure continuous streaming
        }

        dashboard.stopCameraStream(); // Stop streaming when the op mode is stopped
    }

    // Simple pipeline that does nothing
    static class NoOpPipeline extends org.openftc.easyopencv.OpenCvPipeline {
        @Override
        public org.opencv.core.Mat processFrame(org.opencv.core.Mat input) {
            // Just return the input frame as is without any processing
            return input;
        }
    }
}