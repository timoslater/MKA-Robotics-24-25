package org.firstinspires.ftc.teamcode.drive.opmode;


import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.IMU;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;


@TeleOp(name = "MAIN", group = "Linear Opmode")
public class TeleOp2024 extends LinearOpMode {

    private DcMotor leftFront = null;
    private DcMotor rightFront = null;
    private DcMotor leftRear = null;
    //private DcMotor revArm = null;
    private DcMotor rightRear = null;
    private DcMotor lift = null;
    private DcMotor slide = null;
    private DcMotor specimen = null;
    private Servo claw = null;
    private Servo rotate = null;
    private Servo clawSpecimen = null;
    private boolean resetting = false;
    private Gamepad driveGamepad = null;
    private Gamepad armGamepad = null;
    private IMU imu;

    public void movement() {
        double modifier = 1;//nearBoard ? 0.65 : 1;
        double Lpower = 1*modifier;
        double Rpower = .517; //0.52*modifier;//*modifier;
        boolean reverseStick = true;

        double r = Lpower * Math.hypot((!reverseStick) ? driveGamepad.left_stick_x : driveGamepad.right_stick_x, (!reverseStick) ? -driveGamepad.left_stick_y : -driveGamepad.right_stick_y);
        double robotAngle = Math.atan2((!reverseStick) ? -driveGamepad.left_stick_y : -driveGamepad.right_stick_y, (!reverseStick) ? driveGamepad.left_stick_x : driveGamepad.right_stick_x) + 3 * Math.PI / 4;
        double rightX = Rpower * ((!reverseStick) ? driveGamepad.right_stick_x : driveGamepad.left_stick_x) * 1;
        double rightY = Rpower * ((!reverseStick) ? driveGamepad.right_stick_y : driveGamepad.left_stick_y) * 1;
        double v1 = r * Math.cos(robotAngle) - rightX + rightY;
        double v2 = r * Math.sin(robotAngle) + rightX + rightY;
        double v3 = r * Math.sin(robotAngle) - rightX + rightY;
        double v4 = r * Math.cos(robotAngle) + rightX + rightY;

        leftFront.setPower(v1);
        rightFront.setPower(v2);
        leftRear.setPower(v3);
        rightRear.setPower(v4);
    }

    public void liftUp(double power) {
        lift.setPower(power);
    }
    public void liftDown(double power) {
        lift.setPower(-power);
    }

    public void slideUp() {
        slide.setPower(0.75);
    }
    public void slideDown() { slide.setPower(-0.75); }

    public void specimenUp(){
        specimen.setPower(0.75);
    }
    public void specimenDown(){
        specimen.setPower(-0.75);
    }

    public void clawOpen() {
        claw.setPosition(.3);
    }

    public void clawClose() {
        claw.setPosition(.64);
    }

    public void claw2Open(){
        clawSpecimen.setPosition(.3);
    }
    public void claw2Close(){
        clawSpecimen.setPosition(.64);
    }

    public void rotateClawR() {
        rotate.setPosition(rotate.getPosition()+.001);
    }
    public void rotateClawL() {
        rotate.setPosition(rotate.getPosition()-.001);
    }
    @Override
    public void runOpMode() {
        telemetry.addData("Status", "Initialized", "haggis");

        // Initialize the hardware variables. Note that the strings used here as parameters
        // to 'get' must correspond to the names assigned during the robot configuration
        // step (using the FTC Robot Controller app on the phone).


        leftFront = hardwareMap.get(DcMotor.class, "leftFront");
        rightFront = hardwareMap.get(DcMotor.class, "rightFront");

        leftRear = hardwareMap.get(DcMotor.class, "leftRear");
        rightRear = hardwareMap.get(DcMotor.class, "rightRear");

        leftFront.setDirection(DcMotor.Direction.FORWARD);
        rightFront.setDirection(DcMotor.Direction.REVERSE);
        leftRear.setDirection(DcMotor.Direction.FORWARD);
        rightRear.setDirection(DcMotor.Direction.REVERSE);

        lift = hardwareMap.get(DcMotor.class, "lift");
        lift.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        lift.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        lift.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);


        slide = hardwareMap.get(DcMotor.class, "slide");
        slide.setDirection(DcMotor.Direction.REVERSE);
        slide.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        specimen = hardwareMap.get(DcMotor.class, "specimen");
        slide.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
      
        claw = hardwareMap.get(Servo.class, "grabber");
        rotate = hardwareMap.get(Servo.class,"rotator");
        clawSpecimen = hardwareMap.get(Servo.class, "clawSpecimen");



        driveGamepad = gamepad1;


        waitForStart();


        // run until the end of the match (driver presses STOP)
        while (opModeIsActive()) {

          movement();

          armGamepad = gamepad2.getGamepadId() == -1 ? gamepad1 : gamepad2;


          if (armGamepad.left_trigger > 0) {
              liftUp(armGamepad.left_trigger);
          } else if (armGamepad.right_trigger > 0) {
              liftDown(armGamepad.right_trigger);
          } else {
              lift.setPower(0);
          }

          if (armGamepad.right_bumper) {
              slideUp();
          } else if (armGamepad.left_bumper) {
              slideDown();
          } else {
              slide.setPower(0);
          }

//          if (driveGamepad.options) {
//              if (resetting) {
//                  lift.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
//                  lift.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
//                  resetting = false;
//              } else {
//                  resetting = true;
//              }
//          }

          if (armGamepad.triangle) {
              clawOpen();
          } else if (armGamepad.cross) {
              clawClose();
          }
          if (armGamepad.dpad_right){
              rotateClawR();
          } else if(armGamepad.dpad_left){
              rotateClawL();
          }

          if(armGamepad.dpad_up){
              specimenUp();
          } else if(armGamepad.dpad_down){
              specimenDown();
          } else{
              specimen.setPower(0);
          }

          if(armGamepad.square){
              claw2Open();
          } else if(gamepad2.circle){
              claw2Close();
          }


          telemetry.addData("Lift Position", lift.getCurrentPosition());
          telemetry.update();
        }
    }
}