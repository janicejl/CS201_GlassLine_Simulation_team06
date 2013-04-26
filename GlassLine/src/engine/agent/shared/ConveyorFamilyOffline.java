package engine.agent.shared;


import transducer.TChannel;
import transducer.Transducer;
import engine.heidiCF.agent.ConveyorAgent;
import engine.heidiCF.agent.EndSensor;
import engine.heidiCF.agent.FrontSensor;
import engine.heidiCF.agent.PopupAgent;
import engine.heidiCF.interfaces.*;
import engine.agent.shared.Interfaces.*;
import gui.drivers.FactoryFrame;


public class ConveyorFamilyOffline implements ConveyorFamily {
	ConveyorAgent conveyor; 
	PopupAgent popup;
	FrontSensor frontSensor;
	EndSensor endSensor;
//	ArrayList<Robot> robots;

	ConveyorFamily previousCF;
	ConveyorFamily nextCF;
	int myIndex;
	public ConveyorFamilyOffline(FactoryFrame ff, int index, Transducer t,TChannel workStationType)
	{
		myIndex = index;
		conveyor = new ConveyorAgent(index,t);

		popup=new PopupAgent(ff, index-5,t,workStationType);
		conveyor.setPopup(popup);
		popup.setConveyor(conveyor);
		
		frontSensor = new FrontSensor(index*2,t);
		frontSensor.setIndex(2*index);
		endSensor = new EndSensor(index*2+1,t);
		endSensor.setIndex(2*index+1);
		
		conveyor.startThread();
		popup.startThread(); 
		endSensor.startThread();
		frontSensor.startThread();
		
		endSensor.setConveyor(conveyor);
	}

	/* (non-Javadoc)
	 * @see engine.agent.ConveyorFamily#setPreviousCF(engine.agent.ConveyorFamily)
	 */
	public void setPreviousCF(ConveyorFamily prevCF)
	{
		previousCF = prevCF;
		frontSensor.setPrevCF(prevCF);
	}
	
	/* (non-Javadoc)
	 * @see engine.agent.ConveyorFamily#setNextCF(engine.agent.ConveyorFamily)
	 */
	public void setNextCF(ConveyorFamily nextCF)
	{
		this.nextCF = nextCF;
		popup.setNextCF(nextCF);
		
	}
	
	/* (non-Javadoc)
	 * @see engine.agent.ConveyorFamily#msgNewSpaceAvailable()
	 */
	public void msgSpaceAvailable()
	{
		popup.msgNewSpaceAvailable();
	}
	/* (non-Javadoc)
	 * @see engine.agent.ConveyorFamily#msgHereIsGlass(engine.agent.Glass)
	 */
	public void msgHereIsGlass(Glass glass)
	{
		conveyor.msgHereIsGlass(glass);
	}
}
