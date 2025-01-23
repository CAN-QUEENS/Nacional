package org.firstinspires.ftc.teamcode.utils;

public class Constants {
    public static class Intake {
        public static String INTAKE_NAME_DER = "intakeDER";
        public static String INTAKE_NAME_IZQ = "intakeIZQ";
        public static final double INPOS = 0.02;
        public static final double INPOS_IZQ = 0.64;
        public static final double OUTPOS = 0.1;
        public static final double OUTPOS_DER = 0.09;
    }

    public static class Slider {
        public static String SLIDER_NAME = "slider";
        public static double p = 0;
        public static double i = 0;
        public static double d = 0;
        public static double f = 0;

        public static final int MIN_POSITION = 0;
        public static final int MAX_POSITION = 2000;
        public static final int POSITION_TOLERANCE = 10;
        public static int LOW_BASCKET = -1500;
        public static int HIGH_SPECIMEN = -1900;
        public static int LOW_SPECIMEN = -1000;
        public static int SAMPLE = -800;
    }
}