package org.firstinspires.ftc.teamcode.autonomous.ferreria;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.SequentialAction;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.MecanumDrive;
import org.firstinspires.ftc.teamcode.subsystems.Slider;

@Autonomous(name = "PICK", group = "FERRERIA: ")
public class Ferreria extends LinearOpMode {
    private Slider slider;

    @Override
    public void runOpMode() {
        Pose2d startPose = new Pose2d(23, -62, Math.toRadians(90));
        MecanumDrive drive = new MecanumDrive(hardwareMap, startPose);
        slider = new Slider(hardwareMap);

        waitForStart();

        while (opModeIsActive()) {

            new SequentialAction(
                    slider.sliderHighBascket(),
                    slider.sliderSample()
            ).run(new TelemetryPacket());
        }
    }
}