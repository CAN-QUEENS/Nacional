package org.firstinspires.ftc.teamcode.subsystems;

import static org.firstinspires.ftc.teamcode.utils.Constants.Slider.*;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Slider {
    private final DcMotor slider;

    public enum SliderPosition {
        DEFAULT(0), OUT(740);

        private final int TICKS;

        SliderPosition(int TICKS) {
            this.TICKS = TICKS;
        }

        public int getTICKS() {
            return TICKS;
        }
    }

    public Slider(HardwareMap hardwareMap) {
        slider = hardwareMap.get(DcMotor.class, SLIDER_NAME);

        slider.setDirection(DcMotorSimple.Direction.REVERSE);
        slider.setTargetPosition(SliderPosition.DEFAULT.getTICKS());
        slider.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        slider.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        slider.setPower(0);
    }

    public void setTargetPosition(SliderPosition sliderPosition) {
        slider.setTargetPosition(sliderPosition.getTICKS());
        slider.setPower(MAX_SPEED);
    }

    public void setSliderPosition(SliderPosition position) {
        setTargetPosition(position);
    }

    public void periodic() {
        if (!atTargetPosition()) {
            slider.setPower(MAX_SPEED);
        } else {
            stop();
        }
    }

    public boolean atTargetPosition() {
        return Math.abs(slider.getCurrentPosition() - slider.getTargetPosition()) <= TOLERANCE;
    }

    public void stop() {
        slider.setPower(0);
    }

    public class OUT implements Action {
        private final SliderPosition position;

        public OUT(SliderPosition position) {
            this.position = position;
        }

        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket) {
            setSliderPosition(position);
            return false;
        }
    }

    public Action out(SliderPosition position) {
        return new OUT(position);
    }

    public class IN implements Action {
        private final SliderPosition position;

        public IN(SliderPosition position) {
            this.position = position;
        }

        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket) {
            setSliderPosition(position);
            return false;
        }
    }

    public Action in(SliderPosition position) {
        return new IN(position);
    }
    public void setPowerWithJoystick(double joystickValue) {
        slider.setPower(joystickValue);
    }
}