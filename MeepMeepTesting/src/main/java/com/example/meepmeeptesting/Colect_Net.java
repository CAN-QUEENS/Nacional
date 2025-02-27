package com.example.meepmeeptesting;

import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.noahbres.meepmeep.MeepMeep;
import com.noahbres.meepmeep.roadrunner.DefaultBotBuilder;
import com.noahbres.meepmeep.roadrunner.entity.RoadRunnerBotEntity;

public class Colect_Net{
    public static void main(String[] args) {
        MeepMeep meepMeep = new MeepMeep(900);

        RoadRunnerBotEntity myBot = new DefaultBotBuilder(meepMeep)
                // Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
                .setConstraints(80, 80, Math.toRadians(180), Math.toRadians(180), 15)
                .build();

        myBot.runAction(myBot.getDrive().actionBuilder(new Pose2d(-23, -62, Math.PI/2 ))
                .setTangent(Math.PI / 2)
                .splineToConstantHeading(new Vector2d(-36, -28), Math.PI / 2)
                .strafeTo(new Vector2d(-37, -10))
                .strafeTo(new Vector2d(-47, -10))
                .strafeTo(new Vector2d(-47, -57))
                .strafeTo(new Vector2d(-47, -10))
                .strafeTo(new Vector2d(-55, -10))
                .strafeTo(new Vector2d(-55, -53))
                .strafeTo(new Vector2d(-55, -10))
                .strafeTo(new Vector2d(-63, -10))
                .strafeTo(new Vector2d(-63, -53))
                .setTangent(Math.PI /3)
                .splineToLinearHeading(new Pose2d(-39, -10, Math.PI * 2), Math.PI/2)
                .strafeTo(new Vector2d(-25, -10))
                .build());

        meepMeep.setBackground(MeepMeep.Background.FIELD_INTO_THE_DEEP_JUICE_DARK)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)
                .addEntity(myBot)
                .start();
    }
}