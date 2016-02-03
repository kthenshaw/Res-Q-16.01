package com.qualcomm.ftcrobotcontroller.opmodes;

import android.widget.Button;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;

//This is one of the classes called in the main tele-op class
//It is used to run the tank drive used by controller 1 in the main code
//It additionally contains the fragments of a second version of the tank drive
//that allows for a button to be pressed and the drive power to be decreased by
//a given number "divider"

public class TeleopTank  {

    private DcMotor leftMotor;
    private DcMotor rightMotor;
    private int caseVar;

    public TeleopTank (DcMotor left, DcMotor right)
    {
        leftMotor = left;
        rightMotor = right;

    }
    public void Tank(float leftY, float rightY) {

        leftMotor.setPower(leftY);
        rightMotor.setPower(rightY);
    }
    public void Tank(float leftY, float rightY, double divider) {

        leftMotor.setPower(leftY*divider);
        rightMotor.setPower(rightY*divider);
    }
    public void driveFlip (float leftY, float rightY, boolean Button1, boolean Button2)
    {
        if (Button1)
        {caseVar = 1;}
        if (Button2)
        {caseVar = 2;}

        switch (caseVar)
        {
            case 1: Tank(leftY,rightY);
            case 2: Tank(-leftY,-rightY);
        }
    }
}
