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

@Autonomous(name = "Complete", group = "Competition")
public final class Complete extends LinearOpMode {
    private Intake intakeSystem;
    private Slider sliderSystem;

    @Override
    public void runOpMode() throws InterruptedException {
        Pose2d startPose = new Pose2d(23, -62, Math.PI / 2);

        MecanumDrive drive = new MecanumDrive(hardwareMap, startPose);
        sliderSystem = new Slider(hardwareMap, telemetry);
        intakeSystem = new Intake();
        intakeSystem.init(hardwareMap);

        TrajectoryActionBuilder moveToFirstTarget = drive.actionBuilder(startPose)
                .strafeTo(new Vector2d(0, -37));

        TrajectoryActionBuilder moveToSecondTarget = drive.actionBuilder(new Pose2d(0, -37, Math.PI / 2))
                .strafeTo(new Vector2d(0, -47));

        TrajectoryActionBuilder moveBack = drive.actionBuilder(new Pose2d(0, -47, Math.PI / 2))
                .strafeTo(new Vector2d(23, -62));

        SequentialAction actionSequence = new SequentialAction(
                new SleepAction(0.5),
                moveToFirstTarget.build(),
                new SleepAction(0.5),
                sliderSystem.high_CHAMBER(),
                new SleepAction(0.5),
                intakeSystem.IN_intake(),
                new ParallelAction(
                        moveToSecondTarget.build(),
                        new SleepAction(1.5)
                ),
                intakeSystem.STOP_intake()
        );

        waitForStart();

        if (isStopRequested()) return;
        Actions.runBlocking(new ParallelAction(
                actionSequence,
                sliderSystem.SliderUpdate()
        ));

        telemetry.addLine("Autonomous Complete!");
        telemetry.update();
    }
}