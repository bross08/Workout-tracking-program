import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Workout {
	
	private ArrayList<Exercise> workoutList;
	private String workoutName;
	private static File workoutFile = new File("workouts.txt");
	private static File completedWorkoutFile = new File("completedworkouts.txt");
	
	
	public Workout() {
		workoutName = "";
		workoutList = new ArrayList<Exercise>();
	}
	
	public int length() {
		return workoutList.size();
	}
	
	public boolean isEmpty() {
		return workoutList.isEmpty();
	}
	
	public void remove(int i) {
		workoutList.remove(i);
	}
	
	public Exercise getExercise(int i) {
		return workoutList.get(i);
	}
	
	public void setWorkoutName(String name) {
		this.workoutName = name; 
	}
	
	public String getWorkoutName() {
		return workoutName;
	}
	
	public void addExercise(Exercise e) {
		workoutList.add(e);
	}
	
	public boolean equals(Workout w) {
		if(this.workoutName.equalsIgnoreCase(w.workoutName))
			return true;
		return false;
	}
	
	
	
	public ObservableList<String> loadExercises() {
		ObservableList<String> viewList = FXCollections.observableArrayList();
		Exercise currentExercise;
		
		for(int i = 0; i < workoutList.size(); i++) {
			currentExercise = (Exercise)workoutList.get(i);
			viewList.add(currentExercise.getName());
		}
		return viewList;
		
	} //End of load exercises
	
	public boolean saveRoutine() throws IOException{
		
		if(!workoutFile.exists()) {
			System.out.println("New file created");
			workoutFile.createNewFile();
		}
		String line = "Name," + Account.getCurrentUserName() + "," + this.workoutName;
		
		if(Workout.checkDuplicates(this) == true) {
			return false;
		}
		try {
				
			FileWriter fr = new FileWriter(workoutFile.getAbsoluteFile(), true);
			BufferedWriter writer = new BufferedWriter(fr);
			writer.write(line + "\n");
			
			for(int i = 0; i < workoutList.size(); i++) {
				Exercise currentExercise = workoutList.get(i);
				writer.write(currentExercise.toString() + "\n");
				
			}
			writer.write("end\n");
			writer.close();
		}catch(IOException e) {
			System.out.println("I/O Exception");
		}
		return true;
		
	}
	
	
	public static ArrayList<Workout> getAllWorkouts(){
		ArrayList<Workout> workoutList = new ArrayList<Workout>();
		Exercise newExercise;
		Workout currentWorkout = new Workout();
		boolean account = true;
		
		try {
			FileReader fr = new FileReader(workoutFile.getAbsoluteFile());
			BufferedReader reader = new BufferedReader(fr);
			String line;
			
			while((line = reader.readLine()) != null) {
				
				String cell [] = line.split(",");

				if(cell[0].equalsIgnoreCase("end")) {
					if(account == true) {
						workoutList.add(currentWorkout);
						currentWorkout = new Workout();
					}
					else continue;
				}
				
				else if(cell[0].equalsIgnoreCase("Name")) {
					if(!cell[1].equals(Account.getCurrentUserName())) {
						account = false;
						continue;
					}
					else {
						account = true;
						currentWorkout = new Workout();
						currentWorkout.setWorkoutName(cell[2]);
					}
				}	
					
				else if(cell[0].equalsIgnoreCase("Strength")) {
					newExercise = new StrengthExercise(cell[1], cell[2], cell[3], cell[4], cell[5]);
					currentWorkout.addExercise(newExercise);
				}
				else if(cell[0].equalsIgnoreCase("Cardio")){
					newExercise = new CardioExercise(cell[1], cell[2]);
					currentWorkout.addExercise(newExercise);
				}
				
			}
			reader.close();
			
		}catch(FileNotFoundException f) { 
			System.out.println("File not found");
		}catch(IOException e) {
			System.out.println("IO Exception");
		}
		
		
		return workoutList;
	}
	
	public static ObservableList<String> loadWorkouts() {
		ObservableList<String> viewList = FXCollections.observableArrayList();
		Workout currentWorkout;
		ArrayList<Workout> list = getAllWorkouts();
		for(int i = 0; i < list.size(); i++) {
			currentWorkout = list.get(i);
			viewList.add(currentWorkout.getWorkoutName());
		}

		return viewList;
		
	} //End of load exercises
	
	public void saveCompletedWorkout() throws IOException {
		
	    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss"); 
	    Date date = new Date();  
	    
		if(!completedWorkoutFile.exists()) {
			System.out.println("New file created");
			completedWorkoutFile.createNewFile();
		}
		
		String line = "Name," + Account.getCurrentUserName() +"," +this.workoutName + " - Completed:  " + formatter.format(date);
		
		try {
				
			FileWriter fr = new FileWriter(completedWorkoutFile.getAbsoluteFile(), true);
			BufferedWriter writer = new BufferedWriter(fr);
			writer.write(line + "\n");
			
			for(int i = 0; i < this.length(); i++) {
				Exercise currentExercise = this.getExercise(i);
				writer.write(currentExercise.completedToString() + "\n");
				
			}
			writer.write("end\n");
			writer.close();
		}catch(IOException e) {
			System.out.println("I/O Exception");
		}
		
	}
	
	public static ArrayList<Workout> getCompletedWorkouts() {
		
		ArrayList<Workout> completedWorkoutList = new ArrayList<Workout>();
		boolean account = true; 
	//	Exercise newExercise;
		Workout currentWorkout = new Workout();
		
		try {
			FileReader fr = new FileReader(completedWorkoutFile.getAbsoluteFile());
			BufferedReader reader = new BufferedReader(fr);
			String line;
			
			while((line = reader.readLine()) != null) {
				String cell [] = line.split(",");
				

				if(cell[0].equalsIgnoreCase("end")) {
					if(account == true) {
						completedWorkoutList.add(currentWorkout);
						currentWorkout = new Workout();
					}
					else {
						continue;
					}
				}
				
				else if(cell[0].equalsIgnoreCase("Name")) {
					if(!cell[1].equals(Account.getCurrentUserName()))
						account = false;
					else {
						account = true;
						currentWorkout = new Workout();
						currentWorkout.setWorkoutName(cell[2]);
					}
				}	
					
				else if(cell[0].equalsIgnoreCase("Strength")) {
					StrengthExercise newStrength = new StrengthExercise(cell[1], cell[2], cell[3], cell[4], cell[5]);
					if(!cell[6].equalsIgnoreCase("nan")) 
						newStrength.setWeightUsed(Integer.parseInt(cell[6]));
					newStrength.setRepitions(Integer.parseInt(cell[7]));
					newStrength.setSets(Integer.parseInt(cell[8]));
					currentWorkout.addExercise(newStrength);
				}
				else if(cell[0].equalsIgnoreCase("Cardio")){
					CardioExercise newCardio = new CardioExercise(cell[1], cell[2]);
					if(!cell[3].equalsIgnoreCase("nan"))
						newCardio.setLaps(Integer.parseInt(cell[3]));
					if(!cell[4].equalsIgnoreCase("nan"))
						newCardio.setResistanceLevel(Integer.parseInt(cell[4]));
					if(!cell[5].equalsIgnoreCase("nan")) 
						newCardio.setDistance(Double.parseDouble(cell[5]));
					currentWorkout.addExercise(newCardio);
				}
				
			}
			reader.close();
			
		}catch(FileNotFoundException f) { 
			System.out.println("File not found");
		}catch(IOException e) {
			System.out.println("IO Exception");
		}
		
		
		return completedWorkoutList;
	}
	
	public static ObservableList<String> loadCompletedWorkouts() {
		ObservableList<String> viewList = FXCollections.observableArrayList();
		Workout currentWorkout;
		ArrayList<Workout> list = getCompletedWorkouts();
		for(int i = 0; i < list.size(); i++) {
			currentWorkout = list.get(i);
			viewList.add(currentWorkout.getWorkoutName());
		}

		return viewList;
		
	} //End of load exercises
	
	private static boolean checkDuplicates(Workout w) {
		

		boolean exists = false;
		int count = 0;
		
		ArrayList <Workout> allWorkouts = Workout.getAllWorkouts();
		
	
		for(int i = 0; i < allWorkouts.size(); i++) {
			if(allWorkouts.get(i).equals(w)) {
				count++;
			}
		}
		if(count > 0)
			exists = true;
	    
	    return exists;
		
	}
	

}
