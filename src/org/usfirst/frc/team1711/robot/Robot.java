/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team1711.robot;

import org.usfirst.frc.team1711.robot.commands.JoystickDrive;
import org.usfirst.frc.team1711.robot.commands.OrthoSwitchDrive;
import org.usfirst.frc.team1711.robot.subsystems.DriveSystem;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot 
{

	public static RobotMap robotMap;
	public static DriveSystem driveSystem;
	public static Command teleopDrive;
	public static OI oi;

	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	@Override
	public void robotInit() 
	{
		robotMap = new RobotMap();
		robotMap.init();
		driveSystem = new DriveSystem();
		teleopDrive = new OrthoSwitchDrive();
		oi = new OI(); //this needs to be last or else we will get BIG ERROR PROBLEM

	}

	/**
	 * 
	 */
	@Override
	public void disabledInit() 
	{
		
	}

	@Override
	public void disabledPeriodic() 
	{
		Scheduler.getInstance().run();
	}

	@Override
	public void autonomousInit() 
	{
	}

	/**
	 * This function is called periodically during autonomous
	 */
	@Override
	public void autonomousPeriodic() 
	{
		Scheduler.getInstance().run();
	}

	@Override
	public void teleopInit() 
	{
		teleopDrive.start();
	}

	/**
	 * This function is called periodically during operator control
	 */
	@Override
	public void teleopPeriodic() 
	{
		driveSystem.printOutput(2);
		Scheduler.getInstance().run();
	}

	/**
	 * This function is called periodically during test mode
	 */
	@Override
	public void testPeriodic() 
	{
	}
}
