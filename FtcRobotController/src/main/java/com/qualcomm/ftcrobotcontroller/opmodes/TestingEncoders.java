package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorController;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.robocol.Telemetry;

/**
 * Created by Katie on 1/20/2016.
 */
public class TestingEncoders  extends OpMode {

    //Calls the two subclasses containing the separate functions used to control all the functions of the robot
    TeleopTank TeleopTank;
    Controller2 Controller2;

    //leftDrive, rightDrive, and balance are all actually two DC Motors
    //wired in parallel to conserve Motor Controllers and ease programming
    DcMotor leftDrive;
    DcMotor rightDrive;
    DcMotor balance;
    DcMotor slideBot;
    DcMotor slideTop;

    Servo blueClimb;
    Servo redClimb;
    Servo bumper;
    Servo allClear;

    //These are preset servo positions that are called later in the program
    //They make it easier to change the number for recalibration as I have to
    //Only change one number
    //Additionally the red and blue closed/open position are saved in both codes
    //Even through they aren't called in both to allow for further modifications
    //That use the positions
    double redOpen = .88;
    double redClosed = .3;
    double blueOpen = .3;
    double blueClosed = .88 ;
    double bumperOpen = .1;
    double bumperClosed = .9;
    double allClearHit = .05;
    double allClearOpen = .95;


    @Override
    public void init()
    {
        leftDrive = hardwareMap.dcMotor.get("left_drive");
        rightDrive = hardwareMap.dcMotor.get("right_drive");
        balance = hardwareMap.dcMotor.get("balance");
        slideTop = hardwareMap.dcMotor.get("slideTop");
        slideBot = hardwareMap.dcMotor.get("slideBot");
        blueClimb = hardwareMap.servo.get("blueClimb");
        redClimb = hardwareMap.servo.get("redClimb");
        bumper = hardwareMap.servo.get("bumper");
        allClear = hardwareMap.servo.get("allClear");
        rightDrive.setDirection(DcMotor.Direction.REVERSE);
        //leftDrive.setDirection(DcMotor.Direction.REVERSE);
        TeleopTank = new TeleopTank(hardwareMap.dcMotor.get("left_drive"),hardwareMap.dcMotor.get("right_drive"));
        Controller2 = new Controller2();

        //sets initial servo positions
        blueClimb.setPosition(blueClosed);
        redClimb.setPosition(redClosed);
        allClear.setPosition(allClearOpen);

    }

    @Override
    public void loop()
    {
        if (gamepad1.a)
        {
            leftDrive.setMode(DcMotorController.RunMode.RESET_ENCODERS);
            rightDrive.setMode(DcMotorController.RunMode.RESET_ENCODERS);
        }
        else
        {
            TeleopTank.Tank(gamepad1.left_stick_y, gamepad1.right_stick_y);
            telemetry.addData("Right Wheel: ", gamepad1.right_stick_y);
            telemetry.addData("Left Wheel: ", gamepad1.left_stick_y);
            telemetry.addData("Right Encoder: ", rightDrive.getCurrentPosition());
            telemetry.addData("Left Encoder: ", leftDrive.getCurrentPosition());
        }

    }
}
