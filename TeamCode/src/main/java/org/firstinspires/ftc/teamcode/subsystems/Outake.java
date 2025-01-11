package org.firstinspires.ftc.teamcode.subsystems;

import static org.firstinspires.ftc.teamcode.utils.Constants.Outake.*;

import androidx.annotation.NonNull;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class Outake {

    private final Servo outake;
    private double currentPosition = 0;
    private double speed = 0.01;

    public Outake(HardwareMap hardwareMap) {
        outake = hardwareMap.get(Servo.class, OUTAKE_NAME);
        outake.setDirection(Servo.Direction.REVERSE);
        currentPosition = outake.getPosition();
    }

    public void moveToPosition(double targetPosition) {
        while (Math.abs(currentPosition - targetPosition) > 0.01) {
            if (currentPosition < targetPosition) {
                currentPosition += speed;
            } else if (currentPosition > targetPosition) {
                currentPosition -= speed;
            }
            outake.setPosition(currentPosition);
        }
        currentPosition = targetPosition;
    }

    public void holdPosition() {
        outake.setPosition(currentPosition);
    }

    public class Dunk implements Action {
        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket) {
            moveToPosition(IN_POSITION);
            return false;
        }
    }

    public Action Dunk() {
        return new Dunk();
    }

    public class NoDuck implements Action {
        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket) {
            moveToPosition(OUT_POSITION);
            return false;
        }
    }

    public Action NoDuck() {
        return new NoDuck();
    }


    public class Human implements Action {
        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket) {
            moveToPosition(HUMAN_PLAYER);
            return false;
        }
    }

    public Action Human() {
        return new Human();
    }

    public void moveToDunkPosition() {
        moveToPosition(IN_POSITION);
    }

    public void moveToNoDuckPosition() {
        moveToPosition(OUT_POSITION);
    }

    public void moveToHumanPosition() {
        moveToPosition(HUMAN_PLAYER);
    }

    public double getPosition() {
        return outake.getPosition();
    }

    public double setPosition(double position) {
        outake.setPosition(position);
        return outake.getPosition();
    }

    public void setSpeed(double newSpeed) {
        if (newSpeed >= 0 && newSpeed <= 1) {
            this.speed = newSpeed;
        }
    }
}