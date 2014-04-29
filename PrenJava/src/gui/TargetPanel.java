package gui;

import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import ImageProcessing.Crosshair;

public class TargetPanel extends JPanel {
	
	private JTextField txtStep, txtWidth;
	private JButton btnUp, btnDown;
	private JButton btnApply;
	
	private Crosshair crosshair;

	public TargetPanel() {
		super();
		this.setLayout(new GridLayout(3,3,10,10));
		initControls();
	}
	
	private void initControls() {
		txtStep = new JTextField();
		txtWidth = new JTextField();
		btnUp = new JButton("Move up");
		btnDown = new JButton("Move Down");
		btnApply = new JButton("Apply");
		this.add(btnUp);
		this.add(new JLabel(""));
		this.add(txtWidth);
		this.add(btnDown);
		this.add(new JLabel(""));
		this.add(new JLabel(""));
		this.add(txtStep);
		this.add(new JLabel(""));
		this.add(btnApply);
	}
}
