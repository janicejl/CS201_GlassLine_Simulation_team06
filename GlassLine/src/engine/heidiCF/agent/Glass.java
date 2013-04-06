package engine.heidiCF.agent;

import java.util.HashMap;

public class Glass{
	HashMap<Integer,Boolean> recipe  = new HashMap<Integer, Boolean>();
	public Glass(boolean[] array)
	{
		 for(int i=0;i<array.length;i++)
		 {
			 recipe.put(i,array[i]);
		 }
	} 
	public boolean ifNeedMachine(int index)
	{
		return (recipe.get(index));

	}
};