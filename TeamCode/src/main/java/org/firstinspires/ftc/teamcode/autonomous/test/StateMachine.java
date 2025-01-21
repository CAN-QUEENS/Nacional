package org.firstinspires.ftc.teamcode.autonomous.test;

import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;

import org.firstinspires.ftc.teamcode.MecanumDrive;

@Disabled
@Autonomous(name = "StateMachine", group = "TEST: ")
public class StateMachine extends LinearOpMode {

    private enum State {
        FOLLOW_TRAJECTORY1,
        DETECT_RED,
        RETURN_HOME,
        FINISHED
    }

    private State currentState = State.FOLLOW_TRAJECTORY1;

    @Override
    public void runOpMode() {
        Pose2d startPose = new Pose2d(0, 0, Math.toRadians(90));
        MecanumDrive drive = new MecanumDrive(hardwareMap, startPose);
        ColorSensor colorSensor = hardwareMap.get(ColorSensor.class, "colorSensor");

        Action traj1 = drive.actionBuilder(startPose)
                .strafeToLinearHeading(new Vector2d(-20, 20), Math.toRadians(90))
                .build();

        Pose2d endPose1 = new Pose2d(-20, 20, Math.toRadians(90));

        Action returnHome = drive.actionBuilder(endPose1)
                .strafeToLinearHeading(new Vector2d(0, 0), Math.toRadians(90))
                .build();

        waitForStart();

        while (opModeIsActive()) {
            telemetry.addData("State", currentState);
            telemetry.update();

            switch (currentState) {
                case FOLLOW_TRAJECTORY1:
                    telemetry.addLine("Executing trajectory 1");
                    Actions.runBlocking(traj1);
                    currentState = State.DETECT_RED;
                    break;

                case DETECT_RED:
                    telemetry.addLine("Checking for red color");
                    telemetry.addData("Color Sensor Red", colorSensor.red());
                    telemetry.addData("Color Sensor Blue", colorSensor.blue());
                    telemetry.update();

                    if (colorSensor.red() > colorSensor.blue()) {
                        telemetry.addLine("Red color detected! Returning home.");
                        currentState = State.RETURN_HOME;
                    } else {
                        telemetry.addLine("No red detected. Autonomous finished.");
                        currentState = State.DETECT_RED;
                    }
                    break;

                case RETURN_HOME:
                    telemetry.addLine("Returning to start position");
                    Actions.runBlocking(returnHome);
                    currentState = State.FINISHED;
                    break;

                case FINISHED:
                    telemetry.addLine("Autonomous finished!");
                    telemetry.update();
                    stop();
                    break;
            }
        }
    }
}