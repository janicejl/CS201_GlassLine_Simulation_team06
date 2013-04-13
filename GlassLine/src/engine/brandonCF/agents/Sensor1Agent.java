package engine.brandonCF.agents;

import java.util.concurrent.Semaphore;

import transducer.TChannel;
import transducer.TEvent;
import transducer.Transducer;
import engine.agent.Agent;
import engine.agent.shared.Glass;
import engine.agent.shared.Interfaces.Machine;
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
	private Transducer trans;
	private Integer[] number;
	private Machine mac;
	private int iglue = 0;
	
	//Methods:
	public Sensor1Agent(String name, Transducer t, int num)
	{
		super(name, t);
		t.register(this, TChannel.SENSOR);
		trans = t;
		status = Status.open;
		canSend = new Semaphore(0, true);
		number = new Integer[1];
		number[0] = num;
	}
	
	//Msgs:
	@Override
	public void msgHereisGlass(Glass g)//This is where glass is sent in
	{
		this.glass = g;
		status = Status.filled;
		print("Received glass");
		trans.fireEvent(TChannel.CONVEYOR, TEvent.CONVEYOR_DO_START, number);
		stateChanged();
	}
	
	@Override
	public void msgSpaceOpen()//Here the agent is "told" it can send glass when ready
	{
		canSend.release();
		print("Notified that space is open");
		stateChanged();
	}
	
	//Scheduler
	@Override
	public boolean pickAndExecuteAnAction()
	{
		if(status == Status.finished){
			sendFinishedGlass();
			return true;
		}
		if(status == Status.filled)
		{
			sensorDone();
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
	
	private void sensorDone()
	{
		status = Status.finished;
		stateChanged();
	}
	
	private void sendFinishedGlass()
	{
		status = Status.open;
		try{
			canSend.acquire();
			conveyor.msgHereIsGlass(glass);
			glass = null;
			print("Sent glass" +iglue);
			iglue++;
			stateChanged();
		} catch(Exception e){}
	}
	
	private void askForGlass()
	{
		mac.msgSpaceAvailable();
		status = Status.waiting;
		print("Asked for Glass" + iglue);
		stateChanged();
	}

	@Override
	public void eventFired(TChannel channel, TEvent event, Object[] args) {
		if(channel == TChannel.SENSOR & event == TEvent.SENSOR_GUI_PRESSED)
		{
			if(args[0].equals(number[0] *2))
			{
				
				print(""+iglue);
				stateChanged();
			}
		}
	}
	
	@Override
	public void setPreviousFamily(ConveyorFamilyInterface previous)
	{
		this.previous = previous;
	}
	
	public void setPreviousFamily(Machine previous)
	{
		this.mac = previous;
	}
	
	@Override
	public void setConveyor(ConveyorInterface conAgent)
	{
		this.conveyor = conAgent;
	}

	@Override
	public void setPopUp(PopUpInterface popUp) {
		//Does nothing here
		
	}

}
