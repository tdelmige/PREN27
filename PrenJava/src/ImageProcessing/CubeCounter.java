/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ImageProcessing;

import java.util.ArrayList;

import org.opencv.core.*;
import org.opencv.imgproc.Imgproc;

/**
 *
 * @author raffaelsteinmann
 */
public class CubeCounter implements Runnable {
    private ArrayList<Cube> PreviousCubes = new ArrayList<>();
    private ArrayList<Cube> newCubes = new ArrayList<>();
    private CubeFinder Finder;
    private ColorFilter Filter;
    private Mat OriginalImage;
    private Mat ProcessedImage;
    private TargetZone targetZone;
    private int Count;

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

    public TargetZone getTargetZone() {
        return this.targetZone;
    }

    public void setTargetZone(TargetZone targetZone) {
        this.targetZone = targetZone;
    }

    public void count() {
        ArrayList<Cube> tmp = null;
        for (Cube newCube : newCubes){
            if(targetZone.contains(newCube)) {
                if(tmp==null) {
                    tmp = new ArrayList<>();
                }
                tmp.add(newCube);
                if (PreviousCubes != null) {
                    if (alreadyCounted(newCube) == false) {
                        Count++;
                    }
                } else {
                    Count++;
                }
            }
        }
        PreviousCubes = tmp;
    }

    private boolean alreadyCounted(Cube newCube) {
        for (Cube previousCube : PreviousCubes) {
            if (previousCube.getBoundingRect().contains(newCube.getCenter())) {
                return true;
            }
        }
        return false;
    }

    public void resetCount() {
        Count = 0;
    }

    public int getCount() {
        return this.Count;
    }

    public Mat draw(Mat img) {
        this.OriginalImage = img;
        Point topLeft, topRight;
        Point bottomLeft, bottomRight;
        topLeft = new Point(targetZone.getStart(), 0);
        topRight = new Point(targetZone.getEnd(), 0);
        bottomLeft = new Point(targetZone.getStart(), this.OriginalImage.height());
        bottomRight = new Point(targetZone.getEnd(), this.OriginalImage.width());

        Core.line(this.OriginalImage, topLeft, bottomLeft, new Scalar(255,255,255));
        Core.line(this.OriginalImage, topRight, bottomRight, new Scalar(255,255,255));

        for(Cube C : newCubes) {
            Core.circle(this.OriginalImage, C.getCenter(), 2, new Scalar(255, 255, 0));
            Core.rectangle(this.OriginalImage, C.getUpperLeft(), C.getLowerRight(), new Scalar(255, 255, 0));
        }
        return this.OriginalImage;
    }

    public void setNextFrame(Mat nextFrame) {
        setTargetZone(new TargetZone(nextFrame.size(), 30));
        ProcessedImage = nextFrame;
        ProcessedImage = Filter.filter(ProcessedImage);
    }

    public ArrayList<Cube> getCubes(Mat frame)
    {
        ProcessedImage = frame;
        ProcessedImage = Filter.filter(ProcessedImage);
        newCubes = Finder.findCubes(ProcessedImage);
        return newCubes;
    }

    public ColorFilter getColorFilter() {
        return Filter;
    }

    @Override
    public void run() {
        Finder.setInputImage(ProcessedImage);
        Finder.findCubes();
        newCubes = Finder.getCubes();
        count();
    }
}
