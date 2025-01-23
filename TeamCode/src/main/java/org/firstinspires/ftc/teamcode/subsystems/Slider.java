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
    private final double ticksInDegree = 700 / 180.0;

    /**
     * TODO Constructor para inicializar el controlador PID.
     *
     * @param hardwareMap El mapa de hardware del robot.
     * @param telemetry   La instancia de telemetría para depuración.
     */
    public Slider(HardwareMap hardwareMap, Telemetry telemetry) {
        this.controller = new PIDController(p, i, d);
        this.telemetry = telemetry;
        this.motor = hardwareMap.get(DcMotorEx.class, SLIDER_NAME);
        this.telemetry.addLine("PID Controller Initialized for motor: " + SLIDER_NAME);
        FtcDashboard.getInstance().getTelemetry().addLine("Dashboard Connected");
    }

    /**
     *TODO Ajusta el objetivo del slider basado en la entrada del joystick.
     *
     * @param joystickInput Entrada del joystick (-1.0 a 1.0).
     */
    public void setTargetFromJoystick(double joystickInput) {
        double joystickScaled = joystickInput * MAX_POSITION;
        target = (int) Math.round(joystickScaled);
        target = Math.max(MIN_POSITION, Math.min(MAX_POSITION, target));
    }

    public int getCurrentPosition() {
        return motor.getCurrentPosition();
    }

    public void update() {
        controller.setPID(p, i, d);
        int currentPosition = motor.getCurrentPosition();
        double pid = controller.calculate(currentPosition, target);
        double feedForward = Math.cos(Math.toRadians(target / ticksInDegree)) * f;

        double power = pid + feedForward;
        motor.setPower(power);

        telemetry.addData("Motor: ", motor.getDeviceName());
        telemetry.addData("Current Position: ", currentPosition);
        telemetry.addData("Target: ", target);
        telemetry.addData("Power: ", power);
        telemetry.update();
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
    public Action PickS() {
        return new SAMPLE();
    }
}