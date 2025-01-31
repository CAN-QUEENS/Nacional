package com.example.meepmeeptesting;

import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.ProfileAccelConstraint;
import com.acmerobotics.roadrunner.TranslationalVelConstraint;
import com.acmerobotics.roadrunner.Vector2d;
import com.noahbres.meepmeep.MeepMeep;
import com.noahbres.meepmeep.roadrunner.DefaultBotBuilder;
import com.noahbres.meepmeep.roadrunner.entity.RoadRunnerBotEntity;

public class Colect_Human {
    public static void main(String[] args) {
        MeepMeep meepMeep = new MeepMeep(900);

        RoadRunnerBotEntity myBot = new DefaultBotBuilder(meepMeep)
                // Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
                .setConstraints(60, 60, Math.toRadians(180), Math.toRadians(180), 15)
                .build();

        myBot.runAction(myBot.getDrive().actionBuilder(new Pose2d(23, -50, Math.PI/2 ))
                //.strafeTo(new Vector2d(-0, -46))
                //.splineToLinearHeading(new Pose2d(-50.5,-49.5, Math.toRadians(90)), Math.toRadians(180))
                //.strafeToConstantHeading(new Vector2d(-51,-40),new TranslationalVelConstraint(20),new ProfileAccelConstraint(-10,10))
                //.splineToLinearHeading(new Pose2d(-59.5,-49.5, Math.toRadians(90)), Math.toRadians(180), new TranslationalVelConstraint(20))
                .setTangent(Math.PI / 2)
                .splineToConstantHeading(new Vector2d(36, -28), Math.PI / 2)
                .strafeTo(new Vector2d(37, -10))
                .strafeTo(new Vector2d(47, -10))
                .strafeTo(new Vector2d(47, -53))
                .strafeTo(new Vector2d(47, -10))
                .strafeTo(new Vector2d(55, -10))
                .strafeTo(new Vector2d(55, -53))
                .strafeTo(new Vector2d(55, -10))
                .strafeTo(new Vector2d(61, -10))
                .strafeTo(new Vector2d(61, -53))
                .build());

        meepMeep.setBackground(MeepMeep.Background.FIELD_INTO_THE_DEEP_JUICE_DARK)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)
                .addEntity(myBot)
                .start();
    }
}