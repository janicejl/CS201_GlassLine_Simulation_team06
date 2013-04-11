package engine.JaniceCF.interfaces;

import engine.agent.shared.Interfaces.ConveyorFamily;
import engine.agent.shared.Interfaces.Machine;
import engine.ryanCF.interfaces.Bin;

public interface Sensor {

	
	public abstract void setPreviousCF(ConveyorFamily cf);
	public abstract void setMachine (Machine m);
	public abstract void setBin (Bin b);
}
