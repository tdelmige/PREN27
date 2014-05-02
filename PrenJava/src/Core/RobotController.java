package Core;

import org.opencv.highgui.VideoCapture;

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
	
	private VideoCapture capture;


    private Command command;
    private Tower tower;
    private Harpune harpune;
    private Funnel funnel;

    public RobotController() {
		main = new Gui();
        keyboard = new KeyboardAnimation(main, 0);
        keyboard.addAction("LEFT");
        keyboard.addAction("RIGHT");
        keyboard.addAction("UP");
        keyboard.addAction("DOWN");

        command = new Command();
        tower = new Tower(command);
        harpune = new Harpune(command);
        funnel = new Funnel(command);

        InitMotors();
	}




    public void InitMotors(){

        for(short i=0; i<5; i++){
            Command.InitMove(i, (short) 1);
        }
    }

    public void Stop()
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
