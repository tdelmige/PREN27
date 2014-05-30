package Controller;

import Common.IMessage;
import Common.IObserver;

import java.nio.ByteBuffer;

public class Command implements IObserver<IMessage>{
	
	private static final byte InitMove = 0x00;
	private static final byte MoveTo = 0x01;
	private static final byte WaitMoved = 0x02;
	private static final byte IsReady = 0x03;
	private static final byte Move = 0x04;
	private static final byte StopMove = 0x05;
	private static final byte GetAbsPos = 0x06;
	private static final byte SetPin = 0x07;
	private static final byte GetPin = 0x08;
	private static final byte ConfigPin = 0x09;
	
	private static final byte SaveHome = 0x0A;
	private static final byte GoHome = 0x0B;
	private static final byte SaveWayPoint = 0x0C;
	private static final byte MoveToWayPoint = 0x0D;
    private static final byte DCMove = 0x0E;
	
	private static final short  byteLength = 9;
	private static ComPort com = null;
	private static short adr =0;
	
	
	private IMessage lastRespone; 
	private short comAdr;
	
	static 
	{		
		ComPort.PortNr = "COM5";
		com = new ComPort();
		
	}
	
	public Command(){
		//comAdr = ++adr;
		com.addObserver(this);
	}

    public static ComPort getComPortInst() {return com;}
	public static short getComAdr() {return ++adr;}
	
	public void Send(byte[] cmd, short comAdr)
	{

		//�ber event l�sen = answer auswerten
		while(!com.Write(cmd, comAdr))
		{
			//String exception = com.LastResponse();
		}
	}
	
//	private static String Answer(){
//		return com.LastResponse();
//	}

	public static byte[] InitMove(short motor, short dir){
		
		byte[] cmd = new byte[byteLength];
		cmd[0] = InitMove;
		cmd[1] = (byte) motor;
		cmd[2] = (byte) dir;
		

		
		return cmd;
	}
	
	public static byte[] MoveTo(short motor, short dir, int pos, short speed, short acc, short dec){

        byte[] bytes = ByteBuffer.allocate(4).putInt(pos).array();

		byte[] cmd = new byte[byteLength];
		cmd[0] = MoveTo;
		cmd[1] = (byte) motor;
		cmd[2] = (byte) dir;
		cmd[3] = bytes[1];
		cmd[4] = bytes[2];
		cmd[5] = bytes[3];
		cmd[6] = (byte) speed;
		cmd[7] = (byte) acc;
		cmd[8] = (byte) dec;

        for (byte b : cmd) {
            System.out.format("%x ", b);
        }

		return cmd;
	}
	
	public static byte[] WaitMoved (short motor, short timeout){
		
		byte[] cmd = new byte[byteLength];
		cmd[0] = WaitMoved;
		cmd[1] = (byte) motor;
		cmd[2] = (byte) timeout;
		cmd[3] = 0x00;
		
		return cmd;
	}
	
	
	public static byte[] IsReady(short motor){
		
		byte[] cmd = new byte[byteLength];
		cmd[0] = IsReady;
		cmd[1] = (byte) motor;
		
		return cmd;
	}
	 
	 
	public static byte[] Move(short motor, short dir, short speed, short acc, short dec){
		
		byte[] cmd = new byte[byteLength];
		cmd[0] = Move;
		cmd[1] = (byte) motor;
		cmd[2] = (byte) dir;
		cmd[3] = (byte) speed;
		cmd[4] = (byte) acc;
		cmd[5] = (byte) dec;
		for (byte b : cmd) {
            System.out.print(b + " ");
        }
        System.out.println();
		return cmd;
	}
	 
	public static byte[] StopMove(short motor, boolean hardstop){
		
		byte[] cmd = new byte[byteLength];
		cmd[0] = StopMove;
		cmd[1] = (byte) motor;
		cmd[2] = (byte) (hardstop ? 1 : 0);

        for (byte b : cmd) {
            System.out.print(b + " ");
        }
        System.out.println();
		
		return cmd;
	}
	 
	public static byte[] GetAbsPos(short motor){
		
		byte[] cmd = new byte[byteLength];
		cmd[0] = GetAbsPos;
		cmd[1] = (byte) motor;
		
		return cmd;
	}

	public static byte[] SetPin(short pinNr, boolean high){
		
		byte[] cmd = new byte[byteLength];
		cmd[0] = SetPin;
		cmd[1] = (byte) pinNr;
		cmd[2] = (byte) (high ? 1 : 0);
		
		return cmd;
	}	

	public static byte[] GetPin(short pinNr){
		
		byte[] cmd = new byte[byteLength];
		cmd[0] = GetPin;
		cmd[0] = (byte) pinNr;
		
		return cmd;
	}
	
	public static byte[] ConfigPin(short pinNr, boolean output){
		
		byte[] cmd = new byte[byteLength];
		cmd[0] = ConfigPin;
		cmd[1] = (byte) pinNr;
		cmd[2] = (byte) (output ? 1 : 0);
		
		return cmd;
	}
	
	public static byte[] SaveHome(short motor){
		
		byte[] cmd = new byte[byteLength];
		cmd[0] = SaveHome;
		cmd[1] = (byte) motor;
		
		return cmd;
	}
	
	public static byte[] GoHome(short motor){
		
		byte[] cmd = new byte[byteLength];
		cmd[0] = GoHome;
		cmd[1] = (byte) motor;
		
		return cmd;
	}	
	
	public static byte[] SaveWayPoint(short motor){
		
		byte[] cmd = new byte[byteLength];
		cmd[0] = SaveWayPoint;
		cmd[1] = (byte) motor;
		
		return cmd;
	}
	
	public static byte[] MoveToWayPoint(short motor, short wp, short speed, short acc, short dec){
		
		byte[] cmd = new byte[byteLength];
		cmd[0] = MoveToWayPoint;
		cmd[1] = (byte) motor;
		cmd[2] = (byte) wp;
		cmd[3] = (byte) speed;
		cmd[4] = (byte) acc;
		cmd[5] = (byte) dec;
		
		return cmd;
	}

    public static byte[] DCMove(short dir, int time1, int time2, boolean block){

        byte[] cmd = new byte[byteLength];
        cmd[0] = DCMove;
        cmd[1] = (byte) dir;
        cmd[2] = (byte) time1;
        cmd[3] = (byte) time2;
        cmd[4] = (byte) (block ? 1 : 0);

        return cmd;
    }

	@Override
	public void update(IMessage arg) {
        if (arg != null) {
            if (arg.getException() == null && arg.getComAdr() == comAdr) {
                if (arg.getAcknowledge()) {

                }

                //Fehler
                else {
                    String msg = arg.getMessage();
                    System.out.println(msg);
                }
            }
        }
	}

}
