package org.firstinspires.ftc.teamcode.autonomous.test;

import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.PoseVelocity2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.qualcomm.hardware.dfrobot.HuskyLens;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import org.firstinspires.ftc.teamcode.MecanumDrive;

@Disabled
@Autonomous(name = "Obj Tracking", group= "TEST: ")
public class ObjTracking extends LinearOpMode {

    private HuskyLens huskyLens;
    private MecanumDrive drive;

    private static final double CENTER_X = 160;
    private static final double CENTER_Y = 120;
    private static final double PROPORTIONAL_GAIN = 0.001;  // Valor más pequeño para un movimiento más controlado
    private static final double MAX_SPEED = 0.5;  // Velocidad máxima permitida para el movimiento

    @Override
    public void runOpMode() {
        huskyLens = hardwareMap.get(HuskyLens.class, "huskylens");
        Pose2d startPose = new Pose2d(0,0,0);
        drive = new MecanumDrive(hardwareMap, startPose);

        huskyLens.selectAlgorithm(HuskyLens.Algorithm.OBJECT_TRACKING);

        waitForStart();

        while (opModeIsActive()) {
            HuskyLens.Block[] blocks = huskyLens.blocks();

            if (blocks.length > 0) {
                for (int i = 0; i < blocks.length; i++) {
                    HuskyLens.Block block = blocks[i];

                    telemetry.addData("Block " + i, block.toString());

                    if (block != null) {
                        int blockX = block.x;
                        int blockY = block.y;

                        telemetry.addData("Block Coordinates", "X: " + blockX + ", Y: " + blockY);

                        double errorX = blockX - CENTER_X;
                        double errorY = blockY - CENTER_Y;

                        // Calcula los valores de movimiento, con límites para evitar movimientos descontrolados
                        double moveX = -errorY * PROPORTIONAL_GAIN;
                        double moveY = -errorX * PROPORTIONAL_GAIN;

                        // Limita la velocidad máxima
                        moveX = Math.max(Math.min(moveX, MAX_SPEED), -MAX_SPEED);
                        moveY = Math.max(Math.min(moveY, MAX_SPEED), -MAX_SPEED);

                        // Aplica los movimientos al robot
                        drive.setDrivePowers(new PoseVelocity2d(
                                new Vector2d(moveY, moveX), 0
                        ));
                    }
                }
            } else {
                // Si no se detecta ningún objeto, el robot se detiene
                drive.setDrivePowers(new PoseVelocity2d(
                        new Vector2d(0, 0), 0
                ));
                telemetry.addLine("No object detected.");
            }

            telemetry.update();
        }
    }
}