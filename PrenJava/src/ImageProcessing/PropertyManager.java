package ImageProcessing;

import Common.Color;
import org.opencv.core.Scalar;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import static ImageProcessing.ColorFilter.*;

/**
 * Created by raffaelsteinmann on 13.05.14.
 */
public class PropertyManager {
    private Properties config;

    public PropertyManager() {
        config = new Properties();
        try {
            config.load(new FileInputStream("PrenJava/res/config.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveFilterSet(FilterSet filterSet) {
        for (Color c : Color.values()) {
            saveColorFilter(c.toString(), filterSet.getColorFilter(c));
        }
    }

    private void saveColorFilter(String color, ColorFilter filter){
        String lHue, uHue, lSat, uSat, lVal, uVal;
        String lHue2 = "", uHue2 = "";

        lHue = Double.toString(filter.getHueLow());
        lSat = Double.toString(filter.getSatLow());
        lVal = Double.toString(filter.getValLow());
        uHue = Double.toString(filter.getHueUp());
        uSat = Double.toString(filter.getSatUp());
        uVal = Double.toString(filter.getValUp());
        if (color.equals(Color.RED.toString())) {
            lHue2 = Double.toString(filter.getHueLow2());
            uHue2 = Double.toString(filter.getHueUp2());
        }
        config.setProperty(color + "_lHue", lHue);
        config.setProperty(color + "_uHue", uHue);
        if (color.equals(Color.RED.toString())) {
            config.setProperty(color + "_lHue2", lHue2);
            config.setProperty(color + "_uHue2", uHue2);
        }
        config.setProperty(color + "_lVal", lVal);
        config.setProperty(color + "_uVal", uVal);
        config.setProperty(color + "_lSat", lSat);
        config.setProperty(color + "_uSat", uSat);
    }

    public FilterSet getFilterSet() {
        FilterSet filterSet = new FilterSet();
        for (Color c : Color.values()) {
            filterSet.setColorFilter(getColorFilter(c.toString()), c);
        }
        return filterSet;
    }

    private ColorFilter getColorFilter(String color) {
        ColorFilter filter;
        Scalar lower, upper;
        Scalar lower2, upper2;
        int lHue, uHue, lSat, uSat, lVal, uVal;
        int lHue2, uHue2;

        lHue = Integer.valueOf(config.getProperty(color + "_lHue"));
        uHue = Integer.valueOf(config.getProperty(color + "_uHue"));
        lSat = Integer.valueOf(config.getProperty(color + "_lSat"));
        uSat = Integer.valueOf(config.getProperty(color + "_uSat"));
        uVal = Integer.valueOf(config.getProperty(color + "_uVal"));
        lVal = Integer.valueOf(config.getProperty(color + "_lVal"));

        lower = new Scalar(lHue, lSat, lVal);
        upper = new Scalar(uHue, uSat, uVal);
        if (color.equals(Color.RED.toString())) {
            lHue2 = Integer.valueOf(config.getProperty(color + "_lHue2"));
            uHue2 = Integer.valueOf(config.getProperty(color + "_uHue2"));
            lower2 = new Scalar(lHue2, lSat, lVal);
            upper2 = new Scalar(uHue2, uSat, uVal);
            filter = new ColorFilter(lower, upper, lower2, upper2);
        } else {
            filter = new ColorFilter(lower, upper);
        }
        return filter;
    }
}
