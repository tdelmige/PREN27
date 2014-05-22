package Controller;

import java.util.Date;
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
        System.out.println(new Date().toString() + ": Harpune.Fire");
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

    // ebenfalls close bei Funnel
	public void Pull() {
        System.out.println(new Date().toString() + ": Harpune.Pull");
        com.Send(Command.MoveTo((short)2, (short)2, (short)40, (short)0, (short)0, (short)0));
    }

    public void MoveLeft(){
        System.out.println(new Date().toString() + ": Harpune.MoveLeft");
        com.Send(Command.MoveTo((short)0, (short)2, (short)40, (short)0, (short)0, (short)0));

    }

    public void MoveRight(){
        System.out.println(new Date().toString() + ": Harpune.MoveRight");
        com.Send(Command.MoveTo((short)0, (short)1, (short)40, (short)0, (short)0, (short)0));

    }

    public void MoveUp(){
        System.out.println(new Date().toString() + ": Harpune.MoveUp");
        com.Send(Command.MoveTo((short)1, (short)2, (short)40, (short)0, (short)0, (short)0));

    }

    public void MoveDown(){
        System.out.println(new Date().toString() + ": Harpune.MoveDown");
        com.Send(Command.MoveTo((short)1, (short)1, (short)40, (short)0, (short)0, (short)0));

    }

    public void MoveAroundLeft(){
        System.out.println(new Date().toString() + ": Harpune.MoveAroundLeft");
        com.Send(Command.MoveTo((short)0, (short)2, (short)40, (short)0, (short)0, (short)0));

    }


    public void MoveAroundRight(){
        System.out.println(new Date().toString() + ": Harpune.MoveAroundRight");
        com.Send(Command.MoveTo((short)0, (short)1, (short)40, (short)0, (short)0, (short)0));

    }
}
