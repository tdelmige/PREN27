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
    private FilterPicker filterPicker;
    private ManualAim manualAim;
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
        filterPicker = new FilterPicker();
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

        while(!Close) {}
        System.exit(0);
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

    private class FilterPicker implements Runnable{

        private ColorFilter colorFilter;
        private Mat input;
        private Mat output;
        private boolean bRun = false;
        private Size size = new Size(400, 300);
        private String file = null;

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

        public void setFile(String file) {
            this.file = file;
        }

        @Override
        public void run() {
            if (file == null) {
                live();
            } else {
                loopFile(file);
            }
        }

        public void live() {
            bRun = true;
            try {
                //capture = new VideoCapture(0);
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
            } catch (Exception ex) {
                log.error(ex.getMessage());
            }
        }

        public void loopFile(String file) {
            bRun = true;
            try {
                capture = new VideoCapture(file);
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
                        Thread.sleep(50);
                    } else {
                        capture.open(file);
                    }
                }
            } catch (Exception ex) {
                log.error(ex.getMessage());
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
                //capture = new VideoCapture(CamPort);
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

            } catch (Exception ex) {
                log.error(ex.getMessage());
            }
        }
    }

   public static void init(){
       comFunc = "RobotController.init";
       log.info(comFunc);
        for(short i=0; i<4; i++){
            command.Send(Command.InitMove(i, (short) 1),comAdr,comFunc);
        }
   }

    public static void Stop()
    {
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

    @Override
    public void setFilter(Color color) {
        if (filterPicker != null) {
            filterPicker.setColorFilter(customFilterSet.getColorFilter(color));
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
        /*
        aimbot = new Aimbot(capture, scanner.getMostCubeCounter(),harpune);
        Thread tAimbot = new Thread(aimbot);
        tAimbot.start();

        while(tAimbot.isAlive()){
            try {
                Thread.sleep(8);
            } catch (InterruptedException ex) {
                log.error(ex.getMessage());
            }
        }
        */
        //Finish
    }

    @Override
    public void stopAutoAim()
    {
        scanner.Stop();
        aimbot.Stop = true;
    }

    @Override
    public void save() {
        propertyManager.saveFilterSet(customFilterSet);
        filterSet = propertyManager.getFilterSet();
    }

    public void start() {
        // 1. init Parameter
        // 1. Tower in Position
        tower.MoveRight();
        // 2. Scanner
        scanner.run();
        // 3. Aimbot
        aimbot.setCrosshair(crosshair);
        aimbot.run();
        // 4. Funnel
        funnel.Open();
        // 5. Tower back to Start
        tower.MoveLeft();
    }
}