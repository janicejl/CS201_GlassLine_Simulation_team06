package engine.brandonCF.agents;

import engine.agent.shared.Glass;
import engine.brandonCF.interfaces.ConveyorFamilyInterface;
import transducer.Transducer;

public class StandardConveyor implements ConveyorFamilyInterface{

	Sensor1Agent firstSensor;
	Sensor2Agent secondSensor;
	PopUpAgent popUp; //the popUp agent
	ConveyorAgent conveyor;
	ConveyorFamilyInterface previousCon, nextCon;
	String name;
	
	//Methods:
	public StandardConveyor(String name, Transducer t)
	{
		//initilize all variables/agents and set name
		this.name = name;
		firstSensor = new Sensor1Agent(this.name+" Front Sensor",t);
		conveyor = new ConveyorAgent(this.name+ "conveyor",t);
		secondSensor = new Sensor2Agent(this.name + "Second Sensor",t);
		popUp = new PopUpAgent(this.name + "Popup",t);
		
		//sets all the appropriate communication stuff
		firstSensor.setConveyor(conveyor);
		conveyor.setFirstSensor(firstSensor);
		conveyor.setSecondSensor(secondSensor);
		secondSensor.setConveyor(conveyor);
		secondSensor.setPopUp(popUp);
		popUp.setSensor(secondSensor);
		
	}
	
	public void msgHereisGlass(Glass g)//This is where glass is sent in
	{
		//in here, relay this message onto the appropriate agent
		firstSensor.msgHereisGlass(g);
	}
	
	public void msgSpaceOpen()//Here the appropriate agent is "told" it can send glass when ready
	{
		popUp.msgSpaceOpen();
	}

	
	public void setPreviousConveyor(ConveyorFamilyInterface con) {
		previousCon = con;
		firstSensor.setPreviousFamily(previousCon);
		
	}

	
	public void setNextConveyor(ConveyorFamilyInterface con) {
		nextCon = con;
		popUp.setNextCF(nextCon);
	}
	
	public void startConveyorFamily()
	{
		//starts the agents here
		firstSensor.startThread();
		conveyor.startThread();
		secondSensor.startThread();
		popUp.startThread();
	}
}
