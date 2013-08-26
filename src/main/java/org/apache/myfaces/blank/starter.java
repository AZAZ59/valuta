
package org.apache.myfaces.blank;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Scanner;

public class starter {

	/**
	 * @param args
	 */


	static final String asd ="http://export.rbc.ru/free/cb.0/free.fcgi?period=DAILY&tickers=NULL&d1=20&m1=08&y1=2013&d2=26&m2=08&y2=2013&lastdays=6&separator=%3B&data_format=BROWSER&header=1";
	public static void main(final String[] args) throws IOException, ParseException {
		// TODO Auto-generated method stub
		URL con = new URL(asd);

		HttpURLConnection http = (HttpURLConnection) con.openConnection();
		http.setRequestMethod("GET");

		Scanner s = new Scanner(http.getInputStream());
		ArrayList<String> a = new ArrayList<>();
		while (s.hasNext()) {
			a.add(s.nextLine());
		}

		HashMap<Date, ArrayList<Kurs>> storage = new HashMap<>();


		int sizeOfLine = a.get(0).split(";").length;
		//String[][] table = new String[a.size() ][sizeOfLine];
		for (int i = 1; i < a.size(); i++) {
			String[] splStr = a.get(i).split(";");
			System.out.println(Arrays.toString(splStr));

			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Date date = sdf.parse(splStr[1]);
			if(!storage.containsKey(date)){
				storage.put(date, new ArrayList<Kurs>());
			}
			storage.get(date).add(new Kurs(splStr[0], splStr[5], splStr[8]));

			//table[i] = a.get(i).split(";");
			//System.out.println(Arrays.toString(table[i]));
		}

		//print_table(table);

	}

	static void print_table(final String[][] table){
		for(String[] s : table){
			System.out.println(s[0]+
					" "+s[1]+
					" "+s[5]+
					" "+s[8]
					);
		}
	}

}
