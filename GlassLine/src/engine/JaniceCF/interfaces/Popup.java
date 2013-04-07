package engine.JaniceCF.interfaces;

import engine.agent.shared.Glass;

public interface Popup {

	public abstract void msgSpaceAvailable();
	
	public abstract void msgHereIsGlass(Glass g);
	
	public abstract void msgIncomingNeedProcessing();

	public abstract void msgIncomingNoProcessing();
	
	public abstract void msgNeedProcessing(Glass g);
	
	public abstract void msgNoProcessing(Glass g);
	
}
