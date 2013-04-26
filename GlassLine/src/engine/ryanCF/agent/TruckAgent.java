package engine.ryanCF.agent;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import transducer.TChannel;
import transducer.TEvent;
import transducer.TReceiver;
import transducer.Transducer;
import engine.agent.Agent;
import engine.ryanCF.interfaces.Truck;
import engine.agent.shared.*;
import engine.agent.shared.Interfaces.ConveyorFamily;

public class TruckAgent extends Agent implements Truck{

	List<MyGlass> glassInTruck = Collections.synchronizedList(new ArrayList<MyGlass>());
	
	Transducer t;
	
	ConveyorFamily prevConv;
	
	int maxSize = 3; //default
	
	int currentSize = 0;
	int currentServed = 0;
	List<Integer> orders = new ArrayList<Integer>();
	
	enum GlassState { ON_TRUCK, ON_LINE };
	
	class MyGlass {
		Glass g;
		GlassState state;
	}
	public TruckAgent(String name, Transducer t) {
		this.name = name;
		this.t = t;
		
		t.register(this, TChannel.TRUCK);
	}
	
	@Override
	public void msgHereIsGlass(Glass g) {
		print("Received msgHereIsGlass");
		MyGlass myGlass = new MyGlass();
		myGlass.g = g;
		myGlass.state = GlassState.ON_LINE;
		glassInTruck.add(myGlass);
		stateChanged();
	}
	
	
	@Override
	public boolean pickAndExecuteAnAction() {
		// TODO Auto-generated method stub
		
		return false;
	}

	@Override
	public void eventFired(TChannel channel, TEvent event, Object[] args) {
		// TODO Auto-generated method stub
		if(channel == TChannel.TRUCK && event == TEvent.TRUCK_GUI_LOAD_FINISHED) {
			print("Glass Load Complete. Truck to empty glass");
			currentSize++;
			currentServed++;
			if(!orders.isEmpty()) {
				if(currentSize == maxSize || currentServed == orders.get(0))
					t.fireEvent(TChannel.TRUCK, TEvent.TRUCK_DO_EMPTY, null);
				else prevConv.msgSpaceAvailable();
			}
		}
		if(channel == TChannel.TRUCK && event == TEvent.TRUCK_GUI_EMPTY_FINISHED) {
			print("Glass Empty Complete. Space Available");
			currentSize = 0;
			if(currentServed == orders.get(0)) {
				currentServed = 0;
				orders.remove(0);
			}
			prevConv.msgSpaceAvailable();
			
		}
	}
	
	public void setConveyorFamily(ConveyorFamily conv) {
		prevConv = conv;
	}
	public void newOrder(int i) {
		orders.add(new Integer(i));
	}
}
