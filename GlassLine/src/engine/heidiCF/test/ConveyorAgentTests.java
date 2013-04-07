package engine.heidiCF.test;

import java.util.Timer;
import java.util.TimerTask;

import transducer.Transducer;
import engine.heidiCF.agent.*;
import engine.heidiCF.test.Mocks.*;
import junit.framework.TestCase;

public class ConveyorAgentTests extends TestCase{
	public ConveyorAgent conveyor;
	Transducer t = new Transducer();

	
	public void testMsgHereIsGlass_OneGlass()
	{
		conveyor = new ConveyorAgent(0,t);// set its index to be 1
		t.startTransducer();
		final MockAnimation animation = new MockAnimation(t);
		conveyor.setPopup(new MockPopup("MockPopup"));
		boolean[] recipe = new boolean[10];
		recipe[0]=true;
		for(int i=1;i<recipe.length;i++)
		{
			recipe[i]=false;
		}
		Glass g = new Glass(recipe);
		
		assertEquals(
				"The conveyor agent should have 0 glass in the list glasses.", 0,conveyor.glasses.size());
		conveyor.msgHereIsGlass(g);
		
		assertEquals(
				"The conveyor agent should have one glass in the list glasses.", 1,conveyor.glasses.size());
		assertTrue("The endSensor of the conveyor should be empty",conveyor.endSensorEmpty);
		// Call the cashier's scheduler
		conveyor.pickAndExecuteAnAction();
		
		Timer timer = new Timer();
		timer.schedule(new TimerTask(){
		    public void run(){//this routine is like a message reception    		    	
		    	assertTrue("The animation should get a message which asks the conveyor 0 to \"start to move\", Event Log: "+ animation.log.toString(),animation.log.containsString("CONVEYOR_DO_START"));
				assertTrue("The index of the target conveyor should be 0 ",animation.log.containsString("let conveyor 0"));
				assertEquals(
						"Only 1 message should have been sent to the animation. Event log: "
								+ animation.log.toString(), 1, animation.log.size());
		    }		
		 }, 50000);
		
	}
	
	public void testMsgHereIsGlass_TwoGlassesFirstArrivesAtEndSensorWhenSecondComes()
	{
		
		conveyor = new ConveyorAgent(0,t);// set its index to be 1
		t.startTransducer();
		final MockAnimation animation = new MockAnimation(t);
		conveyor.setPopup(new MockPopup("MockPopup"));
		boolean[] recipe = new boolean[10];
		recipe[0]=true;
		for(int i=1;i<recipe.length;i++)
		{
			recipe[i]=false;
		}
		Glass g = new Glass(recipe);
		
		assertEquals(
				"The conveyor agent should have 0 glass in the list glasses.", 0,conveyor.glasses.size());
		conveyor.msgHereIsGlass(g);
		
		assertEquals(
				"The conveyor agent should have one glass in the list glasses.", 1,conveyor.glasses.size());
		assertTrue("The endSensor of the conveyor should be empty",conveyor.endSensorEmpty);
		// Call the cashier's scheduler
		conveyor.pickAndExecuteAnAction();
			
		try {
			Thread.sleep(1500);
		} catch (InterruptedException e) {
		
			e.printStackTrace();
		}   		    	 		    	
		    	assertTrue("The animation should get a message which asks the conveyor 0 to \"start to move\", Event Log: "+ animation.log.toString(),animation.log.containsString("CONVEYOR_DO_START"));
				assertTrue("The index of the target conveyor should be 0 ",animation.log.containsString("let conveyor 0"));
				assertEquals(
						"Only 1 message should have been sent to the animation. Event log: "
								+ animation.log.toString(), 1, animation.log.size());
		   
		animation.log.clear();
		conveyor.msgGlassArrivedAtEnd();
		
		conveyor.pickAndExecuteAnAction();
		conveyor.pickAndExecuteAnAction();
		
		try {
			Thread.sleep(1500);
		} catch (InterruptedException e) {
		
			e.printStackTrace();
		}   		    	 		    	
		    	assertTrue("The animation should get a message which asks the conveyor 0 to \"CONVEYOR_DO_STOP\", Event Log: "+ animation.log.toString(),animation.log.containsString("CONVEYOR_DO_STOP"));
				assertTrue("The index of the target conveyor should be 0 ",animation.log.containsString("let conveyor 0"));
				assertEquals(
						"Only 1 message should have been sent to the animation. Event log: "
								+ animation.log.toString(), 1, animation.log.size());
		
		
		
	}
	
