/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ImageProcessing;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Scalar;

/**
 *
 * @author raffaelsteinmann
 */
public class ColorFilter {
    
    private Mat ProcessedImage = new Mat();

    private String color;
    private String profile;
    private Scalar Lower;
    private Scalar Upper;
    private Scalar Lower2;
    private Scalar Upper2;

    public ColorFilter(Scalar Lower, Scalar Upper) {
        this.Lower = Lower;
        this.Upper = Upper;
        this.Lower2 = null;
        this.Upper2 = null;
    }

    public ColorFilter(Scalar Lower, Scalar Upper, Scalar Lower2, Scalar Upper2) {
        this.Lower = Lower;
        this.Upper = Upper;
        this.Lower2 = Lower2;
        this.Upper2 = Upper2;
    }
    
    public Mat filter(Mat ConvertedImage){
        Core.inRange(ConvertedImage, Lower, Upper, ProcessedImage);
        if((Lower2 != null) && (Upper2 != null)) {
           Mat tmp = new Mat();
           Core.inRange(ConvertedImage, Lower, Upper, tmp);
           Core.bitwise_and(ProcessedImage, ProcessedImage, tmp);
           tmp.release();
        }
        return ProcessedImage;
        
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    public void setLower(Scalar Lower) {
        this.Lower = Lower;
    }

    public void setUpper(Scalar Upper) {
        this.Upper = Upper;
    }

    public Scalar getLower() {
        return Lower;
    }

    public Scalar getLower2() {
        return Lower2;
    }
    
    public Scalar getUpper() {
        return Upper;
    }

    public Scalar getUpper2() {
        return Upper2;
    }

    public double getHueLow() {
        return Lower.val[0];
    }

    public double getHueUp() {
        return Upper.val[0];
    }

    public double getHueLow2() {
        return Lower2.val[0];
    }

    public double getHueUp2() {
        return Upper2.val[0];
    }

    public double getSatLow() {
        return Lower.val[1];
    }

    public double getSatUp() {
        return Upper.val[1];
    }

    public double getValLow() {
        return Lower.val[2];
    }

    public double getValUp() {
        return Upper.val[2];
    }

    public String toString() {
        String s = "Lower: " + Lower + " - " + "Upper: " + Upper;
        if (Lower2 != null && Upper2 != null) {
            s = s + "\nLower2: " + Lower2 + " - " + "Upper2: " + Upper2;
        }
        return s;
    }
}
