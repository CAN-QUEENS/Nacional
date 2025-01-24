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

@Autonomous
public final class Auto2 extends LinearOpMode {

    private Intake intakeSystem;

    @Override
    public void runOpMode() throws InterruptedException {
        // Inicializa la pose y subsistemas
        Pose2d startPose = new Pose2d(-23, -61, Math.PI / 2);
        MecanumDrive drive = new MecanumDrive(hardwareMap, startPose);
        intakeSystem = new Intake();
        intakeSystem.init(hardwareMap);

        // Define la trayectoria
        Action auto = drive.actionBuilder(startPose)
                .strafeTo(new Vector2d(-23, -50)) // Trajectory hasta la posición deseada
                .build();

        // Define las acciones y la secuencia
        waitForStart();

        Actions.runBlocking(new SequentialAction(
                intakeSystem.pickSample(), // Extiende (pick)
                new SleepAction(3),        // Espera 3 segundos
                intakeSystem.dropSample()  // Retracta (drop)
        ));

        telemetry.addLine("FINISH");
        telemetry.update();
    }
}