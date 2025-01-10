package com.example.meepmeeptesting;

import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.noahbres.meepmeep.MeepMeep;
import com.noahbres.meepmeep.roadrunner.DefaultBotBuilder;
import com.noahbres.meepmeep.roadrunner.entity.RoadRunnerBotEntity;

public class CloseAutoTest {
    public static void main(String[] args) {
        MeepMeep meepMeep = new MeepMeep(800);

        RoadRunnerBotEntity myBot = new DefaultBotBuilder(meepMeep)
                // Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
                .setConstraints(60, 60, Math.toRadians(180), Math.toRadians(180), 17.5)
                .build();

        myBot.runAction(myBot.getDrive().actionBuilder(new Pose2d(-11.8, 61.7, 0))
//                .strafeTo(new Vector2d(-11.8, 36)) //toRung1
//
//                .strafeTo(new Vector2d(-11.8, 30)) //toRung2
//
//                .waitSeconds(2)
//
//                //afterRung
//                .strafeTo(new Vector2d(-11.8, 50))
//                .setTangent(0)
//                .strafeTo(new Vector2d(-38.5, 50))
//                .strafeTo(new Vector2d(-38.5, 10))
//                .strafeTo(new Vector2d(-50, 10))
//                .strafeTo(new Vector2d(-50, 57))
//                .strafeTo(new Vector2d(-50, 10))
//
//                .strafeTo(new Vector2d(-61.5, 10))
//                .strafeTo(new Vector2d(-61.5, 33))
//                .strafeTo(new Vector2d(-50, 57))
//                .strafeTo(new Vector2d(-61.5, 10))
//                .strafeTo(new Vector2d(-65.25, 10))
//                .strafeTo(new Vector2d(-65.25, 33))
//                .strafeTo(new Vector2d(-50, 57))
                //.strafeTo(new Vector2d(-65.25, 57))

                .strafeTo(new Vector2d(-11.8, 36)) //toRung1

                .strafeTo(new Vector2d(-11.8, 30)) //toRung2

                .waitSeconds(0.5)

                //afterRung
                .strafeTo(new Vector2d(-11.8, 50))
                .setTangent(0)
                        .setReversed(true)

                .strafeToLinearHeading(new Vector2d(-50, 10), Math.PI)

                //.strafeTo(new Vector2d(-50, 10))


                .strafeTo(new Vector2d(-50, 57))


                        .setReversed(false)
                // pick up specimen 2
                .strafeTo(new Vector2d(-50, 42))

                .strafeToLinearHeading(new Vector2d(-20, 42), 0)

                .strafeTo(new Vector2d(0, 30))
                // drop specimen 2


                .strafeTo(new Vector2d(-11.8, 50))
                        .setReversed(true)
                .strafeToLinearHeading(new Vector2d(-50, 10), Math.PI)
                                .strafeTo(new Vector2d(-61.5, 10))
                                .strafeTo(new Vector2d(-50, 57))
                // pick up specimen 3
                .setReversed(false)
                .strafeTo(new Vector2d(-50, 42))
                .strafeToLinearHeading(new Vector2d(-20, 42), 0)

                .strafeTo(new Vector2d(5, 30))
                // drop specimen 3

                .strafeTo(new Vector2d(-50, 57)) // park

//                .splineToLinearHeading(new Pose2d(0, 30,Math.PI), Math.PI)
//                .strafeToLinearHeading(new Vector2d(-20, 42), 0)
//                .strafeTo(new Vector2d(-11.8, 30))
//                .strafeTo(new Vector2d(-50, 50))
//
//                .strafeTo(new Vector2d(-50, 10))
//                .strafeTo(new Vector2d(-61.5, 10))
//                .strafeTo(new Vector2d(-61.5, 33))
//
//                .strafeTo(new Vector2d(-50, 57))
//                .strafeTo(new Vector2d(-50, 40)) // watiing for human
//                .strafeTo(new Vector2d(-50, 57))
//
//                .strafeTo(new Vector2d(-11.8, 50))
//                .strafeTo(new Vector2d(-11.8, 30))
//
//                .strafeTo(new Vector2d(-61.5, 10))
//                .strafeTo(new Vector2d(-65.25, 10))
//                .strafeTo(new Vector2d(-65.25, 57))
                .build());

        meepMeep.setBackground(MeepMeep.Background.FIELD_INTO_THE_DEEP_JUICE_DARK)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)
                .addEntity(myBot)
                .start();
    }
}