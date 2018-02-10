package org.usfirst.frc.team1711.robot.subsystems;

import org.usfirst.frc.team1711.robot.RobotMap;
import org.usfirst.frc.team1711.robot.commands.*;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.drive.MecanumDrive;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

/**
 *
 */
public class DriveSystem extends Subsystem 
{
	public WPI_TalonSRX frontLeftDrive;
	public WPI_TalonSRX frontRightDrive;
	public WPI_TalonSRX rearLeftDrive;
	public WPI_TalonSRX rearRightDrive;
	
	public ADXRS450_Gyro gyro;
	
	boolean secretMode = true;
	
	DifferentialDrive basicDrive;
	MecanumDrive mecanumDrive;
	
	SpeedControllerGroup left;
	SpeedControllerGroup right;

    public DriveSystem()
    {
    	frontLeftDrive = new WPI_TalonSRX(RobotMap.FLD);
    	frontRightDrive = new WPI_TalonSRX(RobotMap.FRD);
    	rearLeftDrive = new WPI_TalonSRX(RobotMap.RLD);
    	rearRightDrive = new WPI_TalonSRX(RobotMap.RRD);
    	
    	left = new SpeedControllerGroup(frontLeftDrive, rearLeftDrive);
    	right = new SpeedControllerGroup(frontRightDrive, rearRightDrive);
    	
    	//basicDrive = new DifferentialDrive(left, right);
    	mecanumDrive = new MecanumDrive(frontLeftDrive, rearLeftDrive, frontRightDrive, rearRightDrive);
    	
    	mecanumDrive.setSafetyEnabled(true);
    	
    	gyro = new ADXRS450_Gyro();
    }
    
    public void arcadeDriving(double speed, double rotation)
    {
    	basicDrive.arcadeDrive(speed, rotation);
    }
    
    public void printOutput(int info)
    {
    	
    	switch (info)
    	{
    	
    		case 0:
    			System.out.println("Motor outputs: ");
    	    	System.out.println("FL: " + frontLeftDrive.get());
    	    	System.out.println("FR: " + frontRightDrive.get());
    	    	System.out.println("RL: " + rearLeftDrive.get());
    	    	System.out.println("RR: " + rearRightDrive.get());
    	    	
    		case 1:
    			System.out.println("Joystick LR Axis: " + RobotMap.driveStick.getRawAxis(0) + "\r\n" +
    	    			"Joystick UD Axis: " + RobotMap.driveStick.getRawAxis(1));
    		case 2:
    			System.out.println("Gyro: " + gyro.getAngle());
    	
    	}
    	
    }
    
    public void polarDrive(double angle, double magnitude, double rotation)
    {
    	double largestValue = 0;
    	
    	if(angle < 0)
    		angle = ((2*Math.PI) + angle);
    	
    	//this code is based on this pdf:
    	//http://thinktank.wpi.edu/resources/346/ControllingMecanumDrive.pdf
    	//and this repository by Jack Smith:
    	//https://bitbucket.org/jackdeansmith/raptors-2015/
    	//please consult these sources before making changes to the following code
    	
    	double frontLeft = magnitude * (Math.sin(angle + Math.PI/4)) + rotation;
    	largestValue = Math.abs(frontLeft); //used to normalize the motor outputs to fit in the range [-1, 1]
    	
    	double frontRight = magnitude * (Math.sin(angle + Math.PI/4)) - rotation;
    	if(Math.abs(frontRight) > largestValue)
    		largestValue = Math.abs(frontRight);
    	
    	double rearLeft = magnitude * (Math.sin(angle + Math.PI/4)) + rotation;
    	if(Math.abs(rearLeft) > largestValue)
    		largestValue = Math.abs(rearLeft);
    	
    	double rearRight = magnitude * (Math.sin(angle + Math.PI/4)) - rotation;
    	if(Math.abs(rearRight) > largestValue)
    		largestValue = Math.abs(rearRight);
    	
    	//normalize the values to fit within the output range
    	if(largestValue > 1)
    	{
    		frontLeft /= largestValue;
    		frontRight /= largestValue;
    		rearLeft /= largestValue;
    		rearRight /= largestValue;
    		//alex wasn't here
    	}
    	else if(secretMode)
    	{
    		double scale = Math.abs(rotation) + magnitude;
    		if(scale > 1) {scale = 1;}
    		
    		frontLeft /= largestValue;
    		frontRight /= largestValue;
    		rearLeft /= largestValue;
    		rearRight /= largestValue;
    		
    		frontLeft *= scale;
    		frontRight *= scale;
    		rearLeft *= scale;
    		rearRight *= scale;
    	}
    	
    	setMotorOutputs(frontRight, frontLeft, rearRight, rearLeft);
    	
    }
    
    public void setMotorOutputs(double frontRight, double frontLeft, double rearRight, double rearLeft)
    {
    	frontLeftDrive.set(ControlMode.PercentOutput, frontLeft);
    	frontRightDrive.set(ControlMode.PercentOutput, frontRight);
    	rearLeftDrive.set(ControlMode.PercentOutput, rearLeft);
    	rearRightDrive.set(ControlMode.PercentOutput, rearRight);
    }
    
    public void driveCartesian(double x, double y, double rotation)
    {
    	mecanumDrive.driveCartesian(y, x, rotation);
    }
    
    public void drivePolar(double magnitude, double angle, double rotation)
    {
    	mecanumDrive.drivePolar(magnitude, angle, rotation);
    }

    public void initDefaultCommand() 
    {
        setDefaultCommand(new JoystickDrive());
    }
}

