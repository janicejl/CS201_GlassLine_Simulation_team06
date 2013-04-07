package engine.JaniceCF.interfaces;

import engine.agent.shared.Interfaces.Machine;

public interface Sensor {

	
	public abstract void setPreviousCF(ConveyorFamily cf);
	public abstract void setMachine (Machine m);
}
