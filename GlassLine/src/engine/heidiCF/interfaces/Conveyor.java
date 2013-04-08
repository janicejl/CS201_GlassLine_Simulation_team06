package engine.heidiCF.interfaces;

import engine.agent.shared.Glass;

public interface Conveyor {

	public abstract int getMyIndex();

	public abstract void msgHereIsGlass(Glass g);

	public abstract void msgGlassArrivedAtEnd();

	public abstract void msgPopupAvailable();

	public abstract void msgEndSensorAvailable();

}