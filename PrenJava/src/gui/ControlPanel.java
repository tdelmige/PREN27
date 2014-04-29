package gui;

import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class ControlPanel extends JPanel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JButton btnUp, btnDown, btnLeft, btnRight;
	private JButton btnShoot, btnPull;
	private JTextField txtStep;
	
	public ControlPanel() {
		super();
		this.setLayout(new GridLayout(3, 5, 10, 10));
		initControls();
	}
	
	private void initControls() {
		Dimension btnSize = new Dimension(30, 20);
		
		btnUp = new JButton("Up");
		btnUp.setSize(btnSize);
		btnDown = new JButton("Down");
		btnDown.setSize(btnSize);
		btnLeft = new JButton("Left");
		btnLeft.setSize(btnSize);
		btnRight = new JButton("Right");
		btnRight.setSize(btnSize);
		btnShoot = new JButton("Shoot");
		btnShoot.setSize(btnSize);
		btnPull = new JButton("Pull");
		btnPull.setSize(btnSize);
		txtStep = new JTextField();
		txtStep.setText("10");
		
		this.add(new JLabel(""));
		this.add(btnUp);
		this.add(new JLabel(""));
		this.add(new JLabel(""));
		this.add(new JLabel(""));
		
		this.add(btnLeft);
		this.add(txtStep);
		this.add(btnRight);
		this.add(new JLabel(""));
		this.add(btnShoot);
		
		this.add(new JLabel(""));
		this.add(btnDown);
		this.add(new JLabel(""));
		this.add(new JLabel(""));
		this.add(btnPull);
	}

}
