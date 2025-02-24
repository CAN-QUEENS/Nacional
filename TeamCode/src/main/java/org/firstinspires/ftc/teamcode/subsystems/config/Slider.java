package org.firstinspires.ftc.teamcode.subsystems.config;

import static org.firstinspires.ftc.teamcode.utils.Constants.Intake.INTAKE_NAME_DER;
import static org.firstinspires.ftc.teamcode.utils.Constants.Intake.INTAKE_NAME_IZQ;
import androidx.annotation.NonNull;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

@Config
public class Slider{
    private Servo intakeDER;
    private Servo intakeIZQ;

    private double OUTPOS = 0.02;
    private double OUTPOS_IZQ = 0.7;
    private double INPOS = 0.09;
    private double INPOS_IZQ = 0;
    

    public Slider(HardwareMap hardwareMap) {
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


    public Action OUT() {
        return new OUT();
    }

    public Action IN() {
        return new IN();
    }
}