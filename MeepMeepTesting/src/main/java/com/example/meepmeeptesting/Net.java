package com.example.meepmeeptesting;

import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.noahbres.meepmeep.MeepMeep;
import com.noahbres.meepmeep.roadrunner.DefaultBotBuilder;
import com.noahbres.meepmeep.roadrunner.entity.RoadRunnerBotEntity;

public class Net{
    public static void main(String[] args) {
        MeepMeep meepMeep = new MeepMeep(900);

        RoadRunnerBotEntity myBot = new DefaultBotBuilder(meepMeep)
                // Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
                .setConstraints(80, 80, Math.toRadians(180), Math.toRadians(180), 18)
                .build();

        myBot.runAction(myBot.getDrive().actionBuilder(new Pose2d(-23, -61, Math.PI/2))
                //TODO: ****** NET *****
                .setTangent(0)
                .strafeToLinearHeading(new Vector2d(-52, -50), Math.toRadians(226))
                .waitSeconds(1)
                .setTangent(0)
                // TODO: FIRST SAMPLE
                .strafeToLinearHeading(new Vector2d(-47, -39), Math.toRadians(-270))
                .waitSeconds(.6)
                .setTangent(0)
                // TODO: ***** NET *****
                .strafeToLinearHeading(new Vector2d(-52, -50), Math.toRadians(226))
                .waitSeconds(1)
                .setTangent(0)
                //TODO: SECOND SAMPLE
                .strafeToLinearHeading(new Vector2d(-57, -39), Math.toRadians(-270))
                .waitSeconds(.6)
                .setTangent(0)
                // TODO: ***** NET *****
                .strafeToLinearHeading(new Vector2d(-52, -50), Math.toRadians(226))
                .waitSeconds(1)
                .setTangent(Math.PI * 2 )
                //TODO: THIRD SAMPLE
                .strafeToLinearHeading(new Vector2d(-50, -24), Math.toRadians(180))
                .waitSeconds(.6)
                .setTangent(0)
                // TODO: ***** NET *****
                .strafeToLinearHeading(new Vector2d(-52, -50), Math.toRadians(226))
                .waitSeconds(1)
                .setTangent(Math.PI /3)
                .splineToLinearHeading(new Pose2d(-39, -10, Math.PI * 2), Math.PI/2)
                .strafeTo(new Vector2d(-27, -10))
                .build());

        meepMeep.setBackground(MeepMeep.Background.FIELD_INTO_THE_DEEP_JUICE_DARK)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)
                .addEntity(myBot)
                .start();
    }
}