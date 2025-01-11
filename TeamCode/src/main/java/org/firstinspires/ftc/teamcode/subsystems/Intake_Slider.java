package org.firstinspires.ftc.teamcode.subsystems;

import static org.firstinspires.ftc.teamcode.utils.Constants.Intake_Slider.*;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class Intake_Slider {
    private final Servo servo;

    public Intake_Slider(HardwareMap hardwareMap) {
        servo = hardwareMap.get(Servo.class, SLIDER_NAME);
    }

    public void setTurns(double turns) {
        if (turns < 0 || turns > MAX_TURNS) {
            throw new IllegalArgumentException("Turns must be between 0 and " + MAX_TURNS);
        }
        double position = turns / MAX_TURNS;
        servo.setPosition(position);
    }

    public void moveSlowlyToTurns(double targetTurns) {
        if (targetTurns < 0 || targetTurns > MAX_TURNS) {
            throw new IllegalArgumentException("Turns must be between 0 and " + MAX_TURNS);
        }

        double currentPosition = servo.getPosition();
        double targetPosition = targetTurns / MAX_TURNS;

        double stepDirection = (targetPosition > currentPosition) ? STEP : -STEP;

        while (Math.abs(targetPosition - currentPosition) > STEP) {
            currentPosition += stepDirection;
            servo.setPosition(currentPosition);

            try {
                Thread.sleep(STEP_DELAY_MS);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }

        servo.setPosition(targetPosition);
    }

    public void stop() {
        servo.setPosition(0.5);
    }

    public class OUT implements Action {
        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket) {
            moveSlowlyToTurns(OUT_POSITION);
            return false;
        }
    }

    public Action OUT() {
        return new OUT();
    }

    public class IN implements Action {
        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket) {
            moveSlowlyToTurns(IN_POSITION);
            return false;
        }
    }

    public Action IN() {
        return new IN();
    }
}