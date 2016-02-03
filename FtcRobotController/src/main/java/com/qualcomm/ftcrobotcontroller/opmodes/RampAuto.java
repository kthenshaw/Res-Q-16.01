package com.qualcomm.ftcrobotcontroller.opmodes;

import android.app.Activity;
import android.graphics.Color;
import android.view.View;

import com.qualcomm.ftccommon.DbgLog;
import com.qualcomm.ftcrobotcontroller.R;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorController;
import com.qualcomm.robotcore.hardware.DeviceInterfaceModule;
import com.qualcomm.robotcore.hardware.DigitalChannelController;
import com.qualcomm.robotcore.hardware.Servo;

public class RampAuto extends LinearOpMode
{
    AutoFunctions AutoFunctions;

    DcMotor leftDrive;
    DcMotor rightDrive;
    DcMotor balance;
    DcMotor slideBot;
    DcMotor slideTop;

    Servo blueClimb;
    Servo redClimb;
    Servo bumper;
    Servo allClear;

    double redClosed = .3;
    double blueClosed = .88 ;
    double bumperOpen = .1;
    double bumperClosed = .9;



    @Override

    public void runOpMode() throws InterruptedException {
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

        //sets initial servo positions
        blueClimb.setPosition(blueClosed);
        redClimb.setPosition(redClosed);
        bumper.setPosition(bumperClosed);

        AutoFunctions = new AutoFunctions();

        //Sets the encoders to run using the encoder mode - also resets the encoders

        leftDrive.setMode(DcMotorController.RunMode.RUN_USING_ENCODERS);
        rightDrive.setMode(DcMotorController.RunMode.RUN_USING_ENCODERS);
        balance.setMode(DcMotorController.RunMode.RUN_USING_ENCODERS);
        leftDrive.setMode(DcMotorController.RunMode.RESET_ENCODERS);
        rightDrive.setMode(DcMotorController.RunMode.RESET_ENCODERS);
        balance.setMode(DcMotorController.RunMode.RESET_ENCODERS);

        waitForStart();

        //Moves the robot in a curve until it is facing the ramp
        //It uses the encoder turn option in the AutoFunctions class
        AutoFunctions.encoderTurn(rightDrive, leftDrive, -9000, -22000, .5, 1, leftDrive, -15500);

        bumper.setPosition(bumperOpen);

        //Moves the main arm forward to provide balance to the robot before climbing the ramp

        AutoFunctions.singleMotor(balance, 3500, .5);

        //Resets the encoders - as encoders do not reset immedeatly it is in a wait function to ensure encoders are fully reset

        while (leftDrive.getCurrentPosition()<0)
        {
            leftDrive.setMode(DcMotorController.RunMode.RESET_ENCODERS);
            rightDrive.setMode(DcMotorController.RunMode.RESET_ENCODERS);
        }

        //Drives up ramp

        AutoFunctions. encoderTurn(rightDrive, leftDrive, -75000, -75000, 1, 1, leftDrive, -10000);
    }
}

