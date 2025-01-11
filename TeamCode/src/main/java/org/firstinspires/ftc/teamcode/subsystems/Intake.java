package org.firstinspires.ftc.teamcode.subsystems;

import static org.firstinspires.ftc.teamcode.utils.Constants.Intake.INTAKE_NAME;
import static org.firstinspires.ftc.teamcode.utils.Constants.Intake.*;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class Intake {
    private final Servo servo;

    public Intake(HardwareMap hardwareMap) {
        servo = hardwareMap.get(Servo.class, INTAKE_NAME);
    }

    public void setPower(double power) {
        servo.setPosition(power);
    }

    public void stop() {
        servo.setPosition(0.5);
    }

    public class OUT implements Action {
        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket) {
            try {
                setPower(OUT_POSITION);
                Thread.sleep(1000);
                stop();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            return true;
        }
    }

    public Action OUT() {
        return new OUT();
    }

    public class IN implements Action {
        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket) {
            try {
                setPower(IN_POSITION);
                Thread.sleep(1000);
                stop();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            return true;
        }
    }

    public Action IN() {
        return new IN();
    }
}