import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class StrengthExercise extends Exercise {
	

	public final static String [] upperMuscleGroups = {"Shoulders", "Chest", "Biceps", "Triceps", "Forearms", "Abs", "Upper Back"};
	public final static String [] lowerMuscleGroups = {"Quadriceps" , "Glutes" , "Hamstrings", "Calves", "Lower Back"};
	private String targetMuscleArea;
	private String mainTargetMuscle;
	private String weightType;
	private static File standardExerciseFile = new File("standardstrengthexercises.txt");
	private static String fileName = "strengthexercises.txt";
	public final String type = "Strength";
	
	//Instance variables to be set during actual work out
	private int weightUsed;
	private int repitions;
	private int sets;
	
	public StrengthExercise() {
		name = "";
		targetMuscleArea = "";
		timeToComplete = 0;
		mainTargetMuscle = "";
		equiptmentNeeded ="";
		weightType = "";
		weightUsed = 0;
		repitions = 0;
		sets = 0;
	}
	
	public StrengthExercise(String n,  String area, String muscle, String wType, String e) {
		
		name = n;
		targetMuscleArea = area;
		mainTargetMuscle = muscle;
		weightType = wType;
		equiptmentNeeded = e;
		
	}
	

	public void setMainTargetMuscle(String targetMuscle) {
		
		mainTargetMuscle = targetMuscle;
		
	}
	
	public String getMainTargetMuscle() {
		return mainTargetMuscle;
	}

	public String getTargetMuscleArea() {
		return targetMuscleArea;
	}

	public void setTargetMuscleArea(String targetMuscleArea) {
		this.targetMuscleArea = targetMuscleArea;
	}

	public String getWeightType() {
		return weightType;
	}

	public void setWeightType(String weightType) {
		this.weightType = weightType;
	}

	public int getWeightUsed() {
		return weightUsed;
	}

	public void setWeightUsed(int weightUsed) {
		this.weightUsed = weightUsed;
	}

	public int getRepitions() {
		return repitions;
	}

	public void setRepitions(int repitions) {
		this.repitions = repitions;
	}

	public int getSets() {
		return sets;
	}

	public void setSets(int sets) {
		this.sets = sets;
	}
	
	public String getType() {
		return type;
	}

	
	public String toString() {
		return this.type + "," + this.name + "," + this.targetMuscleArea + "," + this.mainTargetMuscle + "," + this.weightType +
				"," + this.equiptmentNeeded;
	} 
	
	public String completedToString() {
		String completedString = this.type + "," + this.name + "," + this.targetMuscleArea + "," + this.mainTargetMuscle + "," + this.weightType +
				"," + this.equiptmentNeeded;
		if(this.weightType.equalsIgnoreCase("Free Weights") || this.weightType.equalsIgnoreCase("Machine Resistance"))
			completedString += "," + this.weightUsed;
		else
			completedString += ",nan";
		
	    completedString += "," + this.repitions + "," + this.sets;
		
		return completedString; 
	}
	
	public static ArrayList<StrengthExercise> getAllExercises() {
		ArrayList<StrengthExercise> exerciseList = new ArrayList<StrengthExercise>();
		StrengthExercise currentExercise;
		
		
		try {
			FileReader fr = new FileReader(standardExerciseFile);
			BufferedReader reader = new BufferedReader(fr);
			String line;
			
			while((line = reader.readLine()) != null) {
				String [] cell = line.split(",");

				currentExercise = new StrengthExercise();
				currentExercise.setName(cell[0]);
				currentExercise.setTargetMuscleArea(cell[1]);
				currentExercise.setMainTargetMuscle(cell[2]);
				currentExercise.setWeightType(cell[3]);
				currentExercise.setEquiptmentNeeded(cell[4]);
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
				currentExercise = new StrengthExercise();
				currentExercise.setName(cell[1]);
				currentExercise.setTargetMuscleArea(cell[2]);
				currentExercise.setMainTargetMuscle(cell[3]);
				currentExercise.setWeightType(cell[4]);
				currentExercise.setEquiptmentNeeded(cell[5]);
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
	

	//Load all of the exercises in the given file into an Observable list to be displayed 
	public static ObservableList<String> loadExercises() {
		ObservableList<String> viewList = FXCollections.observableArrayList();
		StrengthExercise currentExercise;
		ArrayList<StrengthExercise> list = getAllExercises();
		for(int i = 0; i < list.size(); i++) {
			currentExercise = (StrengthExercise)list.get(i);
			viewList.add(currentExercise.getName());
		}
		return viewList;
		
	} //End of load exercises


}
