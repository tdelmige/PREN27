package Controller;

import Common.IMessage;
import Common.IObserver;

import java.util.Date;

public class Tower implements IObserver<IMessage> {
	
	private Command com =null;
    private short comAdr = 0;

    private final short MOTOR = 3;
    private final short LEFT = 2;
    private final short RIGHT = 1;

    private short speed = 50;
    private short acc = 0;
    private short dec = 0;
	
	public Tower(Command com) {
		this.com = com;
        this.comAdr = Command.getComAdr();
        Command.getComPortInst().addObserver(this);
	}

    public short GetComAdr(){return comAdr;}

    public void MoveLeft(){
        System.out.println(new Date().toString() + ": Tower.MoveLeft");
        com.Send(Command.Move(MOTOR, LEFT, speed, acc, dec), comAdr);
    }

    public void MoveLeft(short pos) {
        System.out.println(new Date().toString() + ": Tower.MoveLeft steps: " + pos);
        com.Send(Command.MoveTo(MOTOR, LEFT, pos, speed, acc, dec), comAdr);
    }

    public void MoveRight(){
        System.out.println(new Date().toString() + ": Tower.MoveRight");
        com.Send(Command.Move(MOTOR, RIGHT, speed, acc, dec), comAdr);
    }

	public void MoveRight(short pos){
        System.out.println(new Date().toString() + ": Tower.MoveRight steps: " + pos);
        com.Send(Command.MoveTo(MOTOR, RIGHT, pos, speed, acc, dec), comAdr);
	}

    public void Stop(boolean hardStop) {
        System.out.println(new Date().toString() + ": Tower.Stop");
        com.Send(Command.StopMove(MOTOR, hardStop), comAdr);
    }


    @Override
    public void update(IMessage arg) {

        if (arg.getComAdr() == comAdr){

        }
    }
}