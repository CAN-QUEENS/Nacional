package org.firstinspires.ftc.teamcode.opmodes.test;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import org.firstinspires.ftc.teamcode.vision.robot.RobotCameraDistance;

@TeleOp(name = "VisionCameraDistanceTest", group = "Test")
public class distance extends LinearOpMode {
    private RobotCameraDistance robotCameraDistance;
    private String selectedColor = "red";

    @Override
    public void runOpMode() {
        robotCameraDistance = new RobotCameraDistance();
        robotCameraDistance.initialize(hardwareMap, selectedColor);

        telemetry.addData("Status", "Initialized");
        telemetry.addData("Selected Color", selectedColor);
        telemetry.update();

        waitForStart();

        while (opModeIsActive()) {
            double[] polarCoordinates = robotCameraDistance.getPolarCoordinates();

            if (gamepad1.triangle) {
                updateColor("red");
            } else if (gamepad1.circle) {
                updateColor("yellow");
            } else if (gamepad1.cross) {
                updateColor("blue");
            }

            if (gamepad1.circle) {
                robotCameraDistance.updateCurrentRectExcludingCurrent();
            }

            telemetry.addData("Selected Color", selectedColor);
            telemetry.addData("Distance from Center", polarCoordinates[0]);
            telemetry.addData("Angle from Center", polarCoordinates[1]);
            telemetry.update();
        }
    }

    private void updateColor(String newColor) {
        if (!newColor.equals(selectedColor)) {
            selectedColor = newColor;
            robotCameraDistance.initialize(hardwareMap, selectedColor);
        }
    }
}