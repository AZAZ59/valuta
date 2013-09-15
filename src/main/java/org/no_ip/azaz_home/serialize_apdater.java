package org.no_ip.azaz_home;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.Scanner;
import java.util.TreeMap;

public class serialize_apdater {

	public static TreeMap<Date, ArrayList<Kurs>> Update() throws IOException,
			ClassNotFoundException, ParseException {
		// TODO Auto-generated method stub
		TreeMap<Date, ArrayList<Kurs>> storage = new TreeMap<>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		storage = loadSerialVAlues().storage;

		Date date_iter = getLastDownloadDay(storage);
		String dat = gen_date_string(date_iter);

		parseAndDownload(storage, sdf, dat);

		saveSerialValues(storage);

		return storage;
	}

	/**
	 * @param storage
	 * @param sdf
	 * @param dat
	 * @throws MalformedURLException
	 * @throws IOException
	 * @throws ProtocolException
	 * @throws ParseException
	 */
	private static void parseAndDownload(
			TreeMap<Date, ArrayList<Kurs>> storage, SimpleDateFormat sdf,
			String dat) throws MalformedURLException, IOException,
			ProtocolException, ParseException {
		String str1 = "http://export.rbc.ru/free/cb.0/free.fcgi?"
				+ "period=DAILY&" + "tickers=NULL&";

		String str2 = "&separator=%3B&" + "data_format=BROWSER&" + "header=0";

		URL con = new URL(str1 + dat + str2);

		HttpURLConnection http = (HttpURLConnection) con.openConnection();
		http.setRequestMethod("GET");
		Scanner s = new Scanner(http.getInputStream());
		while (s.hasNext()) {
			String[] splStr = s.nextLine().split(";");

			// System.out.println(nextStr);

			Date date = sdf.parse(splStr[1]);
			if (!storage.containsKey(date)) {
				storage.put(date, new ArrayList<Kurs>());
			}
			storage.get(date).add(new Kurs(splStr[0], splStr[5], splStr[8]));

		}
		s.close();
	}

	/**
	 * @param storage
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	private static void saveSerialValues(TreeMap<Date, ArrayList<Kurs>> storage)
			throws FileNotFoundException, IOException {
		FileOutputStream fos = new FileOutputStream("temp.out");
		ObjectOutputStream oos = new ObjectOutputStream(fos);
		serialize_storage ser_store = new serialize_storage(storage);
		oos.writeObject(ser_store);
		oos.flush();
		oos.close();
	}

	/**
	 * @param storage
	 * @return
	 */
	private static Date getLastDownloadDay(
			TreeMap<Date, ArrayList<Kurs>> storage) {
		Iterator<Date> iter = storage.keySet().iterator();
		Date date_iter = null;

		while (iter.hasNext()) {
			date_iter = (Date) iter.next();
		}
		return date_iter;
	}

	/**
	 * @return
	 * @throws FileNotFoundException
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	private static serialize_storage loadSerialVAlues()
			throws FileNotFoundException, IOException, ClassNotFoundException {
		FileInputStream fis = new FileInputStream("temp.out");
		ObjectInputStream ois = new ObjectInputStream(fis);
		serialize_storage ser_store = new serialize_storage(null);
		ser_store = (serialize_storage) ois.readObject();
		ois.close();
		return ser_store;
	}

	/**
	 * @param date_iter
	 * @return
	 */
	public static String gen_date_string(Date date_iter) {
		Calendar cal = Calendar.getInstance();
		int d1 = date_iter.getDate();
		int m1 = date_iter.getMonth() + 1;
		int y1 = date_iter.getYear() + 1900;
		int d2 = cal.get(Calendar.DAY_OF_MONTH);
		int m2 = cal.get(Calendar.MONTH) + 1;
		int y2 = cal.get(Calendar.YEAR);

		long difference = date_iter.getTime() - System.currentTimeMillis();
		int days = (int) (-difference / (24 * 60 * 60 * 1000));
		// System.out.println(days);

		String dat = "d1=" + d1 + "&m1=" + m1 + "&y1=" + y1 + "&d2=" + d2
				+ "&m2=" + m2 + "&y2=" + y2 + "&lastdays=" + days;
		return dat;
	}
}
