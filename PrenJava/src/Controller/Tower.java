package Controller;

import java.util.Date;

public class Tower {
	
	private Command com;
	
	public Tower(Command com) {
		this.com = com;
		
	}

    public void MoveLeft(){
        System.out.println(new Date().toString() + ": Tower.MoveLeft");
        com.Send(Command.MoveTo((short)3, (short)2, (short)40, (short)0, (short)0, (short)0));

    }

	public void MoveRight(){
        System.out.println(new Date().toString() + ": Tower.MoveRight");
        com.Send(Command.MoveTo((short)3, (short)1, (short)40, (short)0, (short)0, (short)0));

	}
}