package org.firstinspires.ftc.teamcode.subsystems;

import static org.firstinspires.ftc.teamcode.utils.Constants.Claw.*;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class Claw {

    private final Servo claw;

    public Claw(HardwareMap hardwareMap) {
        claw = hardwareMap.get(Servo.class, CLAW_NAME);
    }

    public class Pick implements Action {
        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket) {

            claw.setPosition(PICK_POSITION);
            return false;
        }
    }
    public Action pick(){
        return new Pick();

    }

    public class Hold implements Action {
        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket) {

            claw.setPosition(HOLD_POSITION);
            return false;
        }
    }
    public Action hold(){
        return new Pick();

    }

    public class Drop implements Action {
        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket) {

            claw.setPosition(DROP_POSITION);
            return false;
        }
    }
    public Action drop(){
        return new Pick();

    }

    public void Pick() {
        claw.setPosition(PICK_POSITION);
    }

    public void Hold() {
        claw.setPosition(HOLD_POSITION);
    }

    public void Drop() {
        claw.setPosition(DROP_POSITION);
    }
}