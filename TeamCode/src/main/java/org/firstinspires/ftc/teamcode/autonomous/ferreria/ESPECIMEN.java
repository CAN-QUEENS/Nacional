/*package org.firstinspires.ftc.teamcode.autonomous.ferreria;

import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.ParallelAction;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.ProfileAccelConstraint;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.TranslationalVelConstraint;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.MecanumDrive;
import org.firstinspires.ftc.teamcode.subsystems.config.Intake;
import org.firstinspires.ftc.teamcode.subsystems.teleop.Barras;

@Disabled
@Autonomous
public final class ESPECIMEN extends LinearOpMode {
    private Intake intake;
    private Barras barras;

    @Override
    public void runOpMode() throws InterruptedException {
        Pose2d startPose = new Pose2d(23, -62, Math.PI / 2);

        MecanumDrive drive = new MecanumDrive(hardwareMap, startPose);
        barras = new barras(hardwareMap);
        intakeSystem = new Intake();
        intakeSystem.init(hardwareMap);

        Action actionSequence = drive.actionBuilder(startPose)
                .afterTime(0, sliderSystem.Medium())
                .strafeTo(new Vector2d(0, -46))
                .strafeToConstantHeading(new Vector2d(0,-30),new TranslationalVelConstraint(20),new ProfileAccelConstraint(-10,10))
                .afterTime(0, new SequentialAction(
                        sliderSystem.high_CHAMBER(),
                        intakeSystem.IN_intake()
                ))
                .waitSeconds(1)
                .strafeToConstantHeading(new Vector2d(0,-46),new TranslationalVelConstraint(20),new ProfileAccelConstraint(-10,10))
                .afterTime(1, new SequentialAction(
                        intakeSystem.STOP_intake(),
                        sliderSystem.NoFloor()
                ))
                .strafeTo(new Vector2d(23, -50))
                .afterTime(1.5, new SequentialAction(
                        sliderSystem.PickSAMPLE()
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
}*/