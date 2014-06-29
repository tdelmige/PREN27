package ImageProcessing;

import Core.RobotController;
import org.apache.logging.log4j.Logger;
import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.highgui.Highgui;
import org.opencv.highgui.VideoCapture;
import org.opencv.imgproc.Imgproc;
import org.opencv.video.Video;

import javax.swing.*;
import javax.swing.text.Highlighter;

/**
 * Created by raffaelsteinmann on 26.06.14.
 */
public class FilterPicker implements Runnable {
    private GroundDetector groundDetector;
    private CubeFinder cubeFinder;
    private VideoCapture capture;
    private RobotController rc;
    private Logger log;
    private ColorFilter colorFilter;
    private ColorFilter groundFilter;
    private Mat input;
    private Mat output;
    private Mat original;
    private Mat ground;
    private boolean bRun = false;
    private boolean bGroundDetect = false;
    private Size size = new Size(400, 300);
    private String file = null;

    public FilterPicker(VideoCapture capture, RobotController rc, Logger log) {
        this.capture = capture;
        this.rc = rc;
        this.log = log;
        groundDetector = new GroundDetector();
        cubeFinder = new CubeFinder();
    }

    public ColorFilter getColorFilter() {
        return colorFilter;
    }

    public void setColorFilter(ColorFilter colorFilter) {
        this.colorFilter = colorFilter;
    }

    public void setGroundFilter(ColorFilter colorFilter) {
        this.groundFilter = colorFilter;
    }

    public void stop() {
        bRun = false;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public void setGroundDetection(boolean b) {
        bGroundDetect = b;
    }

    @Override
    public void run() {
        if (file == null) {
            live();
        } else {
            loopPictureFile(file);
        }
    }

    public void live() {
        bRun = true;
        try {
            input = new Mat();
            output = new Mat();
            ground = new Mat();
            while(bRun) {
                capture.read(input);
                if (!input.empty()) {
                    Imgproc.cvtColor(input, output, Imgproc.COLOR_BGR2HSV);
                    ground = output.clone();
                    output = colorFilter.filter(output);
                    Imgproc.resize(input, input, size);
                    Imgproc.resize(output, output, size);
                    if (bGroundDetect) {
                        groundDetector.findGround(output);
                        groundDetector.drawGround(input);
                    } else {
                        cubeFinder.setInputImage(output);
                        cubeFinder.findCubes();
                        cubeFinder.draw(input);
                    }
                    rc.updateFilterPickerImages(input, output);
                }
            }
        } catch (Exception ex) {
            log.error(ex.getMessage());
        }
    }

    public void loopFile(String file) {
        bRun = true;
        try {
            capture = new VideoCapture(file);
            input = new Mat();
            output = new Mat();

            while(bRun) {
                capture.read(input);
                if (!input.empty()) {
                    Imgproc.cvtColor(input, output, Imgproc.COLOR_BGR2HSV);
                    output = colorFilter.filter(output);
                    Imgproc.resize(input, input, size);
                    Imgproc.resize(output, output, size);
                    if (bGroundDetect) {
                        groundDetector.findGround(output);
                        groundDetector.drawGround(input);
                    } else {
                        cubeFinder.setInputImage(output);
                        cubeFinder.findCubes();
                        cubeFinder.draw(input);
                    }
                    rc.updateFilterPickerImages(input, output);
                    //Thread.sleep(50);
                } else {
                    capture.open(file);
                }
            }
        } catch (Exception ex) {
            log.error(ex.getMessage());
        }
    }

    public void loopPictureFile(String file) {
        bRun = true;
        try {
            original = Highgui.imread(file);
            output = new Mat();
            ground = new Mat();
            while(bRun) {
                input = original.clone();
                Imgproc.cvtColor(input, output, Imgproc.COLOR_BGR2HSV);

                if (!bGroundDetect) {
                    // filter colors
                    ground = output.clone();
                    ground = groundFilter.filter(ground);
                    output = colorFilter.filter(output);
                    // detect and set ground
                    groundDetector.findGround(ground);
                    cubeFinder.setGround(groundDetector.getGround());

                    groundDetector.drawGround(input);
                    groundDetector.drawGround(input);
                    cubeFinder.setInputImage(output);
                    cubeFinder.findCubes();
                    cubeFinder.draw(input);

                } else {
                    output = colorFilter.filter(output);
                    groundDetector.findGround(output);
                    groundDetector.drawGround(input);
                }

                Imgproc.resize(input, input, size);
                Imgproc.resize(output, output, size);
                rc.updateFilterPickerImages(input, output);
                input.release();
                output.release();
            }
        } catch (Exception ex) {
            log.error(ex.getMessage());
        }
    }
}
