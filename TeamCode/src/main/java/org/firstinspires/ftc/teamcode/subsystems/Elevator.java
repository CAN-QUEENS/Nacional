package org.firstinspires.ftc.teamcode.subsystems;

import static org.firstinspires.ftc.teamcode.utils.Constants.Elevator.*;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Elevator {
    private final DcMotor elevator;

    public enum ElevatorPosition {
        DEFAULT(0), LOW(1000), MEDIUM(1500), HIGH(3000);

        private final int TICKS;

        ElevatorPosition(int TICKS) {
            this.TICKS = TICKS;
        }

        public int getTICKS() {
            return TICKS;
        }
    }

    public Elevator(HardwareMap hardwareMap) {
        elevator = hardwareMap.get(DcMotor.class, ELEVATOR_NAME);

        elevator.setDirection(DcMotorSimple.Direction.REVERSE);
        elevator.setTargetPosition(ElevatorPosition.DEFAULT.getTICKS());
        elevator.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        elevator.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        elevator.setPower(0);
    }

    public void setTargetPosition(ElevatorPosition elevatorPosition) {
        elevator.setTargetPosition(elevatorPosition.getTICKS());
        elevator.setPower(MAX_SPEED);
    }

    public void setElevatorPosition(ElevatorPosition position) {
        setTargetPosition(position);
    }

    public void setPower(double power) {
        elevator.setPower(power);
    }

    public void periodic() {
        if (!atTargetPosition()) {
            elevator.setPower(MAX_SPEED);
        } else {
            stop();
        }
    }

    public boolean atTargetPosition() {
        return Math.abs(elevator.getCurrentPosition() - elevator.getTargetPosition()) <= TOLERANCE;
    }

    public void stop() {
        elevator.setPower(0);
    }

    public class Up implements Action {
        private final ElevatorPosition position;

        public Up(ElevatorPosition position) {
            this.position = position;
        }

        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket) {
            setElevatorPosition(position);
            return false;
        }
    }

    public Action up(ElevatorPosition position) {
        return new Up(position);
    }

    public class Down implements Action {
        private final ElevatorPosition position;

        public Down(ElevatorPosition position) {
            this.position = position;
        }

        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket) {
            setElevatorPosition(position);
            return false;
        }
    }

    public Action down(ElevatorPosition position) {
        return new Down(position);
    }
}