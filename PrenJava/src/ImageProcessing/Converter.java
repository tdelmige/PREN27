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
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

/**
 *
 * @author raffaelsteinmann
 */
public class Converter {

    private static Logger log = LogManager.getLogger(Converter.class.getName());
    
    public static BufferedImage MatToBufferedImage(Mat image) {
        
        BufferedImage bufImage = null;
        
        MatOfByte matOfByte = new MatOfByte();
        Highgui.imencode(".jpg", image, matOfByte);
        
        byte[] byteArray = matOfByte.toArray();
        
        try {
            InputStream in = new ByteArrayInputStream(byteArray);
            bufImage = ImageIO.read(in);
            
        } catch (Exception ex) {
            log.error(ex.getMessage());
        }
        matOfByte.release();
        return bufImage;
    }
    
}
