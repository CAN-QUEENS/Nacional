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

@Autonomous(name = "NET", group = "FERRERIA: ")
public final class Net extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        Pose2d startPose = new Pose2d(-23, -61, Math.PI/2);
        MecanumDrive drive = new MecanumDrive(hardwareMap, startPose);

        TrajectoryActionBuilder tab1 = drive.actionBuilder(startPose)
                //TODO ****** NET *****
                .setTangent(0)
                .strafeToLinearHeading(new Vector2d(-52, -50), Math.toRadians(226))
                .waitSeconds(1)
                .setTangent(0)
                // TODO FIRST SAMPLE
                .strafeToLinearHeading(new Vector2d(-47, -39), Math.toRadians(-270))
                .waitSeconds(.6)
                .setTangent(0)
                // TODO ***** NET *****
                .strafeToLinearHeading(new Vector2d(-52, -50), Math.toRadians(226))
                .waitSeconds(1)
                .setTangent(0)
                //TODO SECOND SAMPLE
                .strafeToLinearHeading(new Vector2d(-57, -39), Math.toRadians(-270))
                .waitSeconds(.6)
                .setTangent(0)
                // TODO ***** NET *****
                .strafeToLinearHeading(new Vector2d(-52, -50), Math.toRadians(226))
                .waitSeconds(1)
                .setTangent(Math.PI * 2 )
                //TODO THIRD SAMPLE
                .strafeToLinearHeading(new Vector2d(-50, -24), Math.toRadians(180))
                .waitSeconds(.6)
                .setTangent(0)
                // TODO ***** NET *****
                .strafeToLinearHeading(new Vector2d(-52, -50), Math.toRadians(226))
                .waitSeconds(1)
                .setTangent(Math.PI /3)
                .splineToLinearHeading(new Pose2d(-39, -10, Math.PI * 2), Math.PI/2)
                .strafeTo(new Vector2d(-27, -10));


        waitForStart();

        if (isStopRequested()) return;

        Action trajectoryActionChosen;
        trajectoryActionChosen = tab1.build();

        Actions.runBlocking(
                new SequentialAction(
                        trajectoryActionChosen
                )
        );
    }
}

/*
   TODO waitSeconds(double: seconds)
    turn(Math.toRadians(double: angle))
    turnTo(Math.toRadians(double: heading))
    setTangent(double: r)
    setReversed(boolean: reversed)
    .strafeTo(new Vector2d(double: x, double: y)) & .strafeToConstantHeading(new Vector2d(x: double, y: double))
    strafeToLinearHeading(new Vector2d(x, y), Math.toRadians(heading))
    strafeToSplineHeading(new Vector2d(x, y), Math.toRadians(heading))
    lineToX(x: double) & .lineToXConstantHeading(x: double)
    lineToY(y: double) & .lineToYConstantHeading(y: double)
    splineTo(new Vector2d(x, y), tangent)
 */