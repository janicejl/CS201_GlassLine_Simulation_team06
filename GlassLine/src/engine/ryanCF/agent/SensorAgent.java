package engine.ryanCF.agent;
import transducer.TChannel;
import transducer.TEvent;
import engine.agent.Agent;
import engine.ryanCF.interfaces.*;

public class SensorAgent extends Agent implements Sensor {
	public enum Type { FRONT, BACK };
	Type type;
	
	public SensorAgent() {
		this.name = "RTConveyor Beginning Sensor";
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
}
