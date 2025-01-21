package org.firstinspires.ftc.teamcode.subsystems;

import static org.firstinspires.ftc.teamcode.utils.Constants.Intake.*;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class Intake {
    public Servo intakeSERVO;
    public void init(HardwareMap hardwareMap) {
        intakeSERVO = hardwareMap.get(Servo.class, INTAKE_NAME);
    }
    public class OUTSAMPLE implements Action {
        Servo intakeSERVO;
        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket) {
            intakeSERVO.setPosition(POS_OPEN);
            return false;
        }
    }

    public class INSAMPLE implements Action {
        Servo claw;

        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket) {
            claw.setPosition(POS_CLOSE);
            return false;
        }
    }

    public Action dropSample(){
        return new OUTSAMPLE();
    }

    public Action pickSample(){
        return new INSAMPLE();
    }



   /* class SamplePickColor implements Action {

        int Color;
        double Time;
        ElapsedTime timer;

        public SamplePickColor(int color, double time) {
            this.Color = color;
            this.Time = time;
        }

        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket) {
            if (timer == null) {
                timer = new ElapsedTime();
                etesito.pickSample();
                rodeMotor.setPower(0.05);

            }

            return (Color < 350 || etesito.getColorGreen() < 600) || timer.seconds() < Time;
        }

    }*/
}
