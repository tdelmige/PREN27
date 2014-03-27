/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Core;

import java.util.ArrayList;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.MatOfPoint2f;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;
import org.opencv.imgproc.Moments;

/**
 *
 * @author raffaelsteinmann
 */
public class CubeCounter {
    
    private int Count = 0;
    private ArrayList<Cube> PreviousCubes = new ArrayList<>();
    
    private ColorFilter Filter;
    private Mat OriginalImage;
    private Mat ProcessedImage;
    private double minArea;
    private double thresh;
    
    public Mat testImage = new Mat();

    public CubeCounter(ColorFilter Filter, double minArea) {
        this.Filter = Filter;
        this.minArea = minArea;
    }

    public void setFilter(ColorFilter Filter) {
        this.Filter = Filter;
    }
    
    public void setThreshLevel(double thresh) {
        this.thresh = thresh;
    }
    
    public void analyze(Mat nextFrame) {
        OriginalImage = nextFrame.clone();
        ProcessedImage = Filter.filter(nextFrame);
        smoothing();        
        findCubes();
    }
    
    public Mat getImage() {
        return OriginalImage;
    }
    
    public Mat getProcessedImage(){
        return ProcessedImage;
    }
    
    private void smoothing() {
        Imgproc.GaussianBlur(OriginalImage, ProcessedImage, new Size(3,3), 1);
        //Imgproc.adaptiveThreshold(imgproc, imgproc, 255, 2, 1, 11, 2);
        Imgproc.cvtColor(ProcessedImage, ProcessedImage, Imgproc.COLOR_BGR2GRAY);
        Imgproc.adaptiveThreshold(ProcessedImage, ProcessedImage, 100, Imgproc.ADAPTIVE_THRESH_MEAN_C, Imgproc.THRESH_BINARY, 3, 0);
    }
    
    private ArrayList<Cube> findCubes() {
        ArrayList<Cube> Cubes = new ArrayList<>();
        ArrayList<MatOfPoint> Contours = new ArrayList<>();
        Mat Hierarchy = new Mat();
        int Count = 0;
        
        Imgproc.findContours(ProcessedImage, Contours, Hierarchy,Imgproc.RETR_EXTERNAL, Imgproc.CHAIN_APPROX_SIMPLE);
        
        for(MatOfPoint Contour : Contours) {
            
            double Area = Imgproc.contourArea(Contour);
            //System.out.print(Area);
            if(Area > 7000) {
            
                Cube c = new Cube();
                MatOfPoint2f Cntr = new MatOfPoint2f(Contour.toArray());
                MatOfPoint2f ApproxCorners = new MatOfPoint2f();
                
                // approximate Corners
                double Perimeter = Imgproc.arcLength(Cntr, true);
                Imgproc.approxPolyDP(Cntr, ApproxCorners, 0.1*Perimeter, true);

                Moments moments = Imgproc.moments(Contour);
                c.setCenter(new Point(moments.get_m10()/moments.get_m00(),
                        moments.get_m01()/moments.get_m00()));
                Core.circle(OriginalImage, c.getCenter(), 5, new Scalar(255,255,255));
                Count++;
                
                for (Point p : ApproxCorners.toList()) {
                    Core.circle(OriginalImage, p, 3, new Scalar(255,255,0));
                    //System.out.println(p);
                }
                
            }
            System.out.println(Count);
            
        }
        //Imgproc.drawContours(ProcessedImage, Contours, 5, new Scalar(255,255,255));
        return null;
        
    }
    
    
    
}
