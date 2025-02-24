package org.firstinspires.ftc.teamcode.utils;

public class Constants {
    public static class Intake {
        public static String INTAKE_NAME_DER = "intakeDER";
        public static String INTAKE_NAME_IZQ = "intakeIZQ";
        public static String ROTOR_NAME = "rotor";
        public static final double INPOS = 0;
        public static final double OUTPOS = 0.08;
        public static final double INPOS_IZQ = 0.38;
        public static final double OUTPOS_IZQ = 0.01;
    }

    public static class Outake {
        public static String OUTAKE_NAME = "outake";
        public static double IN = 0.22;
        public static int OUT = 1;
    }

    public static class Slider {
        public static String SLIDER_NAME = "slider";
        public static double p = 0.004;
        public static double i = 0;
        public static double d = 0.0001;
        public static double f = 0.1;
        public static final int POSITION_TOLERANCE = 10;
        public static int SAMPLE = 0;

        public static int NO_FLOOR = 800;
        public static int MEDIUM = 600;
        public static int HIGH_CHAMBER = 180;
    }

    public static class Barras {
        public static String BARRAS_NAME = "barras";

        public static double p = 0.004;
        public static double i = 0;
        public static double d = 0.0001;
        public static double f = 0.1;
        public static final int POSITION_TOLERANCE = 10;
        public static int ESPECIMEN = -5800;
        public static int PUT_ESPECIMEN = -2000;
        public static int HUMAN = -100;
    }

    public static class Sensor_color {
        public static String SLIDER_NAME = "sensor";
    }
}