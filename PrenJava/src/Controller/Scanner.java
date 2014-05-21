package Controller;

import java.util.ArrayList;
import java.util.Map;

import ImageProcessing.*;
import org.opencv.core.Mat;
import org.opencv.core.Scalar;
import org.opencv.highgui.VideoCapture;
import org.opencv.imgproc.Imgproc;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Scanner {

    private boolean bScanning = false;

    private int cubeCount;
    private Map<Integer, Cube> greenCubes;
    private Map<Integer, Cube> redCubes;
    private Map<Integer, Cube> yellowCubes;
    private Map<Integer, Cube> blueCubes;

    private FilterSet filterSet;
    private TargetZone targetZone;
    private CubeCounter greenCounter;
    private CubeCounter redCounter;
    private CubeCounter yellowCounter;
    private CubeCounter blueCounter;

    private VideoCapture capture;
    private Mat input, output;
    private ImShow imShow;

    Runnable worker1;
    Runnable worker2;
    Runnable worker3;
    Runnable worker4;

    private ExecutorService workerPool;

    public Scanner(FilterSet filterSet, VideoCapture capture) {
        this.capture = capture;
        this.imShow = new ImShow("Test");
        cubeCount = 0;
        greenCounter = new CubeCounter(filterSet.getGreenFilter(), 400);
        greenCounter.setTargetZone(targetZone);
        redCounter = new CubeCounter(filterSet.getGreenFilter(), 400);
        redCounter.setTargetZone(targetZone);
        blueCounter = new CubeCounter(filterSet.getGreenFilter(), 400);
        blueCounter.setTargetZone(targetZone);
        yellowCounter = new CubeCounter(filterSet.getGreenFilter(), 400);
        yellowCounter.setTargetZone(targetZone);
    }

    public void scan() {

        input = new Mat();
        output = new Mat();

        worker1 = greenCounter;
        worker2 = redCounter;
        worker3 = yellowCounter;
        worker4 = blueCounter;

        while (true) {
            if (capture.isOpened()) {
                capture.read(input);
                if (!input.empty()) {

                    Imgproc.cvtColor(input, output, Imgproc.COLOR_BGR2HSV);

                    Mat imgGreen = output.clone();
                    Mat imgBlue = output.clone();
                    Mat imgRed = output.clone();
                    Mat imgYellow = output.clone();

                    greenCounter.setNextFrame(imgGreen);
                    redCounter.setNextFrame(imgRed);
                    yellowCounter.setNextFrame(imgYellow);
                    blueCounter.setNextFrame(imgBlue);

                    workerPool = Executors.newFixedThreadPool(4);
                    workerPool.execute(worker1);
                    workerPool.execute(worker2);
                    workerPool.execute(worker3);
                    workerPool.execute(worker4);
                    workerPool.shutdown();

                    while (!workerPool.isTerminated()) {
                    }
                    imgBlue.release();
                    imgGreen.release();
                    imgRed.release();
                    imgYellow.release();
                    input.release();
                    output.release();
                }

            }
        }
    }

    public CubeCounter getHighestCounter() {
        CubeCounter highest;
        highest = redCounter;
        if (yellowCounter.getCount() > highest.getCount()) {
            highest = yellowCounter;
        }
        if (blueCounter.getCount() > highest.getCount()) {
            highest = blueCounter;
        }
        if (greenCounter.getCount() > highest.getCount()) {
            highest = greenCounter;
        }
        return highest;
    }

    public void scanFromFile(String file) {
        input = new Mat();
        output = new Mat();
        ArrayList<Cube> cubes;

        worker1 = greenCounter;
        worker2 = redCounter;
        worker3 = yellowCounter;
        worker4 = blueCounter;

        Scalar s1 = new Scalar(35, 0, 0);
        Scalar s2 = new Scalar(60, 255, 255);
        ColorFilter colorFilter1 = new ColorFilter(s1, s2);
        ColorFilter colorFilter2 = new ColorFilter(s1, s2);
        ColorFilter colorFilter3 = new ColorFilter(s1, s2);
        ColorFilter colorFilter4 = new ColorFilter(s1, s2);

        greenCounter.setFilter(colorFilter1);
        redCounter.setFilter(colorFilter2);
        yellowCounter.setFilter(colorFilter3);
        blueCounter.setFilter(colorFilter4);

        while (true) {
            if (capture.isOpened()) {
                capture.read(input);
                if (!input.empty()) {

                    Imgproc.cvtColor(input, output, Imgproc.COLOR_BGR2HSV);

                    Mat imgGreen = output.clone();
                    Mat imgBlue = output.clone();
                    Mat imgRed = output.clone();
                    Mat imgYellow = output.clone();

                    greenCounter.setNextFrame(imgGreen);
                    redCounter.setNextFrame(imgRed);
                    yellowCounter.setNextFrame(imgYellow);
                    blueCounter.setNextFrame(imgBlue);

                    workerPool = Executors.newFixedThreadPool(4);
                    workerPool.execute(worker1);
                    workerPool.execute(worker2);
                    workerPool.execute(worker3);
                    workerPool.execute(worker4);
                    workerPool.shutdown();

                    while (!workerPool.isTerminated()) {
                    }
                    imShow.showImage(output);
                    imgBlue.release();
                    imgGreen.release();
                    imgRed.release();
                    imgYellow.release();
                    input.release();
                    output.release();
                }
                else {
                    capture.open("PrenJava/res/vid.mp4");
                    System.out.println(getCounts());

                    redCounter.resetCount();
                    yellowCounter.resetCount();
                    blueCounter.resetCount();
                    greenCounter.resetCount();

                }
            }
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


    public String getCounts() {
        String s;
        s = "Gruen: " + greenCounter.getCount();
        s = s + "\nBlau: " + blueCounter.getCount();
        s = s + "\nRot: " + redCounter.getCount();
        s = s + "\nGelb: " + yellowCounter.getCount();
        return s;
    }

}
