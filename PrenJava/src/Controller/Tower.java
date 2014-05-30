package Controller;

import java.util.Date;

public class Tower {
	
	private Command com;

    private final short MOTOR = 3;
    private final short LEFT = 2;
    private final short RIGHT = 1;

    private short speed = 50;
    private short acc = 0;
    private short dec = 0;
	
	public Tower(Command com) {
		this.com = com;
	}

    public void MoveLeft(){
        System.out.println(new Date().toString() + ": Tower.MoveLeft");
        com.Send(Command.Move(MOTOR, LEFT, speed, acc, dec));
    }

    public void MoveLeft(short pos) {
        System.out.println(new Date().toString() + ": Tower.MoveLeft steps: " + pos);
        com.Send(Command.MoveTo(MOTOR, LEFT, pos, speed, acc, dec));
    }

    public void MoveRight(){
        System.out.println(new Date().toString() + ": Tower.MoveRight");
        com.Send(Command.Move(MOTOR, RIGHT, speed, acc, dec));
    }

	public void MoveRight(short pos){
        System.out.println(new Date().toString() + ": Tower.MoveRight steps: " + pos);
        com.Send(Command.MoveTo(MOTOR, RIGHT, pos, speed, acc, dec));
	}

    public void Stop(boolean hardStop) {
        System.out.println(new Date().toString() + ": Tower.Stop");
        com.Send(Command.StopMove(MOTOR, hardStop));
    }
}