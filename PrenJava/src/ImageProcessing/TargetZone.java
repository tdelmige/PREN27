/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ImageProcessing;

import org.opencv.core.Rect;
import org.opencv.core.Point;
/**
 *
 * @author raffaelsteinmann
 */
public class TargetZone {
    
    private int Line1;
    private int Line2;
    private Rect Zone;

    public TargetZone(int width, Point center) {
    	
    }

    public int getLine1() {
        return Line1;
    }

    public void setLine1(int Line1) {
        this.Line1 = Line1;
    }

    public int getLine2() {
        return Line2;
    }

    public void setLine2(int Line2) {
        this.Line2 = Line2;
    }
    
    public boolean contains(Cube cube){
    	if (cube.getCenter().x > Line1 && cube.getCenter().x < Line2) {
    		return true;
    	} else {
    		return false;
    	}
    }
    
}
