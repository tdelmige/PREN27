package Controller;

import Common.IMessage;
import Common.IObserver;

import java.nio.ByteBuffer;
import java.util.Date;

public class Tower implements IObserver<IMessage> {
	
	private Command com =null;
    private short comAdr = 0;
    private String comFunc = "";
    private IMessage response;
    private Boolean received = false;

    private final short MOTOR = 3;
    private final short LEFT = 1;
    private final short RIGHT = 0;

    private short speed = 10;
    private short acc = 0;
    private short dec = 0;
	
	public Tower(Command com) {
		this.com = com;
        this.comAdr = Command.getComAdr();
        Command.getComPortInst().addObserver(this);
	}

    public short GetComAdr(){return comAdr;}

    public void MoveLeft(){
        comFunc = "Tower.MoveLeft";
        System.out.println(new Date().toString() + ": Tower.MoveLeft");
        com.Send(Command.Move(MOTOR, LEFT, speed, acc, dec), comAdr, comFunc);
    }

    public void MoveLeft(short steps) {
        int pos = GetPosHorizontal();
        pos += steps;
        pos &= 0x3FFFFF;

        comFunc = "Tower.MoveLeft steps: " + pos;
        System.out.println(new Date().toString() + ": " + comFunc);
        com.Send(Command.MoveTo(MOTOR, LEFT, pos, speed, acc, dec), comAdr, comFunc);
        waitMove();

    }

    public void MoveRight(){
        comFunc = "Tower.MoveRight";
        System.out.println(new Date().toString() + ": " + comFunc);
        com.Send(Command.Move(MOTOR, RIGHT, speed, acc, dec), comAdr, comFunc);
    }

	public void MoveRight(short steps){
        int pos = GetPosHorizontal();
        pos += steps;
        pos &= 0x3FFFFF;

        comFunc = "Tower.MoveRight steps: " + pos;
        System.out.println(new Date().toString() + ": " + comFunc);
        com.Send(Command.MoveTo(MOTOR, RIGHT, pos, speed, acc, dec), comAdr, comFunc);
        waitMove();
	}

    public void Stop(boolean hardStop) {
        comFunc = "Tower.Stop";
        System.out.println(new Date().toString() + ": " + comFunc);
        com.Send(Command.StopMove(MOTOR, hardStop), comAdr, comFunc);
    }

    public int GetPosHorizontal(){
        comFunc = "Tower.GetPosHorizontal";
        System.out.println(new Date().toString() + ": " + comFunc);
        com.Send(Command.GetAbsPos(MOTOR), comAdr, comFunc);

        while (response == null){
            try {
                Thread.sleep(4);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        while(response != null && response.getChecked() == false){
            response.setChecked(true);
            int payload = response.getPayload();
            response = null;
            return payload;
        }

        return 0;
    }

    private void waitMove(){
        comFunc = "Harpune.WaitMoved";
        com.Send(Command.WaitMoved(MOTOR, (short)0), comAdr, comFunc);

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

            if ("Tower.GetPosHorizontal  Tower.WaitMoved".toLowerCase().contains(arg.getFunction().toLowerCase()))
            {
                received = true;
            }

            response = arg;
            response.setChecked(false);
        }
    }
}