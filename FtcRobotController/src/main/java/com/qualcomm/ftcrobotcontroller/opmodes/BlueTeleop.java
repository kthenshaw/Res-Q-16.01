package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import android.widget.Switch;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;

import java.util.ResourceBundle;

/**
 * Created by Team Thunderstone on 9/30/2015.
 */
public class BlueTeleop  extends OpMode {

    //Calls the two subclasses containing the separate functions used to control all the functions of the robot
    //Testing new GitHub Branch
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
    public void init()
    {
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
        TeleopTank = new TeleopTank(hardwareMap.dcMotor.get("left_drive"),hardwareMap.dcMotor.get("right_drive"));
        Controller2 = new Controller2();

        //sets initial servo positions
        blueClimb.setPosition(blueClosed);
        redClimb.setPosition(redClosed);
        blueRESQ.setPosition(bResQStart);
        redRESQ.setPosition(rRESQStart);
    }

    @Override
    public void loop()
    {
        //Basic Tank Drive - each joystick on controller 1 controls the power of each side of wheels
        TeleopTank.Tank(gamepad1.left_stick_y,gamepad1.right_stick_y);

        //Sets flipper arm to the value of the right joystick on controller 2
        Controller2.assignMotor(balance,-gamepad2.right_stick_y);

        //Sets both of the linear slides equal to the power of the joystick
        Controller2.assignMotor(slideBot,gamepad2.left_stick_y);
        Controller2.assignMotor(slideTop,gamepad2.left_stick_y);

        //Allows for greater individual control of linear slides
        //Each slide's power is set to either .5, -.5, or 0 depending on the button pressed
        //This allows our robot to fully hang by stressing the top linear slide further than the bottom linear slide
        //gamepad2.right_trigger>.5 is used to change the double variable into a boolean on/off - the .5
        //can be changed to alter the sensitivity of the bumper
        Controller2.slideIndividual(slideTop,gamepad2.right_bumper,gamepad2.right_trigger>.5, .5);
        Controller2.slideIndividual(slideBot,gamepad2.left_bumper,gamepad2.left_trigger>.5, -.5);

        //Assigns buttons to the continuous servos to allow them to wind the string up during the hang
        //They are in two separate functions to allow both servos to be set to the correct stop position
        //Even though they are assigned to the same two buttons (a and y)
        Controller2.assignServo(blueClimb,gamepad2.y,blueOpen);
        Controller2.assignServo(blueClimb,gamepad2.a,blueClosed);
        //Controller2.assignServo(redClimb,gamepad2.y,redOpen);
        //Controller2.assignServo(redClimb,gamepad2.a,redClosed);

        //Sets the climber arm to allow for corrections if the autonomous fails
        Controller2.assignServo(blueRESQ,gamepad1.a,bResQStart);
        Controller2.assignServo(blueRESQ,gamepad1.y,bRESQDrop);
        Controller2.assignServo(redRESQ,gamepad1.dpad_down,rRESQStart);
        Controller2.assignServo(redRESQ, gamepad1.dpad_up,rRESQDrop);
        Controller2.assignServo(redDump,gamepad1.dpad_left,rRESQStart);
        Controller2.assignServo(redDump,gamepad1.dpad_right,rDumpDrop);
    }
}
