package Controller;

import Common.IMessage;
import Common.IObserver;
import Common.IResponse;

import java.nio.ByteBuffer;
import java.util.Date;
import java.util.Timer;

public class Harpune implements IObserver<IMessage> {

    // Directions
    public final short RIGHT = 0;
    public final short LEFT = 1;
    public final short UP = 1;
    public final short DOWN = 0;

    // Motors
    public final short HORIZONTAL = 0;
    public final short VERTICAL = 1;
    public final short JIG = 2; // Spannvorrichtung

    // Positions
    private final int JIG_MIN = 0;
    private final int JIG_MAX = 842891; // 0C DC 8B - 842 891

    // Speed
    private short speedPull = 10;
    private short speedLoose = 50;

    // Acceleration & Deceleration
    private short accPull = 20;
    private short decPull = 20;
    private short accLoose = 75;
    private short decLoose = 75;

	private short delay = 1000;
	private Command com = null;
    private short comAdr = 0;
    private String comFunc = "";
    private IMessage response;
    private Boolean received = false;

    private short speed = 50;
    private short acc = 0;
    private short dec = 0;
    private short posPull = 40;
    private short posLoose = 0;
    private short speedHarpune = 5 ;

    public final int MaxPos = 4194303;


	public Harpune(Command com) {
		this.com = com;
        this.comAdr = Command.getComAdr();
        Command.getComPortInst().addObserver(this);
	}

    public short GetComAdr(){return comAdr;}

	//Abschuss = SetPin 1 High und SetPin 1 Low
	public void Fire()
	{
        comFunc = "Harpune.Fire";
        System.out.println(new Date().toString() + ": " + comFunc);
		Command.SetPin((short) 1, true);
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
        comFunc = "Harpune.Pull";
        System.out.println(new Date().toString() + ": " + comFunc);
        com.Send(Command.MoveTo(JIG, UP, JIG_MIN, speedPull, accPull, decPull), comAdr, comFunc);
    }

    public void Loose() {
        comFunc = "Harpune.Loose";
        System.out.println(new Date().toString() + ": " + comFunc);
        com.Send(Command.MoveTo(JIG, DOWN, JIG_MAX, speedLoose, accLoose, decLoose), comAdr, comFunc);
    }

    public void stopPullLoose(boolean hardStop) {
        comFunc = "Harpune.stopPullLoose";
        System.out.println(new Date().toString() + ": " + comFunc);
        com.Send(Command.StopMove(JIG, hardStop), comAdr, comFunc);
    }

    public void MoveLeft(){
        comFunc = "Harpune.MoveLeft";
        System.out.println(new Date().toString() + ": " + comFunc);
        com.Send(Command.Move(HORIZONTAL, LEFT, speedHarpune, acc, dec), comAdr, comFunc);
    }

    public void MoveLeft(int steps){
        int pos = GetPosHorizontal();
        pos += steps;
        pos &= 0x3FFFFF;

        comFunc = "Harpune.MoveLeft to: " +pos;
        System.out.println(new Date().toString() + ": " + comFunc);
        com.Send(Command.MoveTo(HORIZONTAL, LEFT, pos, speedHarpune, acc, dec), comAdr, comFunc);
        waitMove(HORIZONTAL);
    }

    public void MoveRight(){
        comFunc = "Harpune.MoveRigh";
        System.out.println(new Date().toString() + ": " + comFunc);
        com.Send(Command.Move(HORIZONTAL, RIGHT, speedHarpune, acc, dec), comAdr, comFunc);
    }

    public void MoveRight(int steps){
        int pos = GetPosHorizontal();
        pos -= steps;
        pos &= 0x3FFFFF;

        comFunc = "Harpune.MoveRight to: " +pos;
        System.out.println(new Date().toString() + ": " + comFunc);
        com.Send(Command.MoveTo(HORIZONTAL, RIGHT, pos, speedHarpune, acc, dec), comAdr, comFunc);
        waitMove(HORIZONTAL);
    }

    public void MoveAroundLeft(){
        comFunc = "Harpune.MoveAroundLeft";
        System.out.println(new Date().toString() + ": " + comFunc);
        com.Send(Command.Move(HORIZONTAL, LEFT, speedHarpune, acc, dec), comAdr, comFunc);

    }

