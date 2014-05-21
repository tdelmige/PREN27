package Controller;

import ImageProcessing.Crosshair;
import ImageProcessing.CubeFinder;
import org.opencv.core.Point;

public class Aimbot {

    private Point coord;
    private Crosshair crosshair;
    private CubeFinder cubeFinder;

    public Aimbot(){}

    public Crosshair getCrosshair() {
        return crosshair;
    }

    public void setCrosshair(Crosshair crosshair) {
        this.crosshair = crosshair;
    }

    public void setCubeFinder(CubeFinder cubeFinder) {
        this.cubeFinder = cubeFinder;
    }

    public void collect() {

    }

}
