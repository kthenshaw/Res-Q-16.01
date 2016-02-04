package com.qualcomm.ftcrobotcontroller.opmodes;

import android.util.Log;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.exception.RobotCoreException;
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
    AdafruitIMU boschBNO055;


    //leftDrive, rightDrive, and balance are all actually two DC Motors
    //wired in parallel to conserve Motor Controllers and ease programming
    DcMotor leftDrive;
    DcMotor rightDrive;
    DcMotor balance;
    DcMotor slideBot;
    DcMotor slideTop;

    Servo blueClimb;
    Servo redClimb;

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

    //This creates the array which the gyro sensor will write into
    //thunderA B and C will then be used to output the roll, pitch, and yaw using telemetry
    volatile double[] rollAngle = new double[2], pitchAngle = new double[2], yawAngle = new double[2];
    double thunderA;
    double thunderB;
    double thunderC;

    long systemTime;//Relevant values of System.nanoTime


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
        rightDrive.setDirection(DcMotor.Direction.REVERSE);
        //leftDrive.setDirection(DcMotor.Direction.REVERSE);
        TeleopTank = new TeleopTank(hardwareMap.dcMotor.get("left_drive"),hardwareMap.dcMotor.get("right_drive"));
        Controller2 = new Controller2();

        //sets initial servo positions
        blueClimb.setPosition(blueClosed);
        redClimb.setPosition(redClosed);

        systemTime = System.nanoTime();
        try
        {
            boschBNO055 = new AdafruitIMU(hardwareMap, "bno055", (byte)(AdafruitIMU.BNO055_ADDRESS_A * 2), (byte)AdafruitIMU.OPERATION_MODE_IMU);
        }
        catch (RobotCoreException e)
        {
            Log.i("FtcRobotController", "Exception: " + e.getMessage());
        }
        Log.i("FtcRobotController", "IMU Init method finished in: " + (-(systemTime - (systemTime = System.nanoTime()))) + " ns.");

    }

    @Override
    public void start()
    {
        systemTime = System.nanoTime();
        boschBNO055.startIMU();//Set up the IMU as needed for a continual stream of I2C reads.
        Log.i("FtcRobotController", "IMU Start method finished in: "
                + (-(systemTime - (systemTime = System.nanoTime()))) + " ns.");
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
            boschBNO055.getIMUGyroAngles(rollAngle,pitchAngle,yawAngle);
            telemetry.addData("Right Wheel: ", gamepad1.right_stick_y);
            telemetry.addData("Left Wheel: ", gamepad1.left_stick_y);
            telemetry.addData("Right Encoder: ", rightDrive.getCurrentPosition());
            telemetry.addData("Left Encoder: ", leftDrive.getCurrentPosition());
            telemetry.addData("Headings(yaw): ", String.format("Euler= %4.5f, Quaternion calculated= %4.5f", yawAngle[0], yawAngle[1]));
            telemetry.addData("Pitches: ", String.format("Euler= %4.5f, Quaternion calculated= %4.5f", pitchAngle[0], pitchAngle[1]));
            telemetry.addData("Max I2C read interval: ", String.format("%4.4f ms. Average interval: %4.4f ms.", boschBNO055.maxReadInterval, boschBNO055.avgReadInterval));

        }

    }
}
