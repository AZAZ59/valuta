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

import javax.swing.JApplet;
import javax.swing.JFrame;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;


public class starter {

	/**
	 * @param args
	 */

//	static final String asd = "http://export.rbc.ru/free/cb.0/free.fcgi?period=DAILY&tickers=NULL&d1=20&m1=08&y1=2013&d2=26&m2=08&y2=2013&lastdays=6&separator=%3B&data_format=BROWSER&header=0";
	
	public static void main(final String[] args) throws IOException,
			ParseException, ClassNotFoundException {
		// TODO Auto-generated method stub
			
		   TreeMap<Date, ArrayList<Kurs>> storage = new TreeMap<>();

		  
		  
		  System.out.println(storage);

		  XYSeries ser= new XYSeries("AAA");
		  int i=0;
		  for(Date dat:storage.keySet()){
			  float nom=storage.get(dat).get(0).close;
			  ser.add(i++, nom);
			  //System.out.println(ser.getX(ser.getItemCount()-1));
			  //System.out.println(ser.getY(ser.getItemCount()-1));
			  
		  }
		  		  	
		    XYDataset xyDataset = new XYSeriesCollection(ser);
			JFreeChart chart = ChartFactory.createXYLineChart("test", "x", "y",
					xyDataset, PlotOrientation.VERTICAL, true, true, true);
			//JFreeChart chr = ChartFactory.c
			JFrame frame = new JFrame("QQQ");
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.getContentPane().add(new ChartPanel(chart));
			frame.setSize(400, 300);
			frame.show();

		  while(frame.isResizable()){
			  ;
		  }

		  System.exit(0);
		  
		  
		  



	}

	static void print_table(final String[][] table) {
		for (String[] s : table) {
			System.out.println(s[0] + " " + s[1] + " " + s[5] + " " + s[8]);
		}
	}

}
