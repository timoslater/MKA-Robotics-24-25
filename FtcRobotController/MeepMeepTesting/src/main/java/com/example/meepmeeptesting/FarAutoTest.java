package com.example.meepmeeptesting;

import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.noahbres.meepmeep.MeepMeep;
import com.noahbres.meepmeep.roadrunner.DefaultBotBuilder;
import com.noahbres.meepmeep.roadrunner.entity.RoadRunnerBotEntity;

public class FarAutoTest {
    public static void main(String[] args) {
        MeepMeep meepMeep = new MeepMeep(800);

        RoadRunnerBotEntity myBot = new DefaultBotBuilder(meepMeep)
                // Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
                .setConstraints(60, 60, Math.toRadians(180), Math.toRadians(180), 17.5)
                .build();

        myBot.runAction(myBot.getDrive().actionBuilder(new Pose2d(35.8, 61.7, Math.PI))
                .strafeTo(new Vector2d(12, 36)) //toRung1
                .strafeTo(new Vector2d(12, 30)) //toRung2
                .waitSeconds(1.5)

                //afterRung1
                .strafeTo(new Vector2d(12, 36))
                .strafeTo(new Vector2d(40, 36))//6200
                .turnTo(Math.PI-0.001)
                .strafeTo(new Vector2d(40, 10))
                .strafeTo(new Vector2d(28, 10))

                //afterRung2
                //.strafeTo(new Vector2d(28, 10))


                //extend slide
                .build());

        meepMeep.setBackground(MeepMeep.Background.FIELD_INTO_THE_DEEP_JUICE_DARK)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)
                .addEntity(myBot)
                .start();
    }
}
