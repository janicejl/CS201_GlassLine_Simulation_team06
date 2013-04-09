package engine.ryanCF.agent;

import java.util.ArrayList;
import java.util.List;

import transducer.TChannel;
import transducer.TEvent;
import engine.agent.Agent;
import engine.ryanCF.interfaces.Bin;
import engine.agent.shared.*;

public class BinAgent extends Agent implements Bin{

	List<Glass> glassInBin = new ArrayList<Glass>();
	//ConveyorFamily0 
	public void msgProcessGlassOrder(List<Glass> glassList) {
		glassInBin.addAll(glassList);
		stateChanged();
	}
	@Override
	public boolean pickAndExecuteAnAction() {
		// TODO Auto-generated method stub
		if(!glassInBin.isEmpty()) {
			sendGlassToCF(glassInBin.remove(0));
			return true;
		}
		return false;
	}

	private void sendGlassToCF(Glass g) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void eventFired(TChannel channel, TEvent event, Object[] args) {
		// TODO Auto-generated method stub
		
	}

}
