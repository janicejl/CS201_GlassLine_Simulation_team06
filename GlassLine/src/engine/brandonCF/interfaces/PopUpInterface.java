package engine.brandonCF.interfaces;

public interface PopUpInterface {
	
	public void msgHereIsGlass(engine.agent.shared.Glass g);
	
	public void msgHereIsFinished(engine.agent.shared.Glass g);
	
	public void msgSpaceOpen();
	
	public void setNextCF(ConveyorFamilyInterface con);
	
	public void setSensor(SensorInterface sensor);
	
	public void setMac1(MachineInterface mac);
	
	public void setMac2(MachineInterface mac);

}
