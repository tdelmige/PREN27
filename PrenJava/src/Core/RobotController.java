package Core;

import Common.EComAction;
import Common.GUIListener;
import ImageProcessing.ColorFilter;
import ImageProcessing.Crosshair;
import ImageProcessing.PropertyManager;
import gui.Gui;
import Controller.Aimbot;
import Controller.Command;
import Controller.Funnel;
import Controller.Harpune;
import Controller.Scanner;
import Controller.Tower;
import ImageProcessing.FilterSet;
import Common.KeyboardAnimation;
import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.highgui.VideoCapture;
import org.opencv.imgproc.Imgproc;

import javax.swing.*;
import java.util.Date;

public class RobotController implements GUIListener {

    private RobotController instance;
    private Gui main;
    private KeyboardAnimation keyboard;
    private VideoCapture capture;
    private FilterSet filterSet;
    private FilterPicker filterPicker;
    private ManualAim manualAim;
    private PropertyManager propertyManager;

    private Command command;
    private Tower tower;
    private Harpune harpune;
    private Funnel funnel;

    private Crosshair crosshair;

    public static boolean Close = false;
    public static short CamPort = 0;

    public RobotController() {
        instance = this;
        command = new Command();
        tower = new Tower(command);
        harpune = new Harpune(command);
        funnel = new Funnel(command);
        propertyManager = new PropertyManager();

        filterSet = propertyManager.getFilterSet("Normal");

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                main = new Gui("test");
                main.setListener(instance);
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
                keyboard.addAction("U", EComAction.FunClose);

                keyboard.addAction("ESCAPE", EComAction.EXIT );
                keyboard.addAction("ENTER", EComAction.STOP );
            }
        });


        while(!Close){

        }
        //InitMotors();

    }

    @Override
    public void startFilterPicker() {
        filterPicker = new FilterPicker();
        filterPicker.setColorFilter(filterSet.getRedFilter());
        Thread t = new Thread(filterPicker);
        t.start();
    }

    @Override
    public void stopFilterPicker() {
        filterPicker.stop();
    }

    private class FilterPicker implements Runnable{

        private ColorFilter colorFilter;
        private Mat input;
        private Mat output;
        private boolean bRun = false;
        private Size size = new Size(400, 300);

        public FilterPicker() {}

        public ColorFilter getColorFilter() {
            return colorFilter;
        }

        public void setColorFilter(ColorFilter colorFilter) {
            this.colorFilter = colorFilter;
        }

        public void stop() {
            bRun = false;
        }

        @Override
        public void run() {
            bRun = true;
            try {
                capture = new VideoCapture(0);
                input = new Mat();
                output = new Mat();
                while(bRun) {
                    capture.read(input);
                    if (!input.empty()) {
                        Imgproc.cvtColor(input, output, Imgproc.COLOR_BGR2HSV);
                        output = colorFilter.filter(output);
                        Imgproc.resize(input, input, size);
                        Imgproc.resize(output, output, size);
                        if (SwingUtilities.isEventDispatchThread()) {
                            main.setOriginalImage(input);
                            main.setProcessedImage(output);
                        } else {
                            SwingUtilities.invokeAndWait( new Runnable() {
                                @Override
                                public void run() {
                                    main.setOriginalImage(input);
                                    main.setProcessedImage(output);
                                }
                            });
                        }
                    }
                }
            } catch (Exception e) {
                System.out.println(e.getLocalizedMessage());
            }
        }
    }

    @Override
    public void startManualAim() {
        manualAim = new ManualAim();
        Thread t = new Thread(manualAim);
        t.start();
    }

    @Override
    public void stopManualAim() {
        manualAim.stop();
    }

    private class ManualAim implements Runnable {

        private boolean bRun = false;
        private Crosshair crosshair = null;
        private Mat input;
        private Size size = new Size(600, 400);
        private Size crosshairSize = new Size(50,50);

        public void stop() {
            bRun = false;
            input.release();
        }

        public void setCrosshairOffset(int offset) {
            crosshair.setOffset(offset);
        }

        public void setCrosshairSize(Size size) {
            crosshair.setCrossHairSize(size);
        }

        @Override
        public void run() {
            bRun = true;
            try {
                capture = new VideoCapture(CamPort);
                input = new Mat();
                while (bRun) {
                    capture.read(input);
                    if (!input.empty()) {
                        if (crosshair == null) {
                            crosshair = new Crosshair(input.size(), crosshairSize);
                        }
                        crosshair.drawCrosshair(input);
                        Imgproc.resize(input, input, size);
                        if (SwingUtilities.isEventDispatchThread()) {
                            main.setImage(input);
                        } else {
                            SwingUtilities.invokeAndWait( new Runnable() {
                                @Override
                                public void run() {
                                    main.setImage(input);
                                }
                            });
                        }
                    }
                }

            } catch (Exception e) {
                System.out.println(e.getLocalizedMessage());
            }
        }
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

    @Override
    public void setFilter(ColorFilter filter, String profile, String color) {
        if (filterPicker != null) {
            filterPicker.setColorFilter(filter);
        }
    }

    @Override
    public void setCrosshairSize(Size size) {
        if (manualAim != null) {
            manualAim.setCrosshairSize(size);
        }
    }

    @Override
    public void setCrosshairOffset(int offset) {
        if (manualAim != null) {
            manualAim.setCrosshairOffset(offset);
        }
    }

    @Override
    public void save() {

    }
}