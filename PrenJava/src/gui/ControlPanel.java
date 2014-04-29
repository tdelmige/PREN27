package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import ImageProcessing.ColorFilter;
import ImageProcessing.FilterSet;

import org.opencv.core.Scalar;

public class ControlPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private JPanel panCtrl, panHueLo, panHueUp, panSatLo, panSatUp, panValLo, panValUp; 
	private JLabel targetLabel, profilLabel, colorLabel; 
	private JLabel lblHueLo, lblHueUp, lblSatLo, lblSatUp, lblValLo, lblValUp; 
	private JTextField txtHueLo, txtHueUp, txtSatLo, txtSatUp, txtValLo, txtValUp; 
	private JSlider slidHueLo, slidHueUp, slidSatLo, slidSatUp, slidValLo, slidValUp; 
	private JComboBox<String> cboProfiles, cboColors;
	private ButtonGroup radOption;
	private JRadioButton radOn, radOff;
	private JButton btnSave;
	
	private Properties config;
	private boolean bTarget = false;
	private FilterSet filterPicker;
	private ColorFilter redFilter, greenFilter, blueFilter, yellowFilter;
	
	public ControlPanel() {
		super();
		this.setSize(1400, 600);
		this.setLayout(new FlowLayout());
		config = new Properties();
		initControls();
		
		Scalar temp = new Scalar(0, 0, 0);
		redFilter = new ColorFilter(temp, temp);
		blueFilter = new ColorFilter(temp, temp);
		yellowFilter = new ColorFilter(temp, temp);
		greenFilter = new ColorFilter(temp, temp);
		
		this.add(panCtrl);
	}
	
	private void initControls() {
		
		panCtrl = new JPanel();
		panHueLo = new JPanel();
		panHueUp = new JPanel();
		panSatLo = new JPanel();
		panSatUp = new JPanel();
		panValLo = new JPanel();
		panValUp = new JPanel();
		
		targetLabel = new JLabel("Target System: ");
		profilLabel = new JLabel("Profil: ");
		colorLabel = new JLabel("Color: ");
		
		slidHueLo = new JSlider(0, 180);
		slidHueLo.setMinimumSize(new Dimension(600, 20));
		slidHueLo.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent arg0) {
				// TODO Auto-generated method stub
				txtHueLo.setText(String.valueOf(slidHueLo.getValue()));
			}
		});
		
		slidHueUp = new JSlider(0, 180);
		slidHueUp.setMinimumSize(new Dimension(600, 20));
		slidHueUp.addChangeListener(new ChangeListener(){
			@Override
			public void stateChanged(ChangeEvent arg0) {
				// TODO Auto-generated method stub
				txtHueUp.setText(String.valueOf(slidHueUp.getValue()));
			}
		});
		
		slidSatLo = new JSlider(0, 255);
		slidSatLo.setMinimumSize(new Dimension(600,20));
		slidSatLo.addChangeListener(new ChangeListener(){
			@Override
			public void stateChanged(ChangeEvent arg0) {
				// TODO Auto-generated method stub
				txtSatLo.setText(String.valueOf(slidSatLo.getValue()));
			}
		});
		
		slidSatUp = new JSlider(0, 255);
		slidSatUp.setMinimumSize(new Dimension(600, 20));
		slidSatUp.addChangeListener(new ChangeListener(){
			@Override
			public void stateChanged(ChangeEvent arg0) {
				// TODO Auto-generated method stub
				txtSatUp.setText(String.valueOf(slidSatUp.getValue()));
			}
		});
		
		slidValLo = new JSlider(0, 255);
		slidValLo.setMinimumSize(new Dimension(600, 20));
		slidValLo.addChangeListener(new ChangeListener(){
			@Override
			public void stateChanged(ChangeEvent arg0) {
				// TODO Auto-generated method stub
				txtValLo.setText(String.valueOf(slidValLo.getValue()));
			}
		});
		
		slidValUp = new JSlider(0, 255);
		slidValUp.setMinimumSize(new Dimension(600, 20));
		slidValUp.addChangeListener(new ChangeListener(){
			@Override
			public void stateChanged(ChangeEvent arg0) {
				// TODO Auto-generated method stub
				txtValUp.setText(String.valueOf(slidValUp.getValue()));
			}
		});
		
		cboProfiles = new JComboBox<String>();
		cboProfiles.addItem("");
		cboProfiles.addItem("Normal");
		cboProfiles.addItem("Dunkel");
		cboProfiles.addItem("Hell");
		cboProfiles.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0){
				getProperties();
			}
		});
		
		cboColors = new JComboBox<String>();
		cboColors.addItem("Blau");
		cboColors.addItem("Gruen");
		cboColors.addItem("RotU");
		cboColors.addItem("RotL");
		cboColors.addItem("Gelb");
		cboColors.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				getProperties();
			}
		});
		
		lblHueLo = new JLabel("lHue: ");
		lblHueUp = new JLabel("uHue: ");
		lblSatLo = new JLabel("lSat: ");
		lblSatUp = new JLabel("uSat: ");
		lblValLo = new JLabel("lSat: ");
		lblValUp = new JLabel("uSat: ");
		
		txtHueLo = new JTextField();
		txtHueLo.setPreferredSize(new Dimension(30, 20));
		txtHueLo.addFocusListener(new FocusListener() {
			@Override
			public void focusLost(FocusEvent arg0){
				slidHueLo.setValue(Integer.valueOf(txtHueLo.getText()));
			}
			@Override
			public void focusGained(FocusEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		
		txtHueUp = new JTextField();
		txtHueUp.setPreferredSize(new Dimension(30, 20));
		txtHueUp.addFocusListener(new FocusListener() {
			@Override
			public void focusLost(FocusEvent arg0){
				slidHueUp.setValue(Integer.valueOf(txtHueLo.getText()));
			}
			@Override
			public void focusGained(FocusEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		
		txtSatLo = new JTextField();
		txtSatLo.setPreferredSize(new Dimension(30, 20));
		txtSatLo.addFocusListener(new FocusListener() {
			@Override
			public void focusGained(FocusEvent e) {
				// TODO Auto-generated method stub
				
			}
			@Override
			public void focusLost(FocusEvent e) {
				slidSatLo.setValue(Integer.valueOf(txtSatLo.getText()));
				
			}
		});
			
		txtSatUp = new JTextField();
		txtSatUp.setPreferredSize(new Dimension(30, 20));
		txtSatUp.addFocusListener(new FocusListener() {
			@Override
			public void focusGained(FocusEvent e) {
				// TODO Auto-generated method stub
				
			}
			@Override
			public void focusLost(FocusEvent e) {
				slidSatUp.setValue(Integer.valueOf(txtSatUp.getText()));
				
			}
		});
		
		txtValLo = new JTextField();
		txtValLo.setPreferredSize(new Dimension(30, 20));
		txtValLo.addFocusListener(new FocusListener() {
			@Override
			public void focusGained(FocusEvent e) {
				// TODO Auto-generated method stub
				
			}
			@Override
			public void focusLost(FocusEvent e) {
				slidValLo.setValue(Integer.valueOf(txtValLo.getText()));
				
			}
		});
		
		txtValUp = new JTextField();
		txtValUp.setPreferredSize(new Dimension(30, 20));
		txtValUp.addFocusListener(new FocusListener() {
			@Override
			public void focusGained(FocusEvent e) {
				// TODO Auto-generated method stub
				
			}
			@Override
			public void focusLost(FocusEvent e) {
				slidValUp.setValue(Integer.valueOf(txtValUp.getText()));
			}
		});
		
		radOn = new JRadioButton("On");
		radOn.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				bTarget = true;
			}
		});
		
		radOff = new JRadioButton("Off");
		radOff.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				bTarget = false;
			}
		});
		
		radOption = new ButtonGroup();
		radOption.add(radOn);
		radOption.add(radOff);
		
		btnSave = new JButton("Save");
		btnSave.addActionListener(new ActionListener() {	
			@Override
			public void actionPerformed(ActionEvent arg0) {
				setProperties();
				try {
					config.store(new FileOutputStream("res//config.properties"), null);
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		});
		
		// Control Panel
		panCtrl.setLayout(new FlowLayout());
		panCtrl.setBackground(Color.GRAY);
		panCtrl.setPreferredSize(new Dimension(1400, 100));
		panCtrl.add(targetLabel);
		panCtrl.add(radOn);
		panCtrl.add(radOff);
		panCtrl.add(profilLabel);
		panCtrl.add(cboProfiles);
		panCtrl.add(colorLabel);
		panCtrl.add(cboColors);
		panCtrl.add(btnSave);
		
		// HueLo Panel
		panHueLo.setMinimumSize(new Dimension(1080, 20));
		panHueLo.add(lblHueLo);
		panHueLo.add(slidHueLo);
		panHueLo.add(txtHueLo);
		panCtrl.add(panHueLo);
		
		// SatLo Panel
		panSatLo.setMinimumSize(new Dimension(1080, 20));
		panSatLo.add(lblSatLo);
		panSatLo.add(slidSatLo);
		panSatLo.add(txtSatLo);
		panCtrl.add(panSatLo);
		
		// ValLo Panel
		panValLo.setMinimumSize(new Dimension(1080, 20));
		panValLo.add(lblValLo);
		panValLo.add(slidValLo);
		panValLo.add(txtValLo);
		panCtrl.add(panValLo);

		// HueUp Panel
		panHueUp.setMinimumSize(new Dimension(1080, 20));
		panHueUp.add(lblHueUp);
		panHueUp.add(slidHueUp);
		panHueUp.add(txtHueUp);
		panCtrl.add(panHueUp);
		
		// SatUp Panel
		panSatUp.setMinimumSize(new Dimension(1080, 20));
		panSatUp.add(lblSatUp);
		panSatUp.add(slidSatUp);
		panSatUp.add(txtSatUp);
		panCtrl.add(panSatUp);
		
		// ValUp Panel
		panValUp.setMinimumSize(new Dimension(1080, 20));
		panValUp.add(lblValUp);
		panValUp.add(slidValUp);
		panValUp.add(txtValUp);
		panCtrl.add(panValUp);
	}
	
	private void getProperties() {
		
	}
	
	private void setProperties() {
		
	}
	
	public void setfilterPicker(FilterSet filterPicker) {
		this.filterPicker = filterPicker;
	}
	
	public FilterSet getFilterPicker() {
		return filterPicker;
	}
	
}
