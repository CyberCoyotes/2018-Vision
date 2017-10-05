package org.usfirst.frc.team3603.robot;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Robot extends IterativeRobot {
	
	Vision vision = new Vision();
	
	@Override
	public void robotInit() {
		vision = new Vision();
	}
	@Override
	public void autonomousInit() {
	}
	@Override
	public void autonomousPeriodic() {
	}
	@Override
	public void teleopPeriodic() {
		SmartDashboard.putString("Keys", vision.getKeys());
		SmartDashboard.putNumber("Vision", vision.getSpeed());
		if(!vision.isWorking()) {
			vision.retry();
		}
	}
	@Override
	public void testPeriodic() {
	}
}