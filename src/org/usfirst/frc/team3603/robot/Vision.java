package org.usfirst.frc.team3603.robot;

import java.util.Set;

import edu.wpi.first.wpilibj.networktables.NetworkTable;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.tables.TableKeyNotDefinedException;

@SuppressWarnings("deprecation")
public class Vision {
	NetworkTable table; //Create a table
	boolean working;
	
	public Vision() {
		table = NetworkTable.getTable("GRIP/cyberVision"); //Begin the table with a key
		
		//See if there are keys in the table
		Set<String> string = table.getKeys();
		String s = string.toString();
		working = (boolean) s.equals("[]") ? false : true;
		
		SmartDashboard.putString("Keys", s); //Publish the keys
	}
	
	public boolean isWorking() {
		Set<String> string = table.getKeys();
		String s = string.toString();
		working = (boolean) s.equals("[]") ? false : true;
		return working;
	}
	
	public double getX() {
		try {
			double[] x = table.getNumberArray("centerX"); //get the x values from the Kangaroo
			if(x.length != 0) { //If there are more than one x value...
				int numObjects = x.length; //Get the number of x's
				double average = 0; //Create an integer
				for(int obj = 0; obj < numObjects; obj++) {
					average = average + x[obj]; //Add all of the x values
				}
				average = average/numObjects; //Find the average x value
				return average;
			} else { //Otherwise there are no contours
				SmartDashboard.putString("Vision Status", "No contours");
				return -5;
			}
		} catch(TableKeyNotDefinedException ex) { //If the key doesn't exist...
			ex.printStackTrace();
			SmartDashboard.putString("Vision Status", "Table key not defined");
			return -3;
		} catch(ArrayIndexOutOfBoundsException ex) { //If there aren't any values
			ex.printStackTrace();
			SmartDashboard.putString("Vision Status", "Array index out of bounds");
			return -4;
		}
	}
	
	
	/**
	 * This function takes the height of the contour and 
	 * processes it into distance. It then gives an adj-
	 * ustment speed in relation to how far off it is.
	 * @return The adjustment speed.
	 */
	public double getForwardSpeed(int min, int max, double speed) {
		min = Math.abs(min);
		max = Math.abs(max);
		if(min>max) {
			int save = min;
			min = max;
			max = save;
		}
		try {
			double[] x = table.getNumberArray("height");
			if(x.length!=0) {
				double distance = 64.08 * Math.pow(Math.E, -0.016*x[0]);
				if(distance > max) {
					return -speed;
				} else if(distance < min) {
					return speed;
				} else {
					return 0;
				}
			} else {
				return 0;
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
	
	public double getRotationSpeed() {
		try {
			double[] x = table.getNumberArray("centerX");
			if(x.length != 0) {
				int numObjects = x.length;
				double average = 0;
				for(int obj = 0; obj < numObjects; obj++) {
					average = average + x[obj];
				}
				average = average/numObjects;
				double speed = average*0.003125-1; //Scale the average between -1 and 1
				if(speed > 0.15) {
					speed = 0.15;
				} else if(speed < -0.15) {
					speed = -0.15;
				} else {
					speed = 0;
				}
				return speed;
			} else {
				SmartDashboard.putString("Vision Status", "No contours");
				return 0;
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
	
	public double getDistance() {
		try {
			double[] x = table.getNumberArray("height");
			if(x.length!=0) {
				double distance = 64.08 * Math.pow(Math.E, -0.016*x[0]);
				return distance;
			} else {
				return 0;
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
	
	public double getHeight() {
		try {
			double[] x = table.getNumberArray("height");
			if(x.length!=0) {
				return x[0];
			} else {
				return 0;
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
	
	public String getKeys() { //Gives a list of keys
		Set<String> string = table.getKeys();
		String s = string.toString();
		return s;
	}
	
	public void retry() { //Restart vision
		table = NetworkTable.getTable("GRIP/cyberCoyotes");
		Set<String> string = table.getKeys();
		String s = string.toString();
		SmartDashboard.putString("Keys", s);
	}
}
