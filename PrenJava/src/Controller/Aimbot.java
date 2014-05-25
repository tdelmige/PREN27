package Controller;

import Common.IMessage;
import Common.IObserver;
import ImageProcessing.*;
import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.highgui.VideoCapture;
import org.opencv.imgproc.Imgproc;

import java.util.*;

public class Aimbot implements Runnable, IObserver<IMessage> {

    private VideoCapture capture;
    private Mat input, output;
    private Crosshair crosshair;
    private CubeCounter counter;
    private TargetZone targetzone;
    private Harpune harpune;

    private boolean waitflag =false;


    public Aimbot(VideoCapture capture, CubeCounter counter, Harpune harpune){

        this.capture = capture;
        this.counter = counter;
        targetzone = new TargetZone(new Size(), 600);

        this.harpune = harpune;
        Command.getComPortInst().addObserver(this);

    }

    public Crosshair getCrosshair() {
        return crosshair;
    }

    public void setCrosshair(Crosshair crosshair) {
        this.crosshair = crosshair;
    }


    @Override
    public void run()
    {
        input = new Mat();
        output = new Mat();

        if (capture.isOpened())
        {
            capture.read(input);
            if (!input.empty())
            {
                Imgproc.cvtColor(input, output, Imgproc.COLOR_BGR2HSV);

                List<Cube> cubes = counter.getCubes(output);
                try {
                    Cube cube = getNextCube(cubes);
                    if (moveToCube(cube, output)){
                        //Todo: Wait for StopSignal && timeout falls kein signal

                        waitForAnswer(30000);

                        harpune.Fire();

                        //Todo: getroffen?

                        // Todo: Überwachungs das Cube auch gezogen wird!
                        harpune.Pull();
                        Thread.sleep(4000);
                    }

                } catch (Exception ex) {
                    ex.printStackTrace();
                }

            }
        }
    }

    private Cube getNextCube(List<Cube> list) throws Exception
    {
        Cube cube = null;

        for(Cube next : list){
            if(targetzone.contains(next)){
                if(cube == null){ cube = next;}
                if(next.getCenter().y < cube.getCenter().y)
                {
                    cube = next;
                }
                if(next.getCenter().y == cube.getCenter().y){
                    if(next.getCenter().x > cube.getCenter().x){
                        cube = next;
                    }
                }

                if( next.getCenter().y == cube.getCenter().y && next.getCenter().y == cube.getCenter().y && next.getCenter().x == cube.getCenter().x )
                {
                    throw new Exception("same Cube");

                }
            }
        }

        return cube;
    }

    private boolean moveToCube(Cube cube, Mat frame){

        double width = frame.size().width;
        double height = frame.size().height;
        double x = cube.getCenter().x - width / 2;
        double y = cube.getCenter().y - height / 2;

        if (x < 0 )
        {
            harpune.MoveLeft((short)((-1) * x));
        }

        if (x > 0 )
        {
            harpune.MoveRight((short) x);
        }

        if (y < 0 )
        {
            harpune.MoveDown((short) ((-1) * y));
        }

        if (y > 0 )
        {
            harpune.MoveUp((short) x);
        }

        if (x == 0 && y == 0){

            System.out.println(new Date().toString() + ": Aimbot.moveToCube: already in Center");
            return true;
        }

        return false;

    }

    @Override
    public void update(IMessage arg) {

        //Todo: Ready Message
        if(true){
            this.waitflag = false;
        }
    }

    private void waitForAnswer(int timeout){
        waitflag = true;

        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                waitflag = false;
            }
        }, timeout);

        while(waitflag == true){}
    }


}
