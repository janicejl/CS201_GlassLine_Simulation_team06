package engine.agent.shared.Interfaces;

import transducer.TChannel;
import engine.JaniceCF.interfaces.*;
import engine.agent.shared.Glass;

public interface Machine {
	//public abstract void msgAssembleGlass(Glass g);
	
	public abstract void msgSpaceAvailable();
	
	public abstract void msgHereIsGlass(Glass g);
	
	
	public abstract void setConveyor(Conveyor c);		//Online
	public abstract void setPopup(Popup p);		//Offline
	public abstract void setNextCF(ConveyorFamily cf);
	public abstract TChannel getChannel();
}
