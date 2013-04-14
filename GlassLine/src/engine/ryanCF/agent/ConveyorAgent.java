package engine.ryanCF.agent;

import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

import transducer.TChannel;
import transducer.TEvent;
import transducer.Transducer;
import engine.agent.Agent;
import engine.agent.shared.ConveyorFamilyOnlineMachine;
import engine.agent.shared.Glass;
import engine.ryanCF.interfaces.*;

public class ConveyorAgent extends Agent implements Conveyor {

	TruckAgent truck;
	FrontSensorAgent front;
	EndSensorAgent end;
	
	List<MyGlass> glassOnConveyor = Collections
			.synchronizedList(new ArrayList<MyGlass>());

	Transducer t;

	int index;

	boolean truckAvailable = true;
	boolean endPressed = false;
	
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
		
		t.register(this, TChannel.SENSOR);
		
		
	}

	//******************MESSAGING*********************
	@Override
	public void msgHereIsGlass(Glass g) {
		// TODO Auto-generated method stub
		print("Received msgHereIsGlass");
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
		print("Received msgSpaceAvailable");
		truckAvailable = true;
		stateChanged();
	}
	
	@Override
	public void msgEndSensorPressed() {
		// TODO Auto-generated method stub
	}
	
	//****************SCHEDULING*******************
	@Override
	public boolean pickAndExecuteAnAction() {
		// TODO Auto-generated method stub
		synchronized(glassOnConveyor) {
			if(!glassOnConveyor.isEmpty()) {
				for(MyGlass g : glassOnConveyor) {
					//if there exists glass on conveyor such that it's on the first sensor, then start conveyor
					if(g.state == GlassState.SENSOR1 && !endPressed){
						startConveyor(g);
						return true;
					}
					if(g.state == GlassState.SENSOR2){
						if(truckAvailable) {
							truckAvailable = false;
							sendGlassToTruck(g);
							return true;
						}
					}
				}
			}
		}
		return false;
	}

	//***************ACTION************************
	public void turnOnConveyor() {
		// TODO Auto-generated method stub
		print("Truck available. Turning on");
		Integer[] arg1 = new Integer[1];
		arg1[0] = index;
		t.fireEvent(TChannel.CONVEYOR, TEvent.CONVEYOR_DO_START, arg1);
	}

	private void startConveyor(MyGlass g) {
		// TODO Auto-generated method stub
		print("Starting Conveyor");
		g.state = GlassState.CONVEYOR;
		turnOnConveyor();
	}

	private void sendGlassToTruck(MyGlass g) {
		print("SENDING GLASS TO TRUCK");
		truck.msgHereIsGlass(g.g);
		turnOnConveyor();
		//front.msgSpaceAvailable();
	}
	
	@Override
	public void eventFired(TChannel channel, TEvent event, Object[] args) {
		// TODO Auto-generated method stub
		if(channel == TChannel.SENSOR && event == TEvent.SENSOR_GUI_PRESSED) {
			if(args[0].equals(index*2+1)) {
				synchronized(glassOnConveyor) {
					endPressed = true;
					print("End Sensor Hit");
					Integer[] arg1 = new Integer[1];
					arg1[0] = index;
					t.fireEvent(TChannel.CONVEYOR, TEvent.CONVEYOR_DO_STOP, arg1);
					glassOnConveyor.get(0).state = GlassState.SENSOR2;
					stateChanged();
				}
				
			}
		}
		if(channel == TChannel.SENSOR && event == TEvent.SENSOR_GUI_RELEASED) {
			if(args[0].equals(index*2+1)) {
				synchronized(glassOnConveyor) {
					truck.msgHereIsGlass(glassOnConveyor.remove(0).g);
					endPressed = false;
					stateChanged();
				}
			}
		}
	}

	public void setTruck(TruckAgent truck) {
		// TODO Auto-generated method stub
		this.truck = truck;
	}

	public FrontSensorAgent getFrontSensor() {
		return this.front;
	}

	public void setFrontSensor(FrontSensorAgent front2) {
		// TODO Auto-generated method stub
		this.front = front2;
	}

	public void setBackSensor(EndSensorAgent end) {
		// TODO Auto-generated method stub
		this.end = end;
	}

	

}
