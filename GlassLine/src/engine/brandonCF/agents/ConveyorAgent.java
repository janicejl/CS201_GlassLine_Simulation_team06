package engine.brandonCF.agents;

import java.util.concurrent.Semaphore;


import transducer.TChannel;
import transducer.TEvent;
import transducer.Transducer;
import engine.agent.Agent;
import engine.agent.shared.Glass;
import engine.brandonCF.interfaces.ConveyorInterface;
import engine.brandonCF.interfaces.SensorInterface;

public class ConveyorAgent extends Agent implements ConveyorInterface
{
	//Data:
	private Glass glass;
	private SensorInterface previousSensor;
	private SensorInterface nextSensor;
	private enum Status {Waiting, Filled, Complete, Open};
	private Status status;
	private Semaphore canSend;
	
	//Method:
	public ConveyorAgent(String name,Transducer t)
	{
		super(name,t);
		transducer.register(this, TChannel.CONVEYOR);
		canSend = new Semaphore(0,true);
		status = Status.Open;
	}
	
	//msgs
	public void msgHereIsGlass(Glass g) //This is where glass is sent in
	{
		this.glass = g;
		status = Status.Filled;
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
		if(status == Status.Filled){
			doAnimation();
			return true;
		}
		if(status == Status.Complete)
		{
				sendGlass();
				return true;
		}
		if(status ==Status.Open){
				tellFirstSensor();
				return true;
		}
		
		return false;
	}
	
	//Actions
	private void doAnimation(){//fires the event to start the conveyor
		transducer.fireEvent(TChannel.ALL_GUI, TEvent.CONVEYOR_DO_START, null);
		status = Status.Waiting;
		stateChanged();
	}
	
	private void tellFirstSensor()//tells the sensor that the conveyor is ready for glass
	{
		previousSensor.msgSpaceOpen();
		status = Status.Waiting;
		print("Asked for glass");
		stateChanged();
	}

	private void sendGlass()//send the glass to the next sensor if it is ready
	{
		try{
			canSend.acquire();
			nextSensor.msgHereisGlass(glass);
			print("Sent glass");
			status = Status.Open;
			glass = null;
			stateChanged();
		} catch(Exception e){}
	}
	
	@Override
	public void eventFired(TChannel channel, TEvent event, Object[] args) {
		if(channel == TChannel.CONVEYOR & event == TEvent.CONVEYOR_DO_STOP)//when the conveyor has stopped, the agent goes to the complete status
		{
			if(glass!= null & status == Status.Waiting)
			{
				status = Status.Complete;
				stateChanged();
			}
		}
		
	}
	
	public void setFirstSensor(SensorInterface first)//setter for the sensor
	{
		this.previousSensor = first;
	}
	public void setSecondSensor(SensorInterface second)//setter for the second sensor
	{
		this.nextSensor = second;
	}
}