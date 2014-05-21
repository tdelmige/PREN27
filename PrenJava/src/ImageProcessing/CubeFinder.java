/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ImageProcessing;

import java.util.ArrayList;
import java.util.List;

import org.opencv.core.*;
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
    
    // Adaptive Threshold
    private double maxValue;
    private int blockSize;
    
    // Images
    private Mat inputImage;
    private Mat processedImage;
    
    // List of found Cubes
    private ArrayList<Cube> Cubes = null;
    
    public CubeFinder() {
    	this.minArea = 1000;
    	this.kSize = new Size(3,3);
    	this.sigmaX = 1;
    	this.maxValue = 100;
    	this.blockSize = 11;
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

    public Mat getInputImage() {
        return inputImage;
    }

    public void setInputImage(Mat inputImage) {
        this.inputImage = inputImage;
        this.processedImage = inputImage.clone();
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
    
    public ArrayList<Cube> getCubes() {
    	return Cubes;
    }

    private void smooth() {
    	Mat tmp = new Mat();
        Imgproc.GaussianBlur(inputImage, tmp, kSize, sigmaX);
        Imgproc.adaptiveThreshold(tmp, processedImage, maxValue, Imgproc.ADAPTIVE_THRESH_MEAN_C, Imgproc.THRESH_BINARY, blockSize, 0);
        tmp.release();
    }

    public ArrayList<Cube> findCubes(Mat input) {
        this.inputImage = input;
        this.processedImage = input.clone();

        ArrayList<MatOfPoint> Contours = new ArrayList<>();
        Mat Hierarchy = new Mat();

        // smooth input image
        smooth();

        // find all Contours
        Imgproc.findContours(processedImage, Contours, Hierarchy, Imgproc.RETR_EXTERNAL, Imgproc.CHAIN_APPROX_SIMPLE);

        // analyze Contours
        for(MatOfPoint Contour : Contours) {
            double Area = Imgproc.contourArea(Contour);

            if (Area > minArea && Area < 5000) {
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

                if (Cubes == null) {
                    Cubes = new ArrayList<>();
                }
                Cubes.add(newCube);
            }
        }

        Hierarchy.release();
        inputImage.release();
        processedImage.release();
        return Cubes;
    }
    
    public void findCubes() {
        Cubes = new ArrayList<>();
        ArrayList<MatOfPoint> Contours = new ArrayList<>();
        Mat Hierarchy = new Mat();
        
        // smooth input image
        smooth();
        
        // find all Contours
        Imgproc.findContours(processedImage, Contours, Hierarchy, Imgproc.RETR_EXTERNAL, Imgproc.CHAIN_APPROX_SIMPLE);
        
        // analyze Contours
        for(MatOfPoint Contour : Contours) {
            double Area = Imgproc.contourArea(Contour);
            
            if (Area > minArea && Area < 5000) {
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
        Hierarchy.release();
        inputImage.release();
        processedImage.release();
    }
    
}
