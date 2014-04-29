package gui;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;

import ImageProcessing.FilterSet;

public class Gui {
	
	private JFrame frame;
	private JPanel container;
	private ImagePanel originalImage, processedImage;
	private ControlPanel ctrlPanel;
	private FilterSet filterPicker;
	
	public Gui() {
		frame = new JFrame("Main");
		container = new JPanel();
		container.setLayout(new BoxLayout(container ,BoxLayout.PAGE_AXIS));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(1400, 800);
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
		ctrlPanel = new ControlPanel();
		originalImage = new ImagePanel();
		processedImage = new ImagePanel();
		container.add(ctrlPanel);
		container.add(originalImage);
		container.add(processedImage);
		frame.add(container);
		frame.setVisible(true);
		
		filterPicker = new FilterSet();
		ctrlPanel.setfilterPicker(filterPicker);
	}

}
