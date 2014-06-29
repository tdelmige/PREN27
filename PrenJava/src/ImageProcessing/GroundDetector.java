package ImageProcessing;

import org.opencv.core.*;
import org.opencv.imgproc.Imgproc;
import org.opencv.imgproc.Moments;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by raffaelsteinmann on 26.06.14.
 */
public class GroundDetector {
    // GaussianBlur
    private double minArea;
    private double sigmaX;
    private Size kSize;

    // Adaptive Threshold
    private double maxValue;
    private int blockSize;

    // Images
    private Mat inputImage;
    private Mat processedImage;


    private Rect rect;

    MatOfPoint2f ground;
    MatOfPoint ground2;
    MatOfInt hull;

    public GroundDetector() {
        this.minArea = 250;
        this.kSize = new Size(3,3);
        this.sigmaX = 1;
        this.maxValue = 100;
        this.blockSize = 11;
    }

    private void smooth() {
        Mat tmp = new Mat();
        Imgproc.GaussianBlur(inputImage, tmp, kSize, sigmaX);
        Imgproc.adaptiveThreshold(tmp, processedImage, maxValue, Imgproc.ADAPTIVE_THRESH_MEAN_C, Imgproc.THRESH_BINARY, blockSize, 0);
        tmp.release();
    }

    public void findGround(Mat input) {
        this.inputImage = input;
        this.processedImage = input.clone();
        ground = null;
        rect = null;

        ArrayList<MatOfPoint> Contours = new ArrayList<>();
        Mat Hierarchy = new Mat();

        // smooth input image
        smooth();

        // find all Contours
        Imgproc.findContours(processedImage, Contours, Hierarchy, Imgproc.RETR_EXTERNAL, Imgproc.CHAIN_APPROX_SIMPLE);

        // analyze Contours
        for(MatOfPoint Contour : Contours) {
            double Area = Imgproc.contourArea(Contour);
            if (Area > 35000) {
                MatOfPoint2f newMat = new MatOfPoint2f(Contour.toArray());
                RotatedRect rrect = Imgproc.minAreaRect(newMat);
                Point[] points = new Point[4];
                rrect.points(points);
                ground = new MatOfPoint2f(points);

                hull = new MatOfInt();
                Imgproc.convexHull(Contour, hull);
                int[] indexes = hull.toArray();
                Point[] ContourArray = Contour.toArray();
                Point[] hullCnt = new Point[indexes.length];
                for (int i = 0; i < indexes.length; i++) {
                    hullCnt[i] = ContourArray[indexes[i]];
                }
                ground = new MatOfPoint2f(hullCnt);
            }
        }

        Hierarchy.release();
        //inputImage.release();
        processedImage.release();
    }

    public void drawGround(Mat m) {
        if (ground != null) {
            ArrayList<MatOfPoint> list = new ArrayList<>();
            MatOfPoint cnt = new MatOfPoint(ground.toArray());
            list.add(cnt);
            Imgproc.drawContours(m, list, 0, new Scalar(255, 255, 255), 3);
        }
    }

    public MatOfPoint2f getGround() {
        return ground;
    }

    public boolean inGround(Cube c) {
        double v = Imgproc.pointPolygonTest(ground, c.getCenter(), false);
        if (v >= 0) {
            return true;
        }
        return false;
    }

}
