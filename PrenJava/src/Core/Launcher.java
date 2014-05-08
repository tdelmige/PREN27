package Core;

import Controller.Command;
import Controller.Funnel;
import Controller.Harpune;
import Controller.Tower;
import org.opencv.core.Core;

public class Launcher {
	

	public static void main(String arg[])
	{
		//System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

		System.out.print("Robot started");

		RobotController control = new RobotController();

        System.out.print("Robot stopped");
	}
	

}
