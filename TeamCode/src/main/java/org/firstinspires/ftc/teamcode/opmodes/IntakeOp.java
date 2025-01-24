package org.firstinspires.ftc.teamcode.opmodes;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.subsystems.Intake;
import org.firstinspires.ftc.teamcode.subsystems.Slider;


@TeleOp(name = "Servo Control TeleOp with Intake", group = "TeleOp")
public class IntakeOp extends LinearOpMode {

    private Intake intakeSystem; // Declaración del sistema Intake
    private Slider sliderSystem;
    Servo rotor;

    @Override
    public void runOpMode() {
        sliderSystem = new Slider(hardwareMap, telemetry);
        rotor = hardwareMap.get(Servo.class, "rotor");
        intakeSystem = new Intake();
        intakeSystem.init(hardwareMap);
        waitForStart();

        while (opModeIsActive()) {
            TelemetryPacket packet = new TelemetryPacket();

            /*if (gamepad1.b) {
                intakeSystem.pickSample().run(packet);
                telemetry.addLine("OUT");
            }else {
                intakeSystem.dropSample().run(packet);
            }*/
            if(gamepad1.a){
                sliderSystem.HIGH_BASCKET().run(packet);
            }else if(gamepad1.b){
                sliderSystem.PickSAMPLE().run(packet);
            }

            if(gamepad1.x){
                rotor.setPosition(1);
            }else if(gamepad1.y){
                rotor.setPosition(0);
            }

            sliderSystem.update();

            telemetry.update();
        }
    }
}