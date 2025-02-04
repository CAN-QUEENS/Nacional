package org.firstinspires.ftc.teamcode.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.vision.color.Sensor;

@TeleOp(name = "Sensor Test", group = "Test")
public class Color_sensor extends OpMode {

    private Sensor sensorColor;
    private boolean lastXButton = false;

    @Override
    public void init() {
        sensorColor = new Sensor(hardwareMap, "sensor_color");
        telemetry.addData("Status", "Initialized");
        telemetry.update();
    }

    @Override
    public void loop() {
        String detectedColor = sensorColor.detectarColor();

        boolean xButton = gamepad1.x;
        if (xButton && !lastXButton) {
            sensorColor.alternarLuz(true);
        } else if (!xButton && lastXButton) {
            sensorColor.alternarLuz(false);
        }
        lastXButton = xButton;

        telemetry.addData("Color Detectado", detectedColor);
        telemetry.addData("Rojo", sensorColor.getRed());
        telemetry.addData("Amarillo", sensorColor.getYellow());
        telemetry.addData("Azul", sensorColor.getBlue());
        telemetry.update();
    }
}