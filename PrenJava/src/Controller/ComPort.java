package Controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.Date;
import javax.swing.Timer;
import Common.*;
import jssc.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ComPort extends Observable<IMessage> implements SerialPortEventListener {
	
	private String[] ports;
	private SerialPort port;
	public static String PortNr;
	public static final short LengthResponse = 4;
	private Timer timer;	
	private static short timeout = 1000;
	private boolean lock = false;
	private short comAdr;
    private byte[] lastMessage;
    private String comFunc;

    private static Logger log = LogManager.getLogger(ComPort.class.getName());
	
	public ComPort(){
        ports = SerialPortList.getPortNames();
        for(int i = 0; i < ports.length; i++){
            log.info(" Ports: "+ ports[i]);
        }

		Init(PortNr);

		timer = new Timer(timeout, new ActionListener() {
	
			@Override
			public void actionPerformed(ActionEvent ea) {
				lock = false;
				
			}
		});
        timer.setRepeats(false);
        timer.stop();

	}
	
	public void Init(String portNumber){
		this.port = new SerialPort(portNumber);
		
        try {
        	port.openPort();//Open serial port
        	port.setParams(SerialPort.BAUDRATE_115200,
                                 SerialPort.DATABITS_8,
                                 SerialPort.STOPBITS_1,
                                 SerialPort.PARITY_NONE);//Set params. Also you can set params by this string: serialPort.setParams(9600, 8, 1, 0);

            //int mask = SerialPort.MASK_RXCHAR + SerialPort.MASK_CTS + SerialPort.MASK_DSR;//Prepare mask
            //port.setEventsMask(mask);//Set mask
            port.addEventListener(this);//Add SerialPortEventListener

        }
        catch (SerialPortException ex) {
            log.error(ex.getMessage());

        }

	}
	
	public byte[] Read(){
        try {
        	return port.readBytes(LengthResponse);//Read 10 bytes from serial port

        }
        catch (SerialPortException ex) {
            log.error(ex.getMessage());
        }
        
        return null;
	}	
	
	public boolean Write(byte[] msg, short adr, String func)
	{
		if (!this.lock)
		{
	        try {
	        	comAdr = adr;
                comFunc = func;
                log.info("Send: " + Arrays.toString(msg));
	        	port.writeBytes(msg);//Write data to port
	        	this.lock = true;
	        	timer.start();
	
	        }
	        catch (SerialPortException ex) {
                log.error(ex.getMessage());
	        }
	        return true;
        }
		return false;
	}

    private void SendAgain(){
        Write(lastMessage, comAdr, comFunc);
    }
	
	public void ClosePort(){
        try {
        	port.closePort();//Close serial port
        }
        catch (SerialPortException ex) {
            log.error(ex.getMessage());
        }
	}

	@Override
	public void serialEvent(SerialPortEvent event) {
		if(event.isRXCHAR()){//If data is available
            //if(event.getEventValue() == LengthResponse) {//Check bytes count in the input buffer
                //Read data, if 10 bytes available 
                try {
                    //byte[] buffer = port.readBytes(LengthResponse);
                    byte[] buffer = null;
                    buffer = port.readBytes();
                    log.info(" Received = " +  Arrays.toString(buffer));

                    byte ack = buffer[0];
                    byte[] payload = Arrays.copyOfRange(buffer, 1, buffer.length);
                    byte chk = 0;

                    if (ack == 1 && buffer.length == LengthResponse)
                    {
                        this.lock = false;
                        IResponse res = new ResponseImpl(ack,payload,chk);
                        IMessage msg = new MessageImpl(null, res , null, comAdr, comFunc);

                        log.info("Received = ack: " +  res.getAck() + " payload: " + Arrays.toString(res.getPayload()) + " intPayload: "  + msg.getPayload() + " :: comAdr: " + comAdr + " Func: " + comFunc );
                        super.notifyObservers(msg);
                    }
                    // Im Fehlerfahl nochmals senden
                    else
                    {
                        SendAgain();
                    }
                }
                catch (Exception ex) {
                    log.error(ex.getMessage());
                }
            //}
        }
        else if(event.isCTS()){//If CTS line has changed state
            if(event.getEventValue() == 1){//If line is ON
                log.info("CTS - ON");
            }
            else {
                log.info("CTS - OFF");
            }
        }
        else if(event.isDSR()){///If DSR line has changed state
            if(event.getEventValue() == 1){//If line is ON
                log.info("DSR - ON");
            }
            else {
                log.info("DSR - OFF");
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
