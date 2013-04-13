package engine.brandonCF.agents;

import java.util.ArrayList;
import java.util.List;

import transducer.TChannel;
import transducer.TEvent;
import transducer.Transducer;
import engine.agent.Agent;
import engine.agent.shared.Glass;
import engine.agent.shared.Interfaces.ConveyorFamily;
import engine.agent.shared.Interfaces.Machine;

public class ConShuttle extends Agent implements ConveyorFamily
{
	public enum Status {normal, waiting, send};
	
	private class GlassPacket
	{
		public Glass g;
		public Status status;
		
		public GlassPacket(Glass g)
		{
			this.g = g;
			status = Status.normal;
		}
	}

	private List<GlassPacket> glass = new ArrayList<GlassPacket>();
	private Integer[] number;
	ConveyorFamily con;
	Machine mac;
	boolean canSend, notified, conMoving;
	
	public ConShuttle(String name, Transducer t, int num)
	{
		super(name, t);
		number = new Integer[1];
		number[0] = num;
		transducer.register(this, TChannel.CONVEYOR);
		transducer.register(this, TChannel.SENSOR);
		canSend = true;
		notified = false;
		conMoving = true;
		transducer.fireEvent(TChannel.CONVEYOR, TEvent.CONVEYOR_DO_START, number);
	}
	
	@Override
	public void msgSpaceAvailable() {
		// TODO Auto-generated method stub
		canSend = true;
	}

	@Override
	public synchronized void msgHereIsGlass(Glass g) {
		this.glass.add(new GlassPacket(g));
		print(name.toString() +" received glass");
		notified = false;
		stateChanged();
	}

	@Override
	public synchronized boolean pickAndExecuteAnAction() {
		// TODO Auto-generated method stub
		if(glass.size()>0)//if there are packets, try to do something
		{
			if(glass.get(0).status == Status.send)//if you can send
			{
				if(canSend){
					sendGlass(glass.get(0));
					return true;
				}
				else
				{
					stopConveyor();
				}
			}

			if(notified ==false & conMoving)//if i haven't notified & the conveyor is moving is not stopped
			{
				msgMac();
				return true;
			}
		}
		
		return false;
	}
	//actions
	
	private void stopConveyor() {
		transducer.fireEvent(TChannel.CONVEYOR, TEvent.CONVEYOR_DO_STOP, number);
		conMoving = false;
		stateChanged();		
	}

	private void msgMac()
	{
		mac.msgSpaceAvailable();
		print(name.toString() +" Space Open");
		notified = true;
	}
	
	private void sendGlass(GlassPacket glassPacket) {
			canSend = false;
			con.msgHereIsGlass(glassPacket.g);//send the glass
			glass.remove(glassPacket);//remove from list
			if(conMoving!= true)
			{
				transducer.fireEvent(TChannel.CONVEYOR, TEvent.CONVEYOR_DO_START, number);
				conMoving = true;
			}
			print(name.toString() +" sent glass");
			stateChanged();
	}

	@Override
	public void eventFired(TChannel channel, TEvent event, Object[] args) {
		// TODO Auto-generated method stub
		if(channel == TChannel.SENSOR & event == TEvent.SENSOR_GUI_RELEASED)
		{
			if(args[0].equals(number[0] *2+1))//if second sensor
			{
				glass.get(0).status = Status.send;
				stateChanged();
			}
		}
	}
	
	public void setConveyor(ConveyorFamily c)
	{
		con = c;
	}
	
	public void setMachine(Machine m)
	{
		mac = m;
	}

}
