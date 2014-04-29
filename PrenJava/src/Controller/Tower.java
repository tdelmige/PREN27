package Controller;

public class Tower {
	
	private Command com;
	
	public Tower(Command com) {
		this.com = com;
		
	}
	
	public void MoveRight(){
		
		com.Send(Command.MoveTo((short)1, (short)1, (short)40, (short)0, (short)0, (short)0));
	}
	
	public void MoveLeft(){}
}
