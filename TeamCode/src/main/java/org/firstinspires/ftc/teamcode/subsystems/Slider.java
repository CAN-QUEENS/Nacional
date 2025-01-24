package org.firstinspires.ftc.teamcode.subsystems;

import static org.firstinspires.ftc.teamcode.utils.Constants.Slider.*;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.arcrobotics.ftclib.controller.PIDController;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import org.firstinspires.ftc.robotcore.external.Telemetry;

@Config
public class Slider {

    private final PIDController controller;
    private final DcMotorEx motor;
    private final Telemetry telemetry;
    public static int target = 0;
    private final double ticksInDegree = 700 / 180.0; // Conversión de ticks a grados.

    public Slider(HardwareMap hardwareMap, Telemetry telemetry) {
        this.controller = new PIDController(p, i, d); // Configuración inicial del PID
        this.telemetry = telemetry;
        this.motor = hardwareMap.get(DcMotorEx.class, SLIDER_NAME);
        this.telemetry.addLine("PID Controller Initialized for motor: " + SLIDER_NAME);
        FtcDashboard.getInstance().getTelemetry().addLine("Dashboard Connected");
    }

    public void setTargetFromJoystick(double joystickInput) {
        double joystickScaled = joystickInput * MAX_POSITION;
        target = (int) Math.round(joystickScaled);
        target = Math.max(MIN_POSITION, Math.min(MAX_POSITION, target));
    }

    public int getCurrentPosition() {
        return motor.getCurrentPosition();
    }

    /**
     * Actualiza la lógica del PID y controla el motor.
     */
    public void update() {
        // Actualizar valores del PID
        controller.setPID(p, i, d);

        // Obtener posición actual del motor
        int currentPosition = motor.getCurrentPosition();

        // Calcular el valor del PID
        double pid = controller.calculate(currentPosition, target);

        // Calcular el término de feedforward (FF)
        double feedForward = Math.cos(Math.toRadians(target / ticksInDegree)) * f;

        // Calcular la potencia total a aplicar
        double power = pid + feedForward;

        // Establecer la potencia en el motor
        motor.setPower(power);

        // Telemetría
        telemetry.addData("Motor: ", motor.getDeviceName());
        telemetry.addData("Current Position: ", currentPosition);
        telemetry.addData("Target: ", target);
        telemetry.addData("PID Output: ", pid);
        telemetry.addData("Feedforward: ", feedForward);
        telemetry.addData("Power: ", power);
        telemetry.update();
    }

    //TODO ****** ACTION FOR UPDATE ******
    public class UPDATE implements Action {
        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket) {
            update();
            telemetryPacket.put("Target", target);
            telemetryPacket.put("Current Position", getCurrentPosition());
            telemetryPacket.put("Power", motor.getPower());
            return false;
        }
    }

    public Action SliderUpdate() {
        return new UPDATE();
    }

    //TODO ****** ACTION FOR SAMPLE ******
    public class SAMPLE implements Action {
        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket) {
            target = SAMPLE;
            update();
            telemetryPacket.put("Target", SAMPLE);
            telemetryPacket.put("Current Position", getCurrentPosition());
            return Math.abs(getCurrentPosition() - SAMPLE) < POSITION_TOLERANCE;
        }
    }

    public Action PickSAMPLE() {
        return new SAMPLE();
    }

    //TODO ****** ACTION FOR HUMAN-PLAYER ******
    public class HUMAN implements Action {
        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket) {
            target = HUMAN;
            update();
            telemetryPacket.put("Target", HUMAN);
            telemetryPacket.put("Current Position", getCurrentPosition());
            return Math.abs(getCurrentPosition() - HUMAN) < POSITION_TOLERANCE;
        }
    }

    public Action HumanSample() {
        return new HUMAN();
    }

    //TODO ****** ACTION FOR HIGH CHAMBER ******
    public class HighCHAMBER implements Action {
        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket) {
            target = HIGH_SPECIMEN;
            update();
            telemetryPacket.put("Target", HIGH_SPECIMEN);
            telemetryPacket.put("Current Position", getCurrentPosition());
            return Math.abs(getCurrentPosition() - HIGH_SPECIMEN) < POSITION_TOLERANCE;
        }
    }

    public Action HIGH_CHAMBER() {
        return new HighCHAMBER();
    }

    //TODO ****** ACTION FOR LOW CHAMBER ******
    public class LowCHAMBER implements Action {
        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket) {
            target = LOW_SPECIMEN;
            update();
            telemetryPacket.put("Target", LOW_SPECIMEN);
            telemetryPacket.put("Current Position", getCurrentPosition());
            return Math.abs(getCurrentPosition() - LOW_SPECIMEN) < POSITION_TOLERANCE;
        }
    }

    public Action LOW_SPECIMEN() {
        return new LowCHAMBER();
    }

    //TODO ****** ACTION FOR HIGH BASKET ******
    public class HighBASCKET implements Action {
        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket) {
            target = HIGH_BASCKET;
            update();
            telemetryPacket.put("Target", HIGH_BASCKET);
            telemetryPacket.put("Current Position", getCurrentPosition());
            return Math.abs(getCurrentPosition() - HIGH_BASCKET) < POSITION_TOLERANCE;
        }
    }

    public Action HIGH_BASCKET() {
        return new HighBASCKET();
    }

    //TODO ****** ACTION FOR LOW BASKET ******
    public class LowBASCKET implements Action {
        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket) {
            target = LOW_BASCKET;
            update();
            telemetryPacket.put("Target", LOW_BASCKET);
            telemetryPacket.put("Current Position", getCurrentPosition());
            return Math.abs(getCurrentPosition() - LOW_BASCKET) < POSITION_TOLERANCE;
        }
    }

    public Action LOW_BASCKET() {
        return new LowBASCKET();
    }
}