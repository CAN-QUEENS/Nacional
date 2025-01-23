package org.firstinspires.ftc.teamcode.autonomous.ferreria;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.ParallelAction;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.SleepAction;
import com.acmerobotics.roadrunner.TrajectoryActionBuilder;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.MecanumDrive;
import org.firstinspires.ftc.teamcode.subsystems.Intake;

@Autonomous(name = "COMPLETEE", group = "FERRERIA")
public final class Complete extends LinearOpMode {

    private Intake intakeSystem;

    @Override
    public void runOpMode() throws InterruptedException {
        Pose2d startPose = new Pose2d(-23, -61, Math.PI / 2);
        MecanumDrive drive = new MecanumDrive(hardwareMap, startPose);
        intakeSystem = new Intake();
        intakeSystem.init(hardwareMap);

        // Definimos el trayecto
        TrajectoryActionBuilder tab1 = drive.actionBuilder(startPose)
                .waitSeconds(0);

        waitForStart();

        if (isStopRequested()) return;


        Actions.runBlocking(
                new SequentialAction(
                        tab1.build(),
                        intakeSystem.pickSample(),
                        new SleepAction(1),
                        intakeSystem.dropSample(),
                        new SleepAction(1),
                        tab1.build()
                )
        );

        telemetry.addData("Servo 1 Posición", intakeSystem.intakeDER.getPosition());
        telemetry.addData("Servo 2 Posición", intakeSystem.intakeIZQ.getPosition());
        telemetry.update();
        telemetry.update();
    }
}