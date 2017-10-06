package org.usfirst.frc.team3603.robot;

import com.ctre.CANTalon;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Robot extends IterativeRobot {
	
	Vision vision = new Vision(); //The vision processor object
	
	//Drive stuff
	CANTalon frontLeft = new CANTalon(0);
	CANTalon backLeft = new CANTalon(1);
	CANTalon frontRight = new CANTalon(2);
	CANTalon backRight = new CANTalon(3);
	RobotDrive mainDrive = new RobotDrive(frontLeft, backLeft, frontRight, backRight);
	
	double speed = 0; //Variable for testing the minimum turn speed
	
	Joystick joy1 = new Joystick(0); //Controller
	
	@Override
	public void robotInit() {
		vision = new Vision(); //Begin the vision processor
		frontRight.setInverted(true); //Invert motor
		backRight.setInverted(true); //Invert motor
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
		if(!vision.isWorking()) { //Test to see if this works for checking if it's working
			vision.retry(); //Restart the vision
		}
		
		//Testing to see the minimum turn speed
		if(joy1.getRawButton(1)) {
			mainDrive.mecanumDrive_Cartesian(0, 0, speed, 0);
			if(joy1.getRawButton(2)) {
				speed = speed + 0.01;
				while(joy1.getRawButton(2)) {}
			}
		}
		SmartDashboard.putNumber("Minimum speed", speed); //Publish the turn speed
	}
	@Override
	public void testPeriodic() {
	}
}