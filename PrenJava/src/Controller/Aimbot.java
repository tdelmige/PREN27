package Controller;

import Common.IMessage;
import Common.IObserver;
import ImageProcessing.*;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.opencv.core.Mat;
import org.opencv.core.Point;
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
    private int moveTo;

    private boolean waitflag =false;
    public boolean Stop = false;

    private static Logger log = LogManager.getLogger(Aimbot.class.getName());

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
            while(!Stop)
            {
                capture.read(input);
                if (!input.empty())
                {
                    Imgproc.cvtColor(input, output, Imgproc.COLOR_BGR2HSV);

                    List<Cube> cubes = counter.getCubes(output);
                    try {
                        Cube cube = getNextCube(cubes);

                        if (cube != null){
                            if (moveToCube(cube, output)){
                                //Todo: Wait for StopSignal && timeout falls kein signal

                                //Harpune wartet in den Move Befehlen
                                //waitForAnswer(30000);

                                harpune.Loose();
                                harpune.Fire();

                                //Todo: getroffen?

                                // Todo: Überwachungs das Cube auch gezogen wird!
                                harpune.Pull();
                                Thread.sleep(8000);
                            }
                        }

                        else{ moveRight(20000); }

                    } catch (Exception ex) {
                        log.error(ex.getMessage());
                    }
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

            log.info("Already in Center");
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

    private boolean moveRight(int steps){

        moveTo = harpune.GetPosHorizontal() + steps;
        if (moveTo > 0){ return false;}

        harpune.MoveRight(moveTo);

        return true;
    }


}
