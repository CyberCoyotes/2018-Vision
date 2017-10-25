package org.usfirst.frc.team3603.robot;

import com.ctre.CANTalon;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Robot extends IterativeRobot {
	
	Vision vision = new Vision(); //The vision processor object
	
	//Drive stuff
	Victor backLeft = new Victor(1);
	Victor backRight = new Victor(2);
	Victor frontLeft = new Victor(3);
	Victor frontRight = new Victor(4);
	RobotDrive mainDrive = new RobotDrive(frontLeft, backLeft, frontRight, backRight);
	
	double speed = 0; //Variable for testing the minimum turn speed
	
	Joystick joy1 = new Joystick(0); //Controller
	
	@Override
	public void robotInit() {
		vision = new Vision(); //Begin the vision processor
		frontRight.setInverted(true); //Invert motor
		backRight.setInverted(true); //Invert motor
		
		mainDrive.setSafetyEnabled(false);
	}
	@Override
	public void autonomousInit() {
	}
	@Override
	public void autonomousPeriodic() {
	}
	@Override
	public void teleopPeriodic() {
		SmartDashboard.putString("Keys", vision.getKeys()); //Publish the NetworkTables keys
		SmartDashboard.putNumber("Vision", vision.getX()); //Publish the center X
		SmartDashboard.putNumber("Height", vision.getHeight());
		SmartDashboard.putNumber("Distance", vision.getDistance());
		
		if(!vision.isWorking()) { //Test to see if this works for checking if it's working
			vision.retry(); //Restart the vision
		}
		
		//Testing to see the minimum turn speed
		if(joy1.getRawAxis(0) > 0.1 || joy1.getRawAxis(0) < -0.1) {
			mainDrive.mecanumDrive_Cartesian(0, 0, joy1.getRawAxis(0), 0);
		} else if(joy1.getRawButton(1) && vision.getSpeed() != -5) {
			mainDrive.mecanumDrive_Cartesian(0, vision.getFSpeed(), vision.getSpeed(), 0);
		} else {
			mainDrive.mecanumDrive_Cartesian(0, 0, 0, 0);
		}
		
		SmartDashboard.putNumber("Speed", vision.getSpeed()); //Publish the turn speed
	}
	@Override
	public void testPeriodic() {
	}
}