package Core;

import Common.EComAction;
import gui.Gui;
import Controller.Aimbot;
import Controller.Command;
import Controller.Funnel;
import Controller.Harpune;
import Controller.Scanner;
import Controller.Tower;
import ImageProcessing.FilterSet;
import Common.KeyboardAnimation;
import org.opencv.highgui.VideoCapture;

import javax.swing.*;
import java.util.Date;

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

    public static boolean Close = false;

    public RobotController() {

        main = new Gui();

        command = new Command();
        tower = new Tower(command);
        harpune = new Harpune(command);
        funnel = new Funnel(command);

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {

                keyboard = new KeyboardAnimation(main.getPanel(), 400, tower, harpune, funnel);
                keyboard.addAction("LEFT", EComAction.TowMoveLeft);
                keyboard.addAction("RIGHT", EComAction.TowMoveRight );
                keyboard.addAction("UP", EComAction.HarFire);
                keyboard.addAction("DOWN", EComAction.HarPull);
                keyboard.addAction("W", EComAction.HarMoveUp );
                keyboard.addAction("A", EComAction.HarMoveLeft);
                keyboard.addAction("D", EComAction.HarMoveRight);
                keyboard.addAction("S", EComAction.HarMoveDown);

                keyboard.addAction("Z", EComAction.FunOpen);

                keyboard.addAction("ESCAPE", EComAction.EXIT );
                keyboard.addAction("ENTER", EComAction.STOP );
            }
        });


        while(!Close){

        }



        //InitMotors();


    }




    public static void InitMotors(){

        for(short i=0; i<5; i++){
            Command.InitMove(i, (short) 1);
        }
    }

    public static void Stop()
    {
        System.out.println(new Date().toString() + ": RobotController.Stop");

        for(short i=0; i<5; i++){
            Command.StopMove(i, true);
        }
    }

    public static void Exit(){

        System.out.println(new Date().toString() + ": RobotController.Exit");
        int q = JOptionPane.showConfirmDialog(null, "Anwendung schliessen?", "Exit", JOptionPane.YES_NO_OPTION);

        if (q == 0){
            System.exit(0);}
        else{

        }
    }

    public void StartMoves(){

        InitMotors();
        tower.MoveRight();
    }
	
}
