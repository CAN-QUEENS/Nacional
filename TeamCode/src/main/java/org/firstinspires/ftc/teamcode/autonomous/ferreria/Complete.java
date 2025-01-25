/*package org.firstinspires.ftc.teamcode.autonomous.ferreria;

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
        Pose2d startPose = new Pose2d(-23, -61, Math.PI / 2);

        // Inicialización de sistemas
        MecanumDrive drive = new MecanumDrive(hardwareMap, startPose);
        sliderSystem = new Slider(hardwareMap, telemetry);
        intakeSystem = new Intake();
        intakeSystem.init(hardwareMap);

        // Trayectoria opcional
        TrajectoryActionBuilder tab1 = drive.actionBuilder(startPose)
                .strafeTo(new Vector2d(-23, -51));  // Movimiento hacia adelante como ejemplo

        TrajectoryActionBuilder tab2 = drive.actionBuilder(startPose)
                .strafeTo(new Vector2d(-23, -55));  // Movimiento hacia adelante como ejemplo

        // Crear la secuencia de acciones para realizar mientras avanza
        SequentialAction actionSequence = new SequentialAction(
                intakeSystem.pickSample(),
                new SleepAction(0.5)
        );

        SequentialAction actionSequence3 = new SequentialAction(
                new SleepAction(1.5),
                intakeSystem.STOP_intake(),
                new SleepAction(1),
                intakeSystem.dropSample(),
                new SleepAction(1.5),
                sliderSystem.high_CHAMBER(),
                new SleepAction(2),
                intakeSystem.OUT_intake(),
                new SleepAction(1.5),
                sliderSystem.PickSAMPLE()
        );


        // Crear una nueva acción paralela que incluya la secuencia de comandos y la trayectoria
        Action actionSequence2 = new ParallelAction(
                intakeSystem.IN_intake(),
                new SleepAction(1.5),  // El tiempo en el que el robot seguirá avanzando
                tab2.build()  // La trayectoria para mover el robot hacia adelante
        );


        waitForStart();

        if (isStopRequested()) return;

        TelemetryPacket packet = new TelemetryPacket();

        // Ejecutar la secuencia de acciones paralelas junto con la trayectoria
        Actions.runBlocking(new ParallelAction(
                tab1.build(),  // Trayectoria original
                actionSequence,  // Secuencia de acciones
                actionSequence2,  // Secuencia de acciones
                actionSequence3,  // Nueva acción paralela
                sliderSystem.SliderUpdate()
        ));

        telemetry.addLine("Autonomous Complete!");
        telemetry.update();
    }
}*/