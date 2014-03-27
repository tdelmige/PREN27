package CamTest;

import java.io.ByteArrayInputStream;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.highgui.Highgui;
import org.opencv.imgproc.Imgproc;

/**
 * Loads an image and displays it in a window and displays its edge-detected version in another window.
 * @author faram
 */
class Test {

    static {
        // Load the OpenCV DLL
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }

    public static void main(String[] args) {
        
        // Load an image file and display it in a window.
        Mat m1 = Highgui.imread("D:/Development/_Java/eclipseworkspace/PrenPython/FilterPicker/pic1.jpg");
        imshow("Original", m1);
        
        // Do some image processing on the image and display in another window.
        Mat m2 = new Mat();
        Imgproc.bilateralFilter(m1, m2, -1, 50, 10);
        Imgproc.Canny(m2, m2, 10, 200);
        imshow("Edge Detected", m2);
    }//main
    
    

    /**
     * Shows given image in a window. Analogous to cv::imshow() of C++ API.
     *
     * @param title
     * @param img
     */
    public static void imshow(String title, Mat img) {
        
        // Convert image Mat to a jpeg
        MatOfByte imageBytes = new MatOfByte();
        Highgui.imencode(".jpg", img, imageBytes);
        
        try {
            // Put the jpeg bytes into a JFrame window and show.
            JFrame frame = new JFrame(title);
            frame.getContentPane().add(new JLabel(new ImageIcon(ImageIO.read(new ByteArrayInputStream(imageBytes.toArray())))));
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.pack();
            frame.setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}