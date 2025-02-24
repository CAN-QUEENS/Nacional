package org.firstinspires.ftc.teamcode.subsystems.teleop;

import static org.firstinspires.ftc.teamcode.utils.Constants.Barras.BARRAS_NAME;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.arcrobotics.ftclib.controller.PIDController;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;

@Disabled
@Config
@TeleOp
public class Barras extends OpMode {
    private PIDController controller;

    public static double p = 0.004, i = 0, d = 0.0001;
    public static double f = 0.1;
    public static int target = 0;

    private final double ticks_in_degree = 700 / 180.0;

    private DcMotor motor;

    @Override
    public void init(){
        controller = new PIDController(p, i , d);
        telemetry = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());

        motor = hardwareMap.get(DcMotor.class, BARRAS_NAME);
    }

    @Override
    public void loop(){
        controller.setPID(p, i, d);
        int ArmPos = motor.getCurrentPosition();
        double pid = controller.calculate(ArmPos, target);
        double ff = Math.cos(Math.toRadians(target / ticks_in_degree)) * f;

        double power = (pid + ff) * 0.8;
        motor.setPower(power);
        telemetry.addData("pos: ", ArmPos);
        telemetry.addData("target: ", target);
        telemetry.update();
    }
}