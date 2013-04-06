package engine.heidiCF.test.Mocks;

import java.util.Timer;
import java.util.TimerTask;

import transducer.TChannel;
import transducer.TEvent;
import transducer.TReceiver;
import transducer.Transducer;

public class MockAnimation implements TReceiver{

	Transducer transducer;
	Timer timer = new Timer();
	public EventLog log = new EventLog();
	public MockAnimation(Transducer t)
	{
		transducer = t;
		t.register(this, TChannel.SENSOR);
		t.register(this, TChannel.POPUP);
		t.register(this, TChannel.DRILL);
		t.register(this, TChannel.CONVEYOR);

	}
	public void eventFired(TChannel channel, TEvent event, Object[] args) {
		
		if(channel .equals(TChannel.CONVEYOR) && event.equals(TEvent.CONVEYOR_DO_START))
		{
			final Object[] temp = args;
			log.add(new LoggedEvent("Animation got an instruction to the conveyor, the instruction is to let conveyor "+args[0]+" CONVEYOR_DO_START"));
			timer.schedule(new TimerTask(){
			    public void run(){//this routine is like a message reception    		    	
			    	transducer.fireEvent(TChannel.POPUP, TEvent.POPUP_GUI_LOAD_FINISHED,temp);
			    }		
			 }, 1000);
		}
		if(channel .equals(TChannel.CONVEYOR) && event.equals(TEvent.CONVEYOR_DO_STOP))
		{
			log.add(new LoggedEvent("Animation got an instruction to the conveyor, the instruction is to let conveyor "+args[0]+" CONVEYOR_DO_STOP"));
			
		}
		else if (channel.equals(TChannel.POPUP)&& event.equals(TEvent.POPUP_DO_MOVE_DOWN))
		{
			final Object[] temp = args;
			log.add(new LoggedEvent("Animation got an instruction to the popup, the instruction is to let popup "+args[0]+" POPUP_DO_MOVE_DOWN"));
			timer.schedule(new TimerTask(){
			    public void run(){//this routine is like a message reception    		    	
			    	transducer.fireEvent(TChannel.POPUP, TEvent.POPUP_GUI_MOVED_DOWN,temp);
			    }		
			 }, 1000);
		}
		else if (channel.equals(TChannel.POPUP)&& event.equals(TEvent.POPUP_DO_MOVE_UP))
		{
			final Object[] temp = args;
			log.add(new LoggedEvent("Animation got an instruction to the popup, the instruction is to let popup "+args[0]+" POPUP_DO_MOVE_UP"));
			timer.schedule(new TimerTask(){
			    public void run(){//this routine is like a message reception    		    	
			    	transducer.fireEvent(TChannel.POPUP, TEvent.POPUP_GUI_MOVED_UP,temp);
			    }		
			 }, 1000);
		}
		else if (channel.equals(TChannel.POPUP)&& event.equals(TEvent.POPUP_RELEASE_GLASS))
		{
			final Object[] temp = args;
			log.add(new LoggedEvent("Animation got an instruction to the popup, the instruction is to let popup "+args[0]+" POPUP_RELEASE_GLASS"));
			timer.schedule(new TimerTask(){
			    public void run(){//this routine is like a message reception    		    	
			    	transducer.fireEvent(TChannel.POPUP, TEvent.POPUP_GUI_RELEASE_FINISHED,temp);
			    }		
			 }, 1000);
		}
		else if (channel.equals(TChannel.DRILL)&& event.equals(TEvent.WORKSTATION_DO_LOAD_GLASS))
		{
			final Object[] temp = args;
			log.add(new LoggedEvent("Animation got an instruction to the robot, the instruction is to let robot "+args[0]+" WORKSTATION_DO_LOAD_GLASS"));
			timer.schedule(new TimerTask(){
			    public void run(){//this routine is like a message reception    		    	
			    	transducer.fireEvent(TChannel.DRILL, TEvent.WORKSTATION_LOAD_FINISHED,temp);
			    }		
			 }, 1000);
			
		}
		else if (channel.equals(TChannel.DRILL)&& event.equals(TEvent.WORKSTATION_RELEASE_GLASS))
		{
			final Object[] temp = args;
			log.add(new LoggedEvent("Animation got an instruction to the robot, the instruction is to let robot "+args[0]+" WORKSTATION_RELEASE_GLASS"));

			timer.schedule(new TimerTask(){
			    public void run(){//this routine is like a message reception    		    	
			    	transducer.fireEvent(TChannel.DRILL, TEvent.WORKSTATION_RELEASE_FINISHED,temp);
			    }		
			 }, 1000);
		}
		
	}
	
}
