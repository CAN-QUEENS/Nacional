package org.firstinspires.ftc.teamcode.utils;

public class Constants {
    public static class Intake {
        public static String INTAKE_NAME = "intake";
        public static final double IN_POSITION = 1;
        public static final double OUT_POSITION = 0;
    }

    public static class Intake_Slider {
        public static String SLIDER_NAME = "intake_slider";
        public static final double IN_POSITION = 0.51;
        public static final double OUT_POSITION = 0.01;

        public static final double MAX_TURNS = 5.0; // Máximo de vueltas físicas
        public static final double STEP = 0.01; // Tamaño del paso para movimientos lentos
        public static final long STEP_DELAY_MS = 20; // Retraso entre pasos (en milisegundos)
    }

    public static class Elevator {

        public static String ELEVATOR_NAME = "elevator";
        public static double kP = 0.1;
        public static int TOLERANCE = 5;
        public static double MAX_SPEED = 1;
    }

    public static class Claw {
        public static final double PICK_POSITION = 0.2;
        public static final double HOLD_POSITION = 0.3;
        public static final double DROP_POSITION = 0.4;

        public static String CLAW_NAME = "clawServo";
    }


    public static class Outake {
        public static final double IN_POSITION = 0.13;
        public static final double DEFAULT = 0;
        public static final double OUT_POSITION = 0.75;
        public static final double HUMAN_PLAYER = 1.1;

        public static String OUTAKE_NAME = "outake";
    }
    public static class TeleOp {
        public static final double DEFAULT_SLOW_DOWN = 0.8;
        public static final double NORMAL_SPEED = 1;
        public static final double SLOW_SPEED = 0.5;
    }

    public static class Slider {
        public static String SLIDER_NAME = "slider";
        public static double kP = 0.1;
        public static int TOLERANCE = 5;
        public static double MAX_SPEED = 1;
    }
}