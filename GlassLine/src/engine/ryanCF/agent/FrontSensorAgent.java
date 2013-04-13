package engine.ryanCF.agent;

import transducer.TChannel;
import transducer.TEvent;
import transducer.Transducer;
import engine.agent.Agent;
import engine.agent.shared.Interfaces.ConveyorFamily;
import engine.ryanCF.interfaces.*;

public class FrontSensorAgent extends Agent implements Sensor {

	Transducer t;
	Conveyor conveyor;

	ConveyorFamily prevConv;

	int index;

	public FrontSensorAgent(int index, String name, Transducer t) {
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
		if (channel == TChannel.SENSOR) {
			if (event == TEvent.SENSOR_GUI_RELEASED) {
				prevConv.msgSpaceAvailable();
			}
		}
	}

	public void setPreviousConveyor(ConveyorFamily conv) {
		this.prevConv = conv;
	}
}
