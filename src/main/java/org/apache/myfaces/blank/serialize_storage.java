package org.apache.myfaces.blank;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.TreeMap;

public class serialize_storage implements Serializable {
	public TreeMap<Date, ArrayList<Kurs>> storage;
	public serialize_storage(TreeMap<Date, ArrayList<Kurs>> a) {
		// TODO Auto-generated constructor stub
		storage=a;
	}
}
