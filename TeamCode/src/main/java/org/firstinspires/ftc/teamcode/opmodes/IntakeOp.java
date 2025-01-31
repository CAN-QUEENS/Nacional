package org.firstinspires.ftc.teamcode.opmodes;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.PoseVelocity2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.MecanumDrive;
import org.firstinspires.ftc.teamcode.robot.FieldCentricDrive;
import org.firstinspires.ftc.teamcode.subsystems.Intake;
import org.firstinspires.ftc.teamcode.subsystems.Slider;

@TeleOp(name = "FERRERIA")
public class IntakeOp extends OpMode {

    private boolean isFieldCentric = false;
    private boolean lastX = false;
    private boolean lastY = false;
    private double speedFactor = 0.8;

    private MecanumDrive drive;
    private FieldCentricDrive fieldDrive;
    private Intake intakeSystem;
    private Slider sliderSystem;
    private Servo rotor;

    @Override
    public void init() {
        drive = new MecanumDrive(hardwareMap, new Pose2d(0, 0, 0));
        fieldDrive = new FieldCentricDrive(hardwareMap);
        sliderSystem = new Slider(hardwareMap, telemetry);
        rotor = hardwareMap.get(Servo.class, "rotor");

        intakeSystem = new Intake();
        intakeSystem.init(hardwareMap);

        telemetry.addLine("Ready to start");
        telemetry.update();
    }

    @Override
    public void loop() {
        TelemetryPacket packet = new TelemetryPacket();

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
            drive.setDrivePowers(new PoseVelocity2d(new Vector2d(y, -x), -rx));
        }

        drive.updatePoseEstimate();

        if (gamepad2.b) {
            intakeSystem.pickSample().run(packet);
            telemetry.addLine("OUT");
        } else {
            intakeSystem.dropSample().run(packet);
        }

        if (gamepad2.left_bumper) {
            intakeSystem.OUT_intake().run(packet);
            telemetry.addLine("Rotando OUT");
        } else if (gamepad2.right_bumper) {
            intakeSystem.IN_intake().run(packet);
            telemetry.addLine("Rotando IN");
        } else {
            intakeSystem.STOP_intake().run(packet);
            telemetry.addLine("Rotor detenido");
        }

        if (gamepad2.a) {
            sliderSystem.high_CHAMBER().run(packet);
        } else {
            sliderSystem.PickSAMPLE().run(packet);
        }

        sliderSystem.update();
        telemetry.update();
    }
}