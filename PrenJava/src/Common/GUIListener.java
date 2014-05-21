package Common;

import ImageProcessing.ColorFilter;
import org.opencv.core.Size;

/**
 * Created by raffaelsteinmann on 14.05.14.
 */
public interface GUIListener {
    void setFilter(ColorFilter filter, String profile, String color);
    void setCrosshairSize(Size size);
    void setCrosshairOffset(int offset);
    void startFilterPicker();
    void stopFilterPicker();
    void startManualAim();
    void stopManualAim();
    void save();
}
