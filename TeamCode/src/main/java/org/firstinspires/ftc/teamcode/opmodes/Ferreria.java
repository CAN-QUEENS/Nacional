package org.firstinspires.ftc.teamcode.opmodes;

import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.PoseVelocity2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.teamcode.MecanumDrive;
import org.firstinspires.ftc.teamcode.robot.FieldCentricDrive;
import org.firstinspires.ftc.teamcode.subsystems.Intake;
import org.firstinspires.ftc.teamcode.subsystems.Slider;

@TeleOp(name = "CANQUEENS", group = "Ferreria: ")
public final class Ferreria extends OpMode {
    private final Intake intakeSERVO = new Intake();
    private boolean isFieldCentric = false;
    private boolean lastX = false;
    private boolean lastY = false;
    private double speedFactor = 0.8;
    private Slider sliderSubsystem;
    private MecanumDrive drive;
    private FieldCentricDrive fieldDrive;

    @Override
    public void init() {
        drive = new MecanumDrive(hardwareMap, new Pose2d(0, 0, 0));
        fieldDrive = new FieldCentricDrive(hardwareMap);
        sliderSubsystem = new Slider(hardwareMap, telemetry); // Nombre del motor
        telemetry.addLine("Initialized");
    }

    @Override
    public void loop() {
        intakeControl();
        sliderControl();
        controlRobot();
        sliderSubsystem.update();
        telemetry.update();
    }

    // TODO: SLIDER CONTROL
    private void sliderControl() {
        double joystickInput = -gamepad2.left_stick_y;
        if (Math.abs(joystickInput) > 0.1) {
            sliderSubsystem.setTargetFromJoystick(joystickInput);
        }
    }

    // TODO: INTAKE CONTROL
    private void intakeControl() {
        if (gamepad2.a) {
            intakeSERVO.pickSample();
        } else if (gamepad2.b) {
            intakeSERVO.dropSample();
        }
    }

    // TODO: CHASIS CONFIG CONTROL
    private void controlRobot() {
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
            speedFactor =  1.0;
        } else {
            speedFactor = 0.8;
        }

        double y = -gamepad1.left_stick_y * speedFactor;
        double x = gamepad1.left_stick_x * speedFactor;
        double rx = gamepad1.right_stick_x * speedFactor;

        if (isFieldCentric) {
            fieldDrive.moveRobot(x, y, rx);
        } else {
            drive.setDrivePowers(new PoseVelocity2d(new Vector2d(y, -x), -rx));
        }

        drive.updatePoseEstimate();
    }
}