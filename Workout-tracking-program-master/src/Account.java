import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Account {
	private String username;
	private String password;
	private int age;
	private String gender;
	private int weight;
	private static File accountFile = new File("Accounts.txt");
	private static File currentAccountFile = new File("currentAccount.txt");


	public Account() {
		username = null;
		password = null;
		age = 0;
		gender = null;
		weight = 0;
	}

	public int getAge() {
		return age;
	}

	public String getGender() {
		return gender;
	}

	public int getWeight() {
		return weight;
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

	public void setAge(int a) {
		age = a;
	}

	public void setGender(String s) {
		gender = s;
	}

	public void setWeight(int w) {
		weight = w;
	}

	public void setUsername(String u) {
		username = u;
	}

	public void setPassword(String p) {
		password = p;
	}
	
	public boolean equals(Account a) {
		if(this.username.equalsIgnoreCase(a.getUsername()))
			return true;
		
		return false;
	}
	
	public String toString() {
		return this.username + "," + this.password + "," + this.age + "," + this.gender + "," + this.weight;
	}
	
	public static ArrayList<Account> getAllAccounts(){
		ArrayList <Account> accountList = new ArrayList<Account>();
		Account currentAccount;
		try {
			FileReader fr = new FileReader(accountFile);
			BufferedReader reader = new BufferedReader(fr);
			String line;
			
			while((line = reader.readLine()) != null) {
				String[] cell = line.split(",");
				currentAccount = new Account();
				currentAccount.setUsername(cell[0]);
				currentAccount.setPassword(cell[1]);
				currentAccount.setAge(Integer.parseInt(cell[2]));
				currentAccount.setGender(cell[3]);
				currentAccount.setWeight(Integer.parseInt(cell[4]));
				accountList.add(currentAccount);
			}
			reader.close();	
		}
		catch(FileNotFoundException f) {
			System.out.println("File not found");
		}
		catch(IOException i) {
			System.out.println("IO exception");
		}		
		return accountList;	
	}
	
	
	public static Account getCurrentAccount() {
		Account currentAccount = new Account();
		try {
			FileReader fr = new FileReader("currentAccount.txt");
			BufferedReader reader = new BufferedReader(fr);
			String line = reader.readLine();
			String[] cell = line.split(",");
			currentAccount.setUsername(cell[0]);
			currentAccount.setPassword(cell[1]);
			currentAccount.setAge(Integer.parseInt(cell[2]));
			currentAccount.setGender(cell[3]);
			currentAccount.setWeight(Integer.parseInt(cell[4]));
			reader.close();			
		}
		catch(FileNotFoundException f) {
			System.out.println("File not found");
		}
		catch(IOException e) {
			System.out.println("IO exception");
		}
		return currentAccount;
	
	}
	
	public static boolean isLoggedIn() {
		if(!currentAccountFile.exists() || currentAccountFile.length() == 0)
			return false;
		
		return true;
	}
	
	public static String getCurrentUserName() {
		if(Account.isLoggedIn()) {
			Account a = getCurrentAccount();
			return a.getUsername();
		}
		return null;
		
	}
	
	public static void logOut() {
		currentAccountFile.delete();
	}
	
	public static void changeAccountInfo(Account oldInfo, Account newInfo) {
		
	    try {
	        // input the (modified) file content to the StringBuffer "input"
	        BufferedReader file = new BufferedReader(new FileReader("Accounts.txt"));
	        String fileText = "";
	        String line;

	        while ((line = file.readLine()) != null) {
	        	
	            fileText += line + System.lineSeparator();
	        }
	        file.close();

	        // write the new string with the replaced line OVER the same file
	        String newFileText = fileText.replaceAll(oldInfo.toString(), newInfo.toString());
	        
	        FileWriter writer = new FileWriter("Accounts.txt");
	        writer.write(newFileText);
	        writer.close();
	        
	        writer = new FileWriter("currentAccount.txt");
	        writer.write(newInfo.toString());
	        writer.close();

	    } catch (Exception e) {
	        System.out.println("Problem reading file.");
	    }
		
	}
	
	public static boolean checkDuplicates(Account a) {
		

		boolean exists = false;
		int count = 0;
		//Fill an array list with accounts from the account file
		ArrayList <Account> allAccounts = Account.getAllAccounts();
		
		//Check if the new account is already listed 
		for(int i = 0; i < allAccounts.size(); i++) {
			if(allAccounts.get(i).equals(a)) {
				count++;
			}
		}
		if(count > 0)
			exists = true;
	    
	    return exists;
		
	}
	
	
} 