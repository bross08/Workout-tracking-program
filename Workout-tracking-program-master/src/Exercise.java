public abstract class Exercise {
	

	protected String name;
	protected String equiptmentNeeded;
	protected double timeToComplete;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getTimeToComplete() {
		return timeToComplete;
	}

	public void setTimeToComplete(double timeToComplete) {
		this.timeToComplete = timeToComplete;
	}

	public String getEquiptmentNeeded() { 
		return equiptmentNeeded;
	}

	public void setEquiptmentNeeded(String equiptment) {
		equiptmentNeeded = equiptment;
	}
	
	public boolean equals(Exercise exercise) {
		if(exercise.getName().equalsIgnoreCase(name))
			return true;
		
		return false;
			
	}
	
	public abstract String getType();
	
	public abstract String completedToString();

}
