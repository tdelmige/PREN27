package ImageProcessing;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.image.BufferedImage;
import java.beans.PropertyVetoException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Filter;
import java.util.logging.LogRecord;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.plaf.SliderUI;

import org.opencv.*;
import org.opencv.core.Mat;
import org.opencv.core.Scalar;
import org.opencv.highgui.VideoCapture;

public class FilterPicker
{
	
	private static JFrame frame;
	private static OCVPanel ovcPanel;
	
	private static JTextField txtlHue;
	private static JSlider slidlHue;
	private static JTextField txtlSat;
	private static JSlider slidlSat;
	private static JTextField txtlVal;
	private static JSlider slidlVal;
	private static JTextField txtuHue;
	private static JSlider sliduHue;
	private static JTextField txtuSat;
	private static JSlider sliduSat;
	private static JTextField txtuVal;
	private static JSlider sliduVal;
	private static JComboBox<String> cboColor;
	private static JComboBox<String> cboProfil;
	private static Boolean bTarget = false;
	private static Boolean bRun = true;
	private static Properties config;
	private static CubeCounter cubeCounter;
	
	public static void main(String arg[])
	{
		config = new Properties();
		try {
			config.load(new FileInputStream("res//config.properties"));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		frame = new JFrame("BasicPanel");
		//frame.setLayout(new BorderLayout());
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.addWindowListener(new WindowListener() {
			
			@Override
			public void windowOpened(WindowEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void windowIconified(WindowEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void windowDeiconified(WindowEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void windowDeactivated(WindowEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void windowClosing(WindowEvent arg0) {
				
				bRun = false;
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
			
			@Override
			public void windowClosed(WindowEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void windowActivated(WindowEvent arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		frame.setSize(1400,800);
		ovcPanel = new OCVPanel();
		ovcPanel.setSize(1400,600);
		//frame.setContentPane(panel);		
		frame.add(getPanel(),BorderLayout.NORTH);
		frame.add(ovcPanel,BorderLayout.CENTER);
		frame.setVisible(true);
		//frame.revalidate();
		getProperties();
		cubeCounter = new CubeCounter(new ColorFilter(new Scalar(0,0,0), new Scalar(0,0,0)), 400);
		startCam();
	}
	
	private static JPanel getPanel(){
		
		JPanel panel = new JPanel();
		panel.setLayout(new FlowLayout());
		panel.setPreferredSize(new Dimension(1400,100));
		
		
		JLabel lbl1 = new JLabel("TargetSystem: ");
		ButtonGroup radOption = new ButtonGroup();
		JRadioButton radOn = new JRadioButton("On");
		radOn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				bTarget = true;
				
			}
		});
		JRadioButton radOff = new JRadioButton("Off");
		radOff.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				bTarget = false;
				
			}
		});
		radOption.add(radOn);
		radOption.add(radOff);
		panel.add(lbl1);
		panel.add(radOn);
		panel.add(radOff);
		
		JLabel lbl2 = new JLabel("Profil: ");
		cboProfil = new JComboBox<String>();
		cboProfil.addItem("");
		cboProfil.addItem("Normal");
		cboProfil.addItem("Dunkel");
		cboProfil.addItem("Hell");
		cboProfil.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				getProperties();
				
			}
		});
		
		panel.add(lbl2);
		panel.add(cboProfil);
		
		JLabel lbl3 = new JLabel("Color: ");
		cboColor = new JComboBox<String>();
		cboColor.addItem("Blau");
		cboColor.addItem("Grün");
		cboColor.addItem("RotU");
		cboColor.addItem("RotL");
		cboColor.addItem("Gelb");
		cboColor.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				getProperties();
				
			}
		});
		
		JButton btnSave = new JButton();
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
		
		btnSave.setText("Save");
		panel.add(lbl3);
		panel.add(cboColor);
		panel.add(btnSave);
		
		JLabel lbllHue = new JLabel("lHue");
		txtlHue = new JTextField();
		txtlHue.setPreferredSize(new Dimension(30,20));
		txtlHue.addFocusListener(new FocusListener() {
			
			@Override
			public void focusLost(FocusEvent arg0) {
				slidlHue.setValue(Integer.valueOf(txtlHue.getText()));
			}
			
			@Override
			public void focusGained(FocusEvent arg0) {
				// TODO Auto-generated method stub
				
			}
		});
			
		slidlHue = new JSlider(0,255);
		slidlHue.setMinimumSize(new Dimension(600, 20));
		slidlHue.addChangeListener(new ChangeListener() {
			
			@Override
			public void stateChanged(ChangeEvent arg0) {
				// TODO Auto-generated method stub
				txtlHue.setText(String.valueOf(slidlHue.getValue()));
			}
		});

		JPanel panellHue = new JPanel(new FlowLayout());
		panellHue.setMinimumSize(new Dimension(1080, 20));
		panellHue.add(lbllHue);
		panellHue.add(slidlHue);
		panellHue.add(txtlHue);
		panel.add(panellHue);
		
		
		JLabel lbllSat = new JLabel("lSat");
		txtlSat = new JTextField();
		txtlSat.setPreferredSize(new Dimension(30,20));
		txtlSat.addFocusListener(new FocusListener() {
			
			@Override
			public void focusLost(FocusEvent arg0) {
				slidlSat.setValue(Integer.valueOf(txtlSat.getText()));
			}
			
			@Override
			public void focusGained(FocusEvent arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		slidlSat = new JSlider(0,255);
		slidlSat.setMinimumSize(new Dimension(600, 20));
		slidlSat.addChangeListener(new ChangeListener() {
			
			@Override
			public void stateChanged(ChangeEvent arg0) {
				// TODO Auto-generated method stub
				txtlSat.setText(String.valueOf(slidlSat.getValue()));
			}
		});

		JPanel panellSat = new JPanel(new FlowLayout());
		panellSat.setMinimumSize(new Dimension(1080, 20));
		panellSat.add(lbllSat);
		panellSat.add(slidlSat);
		panellSat.add(txtlSat);
		panel.add(panellSat);


		JLabel lbllVal = new JLabel("lVal");
		txtlVal = new JTextField();
		txtlVal.setPreferredSize(new Dimension(30,20));
		txtlVal.addFocusListener(new FocusListener() {
			
			@Override
			public void focusLost(FocusEvent arg0) {
				slidlVal.setValue(Integer.valueOf(txtlVal.getText()));
			}
			
			@Override
			public void focusGained(FocusEvent arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		slidlVal = new JSlider(0,255);
		slidlVal.setMinimumSize(new Dimension(600, 20));
		slidlVal.addChangeListener(new ChangeListener() {
			
			@Override
			public void stateChanged(ChangeEvent arg0) {
				// TODO Auto-generated method stub
				txtlVal.setText(String.valueOf(slidlVal.getValue()));
			}
		});

		JPanel panellVal = new JPanel(new FlowLayout());
		panellVal.setMinimumSize(new Dimension(1080, 20));
		panellVal.add(lbllVal);
		panellVal.add(slidlVal);
		panellVal.add(txtlVal);
		panel.add(panellVal);
		
		
		JLabel lbluHue = new JLabel("uHue");
		txtuHue = new JTextField();
		txtuHue.setPreferredSize(new Dimension(30,20));
		txtuHue.addFocusListener(new FocusListener() {
			
			@Override
			public void focusLost(FocusEvent arg0) {
				sliduHue.setValue(Integer.valueOf(txtuHue.getText()));
			}
			
			@Override
			public void focusGained(FocusEvent arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		sliduHue = new JSlider(0,255);
		sliduHue.setMinimumSize(new Dimension(600, 20));
		sliduHue.addChangeListener(new ChangeListener() {
			
			@Override
			public void stateChanged(ChangeEvent arg0) {
				// TODO Auto-generated method stub
				txtuHue.setText(String.valueOf(sliduHue.getValue()));
			}
		});

		JPanel paneluHue = new JPanel(new FlowLayout());
		paneluHue.setMinimumSize(new Dimension(1080, 20));
		paneluHue.add(lbluHue);
		paneluHue.add(sliduHue);
		paneluHue.add(txtuHue);
		panel.add(paneluHue);

		
		JLabel lbluSat = new JLabel("uSat");
		txtuSat = new JTextField();
		txtuSat.setPreferredSize(new Dimension(30,20));
		txtuSat.addFocusListener(new FocusListener() {
			
			@Override
			public void focusLost(FocusEvent arg0) {
				sliduSat.setValue(Integer.valueOf(txtuSat.getText()));
			}
			
			@Override
			public void focusGained(FocusEvent arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		sliduSat = new JSlider(0,255);
		sliduSat.setMinimumSize(new Dimension(600, 20));
		sliduSat.addChangeListener(new ChangeListener() {
			
			@Override
			public void stateChanged(ChangeEvent arg0) {
				// TODO Auto-generated method stub
				txtuSat.setText(String.valueOf(sliduSat.getValue()));
			}
		});

		JPanel paneluSat = new JPanel(new FlowLayout());
		paneluSat.setMinimumSize(new Dimension(1080, 20));
		paneluSat.add(lbluSat);
		paneluSat.add(sliduSat);
		paneluSat.add(txtuSat);
		panel.add(paneluSat);

		
		JLabel lbluVal = new JLabel("uVal");
		txtuVal = new JTextField();
		txtuVal.setPreferredSize(new Dimension(30,20));
		txtuVal.addFocusListener(new FocusListener() {
			
			@Override
			public void focusLost(FocusEvent arg0) {
				sliduVal.setValue(Integer.valueOf(txtuVal.getText()));
			}
			
			@Override
			public void focusGained(FocusEvent arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		sliduVal = new JSlider(0,255);
		sliduVal.setMinimumSize(new Dimension(600, 20));
		sliduVal.addChangeListener(new ChangeListener() {
			
			@Override
			public void stateChanged(ChangeEvent arg0) {
				// TODO Auto-generated method stub
				txtuVal.setText(String.valueOf(sliduVal.getValue()));
			}
		});

		JPanel paneluVal = new JPanel(new FlowLayout());
		paneluVal.setMinimumSize(new Dimension(1080, 20));
		paneluVal.add(lbluVal);
		paneluVal.add(sliduVal);
		paneluVal.add(txtuVal);
		panel.add(paneluVal);
		
		panel.setBackground(Color.GRAY);

		
		//panel.revalidate();
		return panel;
	}
	
	
	private static void startCam(){
		
		Mat webcam_image=new Mat();
		BufferedImage temp;
		VideoCapture capture =new VideoCapture(0);
	
		if( capture.isOpened())
		{
			while( bRun )
			{
				capture.read(webcam_image);
				
				if( !webcam_image.empty() )
				{
					//frame.setSize(webcam_image.width()+40,webcam_image.height()+60);
					
					temp= ovcPanel.matToBufferedImage(webcam_image);
					
				
					double dLHue = slidlHue.getValue();
					double dLVal = slidlVal.getValue();
					double dLSat = slidlSat.getValue();
				    double dUHue = sliduHue.getValue();
				    double dUVal = sliduVal.getValue();
				    double dUSat = sliduSat.getValue();
				    
					Scalar lScalar = new Scalar(dLHue, dLVal, dLSat);
					Scalar uScalar = new Scalar(dUHue, dUVal, dUSat);
				
					ColorFilter filter = new ColorFilter(lScalar, uScalar);
					BufferedImage mask = ovcPanel.matToBufferedImage(filter.filter(webcam_image));
					ovcPanel.setMask(mask);
					

					//ovcPanel.repaint();
					
					if (bTarget)
					{
						cubeCounter.setFilter(filter);
						cubeCounter.analyze(webcam_image);
						
						ovcPanel.setImage(ovcPanel.matToBufferedImage(cubeCounter.getImage())); 
					}
					else
					{
						ovcPanel.setImage(temp);
					}
					
					
				}
				else
				{
					System.out.println(" --(!) No captured frame -- Break!");
					break;
				}
				
				
			}
		}
		
	}
	
	private static void setProperties(){
		
		String profil = (String)cboProfil.getSelectedItem();
		String color = (String)cboColor.getSelectedItem();
		config.setProperty(profil + color + " lHue", txtlHue.getText());
		config.setProperty(profil + color + " lVal", txtlVal.getText());
		config.setProperty(profil + color + " lSat", txtlSat.getText());
		config.setProperty(profil + color + " uHue", txtuHue.getText());
		config.setProperty(profil + color + " uVal", txtuVal.getText());
		config.setProperty(profil + color + " uSat", txtuSat.getText());
	}
	private static void getProperties(){
		
		String profil = (String)cboProfil.getSelectedItem();
		String color = (String)cboColor.getSelectedItem();
		try {
			slidlHue.setValue(Integer.valueOf(config.getProperty(profil + color + " lHue")));
			slidlVal.setValue(Integer.valueOf(config.getProperty(profil + color + " lVal")));
			slidlSat.setValue(Integer.valueOf(config.getProperty(profil + color + " lSat")));
			sliduHue.setValue(Integer.valueOf(config.getProperty(profil + color + " uHue")));
			sliduVal.setValue(Integer.valueOf(config.getProperty(profil + color + " uVal")));
			sliduSat.setValue(Integer.valueOf(config.getProperty(profil + color + " uSat")));
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		}
	}
}
