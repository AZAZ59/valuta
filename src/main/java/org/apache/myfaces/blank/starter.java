package org.apache.myfaces.blank;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.TreeMap;

import javax.swing.JFrame;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.time.Day;
import org.jfree.data.time.TimePeriodValue;
import org.jfree.data.time.TimePeriodValues;
import org.jfree.data.time.TimePeriodValuesCollection;



public class starter {

	/**
	 * @param args
	 */

//	static final String asd = "http://export.rbc.ru/free/cb.0/free.fcgi?period=DAILY&tickers=NULL&d1=20&m1=08&y1=2013&d2=26&m2=08&y2=2013&lastdays=6&separator=%3B&data_format=BROWSER&header=0";
	
	public static void main(final String[] args) throws IOException,
			ParseException, ClassNotFoundException {
		// TODO Auto-generated method stub
			
		   TreeMap<Date, ArrayList<Kurs>> storage =serialize_apdater.Update();
		  
		  System.out.println(storage);

		  TimePeriodValues tpv = new TimePeriodValues("BBB");
	  
		  int i=0;
		  for(Date dat:storage.keySet()){
			  float nom=storage.get(dat).get(5).close; //11 -eur 31 - usd
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
		  
		  
		  



	}

	static void print_table(final String[][] table) {
		for (String[] s : table) {
			System.out.println(s[0] + " " + s[1] + " " + s[5] + " " + s[8]);
		}
	}

}
