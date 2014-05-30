package Controller;

import java.util.Date;

public class Funnel {
	
	private Command com = null;
    private short comAdr = 0;
    private short delay = 2000;


	
	public Funnel(Command com) {
		this.com = com;
        this.comAdr = Command.getComAdr();
		
	}

    public short GetComAdr(){return comAdr;}
	
	public void Open(){
        System.out.println(new Date().toString() + ": Funnel.Open");
        com.Send(Command.DCMove((short)0, 0, 120, true),comAdr);
    }

    public void Close(){
        System.out.println(new Date().toString() + ": Funnel.Close");
        com.Send(Command.DCMove((short)1, 0, 120, true),comAdr);
    }

}
