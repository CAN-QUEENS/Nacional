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
@Autonomous(name = "Line", group = "00-Teleop")
public final class Line extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        Pose2d startPose = new Pose2d(0, 0, Math.toRadians(90));
        MecanumDrive drive = new MecanumDrive(hardwareMap, startPose);

        Action traj1 = drive.actionBuilder(startPose)
                .setTangent(0)
                .splineToLinearHeading(new Pose2d(-20, 20, 0), Math.PI / 2)
                //.strafeToLinearHeading(new Vector2d(-40, 0), Math.toRadians(90))
                .build();

        waitForStart();

        Actions.runBlocking(traj1);

        telemetry.addData("Square complete", "All four trajectories with actions executed.");
        telemetry.update();
    }
}