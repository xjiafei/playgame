package util;


public class IPLocation {
	public String country;
	public String area;

	public IPLocation() {
		country = area = "";
	}

	public IPLocation getCopy() {
		IPLocation ret = new IPLocation();
		ret.country = country;
		ret.area = area;
		return ret;
	}
}
