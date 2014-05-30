package Common;

import ImageProcessing.ColorFilter;
import org.opencv.core.Size;

/**
 * Created by raffaelsteinmann on 14.05.14.
 */
public interface GUIListener {
    void setFilter(Color color);
    void updateFilter(Color color, ColorFilter colorFilter);
    void setCrosshairSize(Size size);
    void setCrosshairOffset(int offset);
    void startFilterPicker();
    void stopFilterPicker();
    void startManualAim();
    void stopManualAim();
    void save();
}
