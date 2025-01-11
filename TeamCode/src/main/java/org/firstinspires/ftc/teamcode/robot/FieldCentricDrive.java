package org.firstinspires.ftc.teamcode.robot;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.IMU;
import com.qualcomm.robotcore.hardware.HardwareMap;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;

public class FieldCentricDrive {
    private DcMotorEx leftFront, leftBack, rightFront, rightBack;
    private IMU imu;

    public FieldCentricDrive(HardwareMap hardwareMap) {
        leftFront = hardwareMap.get(DcMotorEx.class, "front_left");
        leftBack = hardwareMap.get(DcMotorEx.class, "back_left");
        rightFront = hardwareMap.get(DcMotorEx.class, "front_right");
        rightBack = hardwareMap.get(DcMotorEx.class, "back_right");

        leftFront.setDirection(DcMotor.Direction.REVERSE);
        leftBack.setDirection(DcMotor.Direction.REVERSE);

        imu = hardwareMap.get(IMU.class, "imu");
    }

    public void moveRobot(double x, double y, double rx) {
        double botHeading = imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.RADIANS);

        double rotX = x * Math.cos(-botHeading) - y * Math.sin(-botHeading);
        double rotY = x * Math.sin(-botHeading) + y * Math.cos(-botHeading);

        double denominator = Math.max(Math.abs(rotY) + Math.abs(rotX) + Math.abs(rx), 1);
        leftFront.setPower((rotY + rotX + rx) / denominator);
        leftBack.setPower((rotY - rotX + rx) / denominator);
        rightFront.setPower((rotY - rotX - rx) / denominator);
        rightBack.setPower((rotY + rotX - rx) / denominator);
    }

    public void resetYaw (){
        imu.resetYaw();
    }
}