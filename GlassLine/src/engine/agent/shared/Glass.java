package engine.agent.shared;

public class Glass {
	public Recipe recipe;
	
	public Glass(Boolean machine1, Boolean machine2, Boolean machine3) {
		recipe = new Recipe(machine1, machine2, machine3);
	}
	
	public class Recipe {
		public boolean machine1;
		public boolean machine2;
		public boolean machine3;
		
		public Recipe(Boolean m1, Boolean m2, Boolean m3) {
			machine1 = m1;
			machine2 = m2;
			machine3 = m3;
		}
	}
	
	public boolean ifNeedMachine(int popupIndex)
	{
		int tempIndex = popupIndex-5;
		if(tempIndex==0)
			return recipe.machine1;
		if(tempIndex==1)
			return recipe.machine2;
		if(tempIndex==2)
			return recipe.machine3;
		return false;
		
	}
	public Recipe getRecipe() {
		return recipe;
	}
}