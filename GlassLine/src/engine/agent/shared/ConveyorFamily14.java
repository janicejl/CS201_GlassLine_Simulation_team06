package engine.agent.shared;

import transducer.Transducer;
import engine.agent.shared.Interfaces.ConveyorFamily;
import engine.ryanCF.agent.*;
import engine.ryanCF.interfaces.Conveyor;
import engine.ryanCF.interfaces.Sensor;

public class ConveyorFamily14 implements ConveyorFamily{

	ConveyorAgent conveyor;	
	FrontSensorAgent front;
	EndSensorAgent end;
	String name;
	
	MachineAgent prevMachine;
	TruckAgent truck;
	
	public ConveyorFamily14(String name, Transducer t) {
		this.name = name;
		conveyor = new ConveyorAgent(14, "CF14 Conveyor", t);
		front = new FrontSensorAgent(28, "Front Sensor 28", t);
		end = new EndSensorAgent(29, "End Sensor 29", t);
		conveyor.setFrontSensor(front);
		conveyor.setBackSensor(end);
		end.setConveyor(conveyor);

	}
	
	@Override
	public void msgSpaceAvailable() {
		// TODO Auto-generated method stub
		conveyor.msgSpaceAvailable();
	}

	@Override
	public void msgHereIsGlass(Glass g) {
		// TODO Auto-generated method stub
		System.out.println("Received msgHereIsGlass");
		conveyor.msgHereIsGlass(g);
	}

	public void setPreviousMachine(MachineAgent prevMachine) {
		// TODO Auto-generated method stub
		this.prevMachine = prevMachine;
		front.setPreviousMachine(prevMachine);
		//conveyor.setPreviousConveyor(prevCF);
	}

	public void setTruck(TruckAgent truck) {
		// TODO Auto-generated method stub
		this.truck = truck;
		conveyor.setTruck(truck);
	}
	
	public void startThread() {
		conveyor.startThread();
		end.startThread();
		front.startThread();
	}
}
