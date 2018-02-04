package org.usfirst.frc.team1711.robot.subsystems;

import org.usfirst.frc.team1711.robot.RobotMap;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;

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
	
	DifferentialDrive basicDrive;
	
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
    	
    	basicDrive = new DifferentialDrive(left, right);
    }
    
    public void arcadeDriving(double speed, double rotation)
    {
    	basicDrive.arcadeDrive(speed, rotation);
    }

    public void initDefaultCommand() 
    {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
}

