/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ImageProcessing;

import java.util.AbstractCollection;
import java.util.ArrayList;

import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;
import org.opencv.imgproc.Moments;

/**
 *
 * @author raffaelsteinmann
 */
public class CubeFinder {
    
    // GaussianBlur
    private double minArea;
    private double sigmaX;
    private Size kSize;
    
    // Approximation
    private float perimeterCoefficient;
    
    // Adaptive Threshold
    private double maxValue;
    private int blockSize;
    
    // Images
    private Mat inputImage;
    private Mat processedImage;
    
    // List of found Cubes
    private ArrayList<Cube> Cubes;
    
    public CubeFinder() {
    	this.minArea = 2000;
    	this.kSize = new Size(3,3);
    	this.sigmaX = 1;
    	this.maxValue = 100;
    }

    public double getMinArea() {
        return minArea;
    }

    public void setMinArea(double minArea) {
        this.minArea = minArea;
    }

    public double getSigmaX() {
        return sigmaX;
    }

    public void setSigmaX(double sigmaX) {
        this.sigmaX = sigmaX;
    }

    public Size getkSize() {
        return kSize;
    }

    public void setkSize(Size kSize) {
        this.kSize = kSize;
    }

    public float getPerimeterCoefficient() {
        return perimeterCoefficient;
    }

    public void setPerimeterCoefficient(float perimeterCoefficient) {
        this.perimeterCoefficient = perimeterCoefficient;
    }

    public Mat getInputImage() {
        return inputImage;
    }

    public void setInputImage(Mat inputImage) {
        this.inputImage = inputImage;
    }

    public Mat getProcessedImage() {
        return processedImage;
    }

    public void setProcessedImage(Mat processedImage) {
        this.processedImage = processedImage;
    }

    public double getMaxValue() {
        return maxValue;
    }

    public void setMaxValue(double maxValue) {
        this.maxValue = maxValue;
    }

    public int getBlockSize() {
        return blockSize;
    }

    public void setBlockSize(int blockSize) {
        this.blockSize = blockSize;
    }
    
    private void smooth() {
    	Mat tmp = new Mat();
        Imgproc.GaussianBlur(inputImage, tmp, kSize, sigmaX);
        Imgproc.adaptiveThreshold(tmp, processedImage, maxValue, Imgproc.ADAPTIVE_THRESH_MEAN_C, Imgproc.THRESH_BINARY, blockSize, 0);
    }
    
    public void findCubes() {
        Cubes = new ArrayList<>();
        ArrayList<MatOfPoint> Contours = new ArrayList<>();
        Mat Hierarchy = new Mat();
        
        // smooth input image
        smooth();
        
        // find all Contours
        Imgproc.findContours(inputImage, Contours, Hierarchy, Imgproc.RETR_EXTERNAL, Imgproc.CHAIN_APPROX_SIMPLE);
        
        // analyze Contours
        for(MatOfPoint Contour : Contours) {
            double Area = Imgproc.contourArea(Contour);
            
            if (Area > minArea) {
                Cube newCube = new Cube();
                
                // get Center
                Moments moments = Imgproc.moments(Contour);
                double x = moments.get_m10() / moments.get_m00();
                double y = moments.get_m01() / moments.get_m00();
                Point Center = new Point(x,y);
                newCube.setCenter(Center);
                
                // approx Corners
                Rect boundingRect = Imgproc.boundingRect(Contour);
                newCube.setBoundingRect(boundingRect);
                
                
                Cubes.add(newCube);
            }
        }
    }
    
    
}
