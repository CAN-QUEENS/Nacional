package org.firstinspires.ftc.teamcode.autonomous;

import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.TrajectoryActionBuilder;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.MecanumDrive;

@Disabled
@Autonomous(name = "CORRECTION", group = "TEST: ")
public final class Correction extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        Pose2d startPose = new Pose2d(0, 0, Math.toRadians(90));
        MecanumDrive drive = new MecanumDrive(hardwareMap, startPose);

        TrajectoryActionBuilder tab1 = drive.actionBuilder(startPose)
                .strafeToLinearHeading(new Vector2d(-20, 20), Math.toRadians(0));
        Action trajectoryActionCloseOut = tab1.endTrajectory().fresh()
                .strafeTo(new Vector2d(0, 0))
                .build();


        waitForStart();

        if (isStopRequested()) return;

        Action trajectoryActionChosen;
        trajectoryActionChosen = tab1.build();

        Actions.runBlocking(
                new SequentialAction(
                        trajectoryActionChosen,
                        trajectoryActionCloseOut
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