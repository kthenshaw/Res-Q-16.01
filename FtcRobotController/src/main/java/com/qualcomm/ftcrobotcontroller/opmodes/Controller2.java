package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.Servo;

//This sub-class contains the majority of the functions called in the main tele-op code
//Some are not currently in use but each has been used in various other versions of the robot that we have constructed

public class Controller2
{
    //The assignMotor function is one of the most simple functions in the class
    //All it does is assign a motor to a joystick level
    //It is used to assign the Controller2 joystick to the hang arm
    public void assignMotor (DcMotor DC, double joy)
    {
        DC.setPower(joy);
    }

    //The assignServo function assigns a servo to a button input
    //it takes a position given in the main class and states that if the button is pressed
    //the servo should move the the position - pretty simple
    //It is used to assign the red and blue climber arms and the ball bumper
    public void assignServo (Servo S, boolean button, double position)
    {
        if (button)
        {S.setPosition(position);}
    }

    //The assignContServo is a function that uses a continuous servo to move based on two buttons
    //It was designs when the original plan was for continuous servos to run the linear slides
    //Because it was scraped so early it was never perfected, but remains in case an continuous servo is needed again
    public void assignContServo (Servo S, boolean buttonUp, boolean buttonDown, double position, double stop)
    {
        if (buttonUp)
        {
            S.setPosition(position);
        }
        else if (buttonDown)
        {
            S.setPosition(-position);
        }
        else
        {
            S.setPosition(stop);
        }
    }

    //The toggle function is the smaller version of the drive shift function found in
    //TeleopTank OP mode
    //It allows the drive power to be decreased as long as a button is held
    //Its original use was to slow the main arm when on the ramp to prevent random
    //jerking, however the idea was later removed when the gear ratio was increased to 1:12

    public void Toggle (DcMotor motor, float power, double divider, boolean Button1)
    {
        if (Button1)
        {
            motor.setPower(power/divider);
        }
        else
        {
            motor.setPower(power);
        }
    }

    //The slideIndividual function is a second version of the assignContServo after the motors powering
    //The linear slides was changed to DC motors
    //It uses the same framework as the linear slides but it more easially sets the DC motors to a
    //specified power and then uses two buttons to determine if it oves up or down the slide using
    //basic if/else if/else logic to determine the current power

    public void slideIndividual (DcMotor slide, boolean buttonUp, boolean buttonDown, double power)
    {
        if (buttonUp)
        {
            slide.setPower(power);
        }
        else if (buttonDown)
        {
            slide.setPower(-power);
        }
        else
        {
            slide.setPower(0);
        }
    }

    public void armShift (DcMotor balance, float joy, boolean fast, boolean slow, int caseVar)
    {
        if (fast)
        {caseVar = 1;}
        if (slow)
        {caseVar = 2;}

        switch (caseVar)
        {
            case 1: assignMotor(balance,joy);
            case 2: assignMotor(balance,joy/4);
        }
    }
}
