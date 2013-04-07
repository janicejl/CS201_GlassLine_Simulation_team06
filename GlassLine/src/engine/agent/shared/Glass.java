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
	
	public Recipe getRecipe() {
		return recipe;
	}
}