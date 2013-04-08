package engine.heidiCF.agent;

import transducer.TChannel;
import transducer.TEvent;
import transducer.Transducer;
import engine.agent.Agent;
import engine.heidiCF.interfaces.*;
import engine.agent.shared.Interfaces.*;

public class FrontSensor extends Agent implements Sensor {
	
	int myIndex;
	ConveyorFamily prevCF;
	enum SensorStatus {Empty,Released,Pressed};
	SensorStatus status = SensorStatus.Empty;
	
	public FrontSensor(int index, Transducer t)
	{
		super("frontSenosr");
		myIndex = index;
		transducer =t;
		t.register(this, TChannel.SENSOR);
	}
	public void setIndex(int index)
	{
		myIndex = index;
	}
	
	public int getIndex()
	{
		return myIndex;
	}
	
	public void sensorReleased() {
		status = SensorStatus.Empty;
		prevCF.msgSpaceAvailable();
		stateChanged();
	}


	public boolean pickAndExecuteAnAction() {
		if(status==SensorStatus.Released)
		{
			sensorReleased();
			return true;
		}
		return false;
	}
	
	public void eventFired(TChannel channel, TEvent event, Object[] args) {
		if(channel .equals(TChannel.SENSOR)&& event .equals(TEvent.SENSOR_GUI_RELEASED)&& args[0].equals(myIndex))
		{
			if(myIndex!=10)
			{
				status = SensorStatus.Released;
				stateChanged();
			}
			
		}
		
	}
	public void sensorPressed() {
		
		
	}

	public void setPrevCF(ConveyorFamily prevCF)
	{
		this.prevCF = prevCF;
	}
}
