package engine.heidiCF.test.Mocks;

import engine.heidiCF.agent.Glass;
import engine.heidiCF.interfaces.*;

public class MockConveyor extends MockAgent implements Conveyor{

	public MockConveyor(String name) {
		super(name);
		
	}


	public int getMyIndex() {
		
		return 0;
	}


	public void msgHereIsGlass(Glass g) {
		
		
	}

	
	public void msgGlassArrivedAtEnd() {
		
		log.add(new LoggedEvent("the mock conveyor got the message from endSensor:  msgGlassArrivedAtEnd"));
	}

	
	public void msgPopupAvailable() {
		
		log.add(new LoggedEvent("the mock conveyor got the message from popup: msgPopupAvailable"));
	}


	public void msgEndSensorAvailable() {
		log.add(new LoggedEvent("the mock conveyor got the message from endSensor:  msgEndSensorAvailable"));

		
	}

}
