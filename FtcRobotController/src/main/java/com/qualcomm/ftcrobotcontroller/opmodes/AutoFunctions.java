package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorController;

/**
 * Created by TechEd on 10/9/2015.
 */

//This class is a subclass of the ramp autonomous code which contains the functions for moving the encoders
//Some of the functions are used (ie encoderTurn) while others are not and are more fragments of various encoder based movement

public class AutoFunctions
{
    //Encoder turn takes the 2 drive motors and moves them at different powers to different positions (power1/2 and pos1/2)
    //It uses a while function to ensure it remains in a loop until the longer encoder count is met
    //Afterwards it stops the motors and resets the encoders for the next step of the autonomous code

    public void encoderTurn (DcMotor driveR, DcMotor driveL, Integer pos1, Integer pos2, double power1, double power2, DcMotor end, Integer endp)
    {
        while(end.getCurrentPosition()>endp)
        {
            driveR.setTargetPosition(pos1);
            driveL.setTargetPosition(pos2);
            driveR.setMode(DcMotorController.RunMode.RUN_TO_POSITION);
            driveL.setMode(DcMotorController.RunMode.RUN_TO_POSITION);
            driveR.setPower(power1);
            driveL.setPower(power2);
        }
        driveL.setPower(0);
        driveR.setPower(0);


        driveL.setMode(DcMotorController.RunMode.RESET_ENCODERS);
        driveR.setMode(DcMotorController.RunMode.RESET_ENCODERS);
    }

    //singleMotos is an unused function set to work with the arm or other individual DC motors
    //It functions the same as encoderTurn without the extra motor

    public void singleMotor (DcMotor motor, Integer pos, double power)
    {
        while(motor.getCurrentPosition()>pos)
        {
            motor.setTargetPosition(pos);
            motor.setMode(DcMotorController.RunMode.RUN_TO_POSITION);
            motor.setPower(power);
        }
        motor.setPower(0);

        motor.setMode(DcMotorController.RunMode.RESET_ENCODERS);
    }

    //Encoder straight is an unused function that moves 2 motors with encoders to the same distance and speed
    //It is unused becuase we found our two drive motors to be reversed of eachother and the code was problematic
    //with the reversed readings

    public void encoderStraight (DcMotor driveR, DcMotor driveL, Integer pos, double power)
    {
        while(driveR.getCurrentPosition()>pos)
        {
            driveR.setTargetPosition(pos);
            driveL.setTargetPosition(pos);
            driveR.setMode(DcMotorController.RunMode.RUN_TO_POSITION);
            driveL.setMode(DcMotorController.RunMode.RUN_TO_POSITION);
            driveR.setPower(power);
            driveL.setPower(power);
        }
        driveL.setPower(0);
        driveR.setPower(0);


        driveL.setMode(DcMotorController.RunMode.RESET_ENCODERS);
        driveR.setMode(DcMotorController.RunMode.RESET_ENCODERS);
    }
}