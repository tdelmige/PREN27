package ImageProcessing;

import Common.Color;

public class FilterSet {
    private ColorFilter[] colorFilters;
	
	public FilterSet() {
        int maxColors = Color.values().length;
        colorFilters = new ColorFilter[maxColors];
    }

    public void setColorFilter(ColorFilter filter, Color color) {
        colorFilters[color.getValue()] = filter;
    }

    public ColorFilter getColorFilter(Color color) {
        return colorFilters[color.getValue()];
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (ColorFilter c : colorFilters) {
            sb.append(c + "\n");
        }
        return sb.toString();
    }

}
