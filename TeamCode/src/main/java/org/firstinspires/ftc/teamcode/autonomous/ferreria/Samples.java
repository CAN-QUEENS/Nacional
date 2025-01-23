package org.firstinspires.ftc.teamcode.autonomous.ferreria;

import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.TrajectoryActionBuilder;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.MecanumDrive;

@Autonomous(name = "SAMPLES", group = "FERRERIA: ")
public final class Samples extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {
        Pose2d startPose = new Pose2d(23, -62, Math.PI/2);
        MecanumDrive drive = new MecanumDrive(hardwareMap, startPose);

        TrajectoryActionBuilder tab1 = drive.actionBuilder(startPose)
                // TODO: FIRST ESPECIMEN
                .strafeTo(new Vector2d(0,-37))
                .waitSeconds(.8)
                .strafeTo(new Vector2d(36, -37))
                .strafeTo(new Vector2d(36, -12))
                .strafeTo(new Vector2d(47, -12))
                .strafeTo(new Vector2d(47, -48))
                // TODO: ****** FIRST SAMPLE ******
                .strafeTo(new Vector2d(47, -12))
                .strafeTo(new Vector2d(55, -12))
                // TODO: ***** SECOND SAMPLE *****
                .strafeTo(new Vector2d(55, -48))
                .strafeTo(new Vector2d(55, -12))
                .strafeTo(new Vector2d(61, -12))
                // TODO: THIRD SAMPLE
                .strafeTo(new Vector2d(61, -48))
                .setTangent(0)
                .strafeToLinearHeading(new Vector2d(39, -51), Math.toRadians(270))
                .waitSeconds(.8)
                // TODO: SECOND ESPECIMEN
                .setTangent(0)
                .strafeToLinearHeading(new Vector2d(0, -37), Math.toRadians(-270))
                .waitSeconds(.8)
                // TODO: OBSERVATION ZONE
                .setTangent(0)
                .strafeToLinearHeading(new Vector2d(39, -51), Math.toRadians(270))
                .waitSeconds(.8)
                // TODO: THIRD ESPECIMEN
                .setTangent(0)
                .strafeToLinearHeading(new Vector2d(0, -37), Math.toRadians(-270))
                .waitSeconds(.8)
                // TODO: OBSERVATION ZONE
                .setTangent(0)
                .strafeToLinearHeading(new Vector2d(39, -51), Math.toRadians(270))
                .waitSeconds(.8)
                // TODO: FOURTH ESPECIMEN
                .setTangent(0)
                .strafeToLinearHeading(new Vector2d(0, -37), Math.toRadians(-270))
                .waitSeconds(.8)
                // TODO: OBSERVATION ZONE
                .setTangent(0)
                .strafeToLinearHeading(new Vector2d(39, -51), Math.toRadians(270))
                .waitSeconds(.8)
                // TODO: FIFTH SPECIMEN
                .setTangent(0)
                .strafeToLinearHeading(new Vector2d(0, -37), Math.toRadians(-270));

        waitForStart();

        if (isStopRequested()) return;

        Action trajectoryActionChosen = tab1.build();

        Actions.runBlocking(
                new SequentialAction(
                        trajectoryActionChosen
                )
        );
    }
}