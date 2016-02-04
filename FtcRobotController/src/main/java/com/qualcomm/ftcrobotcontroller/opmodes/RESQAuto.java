package com.qualcomm.ftcrobotcontroller.opmodes;

import android.graphics.Color;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorController;
import com.qualcomm.robotcore.hardware.DeviceInterfaceModule;
import com.qualcomm.robotcore.hardware.DigitalChannelController;
import com.qualcomm.robotcore.hardware.Servo;

/**
 * Created by Katie on 1/20/2016.
 */
public class RESQAuto extends LinearOpMode
{   AutoFunctions AutoFunctions;

    DcMotor leftDrive;
    DcMotor rightDrive;
    DcMotor balance;
    DcMotor slideBot;
    DcMotor slideTop;

    Servo blueClimb;
    Servo redClimb;
    Servo blueRESQ;
    Servo redRESQ;
    Servo redDump;

    double redOpen = .88;
    double redClosed = .3;
    double blueOpen = .3;
    double blueClosed = .88 ;
    double bResQStart = 1;
    double bRESQDrop = .01;
    double rRESQStart = .05;
    double rRESQDrop = .95;
    double rDumpStart = 0;
    double rDumpDrop = .95;



    @Override

    public void runOpMode() throws InterruptedException {
        leftDrive = hardwareMap.dcMotor.get("left_drive");
        rightDrive = hardwareMap.dcMotor.get("right_drive");
        balance = hardwareMap.dcMotor.get("balance");
        slideTop = hardwareMap.dcMotor.get("slideTop");
        slideBot = hardwareMap.dcMotor.get("slideBot");
        blueClimb = hardwareMap.servo.get("blueClimb");
        redClimb = hardwareMap.servo.get("redClimb");
        blueRESQ = hardwareMap.servo.get("blueRESQ");
        redRESQ = hardwareMap.servo.get("redRESQ");
        redDump = hardwareMap.servo.get("redDump");
        rightDrive.setDirection(DcMotor.Direction.REVERSE);
        //leftDrive.setDirection(DcMotor.Direction.REVERSE);
        AutoFunctions = new AutoFunctions();

        //sets initial servo positions
        blueClimb.setPosition(blueClosed);
        redClimb.setPosition(redClosed);
        blueRESQ.setPosition(bResQStart);
        redRESQ.setPosition(rRESQStart);

        waitForStart();

        while (Math.abs(rightDrive.getCurrentPosition())<15100)
        {
            rightDrive.setPower(1);
            leftDrive.setPower(1);
        }
        rightDrive.setPower(0);
        leftDrive.setPower(0);
    }
}

