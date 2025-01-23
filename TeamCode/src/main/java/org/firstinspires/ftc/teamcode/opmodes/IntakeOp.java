package org.firstinspires.ftc.teamcode.opmodes;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.subsystems.Intake;


@TeleOp(name = "Servo Control TeleOp with Intake", group = "TeleOp")
public class IntakeOp extends LinearOpMode {

    private Intake intakeSystem; // Declaración del sistema Intake

    @Override
    public void runOpMode() {
        // Inicializamos el sistema Intake
        intakeSystem = new Intake();
        intakeSystem.init(hardwareMap); // Mapeamos el hardware

        // Esperamos a que inicie el TeleOp
        waitForStart();

        while (opModeIsActive()) {
            TelemetryPacket packet = new TelemetryPacket();

            if (gamepad1.b) {
                intakeSystem.pickSample().run(packet);
                telemetry.addLine("OUT");
            }else {
                intakeSystem.dropSample().run(packet);
            }


            telemetry.update();
        }
    }
}