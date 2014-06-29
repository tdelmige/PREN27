package Core;

import Common.*;
import ImageProcessing.*;
import gui.Gui;
import Controller.Aimbot;
import Controller.Command;
import Controller.Funnel;
import Controller.Harpune;
import Controller.Scanner;
import Controller.Tower;
import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.highgui.VideoCapture;
import org.opencv.imgproc.Imgproc;

import javax.swing.*;
import javax.swing.text.AsyncBoxView;
import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

public class RobotController implements GUIListener {

    private RobotController instance;
    private Gui main;
    private KeyboardAnimation keyboard;
    private VideoCapture capture;
    private FilterSet customFilterSet;
    private FilterSet filterSet;
    private FilterPicker filterPicker = null;
    private ManualAim manualAim = null;
    private PropertyManager propertyManager;
    private Scanner scanner;
    private Aimbot aimbot;

    private static Command command;
    private static short comAdr;
    private static String comFunc = "";

    private Tower tower;
    private Harpune harpune;
    private Funnel funnel;

    private Crosshair crosshair;

    private static Logger log = LogManager.getLogger(RobotController.class.getName());

    public static boolean Close = false;
    public static short CamPort = -1;

    static {
        RobotController.comAdr = Command.getComAdr();
    }

    public RobotController() {

        capture = new VideoCapture(CamPort);
        if (capture.isOpened()) {
            log.info("Cam initialisiert");
        } else {
            log.info("Cam nicht initialisiert");
        }

        instance = this;
        command = new Command();

        tower = new Tower(command);
        harpune = new Harpune(command);
        funnel = new Funnel(command);
        propertyManager = new PropertyManager();
        filterSet = propertyManager.getFilterSet();
        customFilterSet = propertyManager.getFilterSet();

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                main = new Gui("test", instance);
                main.init();
                keyboard = new KeyboardAnimation(main.getPanel(), 400, tower, harpune, funnel);
                keyboard.addAction("LEFT", EComAction.TowMoveLeft);
                keyboard.addAction("RIGHT", EComAction.TowMoveRight );
                keyboard.addAction("UP", EComAction.HarLoose);
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

        init();
        //scanner.scanFromFile("PrenJava/res/vid2.m4v");
        //filterPicker.setFile("PrenJava/Res/vid2.m4v");
        //filterPicker.setColorFilter(filterSet.getColorFilter(Color.RED));

        filterPicker = new FilterPicker(capture, this, log);
        filterPicker.setFile("res/pic238.jpg");
        manualAim = new ManualAim(log, capture, this);

        while(!Close) {}
        System.exit(0);
    }

    public static void init(){
        comFunc = "RobotController.init";
        log.info(comFunc);
        for(short i=0; i<4; i++){
            command.Send(Command.InitMove(i, (short) 1),comAdr,comFunc);
        }
    }

    public static void Stop(){
        comFunc = "RobotController.Stop";
        log.info(comFunc);

        for(short i=0; i<4; i++){
            command.Send(Command.StopMove(i, true),comAdr, comFunc);
        }
    }

    public static void Exit(){

        log.info("Close");
        int q = JOptionPane.showConfirmDialog(null, "Anwendung schliessen?", "Exit", JOptionPane.YES_NO_OPTION);

        if (q == 0){
            System.exit(0);}
        else{

        }
    }

    public void updateFilterPickerImages(final Mat orig, final Mat proc) {
        if (SwingUtilities.isEventDispatchThread()) {
            main.setOriginalImage(orig);
            main.setProcessedImage(proc);
        } else try {
            SwingUtilities.invokeAndWait(new Runnable() {
                @Override
                public void run() {
                    main.setOriginalImage(orig);
                    main.setProcessedImage(proc);
                }
            });
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    public void updateManualAimImage(final Mat m) {
        if (SwingUtilities.isEventDispatchThread()) {
            main.setImage(m);
        } else try {
            SwingUtilities.invokeAndWait(new Runnable() {
                @Override
                public void run() {
                    main.setImage(m);
                }
            });
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void startFilterPicker() {
        Thread t = new Thread(filterPicker);
        t.start();
    }

    @Override
    public void stopFilterPicker() {
        filterPicker.stop();
    }

    @Override
    public void startManualAim() {
        Thread t = new Thread(manualAim);
        t.start();
    }

    @Override
    public void stopManualAim() {
        manualAim.stop();
    }

    @Override
    public void setFilter(Color color) {
        if (filterPicker != null) {
            filterPicker.setColorFilter(customFilterSet.getColorFilter(color));
            filterPicker.setGroundFilter(customFilterSet.getColorFilter(Color.GROUND));
            if (color == Color.GROUND)
                filterPicker.setGroundDetection(true);
            else
                filterPicker.setGroundDetection(false);
        }
        main.setColorFilter(customFilterSet.getColorFilter(color));
    }

    @Override
    public void updateFilter(Color color, ColorFilter colorFilter) {
        if (filterPicker != null) {
            filterPicker.setColorFilter(customFilterSet.getColorFilter(color));
        }
        main.setColorFilter(colorFilter);
        customFilterSet.setColorFilter(colorFilter, color);
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
    public void startAutoAim(){

        scanner = new Scanner(customFilterSet, capture, harpune);
        Thread tScanner = new Thread(scanner);
        tScanner.start();

        while(tScanner.isAlive()){
            try {
                Thread.sleep(8);
            } catch (InterruptedException ex) {
                log.error(ex.getMessage());
            }
        }
        harpune.MoveRight(96200);
        harpune.MoveDown(28444);

        CubeCounter counter = scanner.getMostCubeCounter();

        if(counter.getCount() > 0){
            aimbot = new Aimbot(capture, counter, harpune);

            Thread tAimbot = new Thread(aimbot);
            tAimbot.start();

            while(tAimbot.isAlive()){
                try {
                    Thread.sleep(8);
                } catch (InterruptedException ex) {
                    log.error(ex.getMessage());
                }
            }
        }

        //Finish
    }

    @Override
    public void stopAutoAim(){
        scanner.Stop();
        aimbot.Stop = true;
    }

    @Override
    public void save() {
        propertyManager.saveFilterSet(customFilterSet);
        filterSet = propertyManager.getFilterSet();
    }

}