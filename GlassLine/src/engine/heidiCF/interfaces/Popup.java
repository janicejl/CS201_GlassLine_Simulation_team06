package engine.heidiCF.interfaces;

import engine.heidiCF.agent.Glass;

public interface Popup {

	public abstract void msgHereIsGlass(Glass g);

	public abstract void msgGlassReady(Integer i);

	public abstract void msgNewSpaceAvailable();

	public abstract void msgComeDownAndLetGlassPass(Glass g);


}