/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ImageProcessing;

import org.opencv.core.Point;
import org.opencv.core.Rect;

/**
 *
 * @author raffaelsteinmann
 */
public class Cube {

    private Point Center;
    private Point currentPoint;
    private Point lastPoint;
    private Point startingPoint;

    private Point UpperLeft;
    private Point UpperRight;
    private Point LowerLeft;
    private Point LowerRight;
    private Rect boundingRect;
    
    
    public Cube() {}

    public Cube(Point Center, Point UpperLeft, Point UpperRight, Point LowerLeft, Point LowerRight) {
        this.Center = Center;
        this.UpperLeft = UpperLeft;
        this.UpperRight = UpperRight;
        this.LowerLeft = LowerLeft;
        this.LowerRight = LowerRight;
    }

	public Point getCenter() {
        return Center;
    }

    public Point getLastPoint() {
        return this.lastPoint;
    }

    public void setLastPoint(Point p) {
        this.lastPoint = p;
    }

    public Point getStartingPoint() {
        return this.startingPoint;
    }

    public void setStartingPoint(Point p){
        this.startingPoint = p;
    }

    public Point getCurrentPoint() {
        return this.currentPoint;
    }

    public void setCurrentPoint(Point p) {
        this.currentPoint = p;
    }


    public void setCenter(Point Center) {
        this.Center = Center;
    }

    public Point getUpperLeft() {
        return UpperLeft;
    }

    public void setUpperLeft(Point UpperLeft) {
        this.UpperLeft = UpperLeft;
    }

    public Point getUpperRight() {
        return UpperRight;
    }

    public void setUpperRight(Point UpperRight) {
        this.UpperRight = UpperRight;
    }

    public Point getLowerLeft() {
        return LowerLeft;
    }

    public void setLowerLeft(Point LowerLeft) {
        this.LowerLeft = LowerLeft;
    }

    public Point getLowerRight() {
        return LowerRight;
    }

    public void setLowerRight(Point LowerRight) {
        this.LowerRight = LowerRight;
    }
    
    public Rect getBoundingRect() {
    	return this.boundingRect;
    }
    
    public void setBoundingRect(Rect boundingRect) {
    	
        this.UpperLeft = new Point(boundingRect.x, 
                boundingRect.y);
        this.UpperRight = new Point(boundingRect.x + boundingRect.width, 
                boundingRect.y);
        this.LowerLeft = new Point(boundingRect.x, 
                boundingRect.y + boundingRect.height);
        this.LowerRight = new Point(boundingRect.x + boundingRect.width,
                boundingRect.y + boundingRect.height);

        this.boundingRect = boundingRect;
        
    }
    
}
