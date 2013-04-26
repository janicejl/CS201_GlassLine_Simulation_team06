
package gui.panels;

import engine.agent.shared.ConveyorFamily14;
import engine.agent.shared.ConveyorFamilyOffline;
import engine.agent.shared.ConveyorFamilyOnlineMachine;
import engine.agent.shared.Glass;
import engine.agent.shared.MachineAgent;
import engine.brandonCF.agents.ConShuttle;
import engine.ryanCF.agent.BinAgent;
import engine.ryanCF.agent.TruckAgent;
import gui.drivers.FactoryFrame;

import java.util.ArrayList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JPanel;

import transducer.TChannel;
import transducer.Transducer;
import transducer.TransducerDebugMode;

/**
 * The FactoryPanel is highest level panel in the actual kitting cell. The
 * FactoryPanel makes all the back end components, connects them to the
 * GuiComponents in the DisplayPanel. It is responsible for handing
 * communication between the back and front end.
 */
@SuppressWarnings("serial")
public class FactoryPanel extends JPanel
{
	/** The frame connected to the FactoryPanel */
	private FactoryFrame parent;

	/** The control system for the factory, displayed on right */
	private ControlPanel cPanel;

	/** The graphical representation for the factory, displayed on left */
	private DisplayPanel dPanel;

	/** Allows the control panel to communicate with the back end and give commands */
	private Transducer transducer;

	BinAgent binAgent;
	
	TruckAgent truck;
	/**
	 * Constructor links this panel to its frame
	 */
	public FactoryPanel(FactoryFrame fFrame)
	{
		parent = fFrame;

		// initialize transducer
		transducer = new Transducer();
		//transducer.setDebugMode(TransducerDebugMode.EVENTS_AND_ACTIONS);
		transducer.startTransducer();

		// use default layout
		// dPanel = new DisplayPanel(this);
		// dPanel.setDefaultLayout();
		// dPanel.setTimerListeners();

		// initialize and run
		this.initialize();
		this.initializeBackEnd();
	}

