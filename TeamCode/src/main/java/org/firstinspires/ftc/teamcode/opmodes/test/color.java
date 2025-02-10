package org.firstinspires.ftc.teamcode.opmodes.test;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.vision.robot.RobotVisionColor;

@TeleOp(name = "Vision color")
public class color extends LinearOpMode {
    private RobotVisionColor robotVisionColor;

    @Override
    public void runOpMode() {
        robotVisionColor = new RobotVisionColor();

        robotVisionColor.initialize(hardwareMap);

        telemetry.addData("Status", "Initialized");
        telemetry.update();

        waitForStart();

        while (opModeIsActive()) {
            telemetry.addData("Center Color", robotVisionColor.getDetectedColor());
            telemetry.update();
        }
    }
}

