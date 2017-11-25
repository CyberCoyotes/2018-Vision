package org.usfirst.frc.team3603.robot;

import edu.wpi.first.wpilibj.networktables.NetworkTable;
import edu.wpi.first.wpilibj.tables.TableKeyNotDefinedException;

public class BareMinVision {
	NetworkTable table;
	
	public BareMinVision() {
		table = NetworkTable.getTable("GRIP/cyberVision");
	}
	
	public int getData(String key) {
		try {
			@SuppressWarnings("deprecation")
			double[] x = table.getNumberArray(key);
			return (int) x[0];
		} catch(TableKeyNotDefinedException ex) {
			return -3;
		} catch(ArrayIndexOutOfBoundsException ex) {
			return -5;
		}
	}
}
