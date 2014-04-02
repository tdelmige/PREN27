/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ImageProcessing;

import java.util.ArrayList;

import org.opencv.core.Mat;
import org.opencv.core.Rect;

/**
 *
 * @author raffaelsteinmann
 */
public class CubeCounter {
    
    private int Count = 0;
    private ArrayList<Cube> PreviousCubes = new ArrayList<>();
    private ArrayList<Cube> newCubes = new ArrayList<>();
    private CubeFinder Finder;
    private ColorFilter Filter;
    private Mat OriginalImage;
    private Mat ProcessedImage;
    private TargetZone targetZone;

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
        Finder.findCubes();
        PreviousCubes = Finder.getCubes();
        count();
    }   
    
   public void count() {
	  for (Cube newCube : newCubes){
		  if(targetZone.contains(newCube)) {
			  // Save Cube for next iteration
			  ArrayList<Cube> tmp = new ArrayList<>();
			  tmp.add(newCube);
			  
			  for (Cube previousCube : PreviousCubes){
				  // check if same cubes and count accordingly...
				  if(IsSameCube(newCube, previousCube))
					  break;
			  }
		  }
	  }
   }
   
   private boolean IsSameCube(Cube newCube, Cube oldCube){
	  return false;
   }
}
