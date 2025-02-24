/*package org.firstinspires.ftc.teamcode.autonomous.ferreria;

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
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.MecanumDrive;
import org.firstinspires.ftc.teamcode.subsystems.config.Intake;
import org.firstinspires.ftc.teamcode.subsystems.config.Slider;

@Disabled
@Autonomous
public final class TEST extends LinearOpMode {
    private Intake intakeSystem;
    private Slider sliderSystem;

    @Override
    public void runOpMode() throws InterruptedException {
        Pose2d startPose = new Pose2d(-23, -61, Math.PI / 2);

        MecanumDrive drive = new MecanumDrive(hardwareMap, startPose);
        sliderSystem = new Slider(hardwareMap, telemetry);
        intakeSystem = new Intake();
        intakeSystem.init(hardwareMap);

        Action tab2 = drive.actionBuilder(startPose)
                .strafeToConstantHeading(new Vector2d(-23,-40),new TranslationalVelConstraint(20),new ProfileAccelConstraint(-10,10)).build();

        SequentialAction actionSequence = new SequentialAction(
                sliderSystem.NoFloor(),
                new SleepAction(1),
                intakeSystem.pickSample(),
                new SleepAction(1),
                new ParallelAction(
                        intakeSystem.IN_intake(),
                        new SleepAction(2),
                        tab2
                ) ,
                new SleepAction(1.5),
                intakeSystem.STOP_intake(),
                new SleepAction(1.5),
                intakeSystem.dropSample(),
                new SleepAction(1.5)
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
}*/