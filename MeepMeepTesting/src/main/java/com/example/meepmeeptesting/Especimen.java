package com.example.meepmeeptesting;

import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.ProfileAccelConstraint;
import com.acmerobotics.roadrunner.TranslationalVelConstraint;
import com.acmerobotics.roadrunner.Vector2d;
import com.noahbres.meepmeep.MeepMeep;
import com.noahbres.meepmeep.roadrunner.DefaultBotBuilder;
import com.noahbres.meepmeep.roadrunner.entity.RoadRunnerBotEntity;

public class Especimen {
    public static void main(String[] args) {
        MeepMeep meepMeep = new MeepMeep(900);

        // Definir restricciones de velocidad y aceleración
        TranslationalVelConstraint HIGH_SPEED = new TranslationalVelConstraint(100);
        ProfileAccelConstraint HIGH_ACCEL = new ProfileAccelConstraint(-100, 100);

        TranslationalVelConstraint MEDIUM_SPEED = new TranslationalVelConstraint(80);
        ProfileAccelConstraint MEDIUM_ACCEL = new ProfileAccelConstraint(-80, 80);

        TranslationalVelConstraint LOW_SPEED = new TranslationalVelConstraint(40);
        ProfileAccelConstraint LOW_ACCEL = new ProfileAccelConstraint(-40, 40);

        RoadRunnerBotEntity myBot = new DefaultBotBuilder(meepMeep)
                .setConstraints(60, 60, Math.toRadians(180), Math.toRadians(180), 15)
                .build();

        myBot.runAction(myBot.getDrive().actionBuilder(new Pose2d(23, -62, -Math.PI / 2))
                .strafeToConstantHeading(new Vector2d(0, -37), MEDIUM_SPEED, MEDIUM_ACCEL)
                .strafeToConstantHeading(new Vector2d(0, -32), MEDIUM_SPEED, MEDIUM_ACCEL)
                .waitSeconds(0.3)
                .strafeToConstantHeading(new Vector2d(0, -37), MEDIUM_SPEED, MEDIUM_ACCEL)

                .strafeToLinearHeading(new Vector2d(36, -37), Math.PI / 2)

                .strafeToConstantHeading(new Vector2d(37, -10), HIGH_SPEED, HIGH_ACCEL)
                .strafeToConstantHeading(new Vector2d(47, -10), HIGH_SPEED, HIGH_ACCEL)
                .strafeToConstantHeading(new Vector2d(47, -53), HIGH_SPEED, HIGH_ACCEL)
                .strafeToConstantHeading(new Vector2d(47, -10), HIGH_SPEED, HIGH_ACCEL)
                .strafeToConstantHeading(new Vector2d(55, -10), HIGH_SPEED, HIGH_ACCEL)
                .strafeToConstantHeading(new Vector2d(55, -53), HIGH_SPEED, HIGH_ACCEL)

                .strafeTo(new Vector2d(47, -55))
                .strafeToConstantHeading(new Vector2d(47, -60), LOW_SPEED, LOW_ACCEL)
                .waitSeconds(0.3)

                .strafeToLinearHeading(new Vector2d(0, -34), -Math.PI / 2)
                .waitSeconds(0.3)
                .strafeToLinearHeading(new Vector2d(47, -60), Math.PI / 2)
                .waitSeconds(0.3)

                .strafeToLinearHeading(new Vector2d(0, -34), -Math.PI / 2)
                .waitSeconds(0.3)
                .strafeToLinearHeading(new Vector2d(47, -60), Math.PI / 2)
                .waitSeconds(0.3)

                .strafeToLinearHeading(new Vector2d(0, -34), -Math.PI / 2)

                .build());

        meepMeep.setBackground(MeepMeep.Background.FIELD_INTO_THE_DEEP_JUICE_DARK)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)
                .addEntity(myBot)
                .start();
    }
}