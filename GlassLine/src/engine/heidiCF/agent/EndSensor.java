package engine.heidiCF.agent;

import transducer.TChannel;
import transducer.TEvent;
import transducer.Transducer;
import engine.agent.Agent;
import engine.heidiCF.interfaces.*;

public class EndSensor extends Agent implements Sensor{

	Conveyor conveyor;
	Integer myIndex;
	enum SensorStatus {Empty,Released,Pressed};
	SensorStatus status = SensorStatus.Empty;
	public EndSensor(int index, Transducer t)
	{
		super("endSenosr");
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
	public void setConveyor(Conveyor conv)
	{
		conveyor = conv;
	}
	public void sensorPressed()
	{
		status = SensorStatus.Empty;
		conveyor.msgGlassArrivedAtEnd();
		stateChanged();
	}
	public void sensorReleased()
	{
		status = SensorStatus.Empty;
		conveyor.msgEndSensorAvailable();
		stateChanged();
	}
	
	public boolean pickAndExecuteAnAction() {
		if(status==SensorStatus.Released)
		{
			sensorReleased();
			return true;
		}
		else if (status == SensorStatus.Pressed)
		{
			sensorPressed();
			return true;
		}
		return false;
	}
	
	public void eventFired(TChannel channel, TEvent event, Object[] args) {
		if(channel .equals(TChannel.SENSOR)&& event .equals(TEvent.SENSOR_GUI_PRESSED)&& args[0].equals(myIndex))
		{
			status = SensorStatus.Pressed;
			stateChanged();
		}
		else if(channel .equals(TChannel.SENSOR)&& event .equals(TEvent.SENSOR_GUI_RELEASED)&& args[0].equals(myIndex))
		{
			status = SensorStatus.Released;
			stateChanged();
		}
		
	}

}
