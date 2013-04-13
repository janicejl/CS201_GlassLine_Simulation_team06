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
		if(truckSpaceAvailable) {
			
		}
		return false;
	}

	@Override
	public void eventFired(TChannel channel, TEvent event, Object[] args) {
		// TODO Auto-generated method stub
		if(channel == TChannel.SENSOR && event == TEvent.SENSOR_GUI_PRESSED && args[0].equals(index)) {
			if(truckSpaceAvailable) {
				truckSpaceAvailable = false;
			}
			else {
				Integer[] arg1 = new Integer[1];
				arg1[0] = index/2;
				t.fireEvent(TChannel.CONVEYOR, TEvent.CONVEYOR_DO_STOP, arg1);
				conveyor.msgEndSensorPressed();
			}
		}
	}
	public void setConveyor(Conveyor conveyor) {
		this.conveyor = conveyor;
	}
}
