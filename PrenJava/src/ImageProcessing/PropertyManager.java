package ImageProcessing;

import Common.Color;
import org.opencv.core.Scalar;
import java.io.*;
import java.util.Properties;
import static ImageProcessing.ColorFilter.*;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

/**
 * Created by raffaelsteinmann on 13.05.14.
 */
public class PropertyManager {

    private final String FILE = "res/config.properties";
    private Properties config;
    private static Logger log = LogManager.getLogger(PropertyManager.class.getName());

    public PropertyManager() {
        config = new Properties();
        try {
            config.load(new FileInputStream(FILE));
        } catch (IOException ex) {
            log.error(ex.getMessage());
        }
    }

    private void save(){
        try {
            config.store(new FileOutputStream(FILE), null);
        } catch (IOException ex) {
            log.error(ex.getMessage());
        }
    }

    public void saveFilterSet(FilterSet filterSet) {
        for (Color c : Color.values()) {
            saveColorFilter(c.toString(), filterSet.getColorFilter(c));
        }
        save();
    }

    private void saveColorFilter(String color, ColorFilter filter){
        String lHue, uHue, lSat, uSat, lVal, uVal;
        String lHue2 = "", uHue2 = "";

        lHue = Integer.toString((int)filter.getHueLow());
        lSat = Integer.toString((int)filter.getSatLow());
        lVal = Integer.toString((int)filter.getValLow());
        uHue = Integer.toString((int)filter.getHueUp());
        uSat = Integer.toString((int)filter.getSatUp());
        uVal = Integer.toString((int)filter.getValUp());
        if (color.equals(Color.RED.toString())) {
            lHue2 = Integer.toString((int)filter.getHueLow2());
            uHue2 = Integer.toString((int)filter.getHueUp2());
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
        int lHue,uHue, lSat, uSat, lVal, uVal;
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
