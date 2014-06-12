package Controller;

import java.util.Date;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

public class Funnel {
	
	private Command com = null;
    private short comAdr = 0;
    private short delay = 2000;
    private String comFunc = "";

    private static Logger log = LogManager.getLogger(Funnel.class.getName());

	public Funnel(Command com) {
		this.com = com;
        this.comAdr = Command.getComAdr();
		
	}

    public short GetComAdr(){return comAdr;}
	
	public void Open(){
        comFunc = "Funnel.Open";
        log.info(comFunc);
        com.Send(Command.DCMove((short)0, 0, 120, true),comAdr, comFunc);
    }

    public void Close(){
        comFunc = "Funnel.Close";
        log.info(comFunc);
        com.Send(Command.DCMove((short)1, 0, 120, true),comAdr, comFunc);
    }

}
