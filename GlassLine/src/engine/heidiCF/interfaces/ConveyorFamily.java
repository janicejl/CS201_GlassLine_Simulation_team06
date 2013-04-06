package engine.heidiCF.interfaces;

import engine.heidiCF.agent.Glass;

public interface ConveyorFamily {

	public abstract void msgNewSpaceAvailable();

	public abstract void msgHereIsGlass(Glass glass);

}