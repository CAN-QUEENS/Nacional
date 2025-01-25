package org.firstinspires.ftc.teamcode.autonomous.ferreria;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.ParallelAction;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.SleepAction;
import com.acmerobotics.roadrunner.TrajectoryActionBuilder;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.MecanumDrive;
import org.firstinspires.ftc.teamcode.subsystems.Intake;
import org.firstinspires.ftc.teamcode.subsystems.Slider;

@Autonomous(name = "CustomAuto2", group = "Competition")
public final class Auto2 extends LinearOpMode {
    private Intake intakeSystem;
    private Slider sliderSystem;

    @Override
    public void runOpMode() throws InterruptedException {
        Pose2d startPose = new Pose2d(-23, -61, Math.PI / 2);

        MecanumDrive drive = new MecanumDrive(hardwareMap, startPose);
        sliderSystem = new Slider(hardwareMap, telemetry);
        intakeSystem = new Intake();
        intakeSystem.init(hardwareMap);

        TrajectoryActionBuilder tab1 = drive.actionBuilder(startPose)
                .strafeTo(new Vector2d(-23, -50));

        Action tab2 = drive.actionBuilder(startPose)
                .strafeTo(new Vector2d(-23, -40)).build();

        SequentialAction actionSequence = new SequentialAction(
                intakeSystem.pickSample(),
                new SleepAction(0.5),
                new ParallelAction(
                        intakeSystem.IN_intake(),
                        new SleepAction(1.5),
                        tab2
                ) ,
                new SleepAction(1.5),
                intakeSystem.STOP_intake(),
                new SleepAction(1.5),
                intakeSystem.dropSample(),
                new SleepAction(1.5),
                sliderSystem.high_CHAMBER(),
                new SleepAction(2)
        );

        waitForStart();

        if (isStopRequested()) return;

        Actions.runBlocking(new ParallelAction(
                tab1.build(),
                actionSequence,
                sliderSystem.SliderUpdate()
        ));

        telemetry.addLine("Autonomous Complete!");
        telemetry.update();
    }
}