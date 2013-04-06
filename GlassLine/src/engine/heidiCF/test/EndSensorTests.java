package engine.heidiCF.test;

import transducer.TChannel;
import transducer.TEvent;
import transducer.Transducer;
import engine.heidiCF.agent.EndSensor;
import engine.heidiCF.test.Mocks.MockConveyor;

import junit.framework.TestCase;

public class EndSensorTests extends TestCase {
	
	Transducer t = new Transducer();
	EndSensor e;
	MockConveyor conveyor = new MockConveyor("Conveyor");
	
	public void testEndSensorReleased()
	{
		e = new EndSensor(1,t);
		e.setConveyor(conveyor);
		assertEquals(
				"The mock conveyor has 0 log. Event log: "
						+ conveyor.log.toString(), 0, conveyor.log.size());
	
		t.startTransducer(); 
		Integer[] args = new Integer[1];
		args[0] = new Integer(1);
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
						+ conveyor.log.toString(), 1, conveyor.log.size());
		assertTrue("The mock conveyor got a message :" ,conveyor.log.containsString("msgEndSensorAvailable"));
	}
	
	public void testEndSensorPressed()
	{
		e = new EndSensor(1,t);
		e.setConveyor(conveyor);
		assertEquals(
				"The mock conveyor has 0 log. Event log: "
						+ conveyor.log.toString(), 0, conveyor.log.size());
	
		t.startTransducer(); 
		Integer[] args = new Integer[1];
		args[0] = new Integer(1);
		t.fireEvent(TChannel.SENSOR,TEvent.SENSOR_GUI_PRESSED,args);
		//send the event SENSOR_GUI_PRESSED to the end sensor
		
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		e.pickAndExecuteAnAction();
		 
		assertEquals(
				"The mock conveyor has 1 log. Event log: "
						+ conveyor.log.toString(), 1, conveyor.log.size());
		assertTrue("The mock conveyor got a message :" ,conveyor.log.containsString("msgGlassArrivedAtEnd"));
		
	} 
	
}
