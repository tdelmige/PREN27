package Controller;

import java.util.ArrayList;
import java.util.Map;

import Common.Color;
import ImageProcessing.*;
import org.opencv.core.Mat;
import org.opencv.core.Scalar;
import org.opencv.highgui.VideoCapture;
import org.opencv.imgproc.Imgproc;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Scanner {

    private boolean bScanning = false;
    private int scanTime = 10000;

    private FilterSet filterSet;
    private TargetZone targetZone;
    private CubeCounter greenCounter;
    private CubeCounter redCounter;
    private CubeCounter yellowCounter;
    private CubeCounter blueCounter;

    private Harpune harpune;
    private VideoCapture capture;
    private Mat input, output;
    private ImShow imShow;

    Runnable worker1;
    Runnable worker2;
    Runnable worker3;
    Runnable worker4;

    private ExecutorService workerPool;

    public Scanner(FilterSet filterSet, VideoCapture capture, Harpune harpune) {
        this.capture = capture;
        this.harpune = harpune;
        this.imShow = new ImShow("Test");
        greenCounter = new CubeCounter(filterSet.getColorFilter(Color.GREEN), 400);
        greenCounter.setTargetZone(targetZone);
        redCounter = new CubeCounter(filterSet.getColorFilter(Color.RED), 400);
        redCounter.setTargetZone(targetZone);
        blueCounter = new CubeCounter(filterSet.getColorFilter(Color.BLUE), 400);
        blueCounter.setTargetZone(targetZone);
        yellowCounter = new CubeCounter(filterSet.getColorFilter(Color.YELLOW), 400);
        yellowCounter.setTargetZone(targetZone);
    }

    public void run() {
        bScanning = true;
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                scan();
            }
        });
        t.start();
        harpune.MoveLeft();
        try {
            Thread.sleep(scanTime);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        harpune.stopHorizontalMove(false);
        System.out.println(getCounts());
        bScanning = false;
    }

    public void scan() {

        bScanning = true;
        input = new Mat();
        output = new Mat();

        worker1 = greenCounter;
        worker2 = redCounter;
        worker3 = yellowCounter;
        worker4 = blueCounter;

        System.out.println("Start Scanning...");
        capture = new VideoCapture(0);
        while (bScanning) {
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
                    input = greenCounter.draw(input);
                    imShow.showImage(input);
                    imgBlue.release();
                    imgGreen.release();
                    imgRed.release();
                    imgYellow.release();
                    input.release();
                    output.release();
                }

            }
        }
        System.out.println("Stop Scanning.");
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

        capture = new VideoCapture(file);

        System.out.println("Start Scanning...");
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
                    input = blueCounter.draw(input);
                    imShow.showImage(input);
                    imgBlue.release();
                    imgGreen.release();
                    imgRed.release();
                    imgYellow.release();
                    input.release();
                    output.release();
                }
                else {
                    System.out.println(getCounts());
                    capture.open(file);
                    redCounter.resetCount();
                    yellowCounter.resetCount();
                    blueCounter.resetCount();
                    greenCounter.resetCount();
                }
            }
            try {
                Thread.sleep(20);
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
