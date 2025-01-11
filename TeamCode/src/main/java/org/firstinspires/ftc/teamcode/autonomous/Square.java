package org.firstinspires.ftc.teamcode.autonomous;

import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.MecanumDrive;


@Disabled
@Autonomous(name = "Square", group = "00-Teleop")
public final class Square extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        Pose2d startPose = new Pose2d(0, 0, Math.toRadians(90));
        MecanumDrive drive = new MecanumDrive(hardwareMap, startPose);

        Action traj1 = drive.actionBuilder(startPose)
                /*.afterTime(2, () -> {

                })*/
                .strafeToLinearHeading(new Vector2d(-20, 0), Math.toRadians(90))
                .strafeToLinearHeading(new Vector2d(-20, 20), Math.toRadians(90))
                .strafeToLinearHeading(new Vector2d(0, 20), Math.toRadians(90))
                .strafeToLinearHeading(new Vector2d(0, 0), Math.toRadians(90))
                .build();

        waitForStart();

        Actions.runBlocking(traj1);

        telemetry.addData("Square complete", "All four trajectories with actions executed.");
        telemetry.update();
    }
}


    /*
        Action traj1 = drive.actionBuilder(startPose)
                .strafeToLinearHeading(new Vector2d(-20, 0), Math.toRadians(90))
                .build();

        Pose2d endPose1 = new Pose2d(-20, 0, Math.toRadians(90));

        Action traj2 = drive.actionBuilder(endPose1)
                .strafeToLinearHeading(new Vector2d(-20, 20), Math.toRadians(90))
                .build();

        Pose2d endPose2 = new Pose2d(-20, 20, Math.toRadians(90));

        Action traj3 = drive.actionBuilder(endPose2)
                .strafeToLinearHeading(new Vector2d(0, 20), Math.toRadians(90))
                .build();

        Pose2d endPose3 = new Pose2d(0, 20, Math.toRadians(90));

        Action traj4 = drive.actionBuilder(endPose3)
                .strafeToLinearHeading(new Vector2d(0, 0), Math.toRadians(90))
                .build();

        waitForStart();

        Actions.runBlocking(traj1);*/