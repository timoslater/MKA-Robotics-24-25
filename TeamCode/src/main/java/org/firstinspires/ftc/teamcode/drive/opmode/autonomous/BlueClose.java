package org.firstinspires.ftc.teamcode.drive.opmode.autonomous;
import androidx.annotation.NonNull;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.ParallelAction;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.Rotation2d;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.TrajectoryActionBuilder;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import org.firstinspires.ftc.teamcode.MecanumDrive;
import org.firstinspires.ftc.teamcode.drive.opmode.autonomous.utils.BaseAuto;

import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import java.util.Vector;

@Config
@Autonomous(name = "Blue Close", group = "Autonomous")
public class BlueClose extends BaseAuto {
    @Override
    public void runOpMode() {
        Pose2d initialPose = new Pose2d(-11.8, 61.7, 0);
        MecanumDrive drive = new MecanumDrive(hardwareMap, initialPose);
        SideArm sideArm = new SideArm(hardwareMap);

        TrajectoryActionBuilder toRung1 = drive.actionBuilder(initialPose)
                .strafeTo(new Vector2d(-11.8, 36))
                .turnTo(0);

        Action toRung1Action = toRung1.build();

        TrajectoryActionBuilder toRung2 = drive.actionBuilder(initialPose)
                .strafeTo(new Vector2d(-11.8, 32))
                .turnTo(0);

        Action toRung2Action = toRung2.build();

        TrajectoryActionBuilder afterRung = toRung2.fresh()
                .strafeTo(new Vector2d(-11.8, 50))
                .setTangent(0)
                .strafeTo(new Vector2d(-34, 50))
                .strafeTo(new Vector2d(-34, 10))
                .strafeTo(new Vector2d(-45.5, 10))
                .strafeTo(new Vector2d(-45.5, 57))
                .strafeTo(new Vector2d(-45.5, 10))
                .strafeTo(new Vector2d(-60, 10))
                .strafeTo(new Vector2d(-60, 57))
                .strafeTo(new Vector2d(-60, 10))
                .strafeTo(new Vector2d(-65, 10))
                .strafeTo(new Vector2d(-65, 57));

        Action afterRungAction = afterRung.build();

        TrajectoryActionBuilder wait = drive.actionBuilder(initialPose)
                .waitSeconds(10);

        Action waitAction = wait.build();

        Actions.runBlocking(new SequentialAction(
                sideArm.closeClaw(),
                sideArm.resetEncoder()
        ));


        // actions that need to happen on init; for instance, a claw tightening.
        // Actions.runBlocking(claw.closeClaw());




        while (!isStopRequested() && !opModeIsActive()) {
            // in between initialization and match start
        }

        waitForStart();

        if (isStopRequested()) return;

        // start autonomous path

//        Actions.runBlocking(
//                new SequentialAction(
//                        new ParallelAction(
//                                sideArm.moveTo(1000, 0.6),
//                                toRungAction
//                        ),
//                        sideArm.moveTo(800, 0.6),
//                        sideArm.openClaw(),
//                        afterRungAction
//                )
//        );
        //1400
        Actions.runBlocking(
                new SequentialAction(
                        new ParallelAction(
                                sideArm.moveTo(2400, 0.6),
                                toRung1Action
                        ),
                        //waitAction,
                        toRung2Action,
                        sideArm.moveTo(1400, 0.8),
                        sideArm.openClaw(),
                        //waitAction,
                        new ParallelAction(
                                afterRungAction,
                                sideArm.moveTo(0, 1)
                        ),
                        waitAction
                )
        );

        /* For Example:

        Actions.runBlocking(
                new SequentialAction(
                        path1,
                        lift.liftUp(),
                        claw.openClaw(),
                        lift.liftDown(),
                        park
                )
        );

        */
    }
}
