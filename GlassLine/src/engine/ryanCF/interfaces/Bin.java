package engine.ryanCF.interfaces;

import java.util.List;

import transducer.TChannel;
import transducer.TEvent;

import engine.agent.shared.ConveyorFamilyOnlineMachine;
import engine.agent.shared.Glass;

public interface Bin {

	public abstract void msgProcessGlassOrder(List<Glass> glassList);
	public abstract void sendGlassToCF(Glass g);
	public abstract void eventFired(TChannel channel, TEvent event, Object[] args);
	public abstract void setConveyorFamilyOnlineMachine(ConveyorFamilyOnlineMachine cfom);
	public abstract void msgSpaceAvailable();
}
