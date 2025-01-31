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
    public static int target = 0; // Target position in ticks
    private final double ticksInDegree = 700 / 180.0;

    public static double maxPower = 0.5; // Maximum power for the motor
    public static double minPower = 0.1; // Minimum power to prevent stalling
    public static double slowdownThreshold = 500; // Distance in ticks to start slowing down

    public Slider(HardwareMap hardwareMap, Telemetry telemetry) {
        this.controller = new PIDController(p, i, d);
        this.telemetry = telemetry;
        this.motor = hardwareMap.get(DcMotorEx.class, SLIDER_NAME);

        // Reset encoder and set initial target position
        this.motor.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
        this.motor.setTargetPosition(0); // Set an initial target position (e.g., home position)
        this.motor.setMode(DcMotorEx.RunMode.RUN_TO_POSITION); // Now switch to RUN_TO_POSITION mode
        this.motor.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);

        FtcDashboard.getInstance().getTelemetry().addLine("Dashboard Connected");
    }

    public int getCurrentPosition() {
        return motor.getCurrentPosition();
    }

    public void update() {
        // Update the PID coefficients
        controller.setPID(p, i, d);

        int currentPosition = motor.getCurrentPosition();

        // Calculate PID and feed-forward
        double pid = controller.calculate(currentPosition, target);
        double feedForward = Math.cos(Math.toRadians(target / ticksInDegree)) * f;

        // Calculate power dynamically based on the distance to the target
        double distance = Math.abs(target - currentPosition);
        double scaledPower;
        if (distance > slowdownThreshold) {
            scaledPower = maxPower; // Full power if far from the target
        } else {
            scaledPower = minPower + (maxPower - minPower) * (distance / slowdownThreshold); // Linearly scale power
        }

        double power = Math.max(-scaledPower, Math.min(scaledPower, pid + feedForward)); // Limit power to the scaled value

        // Ensure the motor has a valid target position
        motor.setTargetPosition(target);

        // Set motor power
        motor.setPower(power);

        // Telemetry for debugging
        telemetry.addData("Current Position", currentPosition);
        telemetry.addData("Target", target);
        telemetry.addData("Power", power);
        telemetry.addData("Distance to Target", distance);
        telemetry.update();
    }

    // Actions for specific targets
    public class UpdateAction implements Action {
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
        return new UpdateAction();
    }

    public class TargetAction implements Action {
        private final int actionTarget;

        public TargetAction(int actionTarget) {
            this.actionTarget = actionTarget;
        }

        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket) {
            // Update the target position
            target = actionTarget;
            motor.setTargetPosition(actionTarget);

            // Perform an update
            update();

            // Send telemetry data
            telemetryPacket.put("Target", actionTarget);
            telemetryPacket.put("Current Position", getCurrentPosition());

            // Return true if the motor is close enough to the target
            return Math.abs(getCurrentPosition() - actionTarget) < POSITION_TOLERANCE;
        }
    }

    public Action PickSAMPLE() {
        return new TargetAction(SAMPLE);
    }

    public Action NoFloor() {
        return new TargetAction(NO_FLOOR);
    }

    public Action Medium() {
        return new TargetAction(MEDIUM);
    }


    public Action high_CHAMBER() {
        return new TargetAction(HIGH_CHAMBER);
    }
}