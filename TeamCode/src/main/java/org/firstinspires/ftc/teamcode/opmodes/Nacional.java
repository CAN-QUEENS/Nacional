package org.firstinspires.ftc.teamcode.opmodes;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.PoseVelocity2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import org.firstinspires.ftc.teamcode.MecanumDrive;
import org.firstinspires.ftc.teamcode.robot.FieldCentricDrive;
import org.firstinspires.ftc.teamcode.subsystems.config.Barras;
import org.firstinspires.ftc.teamcode.subsystems.config.Intake;
import org.firstinspires.ftc.teamcode.subsystems.config.Outake;
import org.firstinspires.ftc.teamcode.subsystems.config.Slider;

@Config
@TeleOp(name = " ***** NACIONAL *****")
public class Nacional extends OpMode {

    private boolean isFieldCentric = true;
    private boolean lastY = false;
    private double speedFactor = 0.8;

    private MecanumDrive drive;
    private FieldCentricDrive fieldDrive;
    private Slider slider;
    private Barras barras;
    private Outake outake;
    private Intake intake;

    @Override
    public void init() {
        drive = new MecanumDrive(hardwareMap, new Pose2d(0, 0, 0));
        fieldDrive = new FieldCentricDrive(hardwareMap);
        slider = new Slider(hardwareMap);
        barras = new Barras(hardwareMap, telemetry);
        outake = new Outake(hardwareMap);
        intake = new Intake(hardwareMap);
        telemetry.addLine("Ready to start");
        telemetry.update();
    }

    @Override
    public void loop() {
        TelemetryPacket packet = new TelemetryPacket();
        if (gamepad1.triangle){
            fieldDrive.resetYaw();
            telemetry.addLine("Yaw Reiniciado");
        }

        /*if (gamepad1.left_bumper) {
            speedFactor = 0.5;
        } else if (gamepad1.right_bumper) {
            speedFactor = 0.7;
        } else {
            speedFactor = 0.8;
        }*/

        double y = -gamepad1.left_stick_y * speedFactor;
        double x = gamepad1.left_stick_x * speedFactor;
        double rx = gamepad1.right_stick_x * speedFactor;

        if (isFieldCentric) {
            fieldDrive.moveRobot(x, y, rx);
        } else {
            drive.setDrivePowers(new PoseVelocity2d(new Vector2d(y, -x), -rx));
        }

        drive.updatePoseEstimate();

        if(gamepad2.dpad_down){
            barras.PUT_ESPECIMEN().run(packet);
            telemetry.addLine("BARRAS PUT_ESPECIMEN");
        }else if(gamepad2.dpad_up){
            barras.ESPECIMEN().run(packet);
            telemetry.addLine("BARRAS ESPECIMEN");
        }else {
            barras.HUMAN().run(packet);
            telemetry.addLine("BARRAS HUMAN");
        }


        if(gamepad2.y){
            slider.OUT().run(packet);
            telemetry.addLine("SLIDERS OUT");
        }else {
            slider.IN().run(packet);
            telemetry.addLine("SLIDERS IN");
        }

         if(gamepad1.right_trigger > 0){
             intake.PICK_SAMPLE().run(packet);
         }else if (gamepad1.left_trigger > 0){
             intake.DROP_SAMPLE().run(packet);
         }else{
             intake.STOP_intake().run(packet);
         }

        if(gamepad1.right_bumper){
            outake.PICK_SAMPLE().run(packet);
            telemetry.addLine("AGARRANDO ESPECIMEN");
        }else if(gamepad1.left_bumper){
            outake.DROP_SAMPLE().run(packet);
            telemetry.addLine("SOLTANDO ESPECIMEN");
        }

        telemetry.update();
    }
}