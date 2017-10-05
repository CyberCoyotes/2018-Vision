package org.usfirst.frc.team3603.robot;

import java.util.Set;

import edu.wpi.first.wpilibj.networktables.NetworkTable;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.tables.TableKeyNotDefinedException;

@SuppressWarnings("deprecation")
public class Vision {
	NetworkTable table;
	boolean working;
	
	public Vision() {
		table = NetworkTable.getTable("GRIP/cyberVision");
		Set<String> string = table.getKeys();
		String s = string.toString();
		working = (boolean) s.equals("[]") ? false : true;
		SmartDashboard.putString("Keys", s);
	}
	
	public boolean isWorking() {
		Set<String> string = table.getKeys();
		String s = string.toString();
		working = (boolean) s.equals("[]") ? false : true;
		return working;
	}
	
	public double getX() {
		try {
			double[] x = table.getNumberArray("centerX");
			return x[0];
		} catch(TableKeyNotDefinedException ex) {
			ex.printStackTrace();
			SmartDashboard.putString("Vision Status", "Table key not defined");
			return -3;
		} catch(ArrayIndexOutOfBoundsException ex) {
			ex.printStackTrace();
			SmartDashboard.putString("Vision Status", "Array index out of bounds");
			return -4;
		}
	}
	
	public double getSpeed() {
		try {
			double[] x = table.getNumberArray("centerX");
			if(x.length != 0) {
				int numObjects = x.length;
				double average = 0;
				for(int obj = 0; obj < numObjects; obj++) {
					average = average + x[obj];
				}
				average = average/numObjects;
				return average;
			} else {
				SmartDashboard.putString("Vision Status", "Too many contours");
				return -5;
			}
			
		} catch(TableKeyNotDefinedException ex) {
			ex.printStackTrace();
			SmartDashboard.putString("Vision Status", "Table key not defined");
			return 0;
		} catch(ArrayIndexOutOfBoundsException ex) {
			ex.printStackTrace();
			SmartDashboard.putString("Vision Status", "Array index out of bounds");
			return 0;
		}
	}
	
	public double[] get(String key) {
		try {
			return table.getNumberArray(key);
		} catch(TableKeyNotDefinedException ex) {
			ex.printStackTrace();
			SmartDashboard.putString("Vision Status", "Table key not defined: " + key);
			return null;
		}
	}
	
	public String getKeys() {
		Set<String> string = table.getKeys();
		String s = string.toString();
		return s;
	}
	
	public double getNumContours() {
		try {
			double[] x = table.getNumberArray("centerX");
			return x.length;
		} catch (TableKeyNotDefinedException ex){
			ex.printStackTrace();
			SmartDashboard.putString("Vision Status", "Table key not defined");
			return 0;
		}
	}
	
	public void retry() {
		table = NetworkTable.getTable("GRIP/cyberCoyotes");
		Set<String> string = table.getKeys();
		String s = string.toString();
		SmartDashboard.putString("Keys", s);
	}
}
