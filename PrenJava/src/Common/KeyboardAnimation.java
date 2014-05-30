package Common;

import Controller.Funnel;
import Controller.Harpune;
import Controller.Tower;
import Core.RobotController;

import java.awt.*;
import java.awt.event.*;
import java.net.*;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import javax.swing.*;

public class KeyboardAnimation implements ActionListener
{
	private final static String PRESSED = "pressed ";
	private final static String RELEASED = "released ";

	private JPanel panel;
	private Timer timer;
	private Map<String, EComAction> pressedKeys = new HashMap<String, EComAction>();

    private Tower tower;
    private Harpune harpune;
    private Funnel funnel;

	public KeyboardAnimation(JPanel panel, int delay)
	{
		this.panel = panel;

		timer = new Timer(delay , this);
		timer.setInitialDelay( 0 );
	}

    public KeyboardAnimation(JPanel panel, int delay, Tower tower, Harpune harpune, Funnel funnel)
    {
        this(panel, delay);

        this.tower = tower;
        this.harpune = harpune;
        this.funnel = funnel;
    }

	/*
	*  &param keyStroke - see KeyStroke.getKeyStroke(String) for the format of
	*                     of the String. Except the "pressed|released" keywords
	*                     are not to be included in the string.
	*/
	public void addAction(String keyStroke, EComAction action)
	{
		//  Separate the key identifier from the modifiers of the KeyStroke

		int offset = keyStroke.lastIndexOf(" ");
		String key = offset == -1 ? keyStroke :  keyStroke.substring( offset + 1 );
		String modifiers = keyStroke.replace(key, "");

		//  Get the InputMap and ActionMap of the component

		InputMap inputMap = panel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
		ActionMap actionMap = panel.getActionMap();

		//  Create Action and add binding for the pressed key

		Action pressedAction = new AnimationAction(key, action);
		String pressedKey = modifiers + PRESSED + key;
		KeyStroke pressedKeyStroke = KeyStroke.getKeyStroke(pressedKey);
		inputMap.put(pressedKeyStroke, pressedKey);
		actionMap.put(pressedKey, pressedAction);

		//  Create Action and add binding for the released key

		Action releasedAction = new AnimationAction(key, null);
		String releasedKey = modifiers + RELEASED + key;
		KeyStroke releasedKeyStroke = KeyStroke.getKeyStroke(releasedKey);
		inputMap.put(releasedKeyStroke, releasedKey);
		actionMap.put(releasedKey, releasedAction);
	}

	//  Invoked whenever a key is pressed or released

	private void handleKeyEvent(String key, EComAction action)
	{
		//  Keep track of which keys are pressed

		if (action == null) {
            pressedKeys.remove(key);
        }
		else {
            pressedKeys.put(key, action);
        }

		//  Start the Timer when the first key is pressed

   		if (pressedKeys.size() == 1)
   		{
   			timer.start();
   		}

		//  Stop the Timer when all keys have been released

   		if (pressedKeys.size() == 0)
   		{
   			timer.stop();

   		}
	}

	//  Invoked when the Timer fires

	public void actionPerformed(ActionEvent e)
	{
        takeAction();
	}

	//  Move the component to its new location

	private void takeAction()
	{
        for (EComAction action : pressedKeys.values()){

            switch (action){

                case TowMoveLeft:
                    this.tower.MoveLeft();
                    break;

                case TowMoveRight:
                    this.tower.MoveRight();
                    break;

                case HarMoveAroundLeft:
                    this.harpune.MoveAroundLeft();
                    break;

                case HarMoveAroundRight:
                    this.harpune.MoveAroundRight();
                    break;

                case HarMoveUp:
                    this.harpune.MoveUp();
                    break;

                case HarMoveDown:
                    this.harpune.MoveDown();
                    break;

                case HarMoveLeft:
                    this.harpune.MoveLeft();
                    break;

                case HarMoveRight:
                    this.harpune.MoveRight();
                    break;

                case HarFire:
                    this.harpune.Fire();
                    break;

                case HarPull:
                    this.harpune.Pull();
                    break;

                case HarLoose:
                    this.harpune.Loose();
                    break;

                case FunOpen:
                    this.funnel.Open();
                    break;

                case FunClose:
                    this.funnel.Close();
                    break;

                case EXIT:
                    RobotController.Exit();
                    break;

                case STOP:
                    RobotController.Stop();
                    break;

            }
        }

	}

	//  Action to keep track of the key and a Point to represent the movement
	//  of the component. A null Point is specified when the key is released.

	private class AnimationAction extends AbstractAction implements ActionListener
	{
		private EComAction action;

		public AnimationAction(String key, EComAction action)
		{
			super(key);

            this.action = action;
		}

		public void actionPerformed(ActionEvent e)
		{
			handleKeyEvent((String)getValue(NAME), action);
		}
	}

//	public static void main(String[] args)
//	{
//
//		KeyboardAnimation animation = new KeyboardAnimation(label, 24);
//		animation.addAction("LEFT", -3,  0);
//		animation.addAction("RIGHT", 3,  0);
//		animation.addAction("UP",    0, -3);
//		animation.addAction("DOWN",  0,  3);
//
//		animation.addAction("control LEFT", -5,  0);
//		animation.addAction("V",  5,  5);
//
//
//		KeyboardAnimation animation2 = new KeyboardAnimation(label2, 24);
//		animation2.addAction("A", -3,  0);
//		animation2.addAction("D", 3,  0);
//		animation2.addAction("W",    0, -3);
//		animation2.addAction("S",  0,  3);

//	}


}
