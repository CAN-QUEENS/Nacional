package org.firstinspires.ftc.teamcode.vision;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;

@Disabled
@TeleOp(name = "CAMERA STREAM")
public class  CameraStream extends LinearOpMode {
    OpenCvCamera camera;

    @Override
    public void runOpMode() {
        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        camera = OpenCvCameraFactory.getInstance().createWebcam(hardwareMap.get(WebcamName.class, "Webcam 1"), cameraMonitorViewId);

        // No pipeline for processing, just stream
        camera.setPipeline(new NoOpPipeline());

        camera.openCameraDeviceAsync(new OpenCvCamera.AsyncCameraOpenListener() {
            @Override
            public void onOpened() {
                // Start streaming with a more common resolution (640x480) and MJPEG format
                camera.startStreaming(640, 480, OpenCvCameraRotation.UPRIGHT);
            }

            @Override
            public void onError(int errorCode) {
                telemetry.addData("Error", "Failed to open camera: " + errorCode);
                telemetry.update();
            }
        });

        telemetry.setMsTransmissionInterval(50);

        // Wait until the start button is pressed
        waitForStart();

        // Once started, keep the op mode active and continue streaming
        while (opModeIsActive()) {
            telemetry.update();
            sleep(20);
        }
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