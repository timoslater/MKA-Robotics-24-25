package com.example.meepmeeptesting;

import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.noahbres.meepmeep.MeepMeep;
import com.noahbres.meepmeep.roadrunner.DefaultBotBuilder;
import com.noahbres.meepmeep.roadrunner.entity.RoadRunnerBotEntity;

public class BlueCloseTest {
    public static void main(String[] args) {
        MeepMeep meepMeep = new MeepMeep(800);

        RoadRunnerBotEntity myBot = new DefaultBotBuilder(meepMeep)
                // Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
                .setConstraints(60, 60, Math.toRadians(180), Math.toRadians(180), 17.5)
                .build();
        // CHANGE TRACK WIDTH TO MATCH

        myBot.runAction(myBot.getDrive().actionBuilder(new Pose2d(-11.8, 30, 0))
                .strafeTo(new Vector2d(-11.8, 50))
                .setTangent(0)
                .strafeTo(new Vector2d(-37.5, 50))
                .strafeTo(new Vector2d(-37.5, 10))
                .strafeTo(new Vector2d(-48.5, 10))
                .strafeTo(new Vector2d(-48.5, 57))
                .strafeTo(new Vector2d(-48.5, 10))
                .strafeTo(new Vector2d(-62, 10))
                .strafeTo(new Vector2d(-62, 57))
                .strafeTo(new Vector2d(-62, 10))
                .strafeTo(new Vector2d(-65, 10))
                .strafeTo(new Vector2d(-65, 57))
                        .build());

        meepMeep.setBackground(MeepMeep.Background.FIELD_INTO_THE_DEEP_JUICE_DARK)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)
                .addEntity(myBot)
                .start();
    }
}