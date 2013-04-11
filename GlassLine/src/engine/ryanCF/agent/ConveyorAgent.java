package engine.ryanCF.agent;
import transducer.TChannel;
import transducer.TEvent;
import engine.agent.Agent;
import engine.ryanCF.interfaces.*;

public class ConveyorAgent extends Agent implements Conveyor{

	@Override
	public boolean pickAndExecuteAnAction() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void eventFired(TChannel channel, TEvent event, Object[] args) {
		// TODO Auto-generated method stub
		
	}

}
