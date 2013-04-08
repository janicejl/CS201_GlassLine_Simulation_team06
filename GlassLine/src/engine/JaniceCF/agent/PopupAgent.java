package engine.JaniceCF.agent;

import java.util.*;

import transducer.*;
import engine.JaniceCF.interfaces.*;
import engine.agent.Agent;
import engine.agent.shared.Glass;
import engine.agent.shared.Interfaces.ConveyorFamily;
import engine.agent.shared.Interfaces.Machine;

public class PopupAgent extends Agent implements Popup {

	//Data
	public enum PopupStatus {Nothing, IncomingNeedProcessing, IncomingNoProcessing, Loaded, Unloading}
	
	public enum Position {Up, Down, Moving}
	
	Conveyor conveyor;
	ConveyorFamily nextCF;
	
	boolean nextFree;
	PopupStatus status;
	Position position;
	
	ArrayList<MyMachine> machineList = new ArrayList<MyMachine>();
	ArrayList<MyGlass> glassList = new ArrayList<MyGlass>();	
	
	public enum MachineState {Empty, Processing, Done}
	private class MyMachine{
		Machine machine;
		MachineState status;
		
		public MyMachine(Machine m) {
			machine = m;
			status = MachineState.Empty;
		}
	}
	
	public enum GlassState {NeedProcessing, NoProcessing, Done, InMachine, Nothing}
	private class MyGlass {
		Glass glass;
		GlassState status;
		MyMachine machine;
		
		public MyGlass(Glass g, boolean needProcessing) {
			glass = g;
			
			if (needProcessing == true) {
				status = GlassState.NeedProcessing;
			} else {
				status = GlassState.NoProcessing;
			}
		}
	}
	
	public PopupAgent(String name, Transducer transducer) {
		super(name, transducer);
		
		transducer.register(this, TChannel.POPUP);
		
		nextFree = true;
		status = PopupStatus.Nothing;
		position = Position.Down;
	}
	
	//Messages 
	@Override
	public void msgSpaceAvailable() {
		nextFree = true;
		stateChanged();
	}

	//From Machine
	public void msgHereIsGlass(Glass g) {
		for (MyGlass gs: glassList) {
			if (gs.glass.equals(g)) {
				gs.machine.status = MachineState.Done;
				gs.status = GlassState.Done;
				stateChanged();
			}
		}
	}
	
	public void msgIncomingNeedProcessing() {
		status = PopupStatus.IncomingNeedProcessing;
		stateChanged();
	}
	
	public void msgIncomingNoProcessing() {
		status = PopupStatus.IncomingNoProcessing;
		stateChanged();
	}
	
	public void msgNeedProcessing(Glass g) {
		glassList.add(new MyGlass(g, true));
		stateChanged();
	}
	
	public void msgNoProcessing(Glass g) {
		glassList.add(new MyGlass(g, false));
		stateChanged();
	}
	
	//Scheduler
	@Override
	public boolean pickAndExecuteAnAction() {
		MyMachine m = getEmptyMachine();
		
		for (MyMachine ms: machineList) {
			if (ms.status == MachineState.Done) {
				if (position == Position.Down) {
					movePopupUp();
					return true;
				} else if (position == Position.Up) {
					machinePopupReady(ms);
					return true;
				}
			}
		}
		
		if (m != null) {
			if (status == PopupStatus.Loaded) {
				if (nextFree == true) {
					for (MyGlass g : glassList) {
						if (g.status == GlassState.Done) {
							if (position == Position.Up) {
								movePopupDown();
								return true;
							} else if (position == Position.Down) {
								passToNextCF(g);
								return true;
							}
						}
					}
					for (MyGlass g : glassList) {
						if (g.status == GlassState.NoProcessing) {
							passToNextCF(g);
							return true;
						}
					}
				}
				
				for (MyGlass g: glassList) {
					if (g.status == GlassState.NeedProcessing) {
						if (position == Position.Down){
							movePopupUp();
							return true;
						} else if (position == Position.Up){
							passToMachine(g, m);
							return true;
						}
					}
				}
			}
			
			
			if (status == PopupStatus.IncomingNeedProcessing) {
				if (position == Position.Up) {
					movePopupDown();
					return true;
				} else if (position == Position.Down){
					popupReady();
					return true;
				}
			}
		}
		
		return false;
	}

	@Override
	public void eventFired(TChannel channel, TEvent event, Object[] args) {
		
		if (channel == TChannel.POPUP) {
			if (event == TEvent.POPUP_GUI_MOVED_DOWN) {
				position = Position.Down;
				stateChanged();
			} 
			
			if (event == TEvent.POPUP_GUI_MOVED_UP) {
				position = Position.Up;
				stateChanged();
			}
			
			if (event == TEvent.POPUP_GUI_LOAD_FINISHED) {
				status = PopupStatus.Loaded;
				stateChanged();
			}
			
			if (event == TEvent.POPUP_GUI_RELEASE_FINISHED) {
				status = PopupStatus.Nothing;
				stateChanged();
			}
		}
	}

	
	
	//Actions
	private void movePopupDown() {
		position = Position.Moving;
		transducer.fireEvent(TChannel.POPUP, TEvent.POPUP_DO_MOVE_DOWN, null); //TODO put correct args
		stateChanged();
	} 
	
	private void movePopupUp() {
		position = Position.Moving;
		transducer.fireEvent(TChannel.POPUP, TEvent.POPUP_DO_MOVE_UP, null); //TODO put correct args
		stateChanged();
	}
	
	private void popupReady() {
		conveyor.msgSpaceAvailable();
		status = PopupStatus.Nothing;
		stateChanged();
	}
	
	private void passToNextCF(MyGlass g) {
		status = PopupStatus.Nothing;
		nextFree = false;
		transducer.fireEvent(TChannel.POPUP, TEvent.POPUP_RELEASE_GLASS, null);
		nextCF.msgHereIsGlass(g.glass);
		glassList.remove(g);
		stateChanged();
	}
	
	private void passToMachine(MyGlass g, MyMachine m) {
		status = PopupStatus.Nothing;
		m.status = MachineState.Processing;
		g.status = GlassState.InMachine;
		g.machine = m;
		//m.machine.msgAssembleGlass(g.glass);
		m.machine.msgHereIsGlass(g.glass);
		//TODO should I be calling loadWorkstartionTranducer here?
		transducer.fireEvent(TChannel.DRILL, TEvent.WORKSTATION_DO_LOAD_GLASS, null);
		stateChanged();
	}
	
	private void machinePopupReady(MyMachine m) {
		//TODO add channel to machine.
		status = PopupStatus.Loaded;
		m.status = MachineState.Empty;
		transducer.fireEvent(TChannel.DRILL, TEvent.WORKSTATION_RELEASE_GLASS, null);
		stateChanged();
	}
	
	private MyMachine getEmptyMachine() {
		for (MyMachine m: machineList) {
			if (m.status == MachineState.Empty) {
				return m;
			}
		}
		return null;
	}
}

