package org.firstinspires.ftc.teamcode.autonomous.ferreria;

import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.AngularVelConstraint;
import com.acmerobotics.roadrunner.MinVelConstraint;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.ProfileAccelConstraint;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.TrajectoryActionBuilder;
import com.acmerobotics.roadrunner.TranslationalVelConstraint;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.MecanumDrive;

import java.util.Arrays;

@Autonomous(name = "SAMPLES", group = "FERRERIA: ")
public final class Sampless extends LinearOpMode {

    private static final double VELOCIDAD_TRAJECTORIA = 80.0;

    @Override
    public void runOpMode() throws InterruptedException {
        Pose2d startPose = new Pose2d(23, -62, Math.PI/2);
        MecanumDrive drive = new MecanumDrive(hardwareMap, startPose);

        MinVelConstraint translationalVelConstraint = new MinVelConstraint(Arrays.asList(
                new TranslationalVelConstraint(VELOCIDAD_TRAJECTORIA),
                new AngularVelConstraint(Math.PI / 2)
        ));
        ProfileAccelConstraint profileAccelConstraint = new ProfileAccelConstraint(VELOCIDAD_TRAJECTORIA, VELOCIDAD_TRAJECTORIA);

        TrajectoryActionBuilder tab1 = drive.actionBuilder(startPose)
                .strafeTo(new Vector2d(0, -37), translationalVelConstraint, profileAccelConstraint)
                .waitSeconds(1)
                .setTangent(0)
                .strafeToLinearHeading(new Vector2d(36, -37), Math.toRadians(-270), translationalVelConstraint, profileAccelConstraint)
                .strafeTo(new Vector2d(36, -12), translationalVelConstraint, profileAccelConstraint)
                .strafeTo(new Vector2d(47, -12), translationalVelConstraint, profileAccelConstraint)
                .strafeTo(new Vector2d(47, -48), translationalVelConstraint, profileAccelConstraint)
                //TODO: ****** FIRST SAMPLE ******
                .strafeTo(new Vector2d(47, -12), translationalVelConstraint, profileAccelConstraint)
                .strafeTo(new Vector2d(55, -12), translationalVelConstraint, profileAccelConstraint)
                //TODO: ***** SECOND SAMPLE *****
                .strafeTo(new Vector2d(55, -48), translationalVelConstraint, profileAccelConstraint)
                .strafeTo(new Vector2d(55, -12), translationalVelConstraint, profileAccelConstraint)
                .strafeTo(new Vector2d(61, -12), translationalVelConstraint, profileAccelConstraint)
                //TODO: THIRD SAMPLE
                .strafeTo(new Vector2d(61, -59), translationalVelConstraint, profileAccelConstraint)
                .waitSeconds(.6)
                //TODO: SECOND ESPECIMEN
                .strafeTo(new Vector2d(0, -37), translationalVelConstraint, profileAccelConstraint)
                .strafeTo(new Vector2d(0,-37), translationalVelConstraint, profileAccelConstraint)
                .waitSeconds(1)
                //TODO: OBSERVATION ZONE
                .strafeTo(new Vector2d(40, -59), translationalVelConstraint, profileAccelConstraint)
                .waitSeconds(1)
                //TODO: THIRD ESPECIMEN
                .strafeTo(new Vector2d(0, -37), translationalVelConstraint, profileAccelConstraint)
                .strafeTo(new Vector2d(0,-37), translationalVelConstraint, profileAccelConstraint)
                .waitSeconds(1)
                //TODO: OBSERVATION ZONE
                .strafeTo(new Vector2d(40, -59), translationalVelConstraint, profileAccelConstraint)
                .waitSeconds(1)
                //TODO: FOURTH ESPECIMEN
                .strafeTo(new Vector2d(0, -37), translationalVelConstraint, profileAccelConstraint)
                .strafeTo(new Vector2d(0,-37), translationalVelConstraint, profileAccelConstraint)
                .waitSeconds(1)
                .strafeTo(new Vector2d(36, -55), translationalVelConstraint, profileAccelConstraint)
                .waitSeconds(1)
                .strafeTo(new Vector2d(0, -37), translationalVelConstraint, profileAccelConstraint)
                .strafeTo(new Vector2d(0,-37), translationalVelConstraint, profileAccelConstraint);

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