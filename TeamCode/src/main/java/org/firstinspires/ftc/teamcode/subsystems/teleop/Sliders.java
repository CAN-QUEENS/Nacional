/*package org.firstinspires.ftc.teamcode.subsystems.teleop;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;

@Config
@TeleOp
public class Sliders extends OpMode {
    // Posiciones configurables desde FTC Dashboard
    public static double targetServo1 = 0; // Posición objetivo para el Servo 1
    public static double targetServo2 = 0; // Posición objetivo para el Servo 2

    private Servo der, izq;

    @Override
    public void init() {
        telemetry = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());

        // Inicializar los servos
        der = hardwareMap.get(Servo.class, "servo1");
        izq = hardwareMap.get(Servo.class, "servo2");
        izq.setDirection(Servo.Direction.REVERSE);

        telemetry.addLine("Servos listos");
        telemetry.update();
    }

    @Override
    public void loop() {
        // Asignar las posiciones objetivo a los servos
        der.setPosition(targetServo1);
        izq.setPosition(targetServo2/5);

        // Telemetría para depuración
        telemetry.addData("Servo 1 Posición:", der.getPosition());
        telemetry.addData("Servo 2 Posición:", izq.getPosition());
        telemetry.update();
    }
}*/