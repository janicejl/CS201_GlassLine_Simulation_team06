package engine.heidiCF.test.Mocks;

import engine.heidiCF.agent.Glass;
import engine.heidiCF.interfaces.ConveyorFamily;

public class MockNextCF implements ConveyorFamily{

	public EventLog log = new EventLog();
	public MockNextCF()
	{
		
	}

	public void msgNewSpaceAvailable() {
		
		
	}


	public void msgHereIsGlass(Glass glass) {
		
		log.add(new LoggedEvent("Next Conveyor Family got the glass from message: msgHereIsGlass"));
	}

}
