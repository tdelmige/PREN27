package ImageProcessing;


import org.opencv.core.Scalar;


public class FilterSet {
    private String profile;
	private ColorFilter redFilter;
	private ColorFilter yellowFilter;
	private ColorFilter blueFilter;
	private ColorFilter greenFilter;
	
	public FilterSet() {}

    public String getProfile() { return profile; }
    public void setProfile(String profile) { this.profile = profile; }
	public ColorFilter getRedFilter() {
		return redFilter;
	}
	public void setRedFilter(ColorFilter redFilter) {
		this.redFilter = redFilter;
	}
	public ColorFilter getYellowFilter() {
		return yellowFilter;
	}
	public void setYellowFilter(ColorFilter yellowFilter) {
		this.yellowFilter = yellowFilter;
	}
	public ColorFilter getBlueFilter() {
		return blueFilter;
	}
	public void setBlueFilter(ColorFilter blueFilter) {
		this.blueFilter = blueFilter;
	}
	public ColorFilter getGreenFilter() {
		return greenFilter;
	}
	public void setGreenFilter(ColorFilter greenFilter) {
		this.greenFilter = greenFilter;
	}

}
