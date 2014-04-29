/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ImageProcessing;

import java.util.ArrayList;

import org.opencv.core.Mat;
import org.opencv.core.Core;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;

/**
 *
 * @author raffaelsteinmann
 */
public class CubeCounter {
	
    private ArrayList<Cube> PreviousCubes = new ArrayList<>();
    private ArrayList<Cube> newCubes = new ArrayList<>();
    private CubeFinder Finder;
    private ColorFilter Filter;
    private Mat OriginalImage;
    private Mat ProcessedImage;
    private TargetZone targetZone;
    private boolean SameCube;

    public CubeCounter(ColorFilter Filter, double minArea) {
        this.Filter = Filter;
        this.Finder = new CubeFinder();
    }

    public void setFilter(ColorFilter Filter) {
        this.Filter = Filter;
    }
    
    public Mat getImage() {
        return OriginalImage;
    }
    
    public Mat getProcessedImage(){
        return ProcessedImage;
    }
    
    public void analyze(Mat nextFrame) {
        OriginalImage = nextFrame.clone();
        ProcessedImage = Filter.filter(nextFrame);
        Finder.setInputImage(ProcessedImage);
        Finder.findCubes();
        newCubes = Finder.getCubes();
        drawCenterpoints();
        drawRectangles();
        //count();
    }   
    
    public void count() {
    	ArrayList<Cube> tmp = null;
		for (Cube newCube : newCubes){
			if(targetZone.contains(newCube)) {
				  
				  // Save Cube for next iteration
				  if(tmp==null) {
					  tmp = new ArrayList<>();
				  }
				  tmp.add(newCube);
				  
				  SameCube = false;
				  for (Cube previousCube : PreviousCubes){
					  // check if same cubes and count accordingly...
					  if(IsSameCube(newCube, previousCube))
						  SameCube = true;
						  break;
				  }
			  }
		  }
		if(tmp != null) {
			PreviousCubes = tmp;
		}
   }
   
   private boolean IsSameCube(Cube newCube, Cube oldCube){
	   boolean Same = false;
	   int minDistance, Distance;
	   
	   minDistance = calcDistanceFromPoints(newCube.getCenter(), newCube.getUpperLeft());
	   Distance = calcDistanceFromPoints(newCube.getCenter(), oldCube.getCenter());
	   
	   if (Distance < minDistance) {
		   Same = true;
	   }
	   
	   return Same;
	   
   }
   
   private int calcDistanceFromPoints(Point p1, Point p2) {
	   
	   double a = p1.x - p2.x;
	   if(a < 0) a = a *-1;
	   
	   double b = p2.y - p1.y;
	   if(b < 0) b = b * -1;
	   
	   double c;
	   
	   c = Math.pow(Math.pow(a, 2) + Math.pow(b, 2),1/2);
	   
	   return (int)c;
   }
   
   private void drawCenterpoints(){
	   for (Cube C : newCubes) {
		   Core.circle(OriginalImage, C.getCenter(), 2, new Scalar(255,255,0));
	   }
   }
   
   private void drawRectangles(){
	   for (Cube C : newCubes) {
		   Core.rectangle(this.OriginalImage, C.getUpperLeft(), C.getLowerRight(), new Scalar(255,255,0));
	   }
   }
}
