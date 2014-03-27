/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Core;

import org.opencv.core.Point;

/**
 *
 * @author raffaelsteinmann
 */
public class Cube {
    
    private Point Center;
    private Point UpperLeft;
    private Point UpperRight;
    private Point LowerLeft;
    private Point LowerRight;
    
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
    
}
