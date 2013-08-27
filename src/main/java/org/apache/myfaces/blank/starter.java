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
import java.util.Date;
import java.util.Scanner;
import java.util.TreeMap;

import javax.swing.JFrame;



import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.time.Day;
import org.jfree.data.time.TimePeriod;
import org.jfree.data.time.TimePeriodValue;
import org.jfree.data.time.TimePeriodValues;
import org.jfree.data.time.TimePeriodValuesCollection;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;


public class starter {

	/**
	 * @param args
	 */

//	static final String asd = "http://export.rbc.ru/free/cb.0/free.fcgi?period=DAILY&tickers=NULL&d1=20&m1=08&y1=2013&d2=26&m2=08&y2=2013&lastdays=6&separator=%3B&data_format=BROWSER&header=0";
	static final String asd = "http://export.rbc.ru/free/cb.0/free.fcgi?period=DAILY&tickers=NULL&d1=20&m1=08&y1=2013&d2=26&m2=08&y2=2013&lastdays=900&separator=%3B&data_format=BROWSER&header=0";

	public static void main(final String[] args) throws IOException,
			ParseException, ClassNotFoundException {
		// TODO Auto-generated method stub
		TreeMap<Date, ArrayList<Kurs>> storage = new TreeMap<>();

		  FileInputStream fis = new FileInputStream("temp.out");
		  ObjectInputStream ois = new ObjectInputStream(fis);
		  serialize_storage ser_store = new serialize_storage(null);
		  ser_store=(serialize_storage)ois.readObject();
		  ois.close();
		  storage=ser_store.storage;
		  
		  System.out.println(storage);

		  TimePeriodValues tpv = new TimePeriodValues("BBB");
	  
		  int i=0;
		  for(Date dat:storage.keySet()){
			  float nom=storage.get(dat).get(11).close;
			  tpv.add(new TimePeriodValue(new Day(dat), nom));
		  }
		  

		  	TimePeriodValuesCollection tpvc = new TimePeriodValuesCollection(tpv);		  
			JFreeChart chr = ChartFactory.createTimeSeriesChart("EUR/RUR", "days", "eur", tpvc, true,true,true);
					
			JFrame frame = new JFrame("QQQ");
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.getContentPane().add(new ChartPanel(chr));
			frame.setSize(400, 300);
			frame.show();

		  while(frame.isResizable()){
			  ;
		  }

		  System.exit(0);

		URL con = new URL(asd);

		HttpURLConnection http = (HttpURLConnection) con.openConnection();
		http.setRequestMethod("GET");
		Scanner s = new Scanner(http.getInputStream());

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		while (s.hasNext()) {
			String[] splStr = s.nextLine().split(";");

			Date date = sdf.parse(splStr[1]);
			if (!storage.containsKey(date)) {
				storage.put(date, new ArrayList<Kurs>());
			}
			storage.get(date).add(new Kurs(splStr[0], splStr[5], splStr[8]));
		}
		s.close();
		//System.out.println(storage.keySet());

		Date date = new Date(113,7,24);
		System.out.println(date);
		System.out.println(storage.get(date));

		  FileOutputStream fos = new FileOutputStream("temp.out");
		  ObjectOutputStream oos = new ObjectOutputStream(fos);
		  ser_store = new serialize_storage(storage);
		  oos.writeObject(ser_store);
		  oos.flush();oos.close();



	}

	static void print_table(final String[][] table) {
		for (String[] s : table) {
			System.out.println(s[0] + " " + s[1] + " " + s[5] + " " + s[8]);
		}
	}

}
