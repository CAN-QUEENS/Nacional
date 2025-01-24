package org.firstinspires.ftc.teamcode.autonomous.ferreria;

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
public final class Complete extends LinearOpMode {

    private Intake intakeSystem;

    @Override
    public void runOpMode() throws InterruptedException {
        Pose2d startPose = new Pose2d(-23, -61, Math.PI / 2);
        MecanumDrive drive = new MecanumDrive(hardwareMap, startPose);
        intakeSystem = new Intake();
        intakeSystem.init(hardwareMap);

        TrajectoryActionBuilder tab1 = drive.actionBuilder(startPose)
                .strafeTo(new Vector2d(-23, -50))
                .afterTime(0, new ParallelAction(
                    intakeSystem.pickSample()
                ))
                .afterTime(2, new SequentialAction(
                        intakeSystem.dropSample()
                ));

        waitForStart();

        if (isStopRequested()) return;

        Actions.runBlocking(
                new SequentialAction(
                       tab1.build()/*,
                       new ParallelAction(
                               intakeSystem.pickSample()
                       ),
                        new SleepAction(1),
                        intakeSystem.dropSample(),
                        new SleepAction(2)*/
                )
        );
        telemetry.update();
    }
}