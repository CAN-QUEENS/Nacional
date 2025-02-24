package org.firstinspires.ftc.teamcode.autonomous.nacional;

import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.ParallelAction;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.ProfileAccelConstraint;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.TranslationalVelConstraint;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.MecanumDrive;
import org.firstinspires.ftc.teamcode.subsystems.config.Barras;
import org.firstinspires.ftc.teamcode.subsystems.config.Outake;

@Autonomous
public final class ESPECIMEN extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        Pose2d startPose = new Pose2d(23, -62, -Math.PI / 2);

        MecanumDrive drive = new MecanumDrive(hardwareMap, startPose);
        Barras barras = new Barras(hardwareMap, telemetry);
        Outake outake = new Outake(hardwareMap);

        TranslationalVelConstraint HIGH_SPEED = new TranslationalVelConstraint(100);
        ProfileAccelConstraint HIGH_ACCEL = new ProfileAccelConstraint(-100, 100);

        TranslationalVelConstraint MEDIUM_SPEED = new TranslationalVelConstraint(80);
        ProfileAccelConstraint MEDIUM_ACCEL = new ProfileAccelConstraint(-80, 80);

        TranslationalVelConstraint LOW_SPEED = new TranslationalVelConstraint(40);
        ProfileAccelConstraint LOW_ACCEL = new ProfileAccelConstraint(-40, 40);

        Action actionSequence = drive.actionBuilder(startPose)
                .afterTime(0, new ParallelAction(outake.PICK_SAMPLE()))
                /*.strafeToConstantHeading(new Vector2d(0, -37), MEDIUM_SPEED, MEDIUM_ACCEL)
                .afterTime(0, barras.ESPECIMEN())
                .strafeToConstantHeading(new Vector2d(0, -32), MEDIUM_SPEED, MEDIUM_ACCEL)*/
                .waitSeconds(1)
                .afterTime(0.1, new ParallelAction(
                        barras.ESPECIMEN(),
                        barras.PUT_ESPECIMEN(),
                        outake.DROP_SAMPLE()
                ))
                /*.waitSeconds(0.3) //TODO: CONFIG THE TIME
                .strafeToConstantHeading(new Vector2d(0, -37), MEDIUM_SPEED, MEDIUM_ACCEL)

                .afterTime(0, barras.HUMAN())
                .strafeToLinearHeading(new Vector2d(36, -37), Math.PI / 2)

                .strafeToConstantHeading(new Vector2d(37, -10), HIGH_SPEED, HIGH_ACCEL)
                .strafeToConstantHeading(new Vector2d(47, -10), HIGH_SPEED, HIGH_ACCEL)
                .strafeToConstantHeading(new Vector2d(47, -53), HIGH_SPEED, HIGH_ACCEL)
                .strafeToConstantHeading(new Vector2d(47, -10), HIGH_SPEED, HIGH_ACCEL)
                .strafeToConstantHeading(new Vector2d(55, -10), HIGH_SPEED, HIGH_ACCEL)
                .strafeToConstantHeading(new Vector2d(55, -53), HIGH_SPEED, HIGH_ACCEL)

                .strafeTo(new Vector2d(47, -55))
                .strafeToConstantHeading(new Vector2d(47, -60), LOW_SPEED, LOW_ACCEL)
                .afterTime(0.1, outake.PICK_SAMPLE())
                .waitSeconds(0.3)//TODO: CONFIG THE TIME

                .strafeToLinearHeading(new Vector2d(0, -34), -Math.PI / 2)
                .afterTime(0.15, new SequentialAction(
                        barras.ESPECIMEN(),
                        barras.PUT_ESPECIMEN(),
                        outake.DROP_SAMPLE()
                ))
                .afterTime(0, barras.HUMAN())
                .waitSeconds(0.3)//TODO: CONFIG THE TIME
                .strafeToLinearHeading(new Vector2d(47, -60), Math.PI / 2)
                .afterTime(0, outake.PICK_SAMPLE())
                .waitSeconds(0.3)//TODO: CONFIG THE TIME


                .strafeToLinearHeading(new Vector2d(0, -34), -Math.PI / 2)
                .afterTime(0.15, new SequentialAction(
                        barras.ESPECIMEN(),
                        barras.PUT_ESPECIMEN(),
                        outake.DROP_SAMPLE()
                ))
                .afterTime(0, barras.HUMAN())
                .waitSeconds(0.3)//TODO: CONFIG THE TIME
                .strafeToLinearHeading(new Vector2d(47, -60), Math.PI / 2)
                .afterTime(0, outake.PICK_SAMPLE())
                .waitSeconds(0.3)//TODO: CONFIG THE TIME

                .strafeToLinearHeading(new Vector2d(0, -34), -Math.PI / 2)
                .afterTime(0.15, new SequentialAction(
                        barras.ESPECIMEN(),
                        barras.PUT_ESPECIMEN(),
                        outake.DROP_SAMPLE()
                ))
                .afterTime(0, barras.HUMAN())
                .waitSeconds(0.3)//TODO: CONFIG THE TIME
                 */
                .build();

        waitForStart();

        Actions.runBlocking(new ParallelAction(
                actionSequence
        ));

        telemetry.addLine("Autonomous Complete!");
        telemetry.update();
    }
}