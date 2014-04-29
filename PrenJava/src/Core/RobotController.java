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

public class RobotController {
	
	private Gui main;
	private Scanner scanner;
	private Aimbot aimbot;
	
	private FilterSet filterSet;
	
	private VideoCapture capture;
	
	public RobotController() {
		main = new Gui();
	}
	
}
