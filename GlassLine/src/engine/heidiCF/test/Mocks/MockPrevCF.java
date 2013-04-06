package engine.heidiCF.test.Mocks;

import engine.heidiCF.agent.Glass;
import engine.heidiCF.interfaces.ConveyorFamily;

public class MockPrevCF implements ConveyorFamily{

	public EventLog log = new EventLog();
	
	public void msgNewSpaceAvailable() {
		
		log.add(new LoggedEvent("Previous Conveyor Family got a message: msgNewSpaceAvailable"));

	}

	public void msgHereIsGlass(Glass glass) {
		
		
	}

}
