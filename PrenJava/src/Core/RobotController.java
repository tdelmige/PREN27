package Core;

import Common.EComAction;
//

import gui.Gui;
import Controller.Aimbot;
import Controller.Command;
import Controller.Funnel;
import Controller.Harpune;
import Controller.Scanner;
import Controller.Tower;
import ImageProcessing.FilterSet;
import Common.KeyboardAnimation;

public class RobotController {
	
	private Gui main;
    private KeyboardAnimation keyboard;
	private Scanner scanner;
	private Aimbot aimbot;
	
	private FilterSet filterSet;
	
	//private VideoCapture capture;


    private Command command;
    private Tower tower;
    private Harpune harpune;
    private Funnel funnel;

    public static boolean Close = false;

    public RobotController() {
		main = new Gui();
        keyboard = new KeyboardAnimation(main, 0, tower, harpune, funnel);
        keyboard.addAction("LEFT", EComAction.TowMoveLeft);
        keyboard.addAction("RIGHT", EComAction.TowMoveRight );
        keyboard.addAction("UP", EComAction.HarFire);
        keyboard.addAction("DOWN", EComAction.HarPull);
        keyboard.addAction("w", EComAction.HarMoveUp );
        keyboard.addAction("s", EComAction.HarMoveDown);
        keyboard.addAction("a", EComAction.HarMoveLeft);
        keyboard.addAction("d", EComAction.HarMoveRight);

        keyboard.addAction("z", EComAction.FunOpen);

        keyboard.addAction("ESC", EComAction.EXIT );
        keyboard.addAction("SPACE", EComAction.STOP );

        command = new Command();
        tower = new Tower(command);
        harpune = new Harpune(command);
        funnel = new Funnel(command);

        InitMotors();

        while(!Close){}
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

    public void StartMoves(){




        InitMotors();
        tower.MoveRight();
    }
	
}
