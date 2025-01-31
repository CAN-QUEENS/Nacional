package org.firstinspires.ftc.teamcode.autonomous.ferreria;

import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.ParallelAction;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.ProfileAccelConstraint;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.SleepAction;
import com.acmerobotics.roadrunner.TranslationalVelConstraint;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.MecanumDrive;
import org.firstinspires.ftc.teamcode.subsystems.Intake;
import org.firstinspires.ftc.teamcode.subsystems.Slider;

@Autonomous
public final class ESPECIMEN2 extends LinearOpMode {
    private Intake intakeSystem;
    private Slider sliderSystem;

    @Override
    public void runOpMode() throws InterruptedException {
        Pose2d startPose = new Pose2d(23, -62, Math.PI / 2);

        MecanumDrive drive = new MecanumDrive(hardwareMap, startPose);
        sliderSystem = new Slider(hardwareMap, telemetry);
        intakeSystem = new Intake();
        intakeSystem.init(hardwareMap);

        Action actionSequence = drive.actionBuilder(startPose)
                .afterTime(0, new SequentialAction(
                        intakeSystem.STOP_intake(),
                        intakeSystem.pickSample(),
                        new SleepAction(1),
                        sliderSystem.Medium()
                ))
                .waitSeconds(2)
                .strafeTo(new Vector2d(0, -50))
                .strafeToConstantHeading(new Vector2d(0,-40),new TranslationalVelConstraint(20),new ProfileAccelConstraint(-10,10))
                .afterTime(0, sliderSystem.high_CHAMBER())
                .afterTime(0, new ParallelAction(
                        intakeSystem.IN_intake()
                ))
                .waitSeconds(1)
                .strafeToConstantHeading(new Vector2d(0,-50),new TranslationalVelConstraint(20),new ProfileAccelConstraint(-10,10))
                .afterTime(0, new SequentialAction(
                        intakeSystem.STOP_intake(),
                        sliderSystem.PickSAMPLE()
                ))
                .strafeTo(new Vector2d(46, -60))
                .afterTime(1.5, new SequentialAction(
                        sliderSystem.PickSAMPLE(),
                        intakeSystem.STOP_intake()
                ))
                .build();

        waitForStart();

        Actions.runBlocking(new ParallelAction(
                actionSequence,
                sliderSystem.SliderUpdate()
        ));

        telemetry.addLine("Autonomous Complete!");
        telemetry.update();
    }
}