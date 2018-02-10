package org.usfirst.frc.team1711.robot.commands;

import org.usfirst.frc.team1711.robot.Robot;
import org.usfirst.frc.team1711.robot.RobotMap;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class JoystickDrive extends Command {

    public JoystickDrive() 
    {
        requires(Robot.driveSystem);
    }

    // Called just before this Command runs the first time
    protected void initialize() 
    {
    	//Robot.driveSystem.polarDrive(0, 0, 0);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() 
    {
    	if(RobotMap.driveStick.getMagnitude() > RobotMap.driveStickDeadzone || Math.abs(RobotMap.driveStick.getRawAxis(4)) > RobotMap.driveStickDeadzone) {
    		
    		Robot.driveSystem.driveCartesian(-RobotMap.driveStick.getRawAxis(1), RobotMap.driveStick.getRawAxis(0), RobotMap.driveStick.getRawAxis(4));
    		//Robot.driveSystem.polarDrive(RobotMap.driveStick.getDirectionRadians(), RobotMap.driveStick.getMagnitude(), RobotMap.driveStick.getRawAxis(4));
    		
    	} else {
    		
    		Robot.driveSystem.polarDrive(0, 0, 0);
    		
    	}
    		
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() 
    {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() 
    {
    	Robot.driveSystem.polarDrive(0, 0, 0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() 
    {
    	Robot.driveSystem.polarDrive(0, 0, 0);
    }
}
