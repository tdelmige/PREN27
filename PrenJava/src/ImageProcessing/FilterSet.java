package ImageProcessing;


public class FilterSet {
	
	private ColorFilter redFilter;
	private ColorFilter yellowFilter;
	private ColorFilter blueFilter;
	private ColorFilter greenFilter;
	
	public FilterSet() {}
	
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
