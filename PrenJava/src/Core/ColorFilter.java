/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Core;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Scalar;

/**
 *
 * @author raffaelsteinmann
 */
public class ColorFilter {
    
    private Mat ProcessedImage = new Mat();
   
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
        }
        return ProcessedImage;
    }

    public void setLower(Scalar Lower) {
        this.Lower = Lower;
    }

    public void setUpper(Scalar Upper) {
        this.Upper = Upper;
    }
    
    
    
}
