package engine.agent.shared;

import transducer.TChannel;
import transducer.TEvent;
import transducer.Transducer;
import engine.JaniceCF.interfaces.*;
import engine.agent.Agent;
import engine.agent.shared.Interfaces.Machine;

public class MachineAgent extends Agent implements Machine {

	//Data
	TChannel channel;		//Machine Process Name
	Conveyor conveyor;		//If it is Online
	Popup popup;			//If it is Offline
	ConveyorFamily nextCF;
	
	public enum MachineState {Empty, NotProcessed, Processing, DoneProcessing}
	MachineState status;
	
	boolean nextFree;
	
	Glass glass;
	
	
	public MachineAgent(TChannel channel, Transducer transducer) {
		super(channel.toString(), transducer);
		
		transducer.register(this, channel);
		
		this.channel = channel;
		
		glass = null;
		conveyor = null;
		popup = null;
		
		status = MachineState.Empty;
		nextFree = true;
	}
		
	
	//Messages
	public void msgSpaceAvailable() {
		print("Received msgSpaceAvailable");
		nextFree = true;
		stateChanged();
	}
	
	public void msgHereIsGlass(Glass g) {
		print("Received msgHereIsGlass");
		glass = g;
		stateChanged();
	}

	//Scheduler
	@Override
	public boolean pickAndExecuteAnAction() {
	
		if (status == MachineState.NotProcessed) {
			processGlass();
			return true;
		}
		
		if (nextFree == true) {
			if (status == MachineState.DoneProcessing) {
				releaseGlass();
				return true;
			}
		}
		
		return false;
	}

	@Override
	public void eventFired(TChannel channel, TEvent event, Object[] args) {
		
		if (channel == this.channel) {
			if (event == TEvent.WORKSTATION_LOAD_FINISHED) {
				status = MachineState.NotProcessed;
				stateChanged();
			}
			
			if (event == TEvent.WORKSTATION_GUI_ACTION_FINISHED) {
				status = MachineState.DoneProcessing;
				stateChanged();
			}
			
			if (event == TEvent.WORKSTATION_RELEASE_FINISHED) {
				status = MachineState.Empty;
				conveyor.msgSpaceAvailable();
				stateChanged();
			}
		}
	}
	
	
	//Actions
	private void processGlass() {
		print("Processing Glass");
		status = MachineState.Processing;
		transducer.fireEvent(channel, TEvent.WORKSTATION_DO_ACTION, null);
		stateChanged();
	}
	
	private void releaseGlass() {
		print("Releasing glass to nextCF. ");
		transducer.fireEvent(channel, TEvent.WORKSTATION_RELEASE_GLASS, null);
		nextCF.msgHereIsGlass(glass);
		nextFree = false;
		glass = null;
		stateChanged();
	}
	
	
	
	public void setConveyor(Conveyor c) {
		conveyor = c;
	}
	
	public void setPopup(Popup p) {
		popup = p;
	}
	
	public void setNextCF(ConveyorFamily cf) {
		nextCF = cf;
	}
	
	public TChannel getChannel() {
		return channel;
	}

}
