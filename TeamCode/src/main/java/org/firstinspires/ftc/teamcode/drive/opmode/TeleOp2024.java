package org.firstinspires.ftc.teamcode.drive.opmode;


import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;


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

    public void movement() {
        double modifier = 1;//nearBoard ? 0.65 : 1;
        double Lpower = 1*modifier;
        double Rpower = .517; //0.52*modifier;//*modifier;
        boolean reverseStick = true;

        double r = Lpower * Math.hypot((!reverseStick) ? gamepad1.left_stick_x : gamepad1.right_stick_x, (!reverseStick) ? -gamepad1.left_stick_y : -gamepad1.right_stick_y);
        double robotAngle = Math.atan2((!reverseStick) ? -gamepad1.left_stick_y : -gamepad1.right_stick_y, (!reverseStick) ? gamepad1.left_stick_x : gamepad1.right_stick_x) + 3 * Math.PI / 4;
        double rightX = Rpower * ((!reverseStick) ? gamepad1.right_stick_x : gamepad1.left_stick_x) * 1;
        double rightY = Rpower * ((!reverseStick) ? gamepad1.right_stick_y : gamepad1.left_stick_y) * 1;
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
    public void slideDown() {
        slide.setPower(-0.75
        );
    }

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

        leftFront.setDirection(DcMotor.Direction.FORWARD);
        rightFront.setDirection(DcMotor.Direction.REVERSE);
        leftRear.setDirection(DcMotor.Direction.FORWARD);
        rightRear.setDirection(DcMotor.Direction.REVERSE);



        waitForStart();




        // run until the end of the match (driver presses STOP)
        while (opModeIsActive()) {

          movement();

          if (gamepad2.left_trigger > 0) {
              liftUp(gamepad2.left_trigger);
          } else if (gamepad2.right_trigger > 0) {
              liftDown(gamepad2.right_trigger);
          } else {
              lift.setPower(0);
          }

          if (gamepad2.right_bumper) {
              slideUp();
          } else if (gamepad2.left_bumper) {
              slideDown();
          } else {
              slide.setPower(0);
          }

//          if (gamepad1.options) {
//              if (resetting) {
//                  lift.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
//                  lift.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
//                  resetting = false;
//              } else {
//                  resetting = true;
//              }
//          }

          if (gamepad2.triangle) {
              clawOpen();
          } else if (gamepad2.cross) {
              clawClose();
          }
          if (gamepad2.dpad_right){
              rotateClawR();
          } else if(gamepad2.dpad_left){
              rotateClawL();
          }

          if(gamepad2.dpad_up){
              specimenUp();
          } else if(gamepad2.dpad_down){
              specimenDown();
          } else{
              specimen.setPower(0);
          }

          if(gamepad2.square){
              claw2Open();
          } else if(gamepad2.circle){
              claw2Close();
          }


          telemetry.addData("Position", lift.getCurrentPosition());
          telemetry.update();
        }
    }
}