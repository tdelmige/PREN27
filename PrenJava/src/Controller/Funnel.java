package Controller;

import java.util.Date;

public class Funnel {
	
	private Command com;
	
	public Funnel(Command com) {
		this.com = com;
		
	}
	
	
	public void Open(){
        System.out.println(new Date().toString() + ": Funnel.Open");
    }
}
