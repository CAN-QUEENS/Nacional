package org.firstinspires.ftc.teamcode.autonomous.ferreria;

import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.ParallelAction;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.ProfileAccelConstraint;
import com.acmerobotics.roadrunner.TranslationalVelConstraint;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import org.firstinspires.ftc.teamcode.MecanumDrive;
@Autonomous(name = "HUMAN-PLAYER")
public final class HUMAN_SAMPLES extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {
        Pose2d startPose = new Pose2d(23, -62, Math.PI / 2);
        MecanumDrive drive = new MecanumDrive(hardwareMap, startPose);

        Action human = drive.actionBuilder(startPose)
                .setTangent(Math.PI / 2)
                .splineToConstantHeading(new Vector2d(36, -28), Math.PI / 2)
                .strafeTo(new Vector2d(37, -10))
                .strafeTo(new Vector2d(47, -10))
                .strafeToConstantHeading(
                        new Vector2d(47, -53),
                        new TranslationalVelConstraint(100),
                        new ProfileAccelConstraint(-100, 100)
                )
                .strafeToConstantHeading(
                        new Vector2d(47, -10),
                        new TranslationalVelConstraint(150),
                        new ProfileAccelConstraint(-150, 150)
                )
                .strafeTo(new Vector2d(55, -10))
                .strafeToConstantHeading(
                        new Vector2d(55, -53),
                        new TranslationalVelConstraint(100),
                        new ProfileAccelConstraint(-100, 100)
                )
                .strafeToConstantHeading(
                        new Vector2d(55, -10),
                        new TranslationalVelConstraint(150),
                        new ProfileAccelConstraint(-150, 150)
                )
                .strafeTo(new Vector2d(61, -10))
                .strafeTo(new Vector2d(61, -53))
                .build();


        waitForStart();

        if (isStopRequested()) return;

        Actions.runBlocking(new ParallelAction(
                human
        ));

        telemetry.addLine("Autonomous Complete!");
        telemetry.update();
    }
}