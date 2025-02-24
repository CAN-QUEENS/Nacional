package org.firstinspires.ftc.teamcode.subsystems.teleop;

import static org.firstinspires.ftc.teamcode.utils.Constants.Intake.*;
import static org.firstinspires.ftc.teamcode.utils.Constants.Outake.OUTAKE_NAME;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp
@Config
public class Slider extends OpMode {

    private Servo intakeDER;
    private Servo intakeIZQ;

    public static double Pose_der = 0;
    public static double Pose_izq = 0;

    @Override
    public void init() {
        telemetry = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());
        intakeIZQ = hardwareMap.get(Servo.class, "intakeIZQ");
        intakeDER = hardwareMap.get(Servo.class, "intakeDER");

        telemetry.update();
    }

    @Override
    public void loop(){
        intakeDER.setPosition(Pose_der);
        intakeIZQ.setPosition(Pose_izq);
    }
}