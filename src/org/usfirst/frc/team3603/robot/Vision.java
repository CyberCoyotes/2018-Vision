package org.usfirst.frc.team3603.robot;

import java.util.Set;

import edu.wpi.first.wpilibj.networktables.NetworkTable;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.tables.TableKeyNotDefinedException;

public class Vision {
	NetworkTable table;
	boolean working;
	double[] def = {0.0};
	
	public Vision() {
		table = NetworkTable.getTable("GRIP/cyberVision");
		Set<String> string = table.getKeys();
		String s = string.toString();
		SmartDashboard.putString("Keys", s);
	}
	
	public double getX() {
		try {
			double[] x = table.getNumberArray("centerX");
			return x[0];
		} catch(TableKeyNotDefinedException ex) {
			ex.printStackTrace();
			return -3;
		} catch(ArrayIndexOutOfBoundsException ex) {
			ex.printStackTrace();
			return -4;
		}
	}
	
	public double getSpeed() {
		try {
			double[] x = table.getNumberArray("centerX");
			if(x.length == 2) {
				double centX = ((x[0]+x[1])/2)*0.003125-1;
				return centX;
			} else {
				return -5;
			}
			
		} catch(TableKeyNotDefinedException ex) {
			ex.printStackTrace();
			return 0;
		} catch(ArrayIndexOutOfBoundsException ex) {
			ex.printStackTrace();
			return 0;
		}
	}
	
	public String getKeys() {
		Set<String> string = table.getKeys();
		String s = string.toString();
		return s;
	}
	
	public boolean isWorking() {
		return working;
	}
	
	public void retry() {
		table = NetworkTable.getTable("GRIP/cyberCoyotes");
		Set<String> string = table.getKeys();
		String s = string.toString();
		SmartDashboard.putString("Keys", s);
	}
}