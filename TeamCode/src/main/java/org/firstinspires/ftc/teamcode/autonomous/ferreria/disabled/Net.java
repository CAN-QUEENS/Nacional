package org.firstinspires.ftc.teamcode.autonomous.ferreria.disabled;

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
@Autonomous
public final class Net extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        Pose2d startPose = new Pose2d(-23, -61, Math.PI/2);
        MecanumDrive drive = new MecanumDrive(hardwareMap, startPose);

        TrajectoryActionBuilder tab1 = drive.actionBuilder(startPose)
                //TODO: ****** NET *****
                .setTangent(0)
                .strafeToLinearHeading(new Vector2d(-52, -50), Math.toRadians(226))
                .waitSeconds(1)
                .setTangent(0)
                // TODO: FIRST SAMPLE
                .strafeToLinearHeading(new Vector2d(-47, -39), Math.toRadians(-270))
                .waitSeconds(.6)
                .setTangent(0)
                // TODO: ***** NET *****
                .strafeToLinearHeading(new Vector2d(-52, -50), Math.toRadians(226))
                .waitSeconds(1)
                .setTangent(0)
                //TODO: SECOND SAMPLE
                .strafeToLinearHeading(new Vector2d(-57, -39), Math.toRadians(-270))
                .waitSeconds(.6)
                .setTangent(0)
                // TODO: ***** NET *****
                .strafeToLinearHeading(new Vector2d(-52, -50), Math.toRadians(226))
                .waitSeconds(1)
                .setTangent(Math.PI * 2 )
                //TODO: THIRD SAMPLE
                .strafeToLinearHeading(new Vector2d(-50, -24), Math.toRadians(180))
                .waitSeconds(.6)
                .setTangent(0)
                // TODO: ***** NET *****
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