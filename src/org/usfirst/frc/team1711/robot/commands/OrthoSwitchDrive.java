package org.usfirst.frc.team1711.robot.commands;

import org.usfirst.frc.team1711.robot.Robot;
import org.usfirst.frc.team1711.robot.RobotMap;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class OrthoSwitchDrive extends Command 
{

    public OrthoSwitchDrive() 
    {
        requires(Robot.driveSystem);
    }

    // Called just before this Command runs the first time
    protected void initialize() 
    {
    	Robot.driveSystem.driveCartesian(0, 0, 0);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() 
    {
    	double x = -1 * RobotMap.driveStick.getRawAxis(1);
    	double y = -1 * RobotMap.driveStick.getRawAxis(0);
    	double stickRotation = RobotMap.driveStick.getRawAxis(4);
    	double gyroAngle = Robot.driveSystem.gyro.getAngle();
    	
    	if(RobotMap.driveStick.getMagnitude() > RobotMap.driveStickDeadzone || Math.abs(stickRotation) > 0.2)
    	{
    		if(isOrthoMode())
    		{
    			 double rotationLockOutput = -1 * gyroAngle/50;
    			 Robot.driveSystem.driveCartesian(x, y, rotationLockOutput);
    		}
    		else
    			Robot.driveSystem.driveCartesian(x, y, stickRotation);
    	}
    	else
    		Robot.driveSystem.drivePolar(0, 0, 0);
    }
    
    private boolean isOrthoMode()
    {
    	if(Math.abs(RobotMap.driveStick.getRawAxis(4)) > 0.2)
    		return false;
    	else
    		return true;
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() 
    {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() 
    {
    	Robot.driveSystem.drivePolar(0, 0, 0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() 
    {
    	Robot.driveSystem.drivePolar(0, 0, 0);
    }
}
