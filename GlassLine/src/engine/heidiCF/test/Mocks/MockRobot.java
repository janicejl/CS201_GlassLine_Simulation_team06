package engine.heidiCF.test.Mocks;

import engine.heidiCF.agent.Glass;
import engine.heidiCF.interfaces.Robot;

public class MockRobot extends MockAgent implements Robot {

	public MockRobot(String name) {
		super(name);
	}

	public void msgHereIsGlass(Glass g){
		log.add(new LoggedEvent("The robot agent received the message: msgHereIsGlass"));
	}
}