	public void testMsgHereIsGlass_TwoGlasses_FirstNotArrivedWhenSecondComes()
	{
		conveyor = new ConveyorAgent(0,t);// set its index to be 1
		t.startTransducer();
		final MockAnimation animation = new MockAnimation(t);
		conveyor.setPopup(new MockPopup("MockPopup"));
		boolean[] recipe = new boolean[10];
		recipe[0]=true;
		for(int i=1;i<recipe.length;i++)
		{
			recipe[i]=false;
		}
		Glass g = new Glass(recipe);
		Glass g2 = new Glass(recipe);
		assertEquals(
				"The conveyor agent should have 0 glass in the list glasses.", 0,conveyor.glasses.size());
		conveyor.msgHereIsGlass(g);
		
		assertEquals(
				"The conveyor agent should have one glass in the list glasses.", 1,conveyor.glasses.size());
		assertTrue("The endSensor of the conveyor should be empty",conveyor.endSensorEmpty);

		conveyor.pickAndExecuteAnAction();
		try {
			Thread.sleep(1500);
		} catch (InterruptedException e) {
		
			e.printStackTrace();
		}   			    	
		    	assertTrue("The animation should get a message which asks the conveyor 0 to \"start to move\", Event Log: "+ animation.log.toString(),animation.log.containsString("CONVEYOR_DO_START"));
				assertTrue("The index of the target conveyor should be 0 ",animation.log.containsString("let conveyor 0"));
				assertEquals(
						"Only 1 message should have been sent to the animation. Event log: "
								+ animation.log.toString(), 1, animation.log.size());
		 
		
		conveyor.msgHereIsGlass(g2);
		assertEquals(
				"The conveyor agent should have 2 glasses in the list glasses.", 2,conveyor.glasses.size());
		assertTrue("The endSensor of the conveyor should be empty",conveyor.endSensorEmpty);
		conveyor.pickAndExecuteAnAction();
		try {
			Thread.sleep(1500);
		} catch (InterruptedException e) {
		
			e.printStackTrace();
		}   			    	
		 assertEquals(
						"Still, only 1 message should have been sent to the animation. Event log: "
								+ animation.log.toString(), 1, animation.log.size());
				//because the conveyor was running when the second glass gets onto it
		
	
		
	}

	public void testMsgGlassArrivedAtEnd_NeedMachine_MachineAvailable()
	{
		conveyor = new ConveyorAgent(0,t);// set its index to be 1
		t.startTransducer();
		MockPopup popup = new MockPopup("MockPopup");
		conveyor.setPopup(popup);
		boolean[] recipe = new boolean[10];
		recipe[0]=true;
		for(int i=1;i<recipe.length;i++)
		{
			recipe[i]=false;
		}
		Glass g = new Glass(recipe);
		assertEquals(
				"The conveyor agent should have 0 glass in the list glasses.", 0,conveyor.glasses.size());
		conveyor.msgHereIsGlass(g);
		conveyor.pickAndExecuteAnAction();
		
		assertEquals(
				"The next popup should have 2 available slots on the machines", 2,conveyor.myPopup.getReadyPositions());
		
		conveyor.msgGlassArrivedAtEnd();
		assertTrue("The endSensor of the conveyor should not be empty",!conveyor.endSensorEmpty);
		assertEquals(
				"The conveyor agent should have one glass in the list glasses.", 1,conveyor.glasses.size());
		conveyor.pickAndExecuteAnAction();
		assertEquals(
				"The next popup should have 1 available slots now on the machines", 1,conveyor.myPopup.getReadyPositions());
		
		assertTrue("The mockpopup should get a message which gives the glass to it "+ popup.log.toString(),popup.log.containsString("the mock popup receives the glass from the conveyor"));
		assertTrue("The mockpopup should know the glass need the machine "+ popup.log.toString(),popup.log.containsString("it needs the machine"));
		assertEquals(
				"Only 1 message should have been sent to the animation. Event log: "
						+ popup.log.toString(), 1, popup.log.size());
		
		
	}
	
	public void testMsgGlassArrivedAtEnd_NoMachine_MachineAvailable()
	{
		conveyor = new ConveyorAgent(0,t);// set its index to be 1
		t.startTransducer();
		MockPopup popup = new MockPopup("MockPopup");
		conveyor.setPopup(popup);
		boolean[] recipe = new boolean[10];
		recipe[0]=false;
		for(int i=1;i<recipe.length;i++)
		{
			recipe[i]=false;
		}
		Glass g = new Glass(recipe);
		assertEquals(
				"The conveyor agent should have 0 glass in the list glasses.", 0,conveyor.glasses.size());
		conveyor.msgHereIsGlass(g);
		conveyor.pickAndExecuteAnAction();
		
		assertEquals(
				"The next popup should have 2 available slots on the machines", 2,conveyor.myPopup.getReadyPositions());
		
		conveyor.msgGlassArrivedAtEnd();
		assertTrue("The endSensor of the conveyor should not be empty",!conveyor.endSensorEmpty);
		assertEquals(
				"The conveyor agent should have one glass in the list glasses.", 1,conveyor.glasses.size());
		conveyor.pickAndExecuteAnAction();
		
		assertEquals(
				"The next popup should still have 2 available slots now on the machines", 2,conveyor.myPopup.getReadyPositions());
		assertEquals("The next popup should have 1 glass: ", popup.glasses.size(),1);
		assertTrue("The mockpopup should get a message which gives the glass to it "+ popup.log.toString(),popup.log.containsString("the mock popup receives the glass from the conveyor"));
		assertTrue("The mockpopup should know the glass does not need the machine "+ popup.log.toString(),popup.log.containsString("it does not need the machine"));
		assertEquals(
				"Only 1 message should have been sent to the popup. Event log: "
						+ popup.log.toString(), 1, popup.log.size());
	}
	
