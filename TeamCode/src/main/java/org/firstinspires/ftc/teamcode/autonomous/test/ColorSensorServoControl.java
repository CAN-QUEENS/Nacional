package org.firstinspires.ftc.teamcode.autonomous.test;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.Servo;

@Autonomous(name="Color Sensor Servo Control", group="Autonomous")
public class ColorSensorServoControl extends OpMode {
    private ColorSensor colorSensor;
    private Servo servo;
    private boolean colorDetected = false;
    private boolean waiting = false;
    private long detectTime = 0;

    @Override
    public void init() {
        colorSensor = hardwareMap.get(ColorSensor.class, "sensorColor");
        servo = hardwareMap.get(Servo.class, "servoContinuo");
    }

    @Override
    public void start() {
        servo.setPosition(1);
    }

    @Override
    public void loop() {
        int red = colorSensor.red();
        int green = colorSensor.green();
        int blue = colorSensor.blue();

        if (!colorDetected && esColorDetectado(red, green, blue)) {
            colorDetected = true;
            waiting = true;
            detectTime = System.currentTimeMillis();
            servo.setPosition(0);
        }

        if (waiting && System.currentTimeMillis() - detectTime >= 1500) {
            waiting = false;
            if (!(esRojo(red, green, blue) || esAmarillo(red, green, blue))) {
                servo.setPosition(1);
            }
        }
    }

    private boolean esColorDetectado(int r, int g, int b) {
        return (r > 50 || g > 50 || b > 50);
    }

    private boolean esRojo(int r, int g, int b) {
        return (r >= 150 && r <= 255) && (g >= 0 && g <= 100) && (b >= 0 && b <= 100);
    }

    private boolean esAmarillo(int r, int g, int b) {
        return (r >= 180 && r <= 255) && (g >= 180 && g <= 255) && (b >= 0 && b <= 100);
    }
}
