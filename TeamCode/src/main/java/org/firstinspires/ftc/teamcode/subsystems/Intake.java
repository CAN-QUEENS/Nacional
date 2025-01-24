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

    public void init(HardwareMap hardwareMap) {
        try {
            intakeDER = hardwareMap.get(Servo.class, INTAKE_NAME_DER);
            intakeIZQ = hardwareMap.get(Servo.class, INTAKE_NAME_IZQ);
        } catch (Exception e) {
            throw new IllegalStateException("Error al inicializar los servos del Intake: " + e.getMessage());
        }
    }

    public class OUT implements Action {

        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket) {
            intakeDER.setPosition(OUTPOS);
            intakeIZQ.setPosition(OUTPOS_DER);
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
    public Action dropSample() {
        return new OUT();
    }
    public Action pickSample() {
        return new IN();
    }
}