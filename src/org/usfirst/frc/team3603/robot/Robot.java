package org.usfirst.frc.team3603.robot;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Robot extends IterativeRobot {
	//
	int f_min = 40;
	int f_max = 52;
	double f_speed = 0.25;
	
	Vision vision = new Vision();
	
	//Drive stuff
	Victor backLeft = new Victor(1);
	Victor backRight = new Victor(2);
	Victor frontLeft = new Victor(3);
	Victor frontRight = new Victor(4);
	RobotDrive mainDrive = new RobotDrive(frontLeft, backLeft, frontRight, backRight);
	
	Joystick joy1 = new Joystick(0); //Controller
	
	Servo servo = new Servo(0);
	
	boolean light = true;
	
	@Override
	public void robotInit() {
		vision = new Vision(); //Begin the vision processor
		frontRight.setInverted(true); //Invert motor
		backRight.setInverted(true); //Invert motor
		
		mainDrive.setSafetyEnabled(false);
		
		servo.setPosition(0.5);
	}
	@Override
	public void autonomousInit() {
	}
	@Override
	public void autonomousPeriodic() {
	}
	@Override
	public void teleopPeriodic() {
		/*
		if(joy1.getRawButton(4)) {//Use button five on the big joystick
			light = (boolean) light ? false : true;//If the light toggle boolean is true, make it false. If the light toggle boolean is false, make it true.
			while(joy1.getRawButton(4)) {}
		}
		if(light) {
			lights.set(DoubleSolenoid.Value.kForward);
		} else {
			lights.set(DoubleSolenoid.Value.kOff);
		} */
		if(!vision.isWorking()) { //Test to see if this works for checking if it's working
			vision.retry(); //Restart the vision
		}
		
		double x = Math.pow(joy1.getRawAxis(4), 3);
		double y = Math.pow(joy1.getRawAxis(1), 3);
		double rot = Math.pow(joy1.getRawAxis(0), 3);
		
		//Testing to see the minimum turn speed
		if(Math.abs(x) >= 0.05 || Math.abs(y) >= 0.05 || Math.abs(rot) >= 0.05) {
			mainDrive.mecanumDrive_Cartesian(x, y, rot, 0);
		} else if(joy1.getRawButton(1) && vision.getRotationSpeed() != -5) {
			mainDrive.mecanumDrive_Cartesian(0, vision.getForwardSpeed(f_min, f_max, f_speed), vision.getRotationSpeed(), 0);
		} else {
			mainDrive.mecanumDrive_Cartesian(0, 0, 0, 0);
		}
		
		if(vision.getX() != -5) {
			servo.setAngle(90*vision.getX() + 90);
		} else {
			servo.setAngle(90);
		}
		
		read();
	}
	
	void read() {
		SmartDashboard.putString("Keys", vision.getKeys()); //Publish the NetworkTables keys
		SmartDashboard.putBoolean("Working", vision.isWorking());
		SmartDashboard.putNumber("Vision", vision.getX()); //Publish the center X
		SmartDashboard.putNumber("Vision2", vision.getX()); //Publish the center X
		SmartDashboard.putNumber("Height", vision.getHeight());
		SmartDashboard.putNumber("Distance", vision.getDistance());
		SmartDashboard.putNumber("Rotation Speed", vision.getRotationSpeed()); //Publish the turn speed
		SmartDashboard.putNumber("Forward Speed", vision.getForwardSpeed(f_min, f_max, f_speed));
	}
	
	@Override
	public void testPeriodic() {
	}
}
