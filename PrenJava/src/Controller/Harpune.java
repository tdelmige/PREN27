package Controller;

import Common.IMessage;
import Common.IObserver;

import java.util.Date;
import java.util.Timer;

public class Harpune implements IObserver<IMessage> {

    // Directions
    public final short RIGHT = 0;
    public final short LEFT = 1;
    public final short UP = 0;
    public final short DOWN = 1;

    // Motors
    public final short HORIZONTAL = 0;
    public final short VERTICAL = 1;
    public final short JIG = 2; // Spannvorrichtung

    // Positions
    private final int JIG_MIN = 0;
    private final int JIG_MAX = 842891; // 0C DC 8B - 842 891

    // Speed
    private short speedPull = 50;
    private short speedLoose = 20;

    // Acceleration & Deceleration
    private short accPull = 20;
    private short decPull = 20;
    private short accLoose = 75;
    private short decLoose = 75;

	private short delay = 1000;
	private Command com = null;
    private short comAdr = 0;
    private short speed = 50;
    private short acc = 0;
    private short dec = 0;
    private short posPull = 40;
    private short posLoose = 0;
    private short speedHarpune = 5 ;

	public Harpune(Command com) {
		this.com = com;
        this.comAdr = Command.getComAdr();
        Command.getComPortInst().addObserver(this);
	}

    public short GetComAdr(){return comAdr;}

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
        com.Send(Command.MoveTo(JIG, UP, JIG_MIN, speedPull, accPull, decPull), comAdr);
    }

    public void Loose() {
        System.out.println(new Date().toString() + ": Harpune.Loose");
        com.Send(Command.MoveTo(JIG, DOWN, JIG_MAX, speedLoose, accLoose, decLoose), comAdr);
    }

    public void stopPullLoose(boolean hardStop) {
        System.out.println(new Date().toString() + ": Harpune.stopPullLoose");
        com.Send(Command.StopMove(JIG, hardStop), comAdr);
    }

    public void MoveLeft(){
        System.out.println(new Date().toString() + ": Harpune.MoveLeft");
        com.Send(Command.Move(HORIZONTAL, LEFT, speedHarpune, acc, dec), comAdr);
    }

    public void MoveLeft(int pos){
        System.out.println(new Date().toString() + ": Harpune.MoveLeft to:" +pos);
        com.Send(Command.MoveTo(HORIZONTAL, LEFT, pos, speedHarpune, acc, dec), comAdr);
    }

    public void MoveRight(){
        System.out.println(new Date().toString() + ": Harpune.MoveRight");
        com.Send(Command.Move(HORIZONTAL, RIGHT, speedHarpune, acc, dec), comAdr);
    }

    public void MoveRight(int pos){
        System.out.println(new Date().toString() + ": Harpune.MoveRight to:" +pos);
        com.Send(Command.MoveTo(HORIZONTAL, RIGHT, pos, speedHarpune, acc, dec), comAdr);
    }

    public void MoveAroundLeft(){
        System.out.println(new Date().toString() + ": Harpune.MoveAroundLeft");
        com.Send(Command.Move(HORIZONTAL, LEFT, speedHarpune, acc, dec), comAdr);

    }

    public void MoveAroundRight(){
        System.out.println(new Date().toString() + ": Harpune.MoveAroundRight");
        com.Send(Command.Move(HORIZONTAL, RIGHT, speedHarpune, acc, dec), comAdr);
    }

    public void stopHorizontalMove(boolean hardStop) {
        System.out.println(new Date().toString() + ": Harupune.StopHorizontalMove");
        com.Send(Command.StopMove(HORIZONTAL, hardStop), comAdr);
    }

    public void MoveUp(){
        System.out.println(new Date().toString() + ": Harpune.MoveUp");
        com.Send(Command.Move(VERTICAL, UP, speedHarpune, acc, dec), comAdr);
    }

    public void MoveUp(int pos){
        System.out.println(new Date().toString() + ": Harpune.MoveUp, steps:" +pos);
        com.Send(Command.MoveTo(VERTICAL, UP, pos, speedHarpune, acc, dec), comAdr);
    }

    public void MoveDown(){
        System.out.println(new Date().toString() + ": Harpune.MoveDown");
        com.Send(Command.Move(VERTICAL, DOWN, speedHarpune, acc, dec), comAdr);
    }

    public void MoveDown(int pos){
        System.out.println(new Date().toString() + ": Harpune.MoveDown, steps:" +pos);
        com.Send(Command.MoveTo(VERTICAL, UP, pos, speedHarpune, acc, dec), comAdr);
    }

    public void StopVerticalMove(boolean hardStop) {
        System.out.println(new Date().toString() + ": Harpune.StopVerticalMove");
        com.Send(Command.StopMove(VERTICAL, hardStop), comAdr);
    }

    public int GetPosHorizontal(){
        System.out.println(new Date().toString() + ": Harpune.GetPosition");
        com.Send(Command.GetAbsPos(HORIZONTAL), comAdr);



        return 0;
    }

    public int GetPosVertical() {
        System.out.println(new Date().toString() + ": Harpune.GetPosition");
        com.Send(Command.GetAbsPos(VERTICAL), comAdr);



        return 0;
    }

    @Override
    public void update(IMessage arg) {

        if (arg.getComAdr() == comAdr){

        }
    }
}
