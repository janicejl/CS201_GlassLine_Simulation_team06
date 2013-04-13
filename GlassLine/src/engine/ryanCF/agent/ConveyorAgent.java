package engine.ryanCF.agent;

import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

import transducer.TChannel;
import transducer.TEvent;
import transducer.Transducer;
import engine.agent.Agent;
import engine.agent.shared.Glass;
import engine.ryanCF.interfaces.*;

public class ConveyorAgent extends Agent implements Conveyor {

	List<MyGlass> glassOnConveyor = Collections
			.synchronizedList(new ArrayList<MyGlass>());

	Transducer t;

	int index;

	enum GlassState {
		SENSOR1, CONVEYOR, SENSOR2
	};

	class MyGlass {
		Glass g;
		GlassState state;
	}

	public ConveyorAgent(int index, String name, Transducer t) {
		this.index = index;
		this.name = name;
		this.t = t;
	}

	@Override
	public boolean pickAndExecuteAnAction() {
		// TODO Auto-generated method stub
		synchronized(glassOnConveyor) {
			if(!glassOnConveyor.isEmpty()) {
				for(MyGlass g : glassOnConveyor) {
					if(g.state == GlassState.SENSOR1){
						startConveyor(g);
					}
				}
			}
		}
		return false;
	}

	private void startConveyor(MyGlass g) {
		// TODO Auto-generated method stub
		g.state = GlassState.CONVEYOR;
		Integer[] argument = new Integer[1];
		argument[0] = index;
		t.fireEvent(TChannel.CONVEYOR, TEvent.CONVEYOR_DO_START, argument);
	}

	@Override
	public void eventFired(TChannel channel, TEvent event, Object[] args) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void msgHereIsGlass(Glass g) {
		// TODO Auto-generated method stub
		MyGlass myGlass = new MyGlass();
		myGlass.g = g;
		myGlass.state = GlassState.SENSOR1;
		synchronized (glassOnConveyor) {
			glassOnConveyor.add(myGlass);
		}
		stateChanged();
	}

	@Override
	public void msgSpaceAvailable() {
		// TODO Auto-generated method stub

	}

}
