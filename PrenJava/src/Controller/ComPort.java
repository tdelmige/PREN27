package Controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.Date;
import java.util.Stack;

import javax.swing.Timer;

import Common.IMessage;
import Common.MessageImpl;
import Common.Observable;
import jssc.*;

public class ComPort extends Observable<IMessage> implements SerialPortEventListener {
	
	private String[] ports;
	private SerialPort port;
	public static String PortNr;
	public static final short LengthResponse = 4;
	private byte[] lastResponse = null;
	private Stack<byte[]> respStack = null;
	private Timer timer;	
	private static short timeout = 50;
	private boolean lock = false;
	private short comAdr;
	
	public ComPort(){
		ports = SerialPortList.getPortNames();
		Init(PortNr);
		respStack = new Stack<byte[]>();
		timer = new Timer(timeout, new ActionListener() {
	
			@Override
			public void actionPerformed(ActionEvent ea) {
				lock = false;
				
			}
		});
		
	}
	
	public void Init(String portNumber){
		this.port = new SerialPort(portNumber);
		
        try {
        	port.openPort();//Open serial port
        	port.setParams(SerialPort.BAUDRATE_115200,
                                 SerialPort.DATABITS_8,
                                 SerialPort.STOPBITS_1,
                                 SerialPort.PARITY_NONE);//Set params. Also you can set params by this string: serialPort.setParams(9600, 8, 1, 0);

            int mask = SerialPort.MASK_RXCHAR + SerialPort.MASK_CTS + SerialPort.MASK_DSR;//Prepare mask
            port.setEventsMask(mask);//Set mask
            port.addEventListener(this);//Add SerialPortEventListener

        }
        catch (SerialPortException ex) {
            System.out.println(ex);
        }

	}
	
	public byte[] Read(){
        try {
        	return port.readBytes(LengthResponse);//Read 10 bytes from serial port

        }
        catch (SerialPortException ex) {
            System.out.println(ex);
        }
        
        return null;
	}	
	
	public boolean Write(byte[] msg, short adr)
	{
		if (!this.lock)
		{
	        try {
	        	comAdr = adr;
	        	port.writeBytes(msg);//Write data to port
	        	this.lock = true;
	        	timer.start();
	
	        }
	        catch (SerialPortException ex) {
	            System.out.println(ex);
	        }
	        return true;
        }
		return false;
	}
	
	public void ClosePort(){
        try {
        	port.closePort();//Close serial port
        }
        catch (SerialPortException ex) {
            System.out.println(ex);
        }
	}

	@Override
	public void serialEvent(SerialPortEvent event) {
		if(event.isRXCHAR()){//If data is available
            //if(event.getEventValue() == LengthResponse) {//Check bytes count in the input buffer
                //Read data, if 10 bytes available 
                try {
                    //byte[] buffer = port.readBytes(LengthResponse);
                    byte[] buffer = port.readBytes();
                    System.out.println(new Date().toString() + ": Received = " +  Arrays.toString(buffer));
                    lastResponse = buffer;
                    respStack.add(buffer);
                    this.lock = false;
                    
                    IMessage msg = new MessageImpl(null, null, null, null, null, comAdr);
                    super.notifyObservers(msg);
                }
                catch (SerialPortException ex) {
                    System.out.println(ex);
                }
            //}
        }
        else if(event.isCTS()){//If CTS line has changed state
            if(event.getEventValue() == 1){//If line is ON
                System.out.println("CTS - ON");
            }
            else {
                System.out.println("CTS - OFF");
            }
        }
        else if(event.isDSR()){///If DSR line has changed state
            if(event.getEventValue() == 1){//If line is ON
                System.out.println("DSR - ON");
            }
            else {
                System.out.println("DSR - OFF");
            }
        }
	}
	
//	public String LastResponse(){
//		String res = "";
//		for (byte b  : lastResponse) {
//			res = res + String.valueOf(b) + "/";
//		}
//	
//		return res;
//	}
//	
//	public boolean Acknowledge(){
//		int ack = lastResponse[0];
//		
//		if (ack == 1 )
//			{ return true;}
//
//		else 
//			{ return false;}
//	}
	
}
