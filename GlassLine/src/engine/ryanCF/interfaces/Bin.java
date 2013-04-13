package engine.ryanCF.interfaces;

import java.util.List;

import transducer.TChannel;
import transducer.TEvent;

import engine.agent.shared.*;
import engine.agent.shared.Interfaces.ConveyorFamily;

public interface Bin {

	public abstract void msgProcessGlassOrder(List<Glass> glassList);
	public abstract void sendGlassToCF(Glass g);
	public abstract void eventFired(TChannel channel, TEvent event, Object[] args);
	public abstract void setNextCF(ConveyorFamily cfom);
	public abstract void msgSpaceAvailable();
}
