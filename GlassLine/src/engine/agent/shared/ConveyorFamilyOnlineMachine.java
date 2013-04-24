package engine.agent.shared;

import transducer.TChannel;
import transducer.TEvent;
import transducer.Transducer;
import engine.JaniceCF.agent.*;
import engine.JaniceCF.interfaces.*;
import engine.agent.shared.*;
import engine.agent.shared.Interfaces.*;
import engine.ryanCF.interfaces.Bin;

public class ConveyorFamilyOnlineMachine implements ConveyorFamily {

	String name;
	
	ConveyorAgent conveyor;
		
	public ConveyorFamilyOnlineMachine (int index, TChannel channel, Transducer transducer) {
		this.name = channel.toString() + " CF";
		
		conveyor = new ConveyorAgent(channel.toString() + " Conveyor", transducer, index, channel);
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

//	public void msgMachineDisabled() {
//		System.out.println(name + ": Forwarded msgMachineDisabled to Conveyor. ");
//		conveyor.msgMachineDisabled();
//	}
	
	public void setBin(Bin bin) {
		conveyor.setBin(bin);
	}
	
	public void setPreviousMachine(Machine m) {
		conveyor.setPreviousMachine(m);
	}
	
	public void setMachine(Machine m) {
		conveyor.setMachine(m);
	}
	
	public void setPreviousCF(ConveyorFamily cf) {
		conveyor.setPreviousCF(cf);
	}
	
	public Conveyor getConveyor() {
		return conveyor;
	}
}
