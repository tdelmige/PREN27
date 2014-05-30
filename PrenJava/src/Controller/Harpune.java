package Controller;

import java.util.Date;
import java.util.Timer;

public class Harpune {

    public final short LEFT = 2;
    public final short RIGHT = 1;
    public final short UP = 2;
    public final short DOWN = 1;
    public final short HORIZONTAL = 0;
    public final short VERTICAL = 1;
    public final short PULL = 2;

	private short delay = 1000;
	private Command com;
    private short speed = 50;
    private short acc = 0;
    private short dec = 0;
    private short posPull = 40;
    private short posLoose = 0;

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
        com.Send(Command.MoveTo(PULL, UP, posPull, speed, acc, dec));
    }

    public void Loose() {
        System.out.println(new Date().toString() + ": Harpune.Loose");
        com.Send(Command.MoveTo(PULL, DOWN, posLoose, speed, acc, dec));
    }

    public void stopPullLoose(boolean hardStop) {
        System.out.println(new Date().toString() + ": Harpune.stopPullLosse");
        com.Send(Command.StopMove(PULL, hardStop));
    }

    public void MoveLeft(){
        System.out.println(new Date().toString() + ": Harpune.MoveLeft");
        com.Send(Command.Move(HORIZONTAL, LEFT, speed, acc, dec));
    }

    public void MoveLeft(short pos){
        System.out.println(new Date().toString() + ": Harpune.MoveLeft, steps:" +pos);
        com.Send(Command.MoveTo(HORIZONTAL, LEFT, pos, speed, acc, dec));
    }

    public void MoveRight(){
        System.out.println(new Date().toString() + ": Harpune.MoveRight");
        com.Send(Command.Move(HORIZONTAL, RIGHT, speed, acc, dec));
    }

    public void MoveRight(short pos){
        System.out.println(new Date().toString() + ": Harpune.MoveRight, steps:" +pos);
        com.Send(Command.MoveTo(HORIZONTAL, RIGHT, pos, speed, acc, dec));
    }

    public void MoveAroundLeft(){
        System.out.println(new Date().toString() + ": Harpune.MoveAroundLeft");
        com.Send(Command.Move(HORIZONTAL, LEFT, speed, acc, dec));

    }

    public void MoveAroundRight(){
        System.out.println(new Date().toString() + ": Harpune.MoveAroundRight");
        com.Send(Command.Move(HORIZONTAL, RIGHT, speed, acc, dec));
    }

    public void stopHorizontalMove(boolean hardStop) {
        System.out.println(new Date().toString() + ": Harupune.StopHorizontalMove");
        com.Send(Command.StopMove(HORIZONTAL, hardStop));
    }

    public void MoveUp(){
        System.out.println(new Date().toString() + ": Harpune.MoveUp");
        com.Send(Command.Move(VERTICAL, UP, speed, acc, dec));
    }

    public void MoveUp(short pos){
        System.out.println(new Date().toString() + ": Harpune.MoveUp, steps:" +pos);
        com.Send(Command.MoveTo(VERTICAL, UP, pos, speed, acc, dec));
    }

    public void MoveDown(){
        System.out.println(new Date().toString() + ": Harpune.MoveDown");
        com.Send(Command.Move(VERTICAL, DOWN, speed, acc, dec));

    }

    public void MoveDown(short pos){
        System.out.println(new Date().toString() + ": Harpune.MoveDown, steps:" +pos);
        com.Send(Command.MoveTo(VERTICAL, UP, pos, speed, acc, dec));
    }

    public void StopVerticalMove(boolean hardStop) {
        System.out.println(new Date().toString() + ": Harpune.StopVerticalMove");
        com.Send(Command.StopMove(VERTICAL, hardStop));
    }
}
