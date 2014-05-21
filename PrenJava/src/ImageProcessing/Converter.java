/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ImageProcessing;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import javax.imageio.ImageIO;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.highgui.Highgui;

/**
 *
 * @author raffaelsteinmann
 */
public class Converter {
    
    public static BufferedImage MatToBufferedImage(Mat image) {
        
        BufferedImage bufImage = null;
        
        MatOfByte matOfByte = new MatOfByte();
        Highgui.imencode(".jpg", image, matOfByte);
        
        byte[] byteArray = matOfByte.toArray();
        
        try {
            InputStream in = new ByteArrayInputStream(byteArray);
            bufImage = ImageIO.read(in);
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        matOfByte.release();
        return bufImage;
    }
    
}
