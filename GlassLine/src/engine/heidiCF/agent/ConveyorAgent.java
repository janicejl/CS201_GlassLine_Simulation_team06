package engine.heidiCF.agent;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Semaphore;
import engine.agent.Agent;
import engine.heidiCF.interfaces.*;

import transducer.TChannel;
import transducer.TEvent;
import transducer.Transducer;

public class ConveyorAgent extends Agent implements Conveyor{
	
	public ConveyorAgent(int myIndex, Transducer t)
	{
		this.myIndex=myIndex;
		endSensorEmpty =true;
		glasses = Collections.synchronizedList(new ArrayList<MyGlass>());
		transducer = t;
		t.register(this, TChannel.CONVEYOR);
		t.register(this, TChannel.SENSOR);
		t.register(this, TChannel.POPUP);
	}
	Semaphore animationSem = new Semaphore(0,true);
	int myIndex;
	
	class MyGlass{
		Glass glass; 
		GlassStatus  status;
		public MyGlass(Glass g)
		{
			this.glass=g;
		}
	};
	
	enum GlassStatus {
		WaitingFront, Moving, WaitingEnd,Delivering
	};

	public boolean endSensorEmpty;
	
	ConveyorStatus status = ConveyorStatus.Stop;
	enum ConveyorStatus{
		Stop, Running, NeedToStop
	}
	
	public class MyPopup{
		Popup popup;
		//PopupPosition position;
		int readyPositions =2;
		public MyPopup(Popup p)
		{
			popup = p;
		}
		public int getReadyPositions()
		{
			return readyPositions;
		}
		public void setReadyPositions(int r)//only for testing
		{
			readyPositions = r;
		}
	}
	public MyPopup myPopup;
	//enum PopupPosition{Up, Down, Lifting, Falling};

	public List<MyGlass> glasses;
	

	public void msgHereIsGlass(Glass g)	 //3 from previous CF
	{	
		
		MyGlass newGlass = new MyGlass(g);
		newGlass.status = GlassStatus.WaitingFront;
		glasses.add(newGlass);
		stateChanged();
	}


	public void msgGlassArrivedAtEnd() 	//5b from end sensor
	{
		status = ConveyorStatus.NeedToStop;
		endSensorEmpty =false;
		//if there exsists a MyGlass g in glasses such that g.status = GlassStatus.Moving, change its status to waitingEnd
		synchronized(glasses)
		{	for(MyGlass g: glasses)
			{
				if (g.status == GlassStatus.Moving)
				{
					g.status = GlassStatus.WaitingEnd;
					break;
				}
			}
			stateChanged();
		}
		
	}

	public void msgPopupAvailable() 	//5a from popup
	{
		myPopup.readyPositions++; 
		stateChanged();
	}


	public void msgEndSensorAvailable() //10b from end sensor
	{
		endSensorEmpty = true;
		synchronized(glasses)
		{	for(MyGlass g:glasses)
			{
				if(g.status==GlassStatus.Delivering)
				{
					glasses.remove(g);
					break;
				}
			}
			stateChanged();
		}
		
	}
	
	public boolean pickAndExecuteAnAction() {
		
		
		MyGlass tempG = null;
		MyGlass tempG1 = null;
		
		if(status==ConveyorStatus.Running && glasses.size()==0)
		{	
			status = ConveyorStatus.NeedToStop;
			return true;

		}
		
		if(endSensorEmpty)//&& there exists a MyGlass g in glasses such that g.status = GlassStatus.WaitingFront
		{
			synchronized(glasses)
			{	for(MyGlass g: glasses)
				{
					if(g.status == GlassStatus.WaitingFront)
					{
						tempG =g;
						break;
					}
				}
			}
			if(tempG!=null)
			{
				moveGlass(tempG);
				return true;
			}
			
		}

		synchronized(glasses){
			for(MyGlass g: glasses)
			{
				if(g.status ==GlassStatus.WaitingEnd)
				{
					if(myPopup.readyPositions >0 && g.glass.ifNeedMachine(myIndex))
					{
//						givePartToPopup(g);
//						return true;
						tempG=g;
						break;
					}
					
					else if (!g.glass.ifNeedMachine(myIndex))
					{
//						passPartThroughPopup(g);
//						return true;
						tempG1=g;
						break;
					}
				}
			}
		}
		if(tempG!=null)
		{
			givePartToPopup(tempG);
			return true;
		}
		else if(tempG1!=null) {
			passPartThroughPopup(tempG1);
			return true;
		}
		
		if(status == ConveyorStatus.NeedToStop)
		{
			stopConveyor();
			return true;
		}
		
		
		return false;
	}
	
	public void moveGlass(MyGlass g)
	{
		//ASK transdusor to let the conveyor move
		if(status!=ConveyorStatus.Running)
		{	Integer[] args = new Integer[1];
			args[0] = myIndex;
			transducer.fireEvent(TChannel.CONVEYOR,TEvent.CONVEYOR_DO_START,args);
			status = ConveyorStatus.Running;
		}
		
		g.status=GlassStatus.Moving;
		stateChanged();
	}

	public void givePartToPopup(MyGlass g)
	{
		g.status = GlassStatus.Delivering;
		myPopup.readyPositions--; 
		myPopup.popup.msgHereIsGlass(g.glass);
		stateChanged();
	}	
	public void stopConveyor(){
		//ASK transdusor to stop the conveyor
		Integer[] args = new Integer[1];
		args[0] = myIndex;
		transducer.fireEvent(TChannel.CONVEYOR,TEvent.CONVEYOR_DO_STOP,args);
		status = ConveyorStatus.Stop;
		stateChanged();
	}
	public void passPartThroughPopup(MyGlass g)
	{
		g.status = GlassStatus.Delivering;
		myPopup.popup.msgComeDownAndLetGlassPass(g.glass);
		stateChanged();
	}

	public void eventFired(TChannel channel, TEvent event, Object[] args) {
		
	}
	public void setPopup(Popup popup)
	{
		myPopup = new MyPopup(popup);
	}
	
	public int getMyIndex() {
	
		return myIndex;
	}

}
