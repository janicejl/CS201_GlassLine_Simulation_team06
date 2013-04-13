package engine.agent.shared;

import transducer.Transducer;
import engine.agent.shared.Interfaces.ConveyorFamily;
import engine.ryanCF.agent.*;
import engine.ryanCF.interfaces.Conveyor;

public class ConveyorFamily14 implements ConveyorFamily{

	ConveyorAgent conveyor;	
	String name;
	
	ConveyorFamily prevCF;
	TruckAgent truck;
	
	public ConveyorFamily14(String name, Transducer t) {
		this.name = name;
		conveyor = new ConveyorAgent(14, "CF14 Conveyor", t);
	}
	
	@Override
	public void msgSpaceAvailable() {
		// TODO Auto-generated method stub
		conveyor.msgSpaceAvailable();
	}

	@Override
	public void msgHereIsGlass(Glass g) {
		// TODO Auto-generated method stub
		conveyor.msgHereIsGlass(g);
	}

	public void setPreviousCF(ConveyorFamilyOnlineMachine prevCF) {
		// TODO Auto-generated method stub
		this.prevCF = prevCF;
		//conveyor.setPreviousConveyor(prevCF);
	}

	public void setTruck(TruckAgent truck) {
		// TODO Auto-generated method stub
		this.truck = truck;
	}
	
	public void startThread() {
		conveyor.startThread();
	}
}