	/**
	 * Initializes all elements of the front end, including the panels, and lays
	 * them out
	 */
	private void initialize()
	{
		this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));

		// initialize control panel
		cPanel = new ControlPanel(this, transducer);

		// initialize display panel
		dPanel = new DisplayPanel(this, transducer);

		// add panels in
		// JPanel tempPanel = new JPanel();
		// tempPanel.setPreferredSize(new Dimension(830, 880));
		// this.add(tempPanel);

		this.add(dPanel);
		this.add(cPanel);
	}

	/**
	 * Feel free to use this method to start all the Agent threads at the same time
	 */
	private void initializeBackEnd()
	{
		// ===========================================================================
		// TODO initialize and start Agent threads here
		// ===========================================================================

		
		//Initializing Agents
		binAgent = new BinAgent(transducer);
		ConveyorFamilyOnlineMachine cutterCF = new ConveyorFamilyOnlineMachine(0, TChannel.CUTTER, transducer);
		MachineAgent cutter = new MachineAgent(TChannel.CUTTER, transducer, 0);
		ConShuttle shuttleConBL = new ConShuttle("ShuttleConBL", transducer, 1); 
		ConveyorFamilyOnlineMachine breakoutCF = new ConveyorFamilyOnlineMachine(2, TChannel.BREAKOUT, transducer);
		MachineAgent breakout = new MachineAgent(TChannel.BREAKOUT, transducer, 2);
		ConveyorFamilyOnlineMachine manualBreakoutCF = new ConveyorFamilyOnlineMachine(3, TChannel.MANUAL_BREAKOUT, transducer);
		MachineAgent manualBreakout = new MachineAgent(TChannel.MANUAL_BREAKOUT, transducer, 3);
		ConShuttle shuttleConTL = new ConShuttle("ShuttleConTL",transducer,4);
		ConveyorFamilyOnlineMachine washerCF = new ConveyorFamilyOnlineMachine(8, TChannel.WASHER, transducer);
		MachineAgent washer = new MachineAgent(TChannel.WASHER, transducer, 8);
		ConShuttle shuttleConTR = new ConShuttle("ShuttleConTR",transducer, 9);
		ConveyorFamilyOnlineMachine painterCF = new ConveyorFamilyOnlineMachine(10, TChannel.PAINTER, transducer);
		MachineAgent painter = new MachineAgent(TChannel.PAINTER, transducer, 10);
		ConveyorFamilyOnlineMachine uvCF = new ConveyorFamilyOnlineMachine(11, TChannel.UV_LAMP, transducer);
		MachineAgent uv = new MachineAgent(TChannel.UV_LAMP, transducer, 11);
		ConShuttle shuttleConBR = new ConShuttle("ShuttleConBR",transducer, 12);
		ConveyorFamilyOnlineMachine ovenCF = new ConveyorFamilyOnlineMachine(13, TChannel.OVEN, transducer);
		MachineAgent oven = new MachineAgent(TChannel.OVEN, transducer, 13);
		ConveyorFamily14 cf14 = new ConveyorFamily14("CF14", transducer);
		truck = new TruckAgent("Truck", transducer);
		
		ConveyorFamilyOffline popup1 = new ConveyorFamilyOffline(parent,5,transducer,TChannel.DRILL);
		ConveyorFamilyOffline popup2 = new ConveyorFamilyOffline(parent,6,transducer,TChannel.CROSS_SEAMER);
		ConveyorFamilyOffline popup3 = new ConveyorFamilyOffline(parent,7,transducer,TChannel.GRINDER);
		//Linking all the agents
		
		binAgent.setNextCF(cutterCF);
		
		cutterCF.setMachine(cutter);
		cutterCF.setBin(binAgent);
		cutter.setConveyor(cutterCF.getConveyor());
		cutter.setNextCF(shuttleConBL);
		shuttleConBL.setMachine(cutter);
		shuttleConBL.setConveyor(breakoutCF);
		shuttleConBL.startThread();

		breakoutCF.setPreviousCF(shuttleConBL);
		breakoutCF.setMachine(breakout);
		breakout.setConveyor(breakoutCF.getConveyor());
		breakout.setNextCF(manualBreakoutCF);
		
		manualBreakoutCF.setPreviousMachine(breakout);
		manualBreakoutCF.setMachine(manualBreakout);
		manualBreakout.setConveyor(manualBreakoutCF.getConveyor());
		manualBreakout.setNextCF(shuttleConTL);			//TODO have to add shuttle CF as nextCF
		
		shuttleConTL.setMachine(manualBreakout);
		shuttleConTL.setConveyor(popup1);
		shuttleConTL.startThread();
		
		washerCF.setPreviousCF(popup3);
		washerCF.setMachine(washer);
		washer.setConveyor(washerCF.getConveyor());
		washer.setNextCF(shuttleConTR);			//TODO have to add shuttle CF as nextCF
		
		shuttleConTR.setMachine(washer);
		shuttleConTR.setConveyor(painterCF);
		shuttleConTR.startThread();
		
		painterCF.setPreviousCF(shuttleConTR);
		painterCF.setMachine(painter);
		painter.setConveyor(painterCF.getConveyor());
		painter.setNextCF(uvCF);
		
		uvCF.setPreviousMachine(painter);
		uvCF.setMachine(uv);
		uv.setConveyor(uvCF.getConveyor());
		uv.setNextCF(shuttleConBR);			//TODO have to add shuttle CF as nextCF
		
		shuttleConBR.setMachine(uv);
		shuttleConBR.setConveyor(ovenCF);
		
		
		ovenCF.setPreviousCF(shuttleConBR);
		ovenCF.setMachine(oven);
		oven.setConveyor(ovenCF.getConveyor());
		oven.setNextCF(cf14);			//TODO have to add truck CF as nextCF
		
		cf14.setPreviousMachine(oven);
		cf14.setTruck(truck);
		
		truck.setConveyorFamily(cf14);
		
		popup1.setPreviousCF(shuttleConTL);
		popup1.setNextCF(popup2);
		popup2.setPreviousCF(popup1);
		popup2.setNextCF(popup3);
		popup3.setPreviousCF(popup2);
		popup3.setNextCF(washerCF);
		
		System.out.println("Back end initialization finished.");

		//Starting agent threads inside cf groups. 
		binAgent.startThread();
		cutterCF.startThread();
		cutter.startThread();
		//shuttleConBL.startThread(); //with shuttle need to be replaced
		breakoutCF.startThread();
		breakout.startThread();
		manualBreakoutCF.startThread();
		manualBreakout.startThread();
		washerCF.startThread();
		washer.startThread();
		painterCF.startThread();
		painter.startThread();
		uvCF.startThread();
		uv.startThread();
		shuttleConBR.startThread();
		ovenCF.startThread();
		oven.startThread();
		cf14.startThread();
		truck.startThread();

		//Testing for JANICE - Machine agent processing being optional. 
//		boolean[] g = {false,true,true,true,true,true,true,true,true,true,true,true,true,true};
//		cutterCF.msgHereIsGlass(new Glass(g));
//		transducer.fireEvent(TChannel.BIN, TEvent.BIN_CREATE_PART, null);
		
	}

	/**
	 * Returns the parent frame of this panel
	 * 
	 * @return the parent frame
	 */
	public FactoryFrame getGuiParent()
	{
		return parent;
	}

	/**
	 * Returns the control panel
	 * 
	 * @return the control panel
	 */
	public ControlPanel getControlPanel()
	{
		return cPanel;
	}

	/**
	 * Returns the display panel
	 * 
	 * @return the display panel
	 */
	public DisplayPanel getDisplayPanel()
	{
		return dPanel;
	}
	
	/**
	 * Starts GlassLine post-initialization
	 */
	public void startFactory(int quantity, boolean[] setting) {
		List<Glass> tempGlassToProcess = new ArrayList<Glass>();
		for(int i = 0; i < quantity; i++) {
			tempGlassToProcess.add(new Glass(setting));
		}
		
		//tempGlassToProcess.add(new Glass(recipe));
		truck.newOrder(quantity);
		binAgent.msgProcessGlassOrder(tempGlassToProcess);
	}
}
