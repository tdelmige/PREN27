package Controller;

import java.util.Timer;

public class Harpune {
	
	private short delay = 1000;
	private Command com;
	
	public Harpune(Command com) {
		this.com = com;
		
	}	


	//Abschuss = SetPin 1 High und SetPin 1 Low
	public void Fire()
	{
		Command.SetPin((short)1, true);

		try 
		{
			//Bestromungsdauer
			Thread.sleep(delay);
		} catch(InterruptedException ex) {
		    Thread.currentThread().interrupt();
		}
		
		Command.SetPin((short)1, false);


	}
	
	public void Pull() {}
}
