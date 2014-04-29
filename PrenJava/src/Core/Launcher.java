package Core;
import Controller.Command;
import Controller.Funnel;
import Controller.Harpune;
import Controller.Tower;
import org.opencv.core.Core;

public class Launcher {
	
	private static Command com;
	public static void main(String arg[])
	{
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

		/*
		System.out.print("Robot started");
		com = new Command();
		Tower tow = new Tower(com);
		Harpune har = new Harpune(com);
		Funnel fun = new Funnel(com);
	
		InitMotors();
		tow.MoveRight();
		
		System.out.print("Robot stopped");
		*/
		RobotController control = new RobotController();
	}
	
	public static void InitMotors(){
		
		for(short i=0; i<5; i++){
			Command.InitMove(i, (short) 1);
		}
	}
	
	public static void Stop()
	{
		for(short i=0; i<5; i++){
			Command.StopMove(i, true);
		}
	}
}
