package org.apache.myfaces.blank;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;
import java.util.TreeMap;

import javax.swing.text.Document;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.jfree.chart.urls.URLUtilities;
import org.apache.http.client.utils.URIBuilder;

public class serialize_apdater {
	static final String asd = "http://export.rbc.ru/free/cb.0/free.fcgi?"
			+ "period=DAILY&"
			+ "tickers=NULL&"
			+ "d1=20&"
			+ "m1=08&"
			+ "y1=2013&"
			+ "d2=26&"
			+ "m2=08&"
			+ "y2=2013"
			+ "&lastdays=20"
			+ "&separator=%3B&"
			+ "data_format=BROWSER&"
			+ "header=0";
	
	

	private void Update() throws IOException, ClassNotFoundException, ParseException {
		// TODO Auto-generated method stub
		TreeMap<Date, ArrayList<Kurs>> storage = new TreeMap<>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		FileInputStream fis = new FileInputStream("temp.out");
		ObjectInputStream ois = new ObjectInputStream(fis);
		serialize_storage ser_store = new serialize_storage(null);
		ser_store = (serialize_storage) ois.readObject();
		ois.close();
		
		storage = ser_store.storage;
		Calendar cal = Calendar.getInstance();
		
		URL con = new URL(asd);
		
		
		HttpURLConnection http = (HttpURLConnection) con.openConnection();
		http.setRequestMethod("GET");
		Scanner s = new Scanner(http.getInputStream());

		while (s.hasNext()) {
			String[] splStr = s.nextLine().split(";");

			Date date = sdf.parse(splStr[1]);
			if (!storage.containsKey(date)) {
				storage.put(date, new ArrayList<Kurs>());
			}
			storage.get(date).add(new Kurs(splStr[0], splStr[5], splStr[8]));
		}
		s.close();
		// System.out.println(storage.keySet());

		Date date = new Date(113, 7, 24);
		System.out.println(date);
		System.out.println(storage.get(date));

		FileOutputStream fos = new FileOutputStream("temp.out");
		ObjectOutputStream oos = new ObjectOutputStream(fos);
		ser_store = new serialize_storage(storage);
		oos.writeObject(ser_store);
		oos.flush();
		oos.close();
	}
}