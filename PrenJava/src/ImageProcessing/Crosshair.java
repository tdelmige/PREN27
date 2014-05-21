/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ImageProcessing;

import org.opencv.core.*;

/**
 *
 * @author raffaelsteinmann
 */
public class Crosshair {

    private Rect rect;
    private Point position;
    private Size crossHairSize;
    private Size imageSize;
    private int verticalLine;
    private int horizontalLine;
    private int offset;

    public Crosshair(Size imageSize, Size crossHairSize) {
        this.imageSize = imageSize;
        this.crossHairSize = crossHairSize;
        setUpCrosshair();
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
        setUpCrosshair();
    }

    public Rect getRect() {
        return rect;
    }

    public void setRect(Rect rect) {
        this.rect = rect;
        setUpCrosshair();
    }

    public Point getPosition() {
        return position;
    }

    public void setPosition(Point position) {
        this.position = position;
        setUpCrosshair();
    }

    public Size getCrossHairSize() {
        return crossHairSize;
    }

    public void setCrossHairSize(Size crossHairSize) {
        this.crossHairSize = crossHairSize;
        setUpCrosshair();
    }

    public Size getImageSize() {
        return imageSize;
    }

    public void setImageSize(Size imageSize) {
        this.imageSize = imageSize;
        setUpCrosshair();
    }

    public int getVerticalLine() {
        return verticalLine;
    }

    public void setVerticalLine(int verticalLine) {
        this.verticalLine = verticalLine;
        setUpCrosshair();
    }

    public int getHorizontalLine() {
        return horizontalLine;
    }

    public void setHorizontalLine(int horizontalLine) {
        this.horizontalLine = horizontalLine;
        setUpCrosshair();
    }

    private void setUpCrosshair() {
        verticalLine = (int)(imageSize.width / 2);
        horizontalLine = (int)(imageSize.height / 2) + offset;
        position = new Point(verticalLine, horizontalLine);
        int x = (int) (verticalLine - (crossHairSize.width / 2));
        int y = (int) (horizontalLine - (crossHairSize.height / 2));
        rect = new Rect(x, y, (int)crossHairSize.width, (int)crossHairSize.height);
    }

    public Mat drawCrosshair(Mat m) {
        Point p1, p2;
        p1 = new Point(rect.x, rect.y);
        p2 = new Point(rect.x + rect.width, rect.y + rect.height);
        Core.rectangle(m, p1, p2, new Scalar(255,255,255));
        p1.x = verticalLine;
        p1.y = 0;
        p2.x = verticalLine;
        p2.y = imageSize.height;
        Core.line(m, p1, p2, new Scalar(255,255,255));
        p1.x = 0;
        p1.y = horizontalLine;
        p2.x = imageSize.width;
        p2.y = horizontalLine;
        Core.line(m, p1, p2, new Scalar(255,255,255));
        return m;
    }

    public boolean onTarget(Point p) {
        if (rect.contains(p)) {
            return true;
        }
        return false;
    }
}
