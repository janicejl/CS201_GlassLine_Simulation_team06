package engine.ryanCF.agent;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

import transducer.TChannel;
import transducer.TEvent;
import engine.agent.Agent;
import engine.agent.shared.Glass;
import engine.ryanCF.interfaces.*;

public class ConveyorAgent extends Agent implements Conveyor{

	List<Glass> glassOnConveyor = Collections.synchronizedList(new ArrayList<Glass>());
	
	@Override
	public boolean pickAndExecuteAnAction() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void eventFired(TChannel channel, TEvent event, Object[] args) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void msgHereIsGlass(Glass g) {
		// TODO Auto-generated method stub
		
	}

}
