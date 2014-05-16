package gui;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JComponent;

import ImageProcessing.FilterSet;

public class Gui extends JComponent {
	
	private JFrame frame;
	private JPanel container;
	private JPanel imagePanel;
	private ImagePanel originalImage, processedImage;
	private FilterPanel ctrlPanel;
	private ControlPanel panel;
	private FilterSet filterSet;

    public JPanel getPanel(){return container;}
	
    private nGui gui;

	public Gui() {
		frame = new JFrame("Main");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(1400, 600);
		frame.addWindowListener(new WindowListener(){

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
		container = new JPanel();
		container.setLayout(new BoxLayout(container ,BoxLayout.Y_AXIS));
		imagePanel = new JPanel();
		imagePanel.setLayout(new BoxLayout(imagePanel, BoxLayout.X_AXIS));
		ctrlPanel = new FilterPanel();
		originalImage = new ImagePanel();
		processedImage = new ImagePanel();
		imagePanel.add(originalImage);
		imagePanel.add(processedImage);
		container.add(ctrlPanel);
		container.add(imagePanel);
		frame.add(container);
		panel = new ControlPanel();
		frame.add(panel);
		frame.setVisible(true);
		
	    filterSet = new FilterSet();
		ctrlPanel.setfilterPicker(filterSet);
	}
}
