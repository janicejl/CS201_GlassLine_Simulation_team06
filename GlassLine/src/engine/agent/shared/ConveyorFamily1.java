package engine.agent.shared;

import transducer.*;
import engine.JaniceCF.agent.*;
import engine.JaniceCF.interfaces.*;
import engine.agent.shared.Glass;
import engine.agent.shared.Interfaces.Machine;

public class ConveyorFamily1 implements ConveyorFamily {

	String name;
	
	ConveyorAgent conveyor;
	
	public ConveyorFamily1 (Transducer transducer) {
		this.name = "CF1";
		
		conveyor = new ConveyorAgent("CF1 Conveyor", transducer, 1);
	}
	
	public void startThread() {
		conveyor.startThread();
	}
	
	@Override
	public void msgSpaceAvailable() {
		System.out.println(name + ": Forwarded msgSpaceAvailable to Conveyor");
		conveyor.msgSpaceAvailable();
	}

	@Override
	public void msgHereIsGlass(Glass g) {
		System.out.println(name + ": Forwarded msgHereIsGlass to Conveyor. ");
		conveyor.msgHereIsGlass(g);
	}
	
	
	
	public void setMachine(Machine m) {
		conveyor.setPreviousMachine(m);
	}

}
