package ImageProcessing;

import Core.RobotController;
import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.highgui.VideoCapture;
import org.opencv.imgproc.Imgproc;
import org.apache.logging.log4j.Logger;
import org.opencv.video.Video;

import javax.swing.*;

/**
 * Created by raffaelsteinmann on 26.06.14.
 */
public class ManualAim implements Runnable {

    private Logger log;
    private VideoCapture capture;
    private RobotController rc;
    private boolean bRun = false;
    private Crosshair crosshair = null;
    private Mat input;
    private Size size = new Size(600, 400);
    private Size crosshairSize = new Size(50,50);

    public ManualAim(Logger log, VideoCapture vc, RobotController rc) {
        this.log = log;
        this.rc = rc;
        this.capture = vc;
    }

    public void stop() {
        bRun = false;
        input.release();
    }

    public void setCrosshairOffset(int offset) {
        crosshair.setOffset(offset);
    }

    public void setCrosshairSize(Size size) {
        crosshair.setCrossHairSize(size);
    }

    @Override
    public void run() {
        bRun = true;
        try {
            input = new Mat();
            while (bRun) {
                capture.read(input);
                if (!input.empty()) {
                    if (crosshair == null) {
                        crosshair = new Crosshair(input.size(), crosshairSize);
                    }
                    crosshair.drawCrosshair(input);
                    Imgproc.resize(input, input, size);
                    rc.updateManualAimImage(input);
                }
            }

        } catch (Exception ex) {
            log.error(ex.getMessage());
        }
    }
}
