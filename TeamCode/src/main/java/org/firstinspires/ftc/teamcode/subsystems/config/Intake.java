package org.firstinspires.ftc.teamcode.subsystems.config;

import static org.firstinspires.ftc.teamcode.utils.Constants.Intake.*;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

@Config
public class Intake {
    private Servo rotor;

    public Intake(HardwareMap hardwareMap) {
        try {
            rotor = hardwareMap.get(Servo.class, ROTOR_NAME);
        } catch (Exception e) {
            throw new IllegalStateException("Error al inicializar los servos del Intake: " + e.getMessage());
        }
    }

    public class OUT implements Action {
        private long startTime = -1;

        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket) {
            if (startTime == -1) {
                startTime = System.currentTimeMillis(); // Inicia el temporizador
                rotor.setPosition(1); // Gira en dirección hacia "IN"
            }

            // Detiene el servo después de 2 segundos
            if (System.currentTimeMillis() - startTime >= 1500) {
                rotor.setPosition(0.5); // Detener (posición neutra para servos continuos)
                return false; // Acción completada
            }
            return false; // Acción en progreso
        }
    }

    public class IN implements Action {
        private long startTime = -1;

        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket) {
            if (startTime == -1) {
                startTime = System.currentTimeMillis(); // Inicia el temporizador
                rotor.setPosition(0); // Gira en dirección hacia "OUT"
            }

            // Detiene el servo después de 2 segundos
            if (System.currentTimeMillis() - startTime >= 1500) {
                rotor.setPosition(0.5); // Detener (posición neutra para servos continuos)
                return false; // Acción completada
            }
            return false; // Acción en progreso
        }
    }

    // Nuevo comando para detener el rotor
    public class STOPROTOR implements Action {
        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket) {
            rotor.setPosition(0.5); // Posición neutra para detener el servo
            return false; // Acción completada
        }
    }

    public Action DROP_SAMPLE() {return new OUT();}

    public Action PICK_SAMPLE() {return new IN();}

    public Action STOP_intake() {
        return new STOPROTOR();
    }
}