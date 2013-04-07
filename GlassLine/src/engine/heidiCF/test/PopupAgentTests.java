package engine.heidiCF.test;

import transducer.TChannel;
import transducer.Transducer;
import engine.heidiCF.agent.*;
import engine.heidiCF.test.Mocks.*;
import junit.framework.TestCase;

public class PopupAgentTests extends TestCase{
	
	Transducer t = new Transducer();
	//ArrayList<MockRobot> robots = new ArrayList<MockRobot>();
	PopupAgent popup;
	MockConveyor conveyor = new MockConveyor("Conveyor");
	MockAnimation animation = new MockAnimation(t);
	MockNextCF nextCF= new MockNextCF();
	
	public void testMsgHereIsGlass_TwoAvailableMachines()
	{
		t.startTransducer();
//		robots.add(new MockRobot("robot0"));
//		robots.add(new MockRobot("robot1"));
		popup = new PopupAgent(0,t,TChannel.DRILL);
		popup.setConveyor(conveyor);
		
		boolean[] recipe = new boolean[10];
		recipe[0]=true;
		for(int i=1;i<recipe.length;i++)
		{
			recipe[i]=false;
		}
		Glass g = new Glass(recipe);
		
		assertEquals(
				"The popup agent should have 0 glass in the list glasses.", 0,popup.glasses.size());
		assertEquals("The popup should have 2 available machines",2,popup.availableMachine());
		popup.msgHereIsGlass(g);
		assertEquals(
				"The popup agent should have 1 glass in the list glasses.", 1,popup.glasses.size());
		
		popup.pickAndExecuteAnAction();
		
//		Timer timer = new Timer();
//		timer.schedule(new TimerTask(){
//		    public void run(){//this routine is like a message reception 
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		    	assertTrue("The animation should get a message which asks the popup 0 to \"POPUP_DO_MOVE_DOWN\", Event Log: "+ animation.log.toString(),animation.log.containsString("POPUP_DO_MOVE_DOWN"));
				assertTrue("The index of the target popup should be 0 ",animation.log.containsString("let popup 0"));
   		    	
		    	assertTrue("The animation should get a message which asks the conveyor 0 to \"CONVEYOR_DO_START\", Event Log: "+ animation.log.toString(),animation.log.containsString("CONVEYOR_DO_START"));
				assertTrue("The index of the target conveyor should be 0 "+animation.log.toString(),animation.log.containsString("let conveyor 0"));
		      		    	
		    	assertTrue("The animation should get a message which asks the popup 0 to \"POPUP_DO_MOVE_UP\", Event Log: "+ animation.log.toString(),animation.log.containsString("POPUP_DO_MOVE_UP"));
				assertTrue("The index of the target popup should be 0 ",animation.log.containsString("let popup 0"));
		     		    	
		    	assertTrue("The animation should get a message which asks the robot 0 to \"WORKSTATION_DO_LOAD_GLASS\", Event Log: "+ animation.log.toString(),animation.log.containsString("WORKSTATION_DO_LOAD_GLASS"));
				assertTrue("The index of the target robot should be 0 ",animation.log.containsString("let robot 0"));
		       		    			
	}
	
