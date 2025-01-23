package org.firstinspires.ftc.teamcode.subsystems;

import static org.firstinspires.ftc.teamcode.utils.Constants.Intake.*;
import androidx.annotation.NonNull;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class Intake {
    public Servo intakeDER;
    public Servo intakeIZQ; // Segundo servo

    public void init(HardwareMap hardwareMap) {
        intakeDER = hardwareMap.get(Servo.class, INTAKE_NAME_DER);
        intakeIZQ = hardwareMap.get(Servo.class, INTAKE_NAME_IZQ);
    }

    public class OUT implements Action {
        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket) {
            if (intakeDER != null && intakeIZQ != null) {
                intakeDER.setPosition(OUTPOS);
                intakeIZQ.setPosition(OUTPOS_DER);
                telemetryPacket.put("Servo 1 Position", "OPEN");
                telemetryPacket.put("Servo 2 Position", "OPEN");
            } else {
                telemetryPacket.put("Error", "One or both servos are null");
            }
            return false;
        }
    }

    public class IN implements Action {
        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket) {
            if (intakeDER != null && intakeIZQ != null) {
                intakeDER.setPosition(INPOS);
                intakeIZQ.setPosition(INPOS_IZQ);
                telemetryPacket.put("Servo 1 Position", "CLOSE");
                telemetryPacket.put("Servo 2 Position", "CLOSE");
            } else {
                telemetryPacket.put("Error", "One or both servos are null");
            }
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