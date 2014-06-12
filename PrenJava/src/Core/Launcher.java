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
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        //System.load(new File("PrenJava/res/OpenCV/libopencv_java248.dylib").getAbsolutePath());

		log.info("Robot started");

		RobotController control = new RobotController();

        log.info("Robot stopped");
	}
	

}
