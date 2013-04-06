package engine.heidiCF.agent;


import transducer.Transducer;
import engine.heidiCF.interfaces.*;
import engine.heidiCF.test.Mocks.MockRobot;

public class ConveyorFamilyClass implements ConveyorFamily {
	Conveyor conveyor; 
	Popup popup;
	FrontSensor frontSensor;
	EndSensor endSensor;
//	ArrayList<Robot> robots;
	Robot robot0;
	Robot robot1;
	ConveyorFamily previousCF;
	ConveyorFamily nextCF;
	int myIndex;
	ConveyorFamilyClass(int index, Transducer t)
	{
		myIndex = index;
		conveyor = new ConveyorAgent(index,t);
//		robots = new ArrayList<Robot>();
//		robots.add(new MockRobot("Robot1"));
//		robots.add(new MockRobot("Robot2"));
//		popup = new PopupAgent(index,t,robots);
		robot0 = new MockRobot("Robot0");
		robot1 = new MockRobot("Robot1");
		frontSensor = new FrontSensor(index*2,t);
		endSensor = new EndSensor(index*2+1,t);
	}

	/* (non-Javadoc)
	 * @see engine.agent.ConveyorFamily#setPreviousCF(engine.agent.ConveyorFamily)
	 */
	public void setPreviousCF(ConveyorFamily prevCF)
	{
		previousCF = prevCF;
	}
	
	/* (non-Javadoc)
	 * @see engine.agent.ConveyorFamily#setNextCF(engine.agent.ConveyorFamily)
	 */
	public void setNextCF(ConveyorFamily nextCF)
	{
		this.nextCF = nextCF;
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
