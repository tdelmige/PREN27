package Controller;

import java.util.Date;

public class Funnel {
	
	private Command com = null;
    private short comAdr = 0;
    private short delay = 2000;
    private String comFunc = "";

	
	public Funnel(Command com) {
		this.com = com;
        this.comAdr = Command.getComAdr();
		
	}

    public short GetComAdr(){return comAdr;}
	
	public void Open(){
        comFunc = "Funnel.Open";
        System.out.println(new Date().toString() + ": " + comFunc);
        com.Send(Command.DCMove((short)0, 0, 120, true),comAdr, comFunc);
    }

    public void Close(){
        comFunc = "Funnel.Close";
        System.out.println(new Date().toString() + ": " + comFunc);
        com.Send(Command.DCMove((short)1, 0, 120, true),comAdr, comFunc);
    }

}
