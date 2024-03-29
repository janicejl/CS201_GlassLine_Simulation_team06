package engine.JaniceCF.interfaces;

import engine.agent.shared.Glass;
import engine.agent.shared.Interfaces.ConveyorFamily;
import engine.agent.shared.Interfaces.Machine;
import engine.ryanCF.interfaces.Bin;

public interface Conveyor {

	//public abstract void msgGlassAtStart();
	
	//public abstract void msgGlassAtEnd();
	
	public abstract void msgSpaceAvailable();
	
	public abstract void msgHereIsGlass(Glass g);
	
	//FOR V2
	public abstract void msgMachineDisabled();
	
	public abstract void setPreviousCF(ConveyorFamily cf);
	public abstract void setMachine(Machine m);
	//public abstract void setPopup(Popup p);
	public abstract void setBin(Bin b);
	public abstract void setPreviousMachine(Machine m);
}
