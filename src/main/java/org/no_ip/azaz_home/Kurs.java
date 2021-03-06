package org.no_ip.azaz_home;

import java.io.Serializable;

public class Kurs implements Serializable {
	String ticker;
	float close;
	int nominal;

	public Kurs(String ticker, float close, int nominal) {
		setValue(ticker, close, nominal);
	}

	public Kurs(String... a) {
		// TODO Auto-generated constructor stub
		setValue(a[0], Float.parseFloat(a[1]), Integer.parseInt(a[2]));
	}

	private void setValue(String ticker, float close, int nominal) {
		this.ticker = ticker;
		this.close = close;
		this.nominal = nominal;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "\n"+ticker+" "+close+" "+nominal;
	}
}
