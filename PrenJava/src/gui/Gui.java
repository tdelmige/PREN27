package gui;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JComponent;

import Common.GUIListener;
import ImageProcessing.ColorFilter;
import ImageProcessing.FilterSet;
import org.opencv.core.Mat;

public class Gui extends JComponent {

    private JFrame mainFrame;
    private container panel;
    private GUIListener listener;

    public Gui(String title) {
        mainFrame = new JFrame(title);
        this.panel = new container();
        this.panel.setParent(this);
        mainFrame.setContentPane(panel.getContainer());
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.pack();
        mainFrame.addWindowListener(new WindowListener(){

            @Override
            public void windowOpened(WindowEvent e) {
                // TODO Auto-generated method stub

            }

            @Override
            public void windowClosing(WindowEvent e) {
                // TODO Auto-generated method stub
            }

            @Override
            public void windowClosed(WindowEvent e) {
                // TODO Auto-generated method stub

            }

            @Override
            public void windowIconified(WindowEvent e) {
                // TODO Auto-generated method stub

            }

            @Override
            public void windowDeiconified(WindowEvent e) {
                // TODO Auto-generated method stub

            }

            @Override
            public void windowActivated(WindowEvent e) {
                // TODO Auto-generated method stub

            }

            @Override
            public void windowDeactivated(WindowEvent e) {
                // TODO Auto-generated method stub

            }

        });
        mainFrame.setVisible(true);
    }

    public JPanel getPanel() {
        return panel.getContainer();
    }

    public void setOriginalImage(Mat image){
        panel.showOriginalImage(image);
    }

    public void setProcessedImage(Mat image){
        panel.showProcessedImage(image);
    }

    public void setImage(Mat m) {
        panel.showImage(m);
    }

    public void setListener(GUIListener listener) {
        this.listener = listener;
    }

    public void fireSetFilter(ColorFilter filter) {
        listener.setFilter(filter, null, null);
    }

    public void fireStartFiltering() {listener.startFiltering();}

    public void fireStopFiltering() {listener.stopFiltering();}

}

