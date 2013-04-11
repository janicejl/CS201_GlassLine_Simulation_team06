package engine.brandonCF.agents;

import java.util.concurrent.Semaphore;


import transducer.TChannel;
import transducer.TEvent;
import transducer.Transducer;
import engine.agent.Agent;
import engine.agent.shared.Glass;
import engine.brandonCF.interfaces.ConveyorFamilyInterface;
import engine.brandonCF.interfaces.MachineInterface;
import engine.brandonCF.interfaces.PopUpInterface;
import engine.brandonCF.interfaces.SensorInterface;

public class PopUpAgent extends Agent implements PopUpInterface{
	//Data
	private Glass glass;
	private SensorInterface previous;
	private ConveyorFamilyInterface nextConveyor;
	private MachineInterface rob1, rob2;
	private enum Status {waiting, complete, busy, open};
	private Status status;
	private Semaphore spaceOpen, imOpen;
	
	public PopUpAgent(String name, Transducer t)
	{
		super(name, t);
		t.register(this, TChannel.POPUP);
		status = Status.open;
		imOpen = new Semaphore(1,true);
		spaceOpen = new Semaphore(0,true);
	}
	
	//messages
	public void msgHereIsFinished(Glass g)//This is where glass is sent in that does not need a machine
	{
		try{
			imOpen.acquire();//They must wait for the popUp to be open before they can load it
			transducer.fireEvent(TChannel.ALL_GUI, TEvent.POPUP_DO_MOVE_DOWN, null);
			status = Status.waiting;
			this.glass =g;
			print("Received finished glass");
			stateChanged();
		} catch(Exception e){}
	}
	
	public void msgHereIsGlass(Glass g)//This is where glass is sent in
	{
		try{
			imOpen.acquire();//PopUp must be open for them to load it
			this.glass = g;
			status = Status.busy;
			print("Received glass for machine");
			stateChanged();
		} catch(Exception e){}
	}
	
	public void msgSpaceOpen()//Here the agent is "told" it can send glass when ready
	{
		spaceOpen.release();
		print("Told there is space open");
		stateChanged();
	}
	
	//Scheduler
	public boolean pickAndExecuteAnAction() {
		if(status == Status.complete){
			sendGlass();
			return true;
		}
		
		if(status == Status.open){
			askForGlass();
			return true;
		}
		if(status == Status.busy)
		{
			sendToMachines();
			return true;
		}
		return false;
	}

	//Actions
	private void askForGlass() {//Tell the sensor before it is ready for a piece of glass (Both Machines are not full)
		status = Status.waiting;
		print("Asked for glass");
		previous.msgSpaceOpen();
		stateChanged();
	}

	private void sendToMachines() {//sends a piece of glass to a machine (Starts annimation)
		
		transducer.fireEvent(TChannel.ALL_GUI, TEvent.POPUP_DO_MOVE_UP, null);			
		status = Status.waiting;
		stateChanged();
	}
	
	private void sendGlass()//sends the glass to the next COnveyor Family interface
	{
		try{
			spaceOpen.acquire();
			this.nextConveyor.msgHereisGlass(glass);
			print("Sent glass to next conveyorFamily");
			glass = null;
			
			if(rob1.isFull() && rob2.isFull())//if both are full, we must wait before accepting glass that needs a machine happens if glass just "passed" through
			{
				status = Status.waiting;
			}
			else
				status = Status.open;
			
		imOpen.release();//"Opens" the popup
		stateChanged();
			
		} catch(Exception e){}
	}

	public void eventFired(TChannel channel, TEvent event, Object[] args) {
		
		if(channel == TChannel.POPUP & event == TEvent.POPUP_GUI_MOVED_UP)//sends the glass msg to the appropriate machine
		{
			if(glass!= null & status == Status.waiting)
			{
				if(!rob1.isFull())
				{
					rob1.msgHereIsGlass(glass);
					glass = null;
					print("Sent glass to rob1");
				}
				else if(!rob2.isFull())
				{
					rob2.msgHereIsGlass(glass);
					glass = null;
					print("Sent glass to rob2");
				}
				
				imOpen.release();//"Opens" the popUp for more glass
				
				if(rob1.isFull() && rob2.isFull())//if both are full, we must wait before accepting glass that needs a machine
				{
					status = Status.waiting;
				}
				else
					status = Status.open;
			}
		}
		
		if(channel == TChannel.POPUP & event == TEvent.POPUP_GUI_MOVED_DOWN){//moves the popup down and "completes" glass
			if(glass!= null & status ==Status.waiting)
			{
				status= Status.complete;
				print("Popup moved down");
				stateChanged();
			}
		}
	}
	
	public void setNextCF(ConveyorFamilyInterface con)//setter for the next conveyorfamily interface
	{
		this.nextConveyor = con;
	}
	
	public void setSensor(SensorInterface sensor)//setter for the sensor
	{
		this.previous = sensor;
	}
	
	public void setMac1(MachineInterface mac)//setter for the machine1
	{
		this.rob1 = mac;
	}
	public void setMac2(MachineInterface mac)//setter for the machine 2
	{
		this.rob2 = mac;
	}

}
