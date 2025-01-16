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

@Autonomous(name = "HUMAN", group = "Match-Auto: ")
public final class Human extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        Pose2d startPose = new Pose2d(23, -62, Math.PI);
        MecanumDrive drive = new MecanumDrive(hardwareMap, startPose);

        TrajectoryActionBuilder tab1 = drive.actionBuilder(startPose)
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
                .strafeTo(new Vector2d(61, -53));

        /*Action trajectoryActionCloseOut = tab1.endTrajectory().fresh()
                .strafeTo(new Vector2d(0, 0))
                .build();*/


        waitForStart();

        if (isStopRequested()) return;

        Action trajectoryActionChosen;
        trajectoryActionChosen = tab1.build();

        Actions.runBlocking(
                new SequentialAction(
                        trajectoryActionChosen
                        /* trajectoryActionCloseOut*/
                )
        );
    }
}