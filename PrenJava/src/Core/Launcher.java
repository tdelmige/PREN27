package Core;

import Common.Color;
import ImageProcessing.FilterSet;
import ImageProcessing.PropertyManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.opencv.core.Scalar;
import org.opencv.core.Core;

import java.io.File;
import java.nio.ByteBuffer;

public class Launcher {

    private static Logger log = LogManager.getLogger(Launcher.class.getName());

	public static void main(String arg[])
	{
        //System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        //
        //
        //System.load("PrenJava/res/OpenCV/libopencv_java249.dylib");
        System.load(new File("res/OpenCV/libopencv_java249.dylib").getAbsolutePath());

		log.info("Robot started");

		RobotController control = new RobotController();

        log.info("Robot stopped");
	}
	

}
