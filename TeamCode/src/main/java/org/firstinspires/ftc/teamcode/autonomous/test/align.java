package org.firstinspires.ftc.teamcode.autonomous.test;

import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.PoseVelocity2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.PIDCoefficients;
import org.firstinspires.ftc.teamcode.MecanumDrive;
import org.firstinspires.ftc.teamcode.vision.robot.RobotCameraDistance;

@TeleOp(name = "TeleOp_Centrar_Objeto", group = "RoadRunner")
public class align extends OpMode {
    private MecanumDrive drive;
    private RobotCameraDistance camera;
    private PIDCoefficients pid = new PIDCoefficients(0.02, 0, 0.005);
    private double lastErrorX = 0;

    @Override
    public void init() {
        drive = new MecanumDrive(hardwareMap, new Pose2d(0,0,0));
        camera = new RobotCameraDistance();
        camera.initialize(hardwareMap, "red");

        telemetry.addLine("Listo para iniciar...");
        telemetry.update();
    }

    @Override
    public void loop() {
        if (gamepad1.triangle) { // Si el botón "A" está presionado, intenta centrar el objeto
            double[] polarCoords = camera.getPolarCoordinates();
            double errorX = polarCoords[1] - 180;
            double derivativeX = errorX - lastErrorX;
            lastErrorX = errorX;

            double turnPower = (pid.p * errorX) + (pid.d * derivativeX);

            if (Math.abs(errorX) < 20) {
                telemetry.addLine("¡Centrado!");
                turnPower = 0;
            }

            drive.setDrivePowers(new PoseVelocity2d(new Vector2d(0, 0), turnPower));

            telemetry.addData("Error X", errorX);
            telemetry.addData("Corrección", turnPower);
        } else {
            drive.setDrivePowers(new PoseVelocity2d(new Vector2d(0, 0), 0)); // Se queda quieto si no se presiona el botón
            telemetry.addLine("Esperando comando...");
        }

        telemetry.update();
    }
}