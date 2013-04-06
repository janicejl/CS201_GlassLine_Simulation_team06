package engine.heidiCF.test;

import transducer.TChannel;
import transducer.TEvent;
import transducer.Transducer;
import engine.heidiCF.agent.*;
import engine.heidiCF.test.Mocks.*;
import junit.framework.TestCase;

public class FrontSensorTests extends TestCase{
	Transducer t = new Transducer();
	FrontSensor e;
	MockPrevCF prevCF = new MockPrevCF();

	public void testEndSensorReleased()
	{
		e = new FrontSensor(0,t);
		e.setPrevCF(prevCF);
		assertEquals(
				"The mock pervious conveyor has 0 log. Event log: "
						+  prevCF.log.toString(), 0,  prevCF.log.size());
	
		t.startTransducer(); 
		Integer[] args = new Integer[1];
		args[0] = new Integer(0);
		t.fireEvent(TChannel.SENSOR,TEvent.SENSOR_GUI_RELEASED,args);
		//send the event SENSOR_GUI_PRESSED to the end sensor
		
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		e.pickAndExecuteAnAction();
		 
		assertEquals(
				"The mock conveyor has 1 log. Event log: "
						+  prevCF.log.toString(), 1,  prevCF.log.size());
		assertTrue("The mock previous conveyorFamily got a message  msgNewSpaceAvailable:" ,prevCF.log.containsString("msgNewSpaceAvailable"));
	}
	
}
