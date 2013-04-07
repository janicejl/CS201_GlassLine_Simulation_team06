package engine.agent.shared;

import transducer.TChannel;
import transducer.TEvent;
import transducer.Transducer;
import engine.JaniceCF.agent.*;
import engine.JaniceCF.interfaces.*;
import engine.agent.shared.*;
import engine.agent.shared.Interfaces.*;

public class ConveyorFamily0 implements ConveyorFamily {

	String name;
	
	ConveyorAgent conveyor;
		
	public ConveyorFamily0 (TChannel channel, Transducer transducer) {
		this.name = channel.toString() + " CF";
		
		conveyor = new ConveyorAgent(channel.toString() + " Conveyor", transducer, 0);
	}
	
	public void startThread() {
		conveyor.startThread();
	}
	
	public void msgSpaceAvailable() {
		System.out.println(name + ": Forwarded msgSpaceAvailable to conveyor. ");
		conveyor.msgSpaceAvailable();
	}

	
	public void msgHereIsGlass(Glass g) {
		System.out.println(name + ": Forwarded msgHereIsGlass to Conveyor. ");
		conveyor.msgHereIsGlass(g);
	}
	
	public void setMachine(Machine m) {
		conveyor.setMachine(m);
	}
	
	public Conveyor getConveyor() {
		return conveyor;
	}
}
