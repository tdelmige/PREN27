package Core;

import org.opencv.highgui.VideoCapture;

import gui.Gui;
import Controller.Scanner;
import ImageProcessing.FilterSet;

public class RobotController {
	
	private boolean bFiltering = false;

	private VideoCapture capture;
	private Gui mainFrame;
	private FilterSet filterSet;
	private Scanner scanner;
	
	public RobotController() {
		mainFrame = new Gui();
		startFiltering();
	}
	
	private void startFiltering() {
		bFiltering = true;
		filter();
	}
	
	private void stopFiltering() {
		bFiltering = false;
	}
	
	private void filter() {
		while(bFiltering) {
			
			
			
		}
	}
	
}
