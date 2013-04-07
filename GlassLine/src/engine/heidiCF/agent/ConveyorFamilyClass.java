package engine.heidiCF.agent;


import transducer.TChannel;
import transducer.Transducer;
import engine.heidiCF.interfaces.*;


public class ConveyorFamilyClass implements ConveyorFamily {
	ConveyorAgent conveyor; 
	PopupAgent popup;
	FrontSensor frontSensor;
	EndSensor endSensor;
//	ArrayList<Robot> robots;

	ConveyorFamily previousCF;
	ConveyorFamily nextCF;
	int myIndex;
	public ConveyorFamilyClass(int index, Transducer t,TChannel workStationType)
	{
		myIndex = index;
		conveyor = new ConveyorAgent(index,t);

		popup=new PopupAgent(index-5,t,workStationType);
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
	public void msgNewSpaceAvailable()
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
