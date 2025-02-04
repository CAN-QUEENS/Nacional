package org.firstinspires.ftc.teamcode.vision.color;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.SwitchableLight;

@Config
public class Sensor {
    private ColorSensor colorSensor;

    public Sensor(HardwareMap hardwareMap, String sensorName) {
        colorSensor = hardwareMap.get(ColorSensor.class, sensorName);
        if (colorSensor instanceof SwitchableLight) {
            ((SwitchableLight) colorSensor).enableLight(true);
        }
    }

    public String detectarColor() {
        int red = getRed();
        int yellow = getYellow(); // No hay "yellow", se usa el verde
        int blue = getBlue();

        // Encontrar el color con el valor más alto
        if (red > yellow && red > blue) {
            return "Rojo";
        } else if (blue > red && blue > yellow) {
            return "Azul";
        } else if (yellow > red && yellow > blue) {
            return "Amarillo"; // En realidad es verde, pero depende del entorno
        }

        return "Ninguno";
    }

    public void alternarLuz(boolean encender) {
        if (colorSensor instanceof SwitchableLight) {
            ((SwitchableLight) colorSensor).enableLight(encender);
        }
    }

    // Métodos públicos para obtener valores del sensor
    public int getRed() {
        return colorSensor.red();
    }

    public int getYellow() {
        return colorSensor.green();
    }

    public int getBlue() {
        return colorSensor.blue();
    }
}