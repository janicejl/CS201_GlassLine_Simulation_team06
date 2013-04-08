
package gui.panels;

import engine.agent.shared.ConveyorFamilyOnlineMachine;
import engine.agent.shared.ConveyorFamily1;
import engine.agent.shared.Glass;
import engine.agent.shared.MachineAgent;
import engine.heidiCF.agent.ConveyorFamilyClass;
import gui.drivers.FactoryFrame;

import javax.swing.BoxLayout;
import javax.swing.JPanel;

import transducer.TChannel;
import transducer.TEvent;
import transducer.Transducer;

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

	/**
	 * Constructor links this panel to its frame
	 */
	public FactoryPanel(FactoryFrame fFrame)
	{
		parent = fFrame;

		// initialize transducer
		transducer = new Transducer();
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
		ConveyorFamilyOnlineMachine cutterCF = new ConveyorFamilyOnlineMachine(TChannel.CUTTER, transducer);
		MachineAgent cutter = new MachineAgent(TChannel.CUTTER, transducer);
		ConveyorFamily1 cf1 = new ConveyorFamily1(transducer);	// with shuttle - need to be replaced. 

		//Linking all the agents
		cutterCF.setMachine(cutter);
		cutter.setConveyor(cutterCF.getConveyor());
		cutter.setNextCF(cf1);
		cf1.setMachine(cutter);


		System.out.println("Back end initialization finished.");

		//Starting agent threads inside cf groups. 
		cutterCF.startThread();
		cutter.startThread();
		cf1.startThread();

		
		//temporary starting the animation until the bin agent is created. 
		cutterCF.msgHereIsGlass(new Glass(true, true, true));
		transducer.fireEvent(TChannel.BIN, TEvent.BIN_CREATE_PART, null);
		
//		//initialization of the three popup (for test) I started the thread in my conveyor family--Heidi
//		ConveyorFamilyClass popup1 = new ConveyorFamilyClass(5,transducer,TChannel.DRILL);
//		ConveyorFamilyClass popup2 = new ConveyorFamilyClass(6,transducer,TChannel.CROSS_SEAMER);
//		ConveyorFamilyClass popup3 = new ConveyorFamilyClass(7,transducer,TChannel.GRINDER);
//		
//		popup1.setNextCF(popup2);
//		popup2.setNextCF(popup3);
//		popup2.setPreviousCF(popup1);
//		popup3.setPreviousCF(popup2);
//		Glass g = new Glass(false,true,false);
//		popup1.msgHereIsGlass(g);
		
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
}
