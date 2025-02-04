package org.firstinspires.ftc.teamcode.autonomous.ferreria;

import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.ParallelAction;
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
import org.firstinspires.ftc.teamcode.subsystems.Intake;
import org.firstinspires.ftc.teamcode.subsystems.Slider;

@Autonomous
public final class ESPECIMEN_SAMPLES extends LinearOpMode {
    private Intake intakeSystem;
    private Slider sliderSystem;

    @Override
    public void runOpMode() throws InterruptedException {
        Pose2d startPose = new Pose2d(23, -62, Math.PI / 2);

        MecanumDrive drive = new MecanumDrive(hardwareMap, startPose);
        sliderSystem = new Slider(hardwareMap, telemetry);
        intakeSystem = new Intake();
        intakeSystem.init(hardwareMap);

        Action especimen = drive.actionBuilder(startPose)
                .afterTime(0, sliderSystem.Medium())
                .strafeTo(new Vector2d(0, -46))
                .strafeToConstantHeading(new Vector2d(0,-30),new TranslationalVelConstraint(20),new ProfileAccelConstraint(-10,10))
                .afterTime(0, new SequentialAction(
                        sliderSystem.high_CHAMBER()
                ))
                .afterTime(.7, intakeSystem.OUT_intake())
                .strafeToConstantHeading(new Vector2d(0,-46),new TranslationalVelConstraint(20),new ProfileAccelConstraint(-10,10))
                .afterTime(1, new SequentialAction(
                        intakeSystem.STOP_intake(),
                        sliderSystem.NoFloor()
                ))
                .strafeTo(new Vector2d(23, -50))
                .afterTime(1.5, new SequentialAction(
                        sliderSystem.PickSAMPLE()
                ))
                .strafeTo(new Vector2d(23, -62))
                .afterTime(0, new ParallelAction(
                        sliderSystem.NoFloor(),
                        intakeSystem.dropSample()
                ))
                .setTangent(Math.PI / 2)
                .splineToConstantHeading(new Vector2d(36, -28), Math.PI / 2)
                .strafeTo(new Vector2d(37, -10))
                .strafeTo(new Vector2d(47, -10))
                .strafeTo(new Vector2d(47, -53))
                .strafeTo(new Vector2d(47, -10))
                .strafeTo(new Vector2d(55, -10))
                .strafeTo(new Vector2d(55, -53))
                .strafeTo(new Vector2d(55, -10))
                .strafeTo(new Vector2d(65, -10))
                .strafeTo(new Vector2d(65, -53))
                .build();

       /* TrajectoryActionBuilder traj = drive.actionBuilder(startPose)
                .strafeTo(new Vector2d(0,0));

        TrajectoryActionBuilder traj2 = traj.endTrajectory().fresh()
                        .strafeTo(new Vector2d(0,0 ));*/

        waitForStart();

        Actions.runBlocking(new ParallelAction(
               // traj2.build(),
                especimen,
                sliderSystem.SliderUpdate()
        ));

        telemetry.addLine("Autonomous Complete!");
        telemetry.update();
    }
}