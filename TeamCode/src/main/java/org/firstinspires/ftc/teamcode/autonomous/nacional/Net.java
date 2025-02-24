package org.firstinspires.ftc.teamcode.autonomous.nacional;

import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.ParallelAction;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import org.firstinspires.ftc.teamcode.MecanumDrive;
import org.firstinspires.ftc.teamcode.subsystems.config.Barras;
import org.firstinspires.ftc.teamcode.subsystems.config.Intake;
import org.firstinspires.ftc.teamcode.subsystems.config.Slider;

@Autonomous(name = "NET")
public final class Net extends LinearOpMode {
    private Slider sliderSystem;
    private Barras barras;

    @Override
    public void runOpMode() throws InterruptedException {
        Pose2d startPose = new Pose2d(-23, -62, Math.PI / 2);
        MecanumDrive drive = new MecanumDrive(hardwareMap, startPose);
        sliderSystem = new Slider(hardwareMap);
        barras = new Barras(hardwareMap, telemetry);

        Action net = drive.actionBuilder(startPose)
                .setTangent(Math.PI / 2)
                .splineToConstantHeading(new Vector2d(-36, -28), Math.PI / 2)
                .strafeTo(new Vector2d(-37, -10))
                .strafeTo(new Vector2d(-47, -10))
                .strafeTo(new Vector2d(-47, -57))
                .strafeTo(new Vector2d(-47, -10))
                .strafeTo(new Vector2d(-55, -10))
                .strafeTo(new Vector2d(-55, -53))
                .strafeTo(new Vector2d(-55, -10))
                .strafeTo(new Vector2d(-63, -10))
                .strafeTo(new Vector2d(-63, -53))
                .setTangent(Math.PI /3)
                .splineToLinearHeading(new Pose2d(-39, -10, Math.PI * 2), Math.PI/2)
                .strafeTo(new Vector2d(-25, -10))
                .waitSeconds(3)
                .build();

        waitForStart();

        Actions.runBlocking(new ParallelAction(
                barras.HUMAN(),
                sliderSystem.IN(),
                net
        ));

        telemetry.addLine("Autonomous Complete!");
        telemetry.update();
    }
}