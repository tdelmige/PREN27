package ImageProcessing;

import java.awt.*;
import java.awt.image.BufferedImage;

import javax.swing.*;

import org.opencv.core.Point;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfRect;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.highgui.Highgui;
import org.opencv.highgui.VideoCapture;
import org.opencv.objdetect.CascadeClassifier;

public class OCVPanel extends JPanel
{
	private static final long serialVersionUID = 1L;
	private BufferedImage image;
	private BufferedImage mask;
	private JLabel imgLabel;
	private JLabel maskLabel;
	
	// Create a constructor method
	public OCVPanel()
	{
		super();
		System.loadLibrary("opencv_java248");
		//System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
		
		imgLabel = new JLabel();
		imgLabel.setSize(400, 400);
		this.add(imgLabel);
		
		maskLabel = new JLabel();
		maskLabel.setSize(400, 400);
		this.add(maskLabel);
		
		this.setBackground(Color.blue);
	}
	
	private BufferedImage getimage()
	{
		return image;
	}
	
	public void setImage(BufferedImage newimage)
	{
		image=newimage;
		imgLabel.setIcon(new ImageIcon(newimage));
		return;
	}
	
	public void setMask(BufferedImage newmask){
		mask= newmask;
		maskLabel.setIcon(new ImageIcon(newmask));
	}
	
	/**
	* Converts/writes a Mat into a BufferedImage.
	*
	* @param matrix Mat of type CV_8UC3 or CV_8UC1
	* @return BufferedImage of type TYPE_3BYTE_BGR or TYPE_BYTE_GRAY
	*/
	public static BufferedImage matToBufferedImage(Mat matrix) 
	{
		int cols = matrix.cols();
		int rows = matrix.rows();
		int elemSize = (int)matrix.elemSize();
		byte[] data = new byte[cols * rows * elemSize];
		int type;
		matrix.get(0, 0, data);
		
		switch (matrix.channels()) 
		{
			case 1:
				type = BufferedImage.TYPE_BYTE_GRAY;
				break;
			case 3:
				type = BufferedImage.TYPE_3BYTE_BGR;
				// bgr to rgb
				byte b;
				for(int i=0; i<data.length; i=i+3) 
				{
					b = data[i];
					data[i] = data[i+2];
					data[i+2] = b;
				}
				break;
			default:
				return null;
		}
		
		BufferedImage image2 = new BufferedImage(cols, rows, type);
		image2.getRaster().setDataElements(0, 0, cols, rows, data);
		return image2;
	}
	
	public void paintComponent(Graphics g)
	{
		BufferedImage temp=getimage();
		if(temp != null)
		{
			//g.drawImage(temp,10,10,temp.getWidth(),temp.getHeight(), this);
		}
	}
	
	/*
	public static void main(String arg[])
	{
		// Load the native library.
		System.loadLibrary("opencv_java246");
		JFrame frame = new JFrame("BasicPanel");
		CascadeClassifier faceDetector = new CascadeClassifier("./src/main/resources/lbpcascade_frontalface.xml");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(400,400);
		Panel panel = new Panel();
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
					
					
					MatOfRect faceDetections = new MatOfRect();
					faceDetector.detectMultiScale(webcam_image, faceDetections);
					
					System.out.println(String.format("Detected %s faces", faceDetections.toArray().length));
					
					// Draw a bounding box around each face.
					for (Rect rect : faceDetections.toArray()) 
					{
						Core.rectangle(webcam_image, new Point(rect.x, rect.y), new Point(rect.x + rect.width, rect.y + rect.height), new Scalar(0, 255, 0));
					}
					
					
					temp=matToBufferedImage(webcam_image);
					panel.setimage(temp);
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
	*/
} 