	public void testMsgGlassArrivedAtEnd_NeedMachine_MachineUnAvailable()
	{
		conveyor = new ConveyorAgent(0,t);// set its index to be 1
		t.startTransducer();
		MockPopup popup = new MockPopup("MockPopup");
		conveyor.setPopup(popup);
		boolean[] recipe = new boolean[10];
		recipe[0]=true;	//needs the machine
		for(int i=1;i<recipe.length;i++)
		{
			recipe[i]=false;
		}
		Glass g = new Glass(recipe);
		assertEquals("The popup should have no log in it", popup.log.size(),0);

		assertEquals(
				"The conveyor agent should have 0 glass in the list glasses.", 0,conveyor.glasses.size());
		conveyor.msgHereIsGlass(g);
		conveyor.pickAndExecuteAnAction();
		
		conveyor.myPopup.setReadyPositions(0);
		assertEquals(
				"The next popup should have 0 available slots on the machines", 0,conveyor.myPopup.getReadyPositions());
		assertTrue("The next popup should still have 0 glasses: ", popup.glasses.isEmpty());
		
		conveyor.msgGlassArrivedAtEnd();
		assertTrue("The endSensor of the conveyor should not be empty",!conveyor.endSensorEmpty);
		assertEquals(
				"The conveyor agent should have one glass in the list glasses.", 1,conveyor.glasses.size());
		conveyor.pickAndExecuteAnAction();
		
		assertEquals(
				"The next popup should still have 0 available slots now on the machines", 0,conveyor.myPopup.getReadyPositions());
		
		assertTrue("The next popup should still have 0 glasses: ", popup.glasses.isEmpty());
		assertEquals("The popup should have no log in it", popup.log.size(),0);
	}
	
	public void testMsgGlassArrivedAtEnd_NoMachine_MachineUnAvailable()
	{
		conveyor = new ConveyorAgent(0,t);// set its index to be 1
		t.startTransducer();
		MockPopup popup = new MockPopup("MockPopup");
		conveyor.setPopup(popup);
		boolean[] recipe = new boolean[10];
		recipe[0]=false;	//needs the machine
		for(int i=1;i<recipe.length;i++)
		{
			recipe[i]=false;
		}
		Glass g = new Glass(recipe);
		assertEquals(
				"The conveyor agent should have 0 glass in the list glasses.", 0,conveyor.glasses.size());
		conveyor.msgHereIsGlass(g);
		conveyor.pickAndExecuteAnAction();
		
		conveyor.myPopup.setReadyPositions(0);
		assertEquals(
				"The next popup should have 0 available slots on the machines", 0,conveyor.myPopup.getReadyPositions());
		assertTrue("The next popup should still have 0 glasses: ", popup.glasses.isEmpty());
		
		conveyor.msgGlassArrivedAtEnd();
		assertTrue("The endSensor of the conveyor should not be empty",!conveyor.endSensorEmpty);
		assertEquals(
				"The conveyor agent should have one glass in the list glasses.", 1,conveyor.glasses.size());
		conveyor.pickAndExecuteAnAction();
		
		assertEquals(
				"The next popup should still have 0 available slots now on the machines", 0,conveyor.myPopup.getReadyPositions());
		
		assertEquals("The next popup should have 1 glass: ", popup.glasses.size(),1);
		
		assertTrue("The mockpopup should get a message which gives the glass to it "+ popup.log.toString(),popup.log.containsString("the mock popup receives the glass from the conveyor"));
		assertTrue("The mockpopup should know the glass does not need the machine "+ popup.log.toString(),popup.log.containsString("it does not need the machine"));
		assertEquals(
				"Only 1 message should have been sent to the popup. Event log: "
						+ popup.log.toString(), 1, popup.log.size());
	}
	
