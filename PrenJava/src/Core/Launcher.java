package Core;

import Common.Color;
import ImageProcessing.FilterSet;
import ImageProcessing.PropertyManager;
import org.opencv.core.Scalar;

import java.io.File;

public class Launcher {
	

	public static void main(String arg[])
	{
        //System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        System.load(new File("PrenJava/res/OpenCV/libopencv_java248.dylib").getAbsolutePath());

		System.out.println("Robot started");

		RobotController control = new RobotController();

        System.out.println("Robot stopped");
	}
	

}
