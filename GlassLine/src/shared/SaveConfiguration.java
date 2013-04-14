package shared;

import java.io.Serializable;

public class SaveConfiguration implements Serializable{
	String name;
	boolean [] configuration = new boolean[14];
	
	public SaveConfiguration(boolean[] config) {
		this.configuration = config;
	}
	public void setName(String name) {
		// TODO Auto-generated method stub
		this.name = name;
	}
	public void setConfig(boolean[] config) {
		this.configuration = config;
	}
	public String getName() {
		// TODO Auto-generated method stub
		return name;
	}
	public boolean[] getConfig() {
		// TODO Auto-generated method stub
		return configuration;
	}
}