    public void MoveAroundRight(){
        comFunc = "Harpune.MoveAroundRight";
        System.out.println(new Date().toString() + ": " + comFunc);
        com.Send(Command.Move(HORIZONTAL, RIGHT, speedHarpune, acc, dec), comAdr, comFunc);
    }

    public void stopHorizontalMove(boolean hardStop) {
        comFunc = "Harpune.StopHorizontalMove";
        System.out.println(new Date().toString() + ": " + comFunc);
        com.Send(Command.StopMove(HORIZONTAL, hardStop), comAdr, comFunc);
    }

    public void MoveUp(){
        comFunc = "Harpune.MoveUp";
        System.out.println(new Date().toString() + ": " + comFunc);
        com.Send(Command.Move(VERTICAL, UP, speedHarpune, acc, dec), comAdr, comFunc);
    }

    public void MoveUp(int steps){
        int pos = GetPosVertical();
        pos += steps;
        pos &= 0x3FFFFF;

        comFunc = "Harpune.MoveUp, to:" +pos;
        System.out.println(new Date().toString() + ": " + comFunc);
        com.Send(Command.MoveTo(VERTICAL, UP, pos, speedHarpune, acc, dec), comAdr, comFunc);
        waitMove(VERTICAL);
    }

    public void MoveDown(){
        comFunc = "Harpune.MoveDown";
        System.out.println(new Date().toString() + ": " + comFunc);
        com.Send(Command.Move(VERTICAL, DOWN, speedHarpune, acc, dec), comAdr, comFunc);
    }

    public void MoveDown(int steps){
        int pos = GetPosVertical();
        pos -= steps;
        pos &= 0x3FFFFF;

        comFunc = "Harpune.MoveDown, to:" +pos;
        System.out.println(new Date().toString() + ": " + comFunc);
        com.Send(Command.MoveTo(VERTICAL, DOWN, pos, speedHarpune, acc, dec), comAdr, comFunc);
        waitMove(VERTICAL);
    }

    public void StopVerticalMove(boolean hardStop) {
        comFunc = "Harpune.StopVerticalMove";
        System.out.println(new Date().toString() + ": " + comFunc);
        com.Send(Command.StopMove(VERTICAL, hardStop), comAdr, comFunc);
    }

    public int GetPosHorizontal(){
        comFunc = "Harpune.GetPosHorizontal";
        System.out.println(new Date().toString() + ": " + comFunc);
        com.Send(Command.GetAbsPos(HORIZONTAL), comAdr, comFunc);

        while (received == false){
            try {
                Thread.sleep(20);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        received = false;

        while(response != null && response.getChecked() == false){

            int payload = response.getPayload();
            System.out.println(new Date().toString() + "Harpune.GetPosHorizontal Response: " + payload);
            response.setChecked(true);

            if (payload >= 0 || payload <= 4194303)
                return payload;
            else
                return 0;
        }

        return 0;
    }

    public int GetPosVertical() {
        comFunc = "Harpune.GetPosVertical";
        System.out.println(new Date().toString() + ": " + comFunc);
        com.Send(Command.GetAbsPos(VERTICAL), comAdr, comFunc);

        while (received == false ){
            try {
                Thread.sleep(20);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        received = false;

        while(response != null && response.getChecked() == false){

            int payload = response.getPayload();
            System.out.println(new Date().toString() + "Harpune.GetPosVertical Response: " + payload);
            response.setChecked(true);

            if (payload >= 0 || payload <= 4194303)
                return payload;
            else
                return 0;
        }

        return 0;
    }

    private void waitMove(short motor){
        comFunc = "Harpune.WaitMoved";
        com.Send(Command.WaitMoved(motor, (short)0), comAdr, comFunc);

        while (received == false){
            try {
                Thread.sleep(20);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        received = false;

        while(response != null && response.getChecked() == false){
            response.setChecked(true);
            return;
        }
    }


    @Override
    public void update(IMessage arg) {

        if (arg.getComAdr() == comAdr){

            if ("Harpune.GetPosVertical Harpune.GetPosHorizontal Harpune.WaitMoved".toLowerCase().contains(arg.getFunction().toLowerCase()))
            {
                received = true;
            }

            response = arg;
            response.setChecked(false);
        }
    }
}
