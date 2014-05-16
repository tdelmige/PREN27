package Controller;

import java.util.Date;

public class Funnel {
	
	private Command com;
    private short delay = 2000;
	
	public Funnel(Command com) {
		this.com = com;
		
	}
	
	
	public void Open(){
        System.out.println(new Date().toString() + ": Funnel.Open");

        com.Send(Command.DCMove((short)0, 0, 120, true));

    }

    public void Close(){
        System.out.println(new Date().toString() + ": Funnel.Close");

        com.Send(Command.DCMove((short)1, 0, 120, true));

    }
}
