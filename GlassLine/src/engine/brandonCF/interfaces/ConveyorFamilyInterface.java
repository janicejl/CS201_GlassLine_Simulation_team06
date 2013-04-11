package engine.brandonCF.interfaces;

import engine.agent.shared.Glass;

//The conveyor Family is not an agent! It is merely there to help with the organization of each conveyor
//ConveyorFamilies are used to help send messages. In the situation that conveyors are organized in different
//orders, this, I believe, would help with organization and make it easy to change.

public interface ConveyorFamilyInterface {
		
	public void msgHereisGlass(engine.agent.shared.Glass g);
	
	public void msgSpaceOpen();
	
	public void setPreviousConveyor(ConveyorFamilyInterface con);
	
	public void setNextConveyor(ConveyorFamilyInterface con);

	void msgSpaceAvailable();

	void msgHereIsGlass(Glass g);
}
