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
public final class Samples extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {
        Pose2d startPose = new Pose2d(23, -62, Math.PI/2);
        MecanumDrive drive = new MecanumDrive(hardwareMap, startPose);

        TrajectoryActionBuilder tab1 = drive.actionBuilder(startPose)
                //TODO: FIRST ESPECIMEN
                .strafeTo(new Vector2d(0,-37))
                .waitSeconds(1)
                .setTangent(0)
                .strafeToLinearHeading(new Vector2d(36, -37), Math.toRadians(-270))
                .strafeTo(new Vector2d(36, -12))
                .strafeTo(new Vector2d(47, -12))
                .strafeTo(new Vector2d(47, -48))
                //TODO: ****** FIRST SAMPLE ******
                .strafeTo(new Vector2d(47, -12))
                .strafeTo(new Vector2d(55, -12))
                //TODO: ***** SECOND SAMPLE *****
                .strafeTo(new Vector2d(55, -48))
                .strafeTo(new Vector2d(55, -12))
                .strafeTo(new Vector2d(61, -12))
                //TODO: THIRD SAMPLE
                .strafeTo(new Vector2d(61, -59))
                .waitSeconds(.6)
                //TODO: SECOND ESPECIMEN
                .strafeTo(new Vector2d(0, -37))
                .strafeTo(new Vector2d(0,-37))
                .waitSeconds(1)
                //TODO: OBSERVATION ZONE
                .strafeTo(new Vector2d(40, -59))
                .waitSeconds(1)
                //TODO: THIRD ESPECIMEN
                .strafeTo(new Vector2d(0, -37))
                .strafeTo(new Vector2d(0,-37))
                .waitSeconds(1)
                //TODO: OBSERVATION ZONE
                .strafeTo(new Vector2d(40, -59))
                .waitSeconds(1)
                //TODO: FOURTH ESPECIMEN
                .strafeTo(new Vector2d(0, -37))
                .strafeTo(new Vector2d(0,-37))
                .waitSeconds(1)
                .strafeTo(new Vector2d(36, -55))
                .waitSeconds(1)
                .strafeTo(new Vector2d(0, -37))
                .strafeTo(new Vector2d(0,-37));

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