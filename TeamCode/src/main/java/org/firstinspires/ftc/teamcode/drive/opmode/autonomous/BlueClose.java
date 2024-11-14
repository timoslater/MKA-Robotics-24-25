package org.firstinspires.ftc.teamcode.drive.opmode.autonomous;
import androidx.annotation.NonNull;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.ParallelAction;
import com.acmerobotics.roadrunner.Pose2d;
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

        TrajectoryActionBuilder toRung = drive.actionBuilder(initialPose)
                .strafeTo(new Vector2d(-11.8, 32));

        Action toRungAction = toRung.build();

        TrajectoryActionBuilder afterRung = toRung.fresh()
                .strafeTo(new Vector2d(-11.8, 50))
                .setTangent(0)
                .strafeTo(new Vector2d(-34, 50))
                .strafeTo(new Vector2d(-34, 10))
                .strafeTo(new Vector2d(-45.5, 10))
                .strafeTo(new Vector2d(-45.5, 57))
                .strafeTo(new Vector2d(-45.5, 10))
                .strafeTo(new Vector2d(-57, 10))
                .strafeTo(new Vector2d(-57, 57))
                .strafeTo(new Vector2d(-57, 10))
                .strafeTo(new Vector2d(-61, 10))
                .strafeTo(new Vector2d(-61, 57))
                .strafeTo(new Vector2d(-34, 10))
                .strafeTo(new Vector2d(-15, 10));

        Action afterRungAction = afterRung.build();

        sideArm.closeClaw();
        sideArm.resetEncoder();



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
                                sideArm.moveTo(2400, 0.6)
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
