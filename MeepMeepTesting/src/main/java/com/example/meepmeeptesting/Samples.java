package com.example.meepmeeptesting;

import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.noahbres.meepmeep.MeepMeep;
import com.noahbres.meepmeep.roadrunner.DefaultBotBuilder;
import com.noahbres.meepmeep.roadrunner.entity.RoadRunnerBotEntity;

public class Samples{
    public static void main(String[] args) {
        MeepMeep meepMeep = new MeepMeep(900);

        RoadRunnerBotEntity myBot = new DefaultBotBuilder(meepMeep)
                // Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
                .setConstraints(100, 100, Math.toRadians(180), Math.toRadians(180), 18)
                .build();

        myBot.runAction(myBot.getDrive().actionBuilder(new Pose2d(0, -62, Math.PI/2))
                //TODO: FIRST ESPECIMEN
                .strafeTo(new Vector2d(0,-37))
                .waitSeconds(1)
                .setTangent(0)
                .strafeToLinearHeading(new Vector2d(36, -37), Math.toRadians(-270))
                .strafeTo(new Vector2d(36, -12))
                .strafeTo(new Vector2d(47, -12))
                .strafeTo(new Vector2d(47, -48))
                //TODO: ****** FIRST SAMPLE ******
                .strafeTo(new Vector2d(47, -12))
                .strafeTo(new Vector2d(55, -12))
                //TODO: ***** SECOND SAMPLE *****
                .strafeTo(new Vector2d(55, -48))
                .strafeTo(new Vector2d(55, -12))
                .strafeTo(new Vector2d(61, -12))
                //TODO: THIRD SAMPLE
                .strafeTo(new Vector2d(61, -59))
                .waitSeconds(.6)
                //TODO: SECOND ESPECIMEN
                .strafeTo(new Vector2d(0, -37))
                .strafeTo(new Vector2d(0,-37))
                .waitSeconds(1)
                //TODO: OBSERVATION ZONE
                .strafeTo(new Vector2d(40, -59))
                .waitSeconds(1)
                //TODO: THIRD ESPECIMEN
                .strafeTo(new Vector2d(0, -37))
                .strafeTo(new Vector2d(0,-37))
                .waitSeconds(1)
                //TODO: OBSERVATION ZONE
                .strafeTo(new Vector2d(40, -59))
                .waitSeconds(1)
                //TODO: FOURTH ESPECIMEN
                .strafeTo(new Vector2d(0, -37))
                .strafeTo(new Vector2d(0,-37))
                .waitSeconds(1)
                .strafeTo(new Vector2d(36, -55))
                .waitSeconds(1)
                .strafeTo(new Vector2d(0, -37))
                .strafeTo(new Vector2d(0,-37))
                .build());

        meepMeep.setBackground(MeepMeep.Background.FIELD_INTO_THE_DEEP_JUICE_DARK)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)
                .addEntity(myBot)
                .start();
    }
}