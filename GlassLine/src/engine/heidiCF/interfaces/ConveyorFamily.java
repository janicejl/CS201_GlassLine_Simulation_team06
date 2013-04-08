package engine.heidiCF.interfaces;

import engine.agent.shared.Glass;

public interface ConveyorFamily {

	public abstract void msgNewSpaceAvailable();

	public abstract void msgHereIsGlass(Glass glass);

}