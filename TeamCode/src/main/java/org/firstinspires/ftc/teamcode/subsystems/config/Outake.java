package org.firstinspires.ftc.teamcode.subsystems.config;

import static org.firstinspires.ftc.teamcode.utils.Constants.Intake.*;
import static org.firstinspires.ftc.teamcode.utils.Constants.Outake.IN;
import static org.firstinspires.ftc.teamcode.utils.Constants.Outake.OUT;
import static org.firstinspires.ftc.teamcode.utils.Constants.Outake.OUTAKE_NAME;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

@Config
public class Outake {
    private Servo s_outake;

    public Outake(HardwareMap hardwareMap) {
        try {
            s_outake = hardwareMap.get(Servo.class, OUTAKE_NAME);
        } catch (Exception e) {
            throw new IllegalStateException("Error al inicializar los servos del Intake: " + e.getMessage());
        }
    }

    public class PICK implements Action {

        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket) {
            s_outake.setPosition(IN);
            return false;
        }
    }

    public class DROP implements Action {

        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket) {
            s_outake.setPosition(OUT);
            return false;
        }
    }


    public Action DROP_SAMPLE() {return new DROP();}

    public Action PICK_SAMPLE() {return new PICK();}

}