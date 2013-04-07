package engine.heidiCF.test.Mocks;

import java.util.ArrayList;

import engine.heidiCF.agent.Glass;
import engine.heidiCF.interfaces.*;

public class MockPopup extends MockAgent implements Popup{
	public ArrayList<Glass> glasses = new ArrayList<Glass>();
	public MockPopup(String name) {
		super(name);
		
	}

	public void msgHereIsGlass(Glass g) {
		log.add(new LoggedEvent("the mock popup receives the glass from the conveyor and it needs the machine"));
		glasses.add(g);
	}


	public void msgNewSpaceAvailable() {
		
	}


	public void msgComeDownAndLetGlassPass(Glass g) {
		log.add(new LoggedEvent("the mock popup receives the glass from the conveyor and it does not need the machine"));
		glasses.add(g);
	}

	@Override
	public void msgGlassReady(Integer i) {
		// TODO Auto-generated method stub
		
	}

}
