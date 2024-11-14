package org.firstinspires.ftc.teamcode.drive.opmode.autonomous.utils;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.drive.opmode.autonomous.BlueClose;

public abstract class BaseAuto extends LinearOpMode {
    /*
    public class MainArm {
        private DcMotorEx lift;
        private DcMotorEx slide;

        public MainArm(HardwareMap hardwareMap) {
            lift = hardwareMap.get(DcMotorEx.class, "lift");
            slide = hardwareMap.get(DcMotorEx.class, "slide");

            lift.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            lift.setDirection(DcMotor.Direction.FORWARD);

            slide.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            slide.setDirection(DcMotor.Direction.REVERSE);
        }

        public class ArmDown implements Action {
            private boolean initialized = false;

            @Override
            public boolean run(@NonNull TelemetryPacket packet) {
                // placeholder for motor movement code
                return false;
            }
        }
        public Action armDown() {
            return new ArmDown();
        }

        public class ArmUp implements Action {
            private boolean initialized = false;

            @Override
            public boolean run(@NonNull TelemetryPacket packet) {
                // placeholder for motor movement code
                return false;
            }
        }
        public Action armUp(){
            return new ArmUp();
        }
    }
    */

    public class SideArm {
        private DcMotorEx lift;
        private Servo grabber;

        public SideArm(HardwareMap hardwareMap) {
            lift = hardwareMap.get(DcMotorEx.class, "specimen");
            lift.setDirection(DcMotor.Direction.FORWARD);
            lift.setTargetPosition(0);
            lift.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            lift.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            grabber = hardwareMap.get(Servo.class, "clawSpecimen");
        }
        public class ResetEncoder implements Action {
            @Override
            public boolean run(@NonNull TelemetryPacket packet) {
                lift.setDirection(DcMotor.Direction.FORWARD);
                lift.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                lift.setTargetPosition(0);
                lift.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
                lift.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
                return false;
            }
        }
        public Action resetEncoder() {
            return new ResetEncoder();
        }
        public class CloseClaw implements Action {
            @Override
            public boolean run(@NonNull TelemetryPacket packet) {
                grabber.setPosition(0.64);
                return false;
            }
        }
        public Action closeClaw() {
            return new CloseClaw();
        }

        public class OpenClaw implements Action {
            @Override
            public boolean run(@NonNull TelemetryPacket packet) {
                grabber.setPosition(0.3);
                return false;
            }
        }
        public Action openClaw() {
            return new OpenClaw();
        }

        public class MoveTo implements Action {
            int targetPos;
            double power;
            boolean initialized = false;
            public MoveTo(int targetPos, double power) {
                this.targetPos = targetPos;
                this.power = power;
            }

            @Override
            public boolean run(@NonNull TelemetryPacket packet) {
                if (!initialized) {
                    lift.setPower(power);
                    initialized = true;
                }

                double pos = lift.getCurrentPosition();
                packet.put("liftPos", pos);

                if (Math.abs(targetPos - pos) > 30) {
                    return false;
                } else {
                    lift.setPower(0);
                    return true;
                }
            }
        }
        public Action moveTo(int targetPos, double power) {
            return new MoveTo(targetPos, power);
        }
    }

/*

    public class Claw {
        private Servo grabber;
        private Servo rotator;

        public Claw(HardwareMap hardwareMap) {
            grabber = hardwareMap.get(Servo.class, "grabber");
            rotator = hardwareMap.get(Servo.class, "rotator");
        }

        public class CloseClaw implements Action {
            @Override
            public boolean run(@NonNull TelemetryPacket packet) {
                grabber.setPosition(0.84);
                return false;
            }
        }
        public Action closeClaw() {
            return new CloseClaw();
        }

        public class OpenClaw implements Action {
            @Override
            public boolean run(@NonNull TelemetryPacket packet) {
                grabber.setPosition(0.5);
                return false;
            }
        }
        public Action openClaw() {
            return new OpenClaw();
        }

        public class RotateClawTo implements Action {

            double targetPosition;

            public RotateClawTo(double targetPosition) {
                this.targetPosition = targetPosition;
            }
            @Override
            public boolean run(@NonNull TelemetryPacket packet) {
                rotator.setPosition(this.targetPosition);
                return false;
            }
        }
        public Action rotateClawTo(double targetPosition) {
            return new RotateClawTo(targetPosition);
        }
    }
    */
}
