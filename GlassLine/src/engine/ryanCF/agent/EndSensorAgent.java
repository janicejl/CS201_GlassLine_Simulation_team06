package engine.ryanCF.agent;

import transducer.TChannel;
import transducer.TEvent;
import transducer.Transducer;
import engine.agent.Agent;
import engine.ryanCF.interfaces.*;

public class EndSensorAgent extends Agent implements Sensor {

	Transducer t;
	Conveyor conveyor;
	
	boolean truckSpaceAvailable = true;
	int index;

	public EndSensorAgent(int index, String name, Transducer t) {
		this.index = index;
		this.name = name;
		this.t = t;

		t.register(this, TChannel.SENSOR);

	}

	@Override
	public boolean pickAndExecuteAnAction() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void eventFired(TChannel channel, TEvent event, Object[] args) {
		// TODO Auto-generated method stub
		
	}
	public void setConveyor(Conveyor conveyor) {
		this.conveyor = conveyor;
	}
}
