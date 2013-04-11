package engine.brandonCF.interfaces;

public interface SensorInterface {
	
	public void msgHereisGlass(engine.agent.shared.Glass g);
	
	public void msgSpaceOpen();
	
	public void setPreviousFamily(ConveyorFamilyInterface con);
	
	public void setConveyor(ConveyorInterface conAgent);
	
	public void setPopUp (PopUpInterface popUp);

}
