package engine.brandonCF.agents;

import java.util.concurrent.Semaphore;


import engine.agent.Agent;
import engine.agent.shared.Glass;
import engine.brandonCF.interfaces.ConveyorFamilyInterface;
import engine.brandonCF.interfaces.ConveyorInterface;
import engine.brandonCF.interfaces.PopUpInterface;
import engine.brandonCF.interfaces.SensorInterface;

import transducer.TChannel;
import transducer.TEvent;
import transducer.Transducer;

public class Sensor2Agent extends Agent implements SensorInterface{
	//Data:
		Glass glass;
		private ConveyorInterface conveyor;
		private PopUpInterface popUp;
		private enum Status {open, filled, finished, waiting};
		private Status status;
		private Semaphore canSend;
		private int machineNumber;
		
		//Methods:
		public Sensor2Agent(String name, Transducer t)
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
			print("told that ther is space Open");
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
			
			if(glass.ifNeedMachine(machineNumber)){
				try{
					canSend.acquire();
					popUp.msgHereIsGlass(glass);
				} catch(Exception e){}
			}
			else
				popUp.msgHereIsFinished(glass);
			
			glass = null;
			print("Sent off the glass");
			stateChanged();
				
		}
		
		private void doAnimation()
		{
			transducer.fireEvent(TChannel.ALL_GUI, TEvent.SENSOR_GUI_PRESSED, null);
			status = Status.waiting;
			stateChanged();
		}
		
		private void askForGlass()
		{
			conveyor.msgSpaceOpen();
			status = Status.waiting;
			print("I have space");
			stateChanged();
		}

		@Override
		public void eventFired(TChannel channel, TEvent event, Object[] args) {
			if(channel == TChannel.SENSOR & event == TEvent.SENSOR_GUI_RELEASED)
			{
				if(glass!= null & status == Status.waiting)
				{
					status = Status.finished;
					stateChanged();
				}
			}
			
		}
		
		public void setPopUp(PopUpInterface popUp)
		{
			this.popUp = popUp;
		}
		

		public void setPreviousFamily(ConveyorFamilyInterface con) {
			//Does nothing here
			
		}

		public void setConveyor(ConveyorInterface conAgent) {
			this.conveyor = conAgent;
			
		}

}
