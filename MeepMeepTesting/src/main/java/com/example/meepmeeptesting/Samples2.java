package com.example.meepmeeptesting;

import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.noahbres.meepmeep.MeepMeep;
import com.noahbres.meepmeep.roadrunner.DefaultBotBuilder;
import com.noahbres.meepmeep.roadrunner.entity.RoadRunnerBotEntity;

public class Samples2{
    public static void main(String[] args) {
        MeepMeep meepMeep = new MeepMeep(900);

        RoadRunnerBotEntity myBot = new DefaultBotBuilder(meepMeep)
                // Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
                .setConstraints(100, 100, Math.toRadians(180), Math.toRadians(180), 18)
                .build();

        myBot.runAction(myBot.getDrive().actionBuilder(new Pose2d(23, -62, Math.PI/2))
                //TODO: FIRST ESPECIMEN
                .strafeTo(new Vector2d(0,-37))
                .waitSeconds(.8)
                .strafeTo(new Vector2d(36, -37))
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
                .strafeTo(new Vector2d(61, -48))
                .setTangent(0)
                .strafeToLinearHeading(new Vector2d(39, -51), Math.toRadians(270))
                .waitSeconds(.8)
                //TODO: SECOND ESPECIMEN
                .setTangent(0)
                .strafeToLinearHeading(new Vector2d(0, -37), Math.toRadians(-270))
                .waitSeconds(.8)
                //TODO: OBSERVATION ZONE
                .setTangent(0)
                .strafeToLinearHeading(new Vector2d(39, -51), Math.toRadians(270))
                .waitSeconds(.8)
                //TODO: THIRD ESPECIMEN
                .setTangent(0)
                .strafeToLinearHeading(new Vector2d(0, -37), Math.toRadians(-270))
                .waitSeconds(.8)
                //TODO: OBSERVATION ZONE
                .setTangent(0)
                .strafeToLinearHeading(new Vector2d(39, -51), Math.toRadians(270))
                .waitSeconds(.8)
                //TODO: FOURTH ESPECIMEN
                .setTangent(0)
                .strafeToLinearHeading(new Vector2d(0, -37), Math.toRadians(-270))
                .waitSeconds(.8)
                //TODO: OBSERVATION ZONE
                .setTangent(0)
                .strafeToLinearHeading(new Vector2d(39, -51), Math.toRadians(270))
                .waitSeconds(.8)
                //TODO: FIFTH SPECIMEN
                .setTangent(0)
                .strafeToLinearHeading(new Vector2d(0, -37), Math.toRadians(-270))
                /*.waitSeconds(.6)
                //TODO: OBSERVATION ZONE
                .setTangent(0)
                .strafeToLinearHeading(new Vector2d(39, -51), Math.toRadians(270))*/
                .build());

        meepMeep.setBackground(MeepMeep.Background.FIELD_INTO_THE_DEEP_JUICE_DARK)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)
                .addEntity(myBot)
                .start();
    }
}