	public void testMsgHereIsGlass_OneAvailableMachine()
	{
		animation.log.clear();
		t.startTransducer();
//		robots.add(new MockRobot("robot0"));
//		robots.add(new MockRobot("robot1"));
		popup = new PopupAgent(0,t,TChannel.DRILL);
		popup.setConveyor(conveyor);
		
		boolean[] recipe = new boolean[10];
		recipe[0]=true;
		for(int i=1;i<recipe.length;i++)
		{
			recipe[i]=false;
		}
		Glass g = new Glass(recipe);
		
		popup.makeOneMachineUnavailable();
		assertEquals(
				"The popup agent should have 0 glass in the list glasses.", 0,popup.glasses.size());
		assertEquals("The popup should have 1 available machines",1,popup.availableMachine());
		popup.msgHereIsGlass(g);
		assertEquals(
				"The popup agent should have 1 glass in the list glasses.", 1,popup.glasses.size());
		
		popup.pickAndExecuteAnAction();
		
	  
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}		    	
		    	assertTrue("The animation should get a message which asks the popup 0 to \"POPUP_DO_MOVE_DOWN\", Event Log: "+ animation.log.toString(),animation.log.containsString("POPUP_DO_MOVE_DOWN"));
				assertTrue("The index of the target popup should be 0 ",animation.log.containsString("let popup 0"));   		    	
		    	assertTrue("The animation should get a message which asks the conveyor 0 to \"CONVEYOR_DO_START\", Event Log: "+ animation.log.toString(),animation.log.containsString("CONVEYOR_DO_START"));
				assertTrue("The index of the target conveyor should be 0 ",animation.log.containsString("let conveyor 0"));		    	
		    	assertTrue("The animation should get a message which asks the popup 0 to \"POPUP_DO_MOVE_UP\", Event Log: "+ animation.log.toString(),animation.log.containsString("POPUP_DO_MOVE_UP"));
				assertTrue("The index of the target popup should be 0 ",animation.log.containsString("let popup 0"));	    	
		    	assertTrue("The animation should get a message which asks the robot 1 to \"WORKSTATION_DO_LOAD_GLASS\", Event Log: "+ animation.log.toString(),animation.log.containsString("WORKSTATION_DO_LOAD_GLASS"));
				assertTrue("The index of the target robot should be 1 ",animation.log.containsString("let robot 1"));  		    	
		    	
	}
	

	public void testMsgGlassReady_NextConveyorFamilyReady()
	{
		animation.log.clear();
		t.startTransducer();

		popup = new PopupAgent(0,t,TChannel.DRILL);
		popup.setConveyor(conveyor);
		
		boolean[] recipe = new boolean[10];
		recipe[0]=true;
		for(int i=1;i<recipe.length;i++)
		{
			recipe[i]=false;
		}
		Glass g = new Glass(recipe);

		popup.setNextCF(nextCF);
		
		assertEquals(
				"The popup agent should have 0 glass in the list glasses.", 0,popup.glasses.size());
		assertEquals("The popup should have 2 available machines",2,popup.availableMachine());
		popup.msgHereIsGlass(g);
		assertEquals(
				"The popup agent should have 1 glass in the list glasses.", 1,popup.glasses.size());

		animation.log.clear();
		popup.pickAndExecuteAnAction();
		popup.nextFamilyAvailable=true;
		assertTrue("NextConveyorFamily should be Ready",popup.nextFamilyAvailable);
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//popup.msgGlassReady();
		assertTrue("The animation should get a message which asks the WORKSTATION 0 to \" WORKSTATION_DO_ACTION\", Event Log: "+ animation.log.toString(),animation.log.containsString(" WORKSTATION_DO_ACTION"));
		animation.log.clear();

		popup.pickAndExecuteAnAction();
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		    	assertTrue("The animation should get a message which asks the popup 0 to \"POPUP_DO_MOVE_UP\", Event Log: "+ animation.log.toString(),animation.log.containsString("POPUP_DO_MOVE_UP"));
				assertTrue("The index of the target popup should be 0 ",animation.log.containsString("let popup 0"));    		    	
		    	assertTrue("The animation should get a message which asks the robot 0 to \"WORKSTATION_RELEASE_GLASS\", Event Log: "+ animation.log.toString(),animation.log.containsString("WORKSTATION_RELEASE_GLASS"));
				assertTrue("The index of the target robot should be 0 Event Log:"+animation.log.toString(),animation.log.containsString("let robot 0"));   		    	
		    	assertTrue("The animation should get a message which asks the popup 0 to \"POPUP_DO_MOVE_DOWN\", Event Log: "+ animation.log.toString(),animation.log.containsString("POPUP_DO_MOVE_DOWN"));
				assertTrue("The index of the target popup should be 0 ",animation.log.containsString("let popup 0"));   		    	
		    	assertTrue("The animation should get a message which asks the popup 0 to \"POPUP_RELEASE_GLASS\", Event Log: "+ animation.log.toString(),animation.log.containsString("POPUP_RELEASE_GLASS"));
				assertTrue("The index of the target popup should be 0 ",animation.log.containsString("let popup 0"));   		    	
		    	assertTrue("The next conveyor family should get a message  \"msgHereIsGlass\", Event Log: "+ nextCF.log.toString(),nextCF.log.containsString("msgHereIsGlass"));
		    	assertTrue("The conveyor should get a message",conveyor.log.containsString("msgPopupAvailable"));
 	
	}
	
	public void testMsgGlassReady_NextConveyorFamilyNotReady()
	{
		animation.log.clear();
		t.startTransducer();

		popup = new PopupAgent(0,t,TChannel.DRILL);
		popup.setConveyor(conveyor);
		
		boolean[] recipe = new boolean[10];
		recipe[0]=true;
		for(int i=1;i<recipe.length;i++)
		{
			recipe[i]=false;
		}
		Glass g = new Glass(recipe);

		popup.setNextCF(nextCF);
		
		assertEquals(
				"The popup agent should have 0 glass in the list glasses.", 0,popup.glasses.size());
		assertEquals("The popup should have 2 available machines",2,popup.availableMachine());
		popup.msgHereIsGlass(g);
		assertEquals(
				"The popup agent should have 1 glass in the list glasses.", 1,popup.glasses.size());

		animation.log.clear();
		popup.pickAndExecuteAnAction();
		popup.nextFamilyAvailable=false;
		assertTrue("NextConveyorFamily should not be Ready",!popup.nextFamilyAvailable);
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//popup.msgGlassReady();
		assertTrue("The animation should get a message which asks the WORKSTATION 0 to \" WORKSTATION_DO_ACTION\", Event Log: "+ animation.log.toString(),animation.log.containsString(" WORKSTATION_DO_ACTION"));
		animation.log.clear();

		popup.pickAndExecuteAnAction();
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		    	assertTrue("The animation should not get any message because the next conveyor family is not ready"+ animation.log.toString(),animation.log.size()==0);
		  
			
	}
	
	
	public void testMsgNewSpaceAvailable()
	{
		animation.log.clear();
		t.startTransducer();
//		robots.add(new MockRobot("robot0"));
//		robots.add(new MockRobot("robot1"));
		popup = new PopupAgent(0,t,TChannel.DRILL);
		popup.setConveyor(conveyor);
		
		boolean[] recipe = new boolean[10];
		recipe[0]=true;
		for(int i=1;i<recipe.length;i++)
		{
			recipe[i]=false;
		}
		popup.nextFamilyAvailable=false;
		
		assertEquals(
				"The popup agent should have 0 glass in the list glasses.", 0,popup.glasses.size());
		assertEquals("The popup should have 2 available machines",2,popup.availableMachine());
		assertTrue("The popup's next conveyor family should not be available", !popup.nextFamilyAvailable);
		popup.msgNewSpaceAvailable();
		assertTrue("The popup's next conveyor family should be available", popup.nextFamilyAvailable);		
		
	}
	public void testMsgComeDownAndLetGlassPass_NoneAvailableMachine(){
		animation.log.clear();
		t.startTransducer();
//		robots.add(new MockRobot("robot0"));
//		robots.add(new MockRobot("robot1"));
		popup = new PopupAgent(0,t,TChannel.DRILL);
		popup.setConveyor(conveyor);
		popup.setNextCF(nextCF);

		
		boolean[] recipe = new boolean[10];
		recipe[0]=false;
		for(int i=1;i<recipe.length;i++)
		{
			recipe[i]=false;
		}
		//The glass does not need to processed in the machine
		Glass g = new Glass(recipe);
		
		popup.makeBothMachineUnavailable();
		assertEquals(
				"The popup agent should have 0 glass in the list glasses.", 0,popup.glasses.size());
		assertEquals("The popup should have 0 available machines",0,popup.availableMachine());
		assertTrue("The popup's next conveyor family should be available", popup.nextFamilyAvailable);		

		
		popup.msgComeDownAndLetGlassPass(g);
		assertEquals(
				"The popup agent should have 1 glass in the list glasses.", 1,popup.glasses.size());
		
		popup.pickAndExecuteAnAction();
		
	  
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}		    	
		    	assertTrue("The animation should get a message which asks the popup 0 to \"POPUP_DO_MOVE_DOWN\", Event Log: "+ animation.log.toString(),animation.log.containsString("POPUP_DO_MOVE_DOWN"));
				assertTrue("The index of the target popup should be 0 ",animation.log.containsString("let popup 0"));   		    	
		    	assertTrue("The animation should get a message which asks the conveyor 0 to \"CONVEYOR_DO_START\", Event Log: "+ animation.log.toString(),animation.log.containsString("CONVEYOR_DO_START"));
				assertTrue("The index of the target conveyor should be 0 ",animation.log.containsString("let conveyor 0"));		    	
		    	assertTrue("The animation should get a message which asks the popup 0 to \"POPUP_RELEASE_GLASS\", Event Log: "+ animation.log.toString(),animation.log.containsString("POPUP_RELEASE_GLASS"));
				assertTrue("The index of the target popup should be 0 ",animation.log.containsString("let popup 0"));	    	
		    	
		    	assertTrue("The next conveyor family should get a message  \"msgHereIsGlass\", Event Log: "+ nextCF.log.toString(),nextCF.log.containsString("msgHereIsGlass"));

	}
	
	public void testMsgComeDownAndLetGlassPass_hasAvailableMachine(){
		animation.log.clear();
		t.startTransducer();
//		robots.add(new MockRobot("robot0"));
//		robots.add(new MockRobot("robot1"));
		popup = new PopupAgent(0,t,TChannel.DRILL);
		popup.setConveyor(conveyor);
		popup.setNextCF(nextCF);

		
		boolean[] recipe = new boolean[10];
		recipe[0]=false;
		for(int i=1;i<recipe.length;i++)
		{
			recipe[i]=false;
		}
		Glass g = new Glass(recipe);
		//The glass does not need to processed in the machine
		
		popup.makeOneMachineUnavailable();
		assertEquals(
				"The popup agent should have 0 glass in the list glasses.", 0,popup.glasses.size());
		assertEquals("The popup should have 1 available machines",1,popup.availableMachine());
		
		popup.msgComeDownAndLetGlassPass(g);
		assertEquals(
				"The popup agent should have 1 glass in the list glasses.", 1,popup.glasses.size());
		
		popup.pickAndExecuteAnAction();
		
	  
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		    	
	    	assertTrue("The animation should get a message which asks the popup 0 to \"POPUP_DO_MOVE_DOWN\", Event Log: "+ animation.log.toString(),animation.log.containsString("POPUP_DO_MOVE_DOWN"));
			assertTrue("The index of the target popup should be 0 ",animation.log.containsString("let popup 0"));   		    	
	    	assertTrue("The animation should get a message which asks the conveyor 0 to \"CONVEYOR_DO_START\", Event Log: "+ animation.log.toString(),animation.log.containsString("CONVEYOR_DO_START"));
			assertTrue("The index of the target conveyor should be 0 ",animation.log.containsString("let conveyor 0"));		    	
	    	assertTrue("The animation should get a message which asks the popup 0 to \"POPUP_RELEASE_GLASS\", Event Log: "+ animation.log.toString(),animation.log.containsString("POPUP_RELEASE_GLASS"));
			assertTrue("The index of the target popup should be 0 ",animation.log.containsString("let popup 0"));	    	
	    	
	    	assertTrue("The next conveyor family should get a message  \"msgHereIsGlass\", Event Log: "+ nextCF.log.toString(),nextCF.log.containsString("msgHereIsGlass"));

	}
}
