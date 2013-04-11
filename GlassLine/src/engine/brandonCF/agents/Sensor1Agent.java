package engine.brandonCF.agents;

import java.util.concurrent.Semaphore;


import transducer.TChannel;
import transducer.TEvent;
import transducer.Transducer;

import engine.agent.Agent;
import engine.agent.shared.Glass;
import engine.brandonCF.interfaces.ConveyorFamilyInterface;
import engine.brandonCF.interfaces.ConveyorInterface;
import engine.brandonCF.interfaces.PopUpInterface;
import engine.brandonCF.interfaces.SensorInterface;

public class Sensor1Agent extends Agent implements SensorInterface
{
	//Data:
	Glass glass;
	private ConveyorFamilyInterface previous;
	private ConveyorInterface conveyor;
	private enum Status {open, filled, finished, waiting};
	private Status status;
	private Semaphore canSend;
	
	//Methods:
	public Sensor1Agent(String name, Transducer t)
	{
		super(name, t);
		t.register(this, TChannel.SENSOR);
		status = Status.open;
		canSend = new Semaphore(0, true);
	}
	
	//Msgs:
	public void msgHereisGlass(Glass g)//This is where glass is sent in
	{
		this.glass = g;
		status = Status.filled;
		print("Received glass");
		stateChanged();
	}
	
	public void msgSpaceOpen()//Here the agent is "told" it can send glass when ready
	{
		canSend.release();
		print("Notified that space is open");
		stateChanged();
	}
	
	//Scheduler
	public boolean pickAndExecuteAnAction()
	{
		if(status == Status.finished){
			sendFinishedGlass();
			return true;
		}
		if(status == Status.filled)
		{
			doAnimation();
			return true;
		}
		if(status == Status.open)
		{
			askForGlass();
			return true;
		}
		return false;
	}
	
	//Actions
	private void sendFinishedGlass()
	{
		status = Status.open;
		try{
			canSend.acquire();
			conveyor.msgHereIsGlass(glass);
			glass = null;
			print("Sent glass");
			stateChanged();
		} catch(Exception e){}
	}
	
	private void doAnimation()
	{
		transducer.fireEvent(TChannel.ALL_GUI, TEvent.SENSOR_GUI_PRESSED, null);//tellsensor1 to light up
		status = Status.waiting;
		stateChanged();
	}
	
	private void askForGlass()
	{
		previous.msgSpaceOpen();
		status = Status.waiting;
		print("Asked for Glass");
		stateChanged();
	}

	@Override
	public void eventFired(TChannel channel, TEvent event, Object[] args) {
		if(channel == TChannel.SENSOR & event == TEvent.SENSOR_GUI_RELEASED)
		{
			if(glass!= null & status== Status.waiting)
				{
					status = Status.finished;
					stateChanged();
				}
		}
		
	}
	
	public void setPreviousFamily(ConveyorFamilyInterface previous)
	{
		this.previous = previous;
	}
	
	public void setConveyor(ConveyorInterface conAgent)
	{
		this.conveyor = conAgent;
	}

	@Override
	public void setPopUp(PopUpInterface popUp) {
		//Does nothing here
		
	}

}
