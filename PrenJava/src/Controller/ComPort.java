package Controller;

import jssc.*;

public class ComPort implements SerialPortEventListener {
	
	private String[] ports;
	private SerialPort port;
	public static String PortNr;
	public static int byteLength = 11;
	
	public ComPort(){
		ports = SerialPortList.getPortNames();
		Init(PortNr);
	}
	
	public void Init(String portNumber){
		this.port = new SerialPort(portNumber);
		
        try {
        	port.openPort();//Open serial port
        	port.setParams(SerialPort.BAUDRATE_9600, 
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
        	return port.readBytes(byteLength);//Read 10 bytes from serial port

        }
        catch (SerialPortException ex) {
            System.out.println(ex);
        }
        
        return null;
	}	
	
	public void Write(byte[] msg){
        try {
        	port.writeBytes(msg);//Write data to port

        }
        catch (SerialPortException ex) {
            System.out.println(ex);
        }
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
            if(event.getEventValue() == byteLength){//Check bytes count in the input buffer
                //Read data, if 10 bytes available 
                try {
                    byte buffer[] = port.readBytes(byteLength);
                }
                catch (SerialPortException ex) {
                    System.out.println(ex);
                }
            }
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
}
