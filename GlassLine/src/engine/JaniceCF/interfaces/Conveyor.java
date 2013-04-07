package engine.JaniceCF.interfaces;

import engine.agent.shared.Glass;
import engine.agent.shared.Interfaces.Machine;

public interface Conveyor {

	public abstract void msgGlassAtEnd();
	
	public abstract void msgSpaceAvailable();
	
	public abstract void msgHereIsGlass(Glass g);
	
	
	public abstract void setMachine(Machine m);
	public abstract void setPopup(Popup p);
}
