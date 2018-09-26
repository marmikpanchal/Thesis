enum Trend {
	Up, Down, Stable
}

public class Data {

	double open;
	double high;
	double low;
	long volume;
	long mCap;
	double close;
	Trend trend = null;
	
	public Data (double open, double high, double low, long volume, long mCap, double close, Trend trend) {
		this.open = open;
		this.high = high;
		this.low = low;
		this.volume = volume;
		this.mCap = mCap;
		this.close = close;
		this.trend = trend;
	}
	
	public Data() {
	}

	public double getOpen() {
		return open;
	}

	public void setOpen(double open) {
		this.open = open;
	}

	public double getHigh() {
		return high;
	}

	public void setHigh(double high) {
		this.high = high;
	}

	public double getLow() {
		return low;
	}

	public void setLow(double low) {
		this.low = low;
	}

	public long getVolume() {
		return volume;
	}

	public void setVolume(long volume) {
		this.volume = volume;
	}

	public long getmCap() {
		return mCap;
	}

	public void setmCap(long mCap) {
		this.mCap = mCap;
	}

	public double getClose() {
		return close;
	}

	public void setClose(double close) {
		this.close = close;
	}

	public Trend getTrend() {
		return trend;
	}

	public void setTrend(Trend trend) {
		this.trend = trend;
	}

	
}
