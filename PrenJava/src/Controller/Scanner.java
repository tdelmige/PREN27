package Controller;

import java.util.Map;

import ImageProcessing.CubeCounter;
import ImageProcessing.Cube;
import ImageProcessing.FilterSet;

public class Scanner {
	
	private int cubeCount;
	
	private Map<Integer, Cube> greenCubes;
	private Map<Integer, Cube> redCubes;
	private Map<Integer, Cube> yellowCubes;
	private Map<Integer, Cube> blueCubes;
	
	private CubeCounter greenCounter;
	private CubeCounter redCounter;
	private CubeCounter yellowCounter;
	private CubeCounter blueCounter;

	public Scanner(FilterSet filterPicker) {
		cubeCount = 0;
		greenCounter = new CubeCounter(filterPicker.getGreenFilter(), 400);
		redCounter = new CubeCounter(filterPicker.getRedFilter(), 400);
		blueCounter = new CubeCounter(filterPicker.getBlueFilter(), 400);
		yellowCounter= new CubeCounter(filterPicker.getYellowFilter(), 400);
	}
	
	public void scan() {
		
	}
	
}
