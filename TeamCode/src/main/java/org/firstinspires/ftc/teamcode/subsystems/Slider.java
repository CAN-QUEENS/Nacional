package org.firstinspires.ftc.teamcode.subsystems;

import static org.firstinspires.ftc.teamcode.utils.Constants.Slider.*;
import androidx.annotation.NonNull;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.ParallelAction;
import com.acmerobotics.roadrunner.SequentialAction;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;
import org.firstinspires.ftc.teamcode.utils.PID;

public class Slider {
    private DcMotorEx slider;

    public int SliderTarget;
    private PID sliderController;

    public Slider(HardwareMap hardwareMap) {
        slider = hardwareMap.get(DcMotorEx.class, SLIDER_NAME);
        slider.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        slider.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        slider.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        sliderController = new PID(new PID.PIDCoefficients(0.0015, 0, 0.0017));
    }

    public void setSliderTarget(int position) {
        SliderTarget = Range.clip(position, SLIDER_MIN_POSITION, SLIDER_MAX_POSITION);
    }

    public void updateSlider() {
        sliderController.targetPosition = Range.clip(SliderTarget, SLIDER_MIN_POSITION, SLIDER_MAX_POSITION);
        slider.setPower(-sliderController.update(slider.getCurrentPosition()) * 0.4);
    }

    class SliderToPos implements Action {
        int ticks;

        public SliderToPos(int ticks) {
            this.ticks = ticks;
        }

        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket) {
            setSliderTarget(ticks);
            return slider.getCurrentPosition() == ticks; // Espera a que llegue a la posición deseada
        }
    }

    class SliderToPosSmooth implements Action {
        int ticks;
        int currentTicks;
        double timeSeconds;

        ElapsedTime timer = null;

        public SliderToPosSmooth(int ticks, double timeSeconds) {
            this.ticks = ticks;
            this.timeSeconds = timeSeconds;
        }

        private double lerp(double start, double end, double t) {
            return start * (1 - t) + end * t;
        }

        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket) {
            if (timer == null) {
                timer = new ElapsedTime();
                currentTicks = slider.getCurrentPosition();
            }
            double t = Range.clip(timer.seconds() / timeSeconds, 0, 1);
            SliderTarget = (int) lerp(currentTicks, ticks, t);
            updateSlider();
            return timer.seconds() <= timeSeconds;
        }
    }

    class SliderUpdate implements Action {
        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket) {
            updateSlider();
            return true;
        }
    }

    public Action sliderUp() {
        return new SequentialAction(
                new ParallelAction(
                        new SliderToPos(SLIDER_MAX_POSITION - 50)
                )
        );
    }

    public Action sliderDown() {
        return new SequentialAction(
                new ParallelAction(
                        new SliderToPos(SLIDER_MIN_POSITION + 50)
                )
        );
    }

    public Action armUpdate() {
        return new SliderUpdate();
    }

    // Las funciones sliderSample, sliderHighBascket, etc., ahora devuelven un Action de la siguiente manera:

    public Action sliderSample() {
        setSliderTarget(SAMPLE);
        return new SliderToPos(SAMPLE); // Acción que mueve el slider a SAMPLE
    }

    public Action sliderHighBascket() {
        setSliderTarget(HIGH_BASCKET);
        return new SliderToPos(HIGH_BASCKET); // Acción que mueve el slider al cesto alto
    }

    public Action sliderLowBascket() {
        setSliderTarget(LOW_BASCKET);
        return new SliderToPos(LOW_BASCKET); // Acción que mueve el slider al cesto bajo
    }

    public Action sliderHighSpecimen() {
        setSliderTarget(HIGH_SPECIMEN);
        return new SliderToPos(HIGH_SPECIMEN); // Acción que mueve el slider al espécimen alto
    }

    public Action sliderLowSpecimen() {
        setSliderTarget(LOW_SPECIMEN);
        return new SliderToPos(LOW_SPECIMEN); // Acción que mueve el slider al espécimen bajo
    }
}