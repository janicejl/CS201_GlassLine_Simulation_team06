package engine.brandonCF.agents;

import transducer.Transducer;
import engine.agent.shared.Glass;
import engine.agent.shared.Interfaces.ConveyorFamily;
import engine.agent.shared.Interfaces.Machine;
import engine.brandonCF.interfaces.ConveyorFamilyInterface;

public class ShuttleCon implements ConveyorFamily{

	Sensor1Agent firstSensor;
	ShuttleSensor secondSensor;
	ConveyorAgent conveyor;
	ConveyorFamilyInterface previousCon, nextCon;
	String name;
	int number;
	
	//Methods:
	public ShuttleCon(String name, Transducer t, int num)
	{
		//initilize all variables/agents and set name
		this.name = name;
		number = num;
		firstSensor = new Sensor1Agent(this.name+" Front Sensor",t, number);
		conveyor = new ConveyorAgent(this.name+ "conveyor",t);
		secondSensor = new ShuttleSensor(this.name + "Second Sensor", t,number);
		
		//sets all the appropriate communication stuff
		firstSensor.setConveyor(conveyor);
		conveyor.setFirstSensor(firstSensor);
		conveyor.setSecondSensor(secondSensor);
		secondSensor.setConveyor(conveyor);
		
	}
	
	@Override
	public void msgHereIsGlass(Glass g)//This is where glass is sent in
	{
		//in here, relay this message onto the appropriate agent
		firstSensor.msgHereisGlass(g);
	}
	
	@Override
	public void msgSpaceAvailable()//Here the appropriate agent is "told" it can send glass when ready
	{
		secondSensor.msgSpaceOpen();
	}

	
	
	public void setPreviousConveyor(ConveyorFamilyInterface con) {
		previousCon = con;
		firstSensor.setPreviousFamily(previousCon);
		
	}
	
	public void setNextConveyor(ConveyorFamily con)
	{
		secondSensor.setNextConveyor(con);
	}
	
	public void startConveyorFamily()
	{
		//starts the agents here
		firstSensor.startThread();
		conveyor.startThread();
		secondSensor.startThread();
	}

	public void setPreviousMachine(Machine m)
	{
		firstSensor.setPreviousFamily(m);
	}
	
}