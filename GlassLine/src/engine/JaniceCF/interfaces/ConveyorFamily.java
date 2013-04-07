package engine.JaniceCF.interfaces;

import engine.agent.shared.Glass;

public interface ConveyorFamily {
	
	public abstract void msgSpaceAvailable();
	
	public abstract void msgHereIsGlass(Glass g);
	
}
