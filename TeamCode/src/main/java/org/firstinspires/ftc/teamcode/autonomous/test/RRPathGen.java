/*package org.firstinspires.ftc.teamcode.autonomous.test;

import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.ParallelAction;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.MecanumDrive;
import org.firstinspires.ftc.teamcode.subsystems.config.Intake;
import org.firstinspires.ftc.teamcode.subsystems.config.Slider;

@Autonomous
public final class RRPathGen extends LinearOpMode {
    private Intake intakeSystem;
    private Slider sliderSystem;

    @Override
    public void runOpMode() throws InterruptedException {
        Pose2d startPose = new Pose2d(23, -62, Math.PI / 2);

        MecanumDrive drive = new MecanumDrive(hardwareMap, startPose);
        sliderSystem = new Slider(hardwareMap, telemetry);
        intakeSystem = new Intake();
        intakeSystem.init(hardwareMap);

        Action trajectory0 = drive.actionBuilder(new Pose2d(23.90, -63.13, Math.toRadians(90.00)))
                .splineTo(new Vector2d(35.73, -18.29), Math.toRadians(90.00))
                .build();


        waitForStart();

        Actions.runBlocking(new ParallelAction(
                trajectory0,
                sliderSystem.SliderUpdate()
        ));

        telemetry.addLine("Autonomous Complete!");
        telemetry.update();
    }
}*/