	public void testMsgPopupAvailable_OneAvailableMachineLeft()
	{
		conveyor = new ConveyorAgent(0,t);// set its index to be 0
		t.startTransducer();
		MockPopup popup = new MockPopup("MockPopup");
		conveyor.setPopup(popup);
		boolean[] recipe = new boolean[10];
		recipe[0]=true;
		for(int i=1;i<recipe.length;i++)
		{
			recipe[i]=false;
		}
		Glass g = new Glass(recipe);
		conveyor.msgHereIsGlass(g);
		conveyor.pickAndExecuteAnAction();
		conveyor.msgGlassArrivedAtEnd();
		conveyor.pickAndExecuteAnAction();
		
		assertEquals(
				"The next popup should have 1 available slots now on the machines", 1,conveyor.myPopup.getReadyPositions());
		
		conveyor.msgPopupAvailable();
		
		assertEquals(
				"The next popup should now have 2 available slots on the machines", 2,conveyor.myPopup.getReadyPositions());
				
	}
	
	public void testMsgPopupAvailable_NoAvailableMachineAndOneGlassWaitingForPopup()
	{
		conveyor = new ConveyorAgent(0,t);// set its index to be 1
		t.startTransducer();
		MockPopup popup = new MockPopup("MockPopup");
		conveyor.setPopup(popup);
		boolean[] recipe = new boolean[10];
		recipe[0]=true;
		for(int i=1;i<recipe.length;i++)
		{
			recipe[i]=false;
		}
		Glass g = new Glass(recipe);
		Glass g2 = new Glass(recipe);
		Glass g3 = new Glass(recipe);
		conveyor.msgHereIsGlass(g);
		conveyor.pickAndExecuteAnAction();
		conveyor.msgHereIsGlass(g2);	
		conveyor.pickAndExecuteAnAction();
		conveyor.msgHereIsGlass(g3);	
		conveyor.pickAndExecuteAnAction();
		conveyor.msgGlassArrivedAtEnd();
		conveyor.pickAndExecuteAnAction();
		conveyor.msgGlassArrivedAtEnd();
		conveyor.pickAndExecuteAnAction();	
		
		assertEquals(
				"The next popup should have 0 available slots now on the machines", 0,conveyor.myPopup.getReadyPositions());
		assertEquals(
				"The conveyor should have 3 glasses", 3,conveyor.glasses.size());
		assertEquals(
				"The next popup should have 2 glasses", 2,popup.glasses.size());
		
		conveyor.msgGlassArrivedAtEnd();	//even if the third piece arrives here, the popup won't take it
		conveyor.pickAndExecuteAnAction();
		
		assertEquals(
				"The next popup should have 0 available slots now on the machines", 0,conveyor.myPopup.getReadyPositions());
		assertEquals(
				"The conveyor should have 3 glasses", 3,conveyor.glasses.size());
		assertEquals(
				"The next popup should have 2 glasses", 2,popup.glasses.size());
		
		conveyor.msgPopupAvailable();
		conveyor.pickAndExecuteAnAction();
		
		
		assertEquals(
				"The conveyor should have 3 glasses", 3,conveyor.glasses.size());
		assertEquals(
				"The next popup should have 3 glasses", 3,popup.glasses.size());	
	}

	//test the situation where 2 glasses are in the machine and the third one does not need to be onto the machine

	public void testMsgEndSensorAvailable() //the conveyor should delete the glass
	{
		conveyor = new ConveyorAgent(0,t);// set its index to be 1
		t.startTransducer();
		final MockAnimation animation = new MockAnimation(t);
		MockPopup popup = new MockPopup("MockPopup");
		conveyor.setPopup(popup);
		boolean[] recipe = new boolean[10];
		recipe[0]=true;
		for(int i=1;i<recipe.length;i++)
		{
			recipe[i]=false;
		}
		Glass g = new Glass(recipe);
		Glass g2 = new Glass(recipe);
		Glass g3 = new Glass(recipe);
		conveyor.msgHereIsGlass(g);
		conveyor.pickAndExecuteAnAction();
		conveyor.msgHereIsGlass(g2);	
		conveyor.pickAndExecuteAnAction();
		conveyor.msgHereIsGlass(g3);	
		conveyor.pickAndExecuteAnAction();
		
		conveyor.msgGlassArrivedAtEnd();		
		conveyor.pickAndExecuteAnAction();
		
		assertEquals(
				"The next popup should have 1 available slots now on the machines", 1,conveyor.myPopup.getReadyPositions());
		assertEquals(
				"The conveyor should have 3 glasses", 3,conveyor.glasses.size());
		assertEquals(
				"The next popup should have 1 glasses", 1,popup.glasses.size());
		
		conveyor.msgEndSensorAvailable();
		conveyor.pickAndExecuteAnAction();
		
		assertEquals(
				"The next popup should have 1 available slots now on the machines", 1,conveyor.myPopup.getReadyPositions());
		assertEquals(
				"The conveyor should have 2 glasses", 2,conveyor.glasses.size());
		assertEquals(
				"The next popup should have 1 glasses", 1,popup.glasses.size());
		
		
		
	}
	
	
	
}
