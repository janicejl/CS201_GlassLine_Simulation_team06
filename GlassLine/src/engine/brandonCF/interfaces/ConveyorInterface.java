package engine.brandonCF.interfaces;

public interface ConveyorInterface {
	
	public void msgHereIsGlass(engine.agent.shared.Glass g);
	
	public void msgSpaceOpen();
	
	public void setFirstSensor(SensorInterface first);
	
	public void setSecondSensor(SensorInterface second);

}
