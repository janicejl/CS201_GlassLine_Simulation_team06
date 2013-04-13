package engine.ryanCF.interfaces;

import engine.agent.shared.Glass;

public interface Conveyor {

	public abstract void msgHereIsGlass(Glass g);
	public abstract void msgSpaceAvailable();

}
