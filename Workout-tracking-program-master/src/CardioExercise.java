import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class CardioExercise extends Exercise{
	
	private int resistanceLevel;
	private int laps;
	private double distance;
	private double lapTime;
	private static File standardExerciseFile = new File("standardcardioexercises.txt");
	private static String fileName = "cardioexercises.txt";
	public final String type = "Cardio";
	private boolean hasLaps;
	private boolean hasResistance;
	private boolean hasDistance;

	
	public CardioExercise() {
		name = "";
		resistanceLevel = 0;
		equiptmentNeeded = "";
		hasLaps = false;
		hasResistance = false;
		hasDistance = false;
		laps = 0;
		lapTime = 0;
		timeToComplete = 0;
		
	}
	
	public boolean getHasLaps() {
		return hasLaps;
	}

	public void setHasLaps(boolean hasLaps) {
		this.hasLaps = hasLaps;
	}

	public boolean getHasResistance() {
		return hasResistance;
	}

	public void setHasResistance(boolean hasResistance) {
		this.hasResistance = hasResistance;
	}
	
	public boolean getHasDistance() {
		return hasDistance;
	}
	
	public void setHasDistance(boolean d) {
		hasDistance = d;
	}
	

	public CardioExercise(String n, String equiptment) {
		name = n;
		equiptmentNeeded = equiptment;
	}

	
	public int getResistanceLevel() {
		return resistanceLevel;
	}

	public void setResistanceLevel(int resistanceLevel) {
		this.resistanceLevel = resistanceLevel;
	}

	public int getLaps() {
		return laps;
	}

	public void setLaps(int laps) {
		this.laps = laps;
	}

	public double getLapTime() {
		return lapTime;
	}

	public void setLapTime(double lapTime) {
		this.lapTime = lapTime;
	}
	
	public double getDistance() {
		return distance;
	}
	
	public void setDistance(double d) {
		distance = d;
	}
	
	public String getType() {
		return type;
	}
	
	public String toString() {
		return this.type + "," + this.name + "," + this.equiptmentNeeded;
	}
	
	public String completedToString() {
		String completedString = this.type + "," + this.name  + "," + this.equiptmentNeeded;
		
		if(this.hasLaps == true)
			completedString += "," + this.laps;
		else if(this.hasLaps == false)
			completedString += ",nan";
		
		if(this.hasResistance == true)
			completedString += "," + this.resistanceLevel;
		else if(this.hasResistance == false)
			completedString += ",nan";
		
		if(this.hasDistance == true)
			completedString += "," + this.distance;
		else if(this.hasDistance == false)
			completedString += ",nan";
		
		return completedString;
			
	}
	
	public static ArrayList<CardioExercise> getAllExercises() {
		ArrayList<CardioExercise> exerciseList = new ArrayList<CardioExercise>();
		CardioExercise currentExercise;
		
		try {
			FileReader fr = new FileReader(standardExerciseFile);
			BufferedReader reader = new BufferedReader(fr);
			String line;
			
			while((line = reader.readLine()) != null) {
				String [] cell = line.split(",");
				currentExercise = new CardioExercise();
				currentExercise.setName(cell[0]);;
				currentExercise.setEquiptmentNeeded(cell[1]);
				
				exerciseList.add(currentExercise);
			}
			reader.close();
		
		}catch(FileNotFoundException f) { 
			System.out.println("File not found");
		}//End of catch
		
		catch(IOException i) {
			System.out.println("IO exception");
		}
		
		
		
		try {
			FileReader fr = new FileReader(fileName);
			BufferedReader reader = new BufferedReader(fr);
			String line;
			
			while((line = reader.readLine()) != null) {
				String [] cell = line.split(",");
				if(!cell[0].equals(Account.getCurrentUserName()))
					continue;
				currentExercise = new CardioExercise();
				currentExercise.setName(cell[1]);;
				currentExercise.setEquiptmentNeeded(cell[2]);
				
				exerciseList.add(currentExercise);
			}
			reader.close();
		
		}catch(FileNotFoundException f) { 
			System.out.println("File not found");
		}//End of catch
		
		catch(IOException i) {
			System.out.println("IO exception");
		}
		
		
		return exerciseList;
	}
	
	
	
	
	public static ObservableList<String> loadExercises() {
		ObservableList<String> viewList = FXCollections.observableArrayList();
		CardioExercise currentExercise;
		ArrayList<CardioExercise> list = getAllExercises();
		for(int i = 0; i < list.size(); i++) {
			currentExercise = (CardioExercise)list.get(i);
			viewList.add(currentExercise.getName());
		}
		return viewList;
		
	} //End of load exercises
	

}
