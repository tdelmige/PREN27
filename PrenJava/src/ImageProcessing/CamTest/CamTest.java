package ImageProcessing.CamTest;

import java.awt.image.BufferedImage;

import javax.swing.JFrame;

import org.opencv.core.Mat;
import org.opencv.highgui.VideoCapture;

import ImageProcessing.Converter;
import ImageProcessing.OCVPanel;

public class CamTest 
{	
	public CamTest()
	{
	}
	

	public static void main(String arg[])
	{
		JFrame frame = new JFrame("BasicPanel");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(400,400);
		OCVPanel panel = new OCVPanel();
		frame.setContentPane(panel);
		frame.setVisible(true);
		Mat webcam_image=new Mat();
		BufferedImage temp;
		VideoCapture capture =new VideoCapture(0);
	
		if( capture.isOpened())
		{
			while( true )
			{
				capture.read(webcam_image);
				
				if( !webcam_image.empty() )
				{
					frame.setSize(webcam_image.width()+40,webcam_image.height()+60);
					
					temp= Converter.MatToBufferedImage(webcam_image);
					panel.setImage(temp);
					panel.repaint();
				}
				else
				{
					System.out.println(" --(!) No captured frame -- Break!");
					break;
				}
			}
		}
		return;
	}
	
}
