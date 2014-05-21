package ImageProcessing;

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

    public void saveFilterSet(FilterSet fs) {
        String profile = fs.getProfile();
        ColorFilter red = fs.getRedFilter();
        ColorFilter yellow = fs.getYellowFilter();
        ColorFilter blue = fs.getBlueFilter();
        ColorFilter green = fs.getGreenFilter();
        saveColorFilter(profile, ColorFilter.C_RED, red);
        saveColorFilter(profile, ColorFilter.C_YELLOW, yellow);
        saveColorFilter(profile, ColorFilter.C_GREEN, green);
        saveColorFilter(profile, ColorFilter.C_BLUE, blue);
    }

    private void saveColorFilter(String profile, String color, ColorFilter filter){
        String lHue, uHue, lSat, uSat, lVal, uVal;
        String lHue2 = "", uHue2 = "";

        lHue = Double.toString(filter.getLower().val[0]);
        lSat = Double.toString(filter.getLower().val[1]);
        lVal = Double.toString(filter.getLower().val[2]);
        uHue = Double.toString(filter.getUpper().val[0]);
        uSat = Double.toString(filter.getUpper().val[1]);
        uVal = Double.toString(filter.getUpper().val[2]);
        if (C_RED.equals(color)) {
            lHue2 = Double.toString(filter.getLower2().val[0]);
            uHue2 = Double.toString(filter.getUpper2().val[0]);
        }

        config.setProperty(profile + "_" + color + "_lHue", lHue);
        config.setProperty(profile + "_" + color + "_uHue", uHue);
        if (color.equals(C_RED)) {
            config.setProperty(profile + "_" + color + "_lHue2", lHue2);
            config.setProperty(profile + "_" + color + "_uHue2", uHue2);
        }
        config.setProperty(profile + "_" + color + "_lVal", lVal);
        config.setProperty(profile + "_" + color + "_uVal", uVal);
        config.setProperty(profile + "_" + color + "_lSat", lSat);
        config.setProperty(profile + "_" + color + "_uSat", uSat);
    }

    public FilterSet getFilterSet(String profile) {
        FilterSet filterSet = new FilterSet();
        ColorFilter red, yellow, green, blue;
        red = getColorFilter(profile, C_RED);
        blue = getColorFilter(profile, C_BLUE);
        yellow = getColorFilter(profile, C_YELLOW);
        green = getColorFilter(profile, C_GREEN);
        filterSet.setBlueFilter(blue);
        filterSet.setRedFilter(red);
        filterSet.setYellowFilter(yellow);
        filterSet.setGreenFilter(green);
        return filterSet;
    }

    private ColorFilter getColorFilter(String profile, String color) {
        ColorFilter filter;
        Scalar lower, upper;
        Scalar lower2, upper2;
        int lHue, uHue, lSat, uSat, lVal, uVal;
        int lHue2, uHue2;

        lHue = Integer.valueOf(config.getProperty(profile + "_" + color + "_lHue"));
        uHue = Integer.valueOf(config.getProperty(profile + "_" + color + "_uHue"));
        lSat = Integer.valueOf(config.getProperty(profile + "_" + color + "_lSat"));
        uSat = Integer.valueOf(config.getProperty(profile + "_" + color + "_uSat"));
        uVal = Integer.valueOf(config.getProperty(profile + "_" + color + "_uVal"));
        lVal = Integer.valueOf(config.getProperty(profile + "_" + color + "_lVal"));

        lower = new Scalar(lHue, lSat, lVal);
        upper = new Scalar(uHue, uSat, uVal);
        if(color.equals(C_RED)) {
            lHue2 = Integer.valueOf(config.getProperty(profile + "_" + color + "_lHue2"));
            uHue2 = Integer.valueOf(config.getProperty(profile + "_" + color + "_uHue2"));
            lower2 = new Scalar(lHue2, lSat, lVal);
            upper2 = new Scalar(uHue2, uSat, uVal);
            filter = new ColorFilter(lower, upper, lower2, upper2);
        } else {
            filter = new ColorFilter(lower, upper);
        }
        return filter;
    }
}
