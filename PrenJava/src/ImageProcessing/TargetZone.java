/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ImageProcessing;

import org.opencv.core.Rect;
import org.opencv.core.Point;
import org.opencv.core.Size;

/**
 *
 * @author raffaelsteinmann
 */
public class TargetZone {
    
    private int start;
    private int end;
    private int width;
    private Size imageSize;

    public TargetZone(Size imageSize, int width) {
        this.width = width;
        this.imageSize = imageSize;
    }

    public int getStart() {
        return start;
    }

    public int getEnd() {
        return end;
    }

    public void setWidth(int width) {
        this.width = width;
        setUpTargetZone();
    }

    public int getWidth() {
        return width;
    }

    private void setUpTargetZone() {
        start = (int) (imageSize.width / 2) - (width / 2);
        end = start + width;
    }
    
    public boolean contains(Cube cube){
    	if (cube.getCenter().x > start && cube.getCenter().x < end) {
    		return true;
    	} else {
    		return false;
    	}
    }

    public String toString() {
        return start + " : " + end;
    }
    
}
