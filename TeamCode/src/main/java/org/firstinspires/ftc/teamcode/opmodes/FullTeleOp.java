package org.firstinspires.ftc.teamcode.opmodes;

import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.PoseVelocity2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.MecanumDrive;
import org.firstinspires.ftc.teamcode.robot.FieldCentricDrive;

@TeleOp(name = "CANQUEENS", group = "00-Teleop")
public final class FullTeleOp extends LinearOpMode {


    @Override
    public void runOpMode() throws InterruptedException {
        MecanumDrive drive = new MecanumDrive(hardwareMap, new Pose2d(0, 0, 0));
        FieldCentricDrive fieldDrive = new FieldCentricDrive(hardwareMap);

        boolean isFieldCentric = false;
        boolean lastX = false;
        boolean lastY = false;

        double speedFactor = 0.8;

        waitForStart();

        while (opModeIsActive()) {

            if (gamepad1.x && !lastX) {
                isFieldCentric = !isFieldCentric;
                telemetry.addLine(isFieldCentric ? "Field-Centric Mode" : "Mecanum Mode");
            }
            lastX = gamepad1.x;

            if (isFieldCentric && gamepad1.y && !lastY) {
                fieldDrive.resetYaw();
                telemetry.addLine("Yaw Reiniciado");
            }
            lastY = gamepad1.y;

            if (gamepad1.left_bumper) {
                speedFactor = 0.5;
            } else if (gamepad1.right_bumper) {
                speedFactor = 0.7;
            } else {
                speedFactor = 0.8;
            }

            double y = -gamepad1.left_stick_y * speedFactor;
            double x = gamepad1.left_stick_x * speedFactor;
            double rx = gamepad1.right_stick_x * speedFactor;

            if (isFieldCentric) {
                fieldDrive.moveRobot(x, y, rx);
            } else {
                drive.setDrivePowers(new PoseVelocity2d(
                        new Vector2d(y, -x), -rx
                ));
            }

            drive.updatePoseEstimate();

            // Telemetría
            telemetry.addData("Mode", isFieldCentric ? "Field-Centric" : "Mecanum");
            telemetry.addData("Speed Factor", speedFactor);
            telemetry.update();
        }
    }
}