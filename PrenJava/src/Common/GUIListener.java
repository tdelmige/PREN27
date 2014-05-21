package Common;

import ImageProcessing.ColorFilter;
import org.opencv.core.Size;

/**
 * Created by raffaelsteinmann on 14.05.14.
 */
public interface GUIListener {
    void setFilter(ColorFilter filter, String profile, String color);
    void setCrosshairSize(Size s);
    void setDetection(boolean b);
    void startFiltering();
    void stopFiltering();
    void save();
}
