package org.firstinspires.ftc.teamcode.autonomous.ferreria;

import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.ParallelAction;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.ProfileAccelConstraint;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.TranslationalVelConstraint;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.MecanumDrive;
import org.firstinspires.ftc.teamcode.subsystems.Intake;
import org.firstinspires.ftc.teamcode.subsystems.Slider;

@Autonomous
public final class ESPECIMEN_SAMPLES extends LinearOpMode {
    private Intake intakeSystem;
    private Slider sliderSystem;

    @Override
    public void runOpMode() throws InterruptedException {
        Pose2d startPose = new Pose2d(23, -62, Math.PI / 2);

        MecanumDrive drive = new MecanumDrive(hardwareMap, startPose);
        sliderSystem = new Slider(hardwareMap, telemetry);
        intakeSystem = new Intake();
        intakeSystem.init(hardwareMap);

        Action especimen = drive.actionBuilder(startPose)
                .afterTime(0, sliderSystem.Medium())
                .strafeTo(new Vector2d(0, -46))
                .strafeToConstantHeading(new Vector2d(0,-30),new TranslationalVelConstraint(20),new ProfileAccelConstraint(-10,10))
                .afterTime(0, sliderSystem.high_CHAMBER())
                .afterTime(0, new ParallelAction(
                        intakeSystem.IN_intake()
                ))
                .waitSeconds(1)
                .strafeToConstantHeading(new Vector2d(0,-46),new TranslationalVelConstraint(20),new ProfileAccelConstraint(-10,10))
                .afterTime(0, intakeSystem.STOP_intake())
                .strafeTo(new Vector2d(23, -50))
                .afterTime(1.5, new SequentialAction(
                        sliderSystem.NoFloor(),
                        intakeSystem.STOP_intake(),
                        intakeSystem.dropSample()
                ))
                .waitSeconds(2)
                .build();
        Action samples = drive.actionBuilder(startPose)
                .setTangent(Math.PI / 2)
                .splineToConstantHeading(new Vector2d(36, -28), Math.PI / 2)
                .strafeTo(new Vector2d(37, -10))
                .strafeTo(new Vector2d(47, -10))
                .strafeTo(new Vector2d(47, -53))
                .strafeTo(new Vector2d(47, -10))
                .strafeTo(new Vector2d(55, -10))
                .strafeTo(new Vector2d(55, -53))
                .strafeTo(new Vector2d(55, -10))
                .strafeTo(new Vector2d(61, -10))
                .strafeTo(new Vector2d(61, -53))
                .build();

        waitForStart();

        Actions.runBlocking(new ParallelAction(
                especimen,
                samples,
                sliderSystem.SliderUpdate()
        ));

        telemetry.addLine("Autonomous Complete!");
        telemetry.update();
    }
}