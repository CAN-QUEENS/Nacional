package org.firstinspires.ftc.teamcode.utils;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.arcrobotics.ftclib.controller.PIDController;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorEx;

@Config
@TeleOp
public class PID extends OpMode {
    private PIDController controller;

    public static double p = 0, i = 0, d = 0;
    public static double f = 0;
    public static int target = 0;

    private final double ticks_in_degree = 700 / 180.0;

    private DcMotorEx motor;

    @Override
    public void init(){
        controller = new PIDController(p, i , d);
        telemetry = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());

        motor = hardwareMap.get(DcMotorEx.class, "slider");
    }

    @Override
    public void loop(){
        controller.setPID(p, i, d);
        int ArmPos = motor.getCurrentPosition();
        double pid = controller.calculate(ArmPos, target);
        double ff = Math.cos(Math.toRadians(target / ticks_in_degree)) * f;

        double power = pid + ff;
        motor.setPower(power);
        telemetry.addData("pos: ", ArmPos);
        telemetry.addData("target: ", target);
        telemetry.update();
    }
}