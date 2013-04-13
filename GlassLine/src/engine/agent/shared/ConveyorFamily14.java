package engine.agent.shared;

import engine.agent.shared.Interfaces.ConveyorFamily;
import engine.ryanCF.agent.*;
import engine.ryanCF.interfaces.Conveyor;

public class ConveyorFamily14 implements ConveyorFamily{

	Conveyor conveyor;
	@Override
	public void msgSpaceAvailable() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void msgHereIsGlass(Glass g) {
		// TODO Auto-generated method stub
		conveyor.msgHereIsGlass(g);
	}
	
}
