package org.firstinspires.ftc.teamcode.subsystems;

import static org.firstinspires.ftc.teamcode.utils.Constants.Intake.*;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class Intake {
    private Servo intakeDER;
    private Servo intakeIZQ;
    private Servo rotor;

    public void init(HardwareMap hardwareMap) {
        try {
            intakeDER = hardwareMap.get(Servo.class, INTAKE_NAME_DER);
            intakeIZQ = hardwareMap.get(Servo.class, INTAKE_NAME_IZQ);
            rotor = hardwareMap.get(Servo.class, ROTOR_NAME);
        } catch (Exception e) {
            throw new IllegalStateException("Error al inicializar los servos del Intake: " + e.getMessage());
        }
    }

    public class OUT implements Action {
        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket) {
            intakeDER.setPosition(OUTPOS);
            intakeIZQ.setPosition(OUTPOS_IZQ);
            return false;
        }
    }

    public class IN implements Action {
        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket) {
            intakeDER.setPosition(INPOS);
            intakeIZQ.setPosition(INPOS_IZQ);
            return false;
        }
    }

    public class OUTROTOR implements Action {
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

    public class INROTOR implements Action {
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

    public Action dropSample() {
        return new OUT();
    }

    public Action pickSample() {
        return new IN();
    }

    public Action IN_intake() {
        return new INROTOR();
    }

    public Action OUT_intake() {
        return new OUTROTOR();
    }

    public Action STOP_intake() {
        return new STOPROTOR();
    }
}