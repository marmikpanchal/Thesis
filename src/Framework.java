import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.rosuda.JRI.REXP;
import org.rosuda.JRI.Rengine;

public class Framework {
	
	static ArrayList<Data> trainingDataset = new ArrayList<Data>();
	static ArrayList<Data> testingDataset = new ArrayList<Data>();
	static ArrayList<String> rules = new ArrayList<String>();
	
	
	public static void main(String[] args) throws FileNotFoundException, IOException, InvalidFormatException {
		System.out.println("\n\nWelcome to Bitcoin Price Prediction Framework!\n\n");
		Rengine re = new Rengine(new String [] {"--vanilla"}, false, null);
		
		runner(re);
		
//		String my[] = { "--vanilla" };
//		Rengine re = new Rengine(my, false, null);
//		re.eval("library(inductrdr)");
//		re.eval("library(Rcpp)");
//		String traincsv = "C:/Users/student/Desktop/Thesis/traincsv.csv";
//		re.assign("train", traincsv);
//		re.eval("trainData <- read.csv(train)");
//		re.eval("vec <- c(2,2,2,1,1,2,0)");
//		re.eval("trainRules <- createRules(trainData, vec)");
//		REXP result = re.eval("capture.output(trainRules)");
//		String[] output = result.asStringArray();
//		for (String s : output) {
//			System.out.println(s);
//		}
		
		
		
		
		//re.eval(String.format("g <- '%s'", "Hello R..."));
		//REXP result = re.eval("g");
		//System.out.println("Greeting R test...: " + result);
		
		//String javaVector = "c(1,2,3,4,5)";
		//re.eval("rVector="+javaVector);
		//re.eval("meanVal=mean(rVector)");
		//double mean = re.eval("meanVal").asDouble();
		//System.out.println("Mean of the vector is=" + mean);
		
		return;
	}
	
	public static void printOptions() {
		
		System.out.println("\n\n1. Configure training dataset");
		System.out.println("2. Configure testing dataset");
		System.out.println("3. Generate Rules [NOTE: choose training dataset first]");
		System.out.println("4. Display Rules");
		System.out.println("5. Modify Rules [NOTE: choose training dataset first]");
		System.out.println("6. Predict Test Dataset and Display Results [NOTE: choose training and testing datasets first]");
		System.out.println("7. Exit\n");
		System.out.println("CHOOSE YOUR OPTION:");
		System.out.print("> ");
		

	}
	
	
	public static void runner(Rengine re) throws FileNotFoundException, InvalidFormatException, IOException {
		
		re.eval("library(inductrdr)");
		re.eval("library(Rcpp)");
		
		Scanner reader = new Scanner(System.in); 
		while (true) {
			printOptions();
			int choice = reader.nextInt();
			if (choice == 1) {
				configTrainingDataset(reader);
			}
			else if (choice == 2) {
				configTestingDataset(reader);
			}
			else if (choice == 3) {
				generateRules(re);
			}
			else if (choice == 4) {
				displayRules();
			}
			else if (choice == 5) {
				modifyRules(reader);
			}
			else if (choice == 6) {
				predictionOutcome(re);
			}
			else if (choice == 7) {
				System.out.println("\nExiting the Framework!");
				System.out.println("DONE.");
				reader.close();
				return;
			}
		}
	}
	
	@SuppressWarnings("resource")
	public static void configTrainingDataset(Scanner reader) throws FileNotFoundException, InvalidFormatException, IOException {
		
		System.out.println("Type in the filename of your Training Dataset: ");
		System.out.print("> ");
		String path = "C:/Users/student/Desktop/Thesis/";
		reader = new Scanner(System.in);
		String filename = reader.next();
		path = path + filename + ".xlsx";
		
		while(true) {
			File file = new File(path);
			if (!file.exists()) {
				System.out.println("FILE NOT FOUND! TRY AGAIN!\n");
				System.out.println("Type in the filename of your Training Dataset: ");
				System.out.print("> ");
				path = reader.next();
			} else {
				break;
			}
		}
		
		System.out.println("\nLoading the Dataset: " + path + "\n");
		loadFile(path, trainingDataset);
	}
	
	
	@SuppressWarnings("resource")
	public static void configTestingDataset(Scanner reader) throws FileNotFoundException, InvalidFormatException, IOException {
		
		System.out.println("Type in the filename of your Testing Dataset: ");
		System.out.print("> ");
		String path = "C:/Users/student/Desktop/Thesis/";
		reader = new Scanner(System.in);
		String filename = reader.next();
		path = path + filename + ".xlsx";

		while(true) {
			File file = new File(path);
			if (!file.exists()) {
				System.out.println("FILE NOT FOUND! TRY AGAIN!\n");
				System.out.println("Type in the filename of your Testing Dataset: ");
				System.out.print("> ");
				path = reader.next();
			} else {
				break;
			}
		}
		
		System.out.println("\nLoading the Dataset: " + path + "\n");
		loadFile(path, testingDataset);
	}
	
	
	public static void generateRules(Rengine re) {
		
//		if (trainingDataset.isEmpty()) {
//			System.out.println("Please select the training dataset!\n");
//			return;
//		}
		
		String traincsv = "C:/Users/student/Desktop/Thesis/traincsv.csv";
		
		re.assign("train", traincsv);
		re.eval("trainData <- read.csv(train)");
		re.eval("vec <- c(2,2,2,1,1,2,0)");
		re.eval("trainRules <- createRules(trainData, vec)");
		
		REXP result = re.eval("capture.output(outputRules(trainRules))");
		
		String[] output = result.asStringArray();
		
		System.out.println("Size of output: " + output.length);
		
		int i = 0;
		for (String s : output) {
			rules.add(s);
			System.out.println("["+ i++ +"]\t" + s);
		}
		
	}
	
	public static void modifyRules(Scanner reader) {
		
		int i = 0;
		for (String s : rules) {
			System.out.println("["+ i++ +"]\t" + s);
		}
		
		
		while (true) {

			System.out.println("\n\nSelect from the following options:\n");
			System.out.println("1. Add a Condition");
			System.out.println("2. Edit a Condition");
			System.out.println("3. Delete a Condition");
			System.out.println("4. Display Terms");
			System.out.println("5. Go Back to Main Screen");
			System.out.println("\nCHOOSE YOUR OPTION:");
			System.out.print("> ");
			
			int choice = reader.nextInt();
			if (choice == 1) {
				addCondition(reader);
			}
			else if (choice == 2) {
				editCondition(reader);
			}
			else if (choice == 3) {
				deleteCondition(reader);
			}
			else if (choice == 4) {
				displayTerms();
			} 
			else if (choice == 5) {
				break;
			}
		}
	}
	
	public static void displayRules() {
		int i = 0;
		for (String s : rules) {
			System.out.println("["+ i++ +"]\t" + s);
		}
	}
	
	public static void displayTerms() {
		int i = 0;
		boolean flag = false;
		for (String s : rules) {
			if (s.matches(".*Terms:")) {
				System.out.println("["+i+"]" + " Terms:");
				flag = true;
			}
			else if (s.matches(".*If_true:.*")) {
				flag = false;
			}
			else if (flag == true) {
				String cond = s.trim().replaceAll("\\s{2,}", "");
				cond = cond.trim().replaceAll("\\|", "");
				System.out.println("\t" + cond);
			}
			i++;
		}
	}

	
	public static void addCondition(Scanner reader) {
		
		displayTerms();
		System.out.println("\n\nType the index number of condition insertion");
		System.out.print("> ");
		int index = reader.nextInt();
		
		System.out.print("\nType attribute:\t");
		String attribute = reader.next();
		System.out.print("Type operator:\t");
		String operator = reader.next();
		System.out.print("Type value:\t");
		double value = reader.nextDouble();
		String condition = attribute + " " + operator + " " + Double.toString(value);
		
		System.out.println("\nAdded the condition \"" + condition + "\" to the corresponding branch");
		
		String before = rules.get(index+1);
		int count = before.length() - before.replace("|", "").length();
		
		String insertRule = "";
		for (int i = 0; i < count; i++) {
			insertRule += "|   ";
		}
		insertRule += " ";
		insertRule += condition;
		rules.add(index+1, insertRule);
		
	}
	
	public static void editCondition(Scanner reader) {
		
		int i = 0;
		boolean flag = false;
		for (String s : rules) {
			if (s.matches(".*Terms:")) {
				System.out.println("["+i+"]" + " Terms:");
				flag = true;
			}
			else if (s.matches(".*If_true:.*")) {
				flag = false;
			}
			else if (flag == true) {
				String cond = s.trim().replaceAll("\\s{2,}", "");
				cond = cond.trim().replaceAll("\\|", "");
				System.out.println("\t" + "["+i+"]  " + cond);
			}
			i++;
		}
		
		
		System.out.println("\n\nType the index number of the condition that you would like to edit:");
		System.out.print("> ");
		int index = reader.nextInt();
		
		System.out.print("\nType attribute:\t");
		String attribute = reader.next();
		System.out.print("Type operator:\t");
		String operator = reader.next();
		System.out.print("Type value:\t");
		double value = reader.nextDouble();
		String condition = attribute + " " + operator + " " + Double.toString(value);

		
		System.out.println("\nEdited the condition at index " + index + " in the rules");
		
		String before = rules.get(index+1);
		int count = before.length() - before.replace("|", "").length();
		
		String insertRule = "";
		for (int j = 0; j < count; j++) {
			insertRule += "|   ";
		}
		insertRule += " ";
		insertRule += condition;
		rules.remove(index);
		rules.add(index, insertRule);
		
	}
	
	public static void deleteCondition(Scanner reader) {
		
		int i = 0;
		boolean flag = false;
		for (String s : rules) {
			if (s.matches(".*Terms:")) {
				System.out.println("["+i+"]" + " Terms:");
				flag = true;
			}
			else if (s.matches(".*If_true:.*")) {
				flag = false;
			}
			else if (flag == true) {
				String cond = s.trim().replaceAll("\\s{2,}", "");
				cond = cond.trim().replaceAll("\\|", "");
				System.out.println("\t" + "["+i+"]  " + cond);
			}
			i++;
		}

		while (true) {
			
			System.out.println("\n\nType the index number of the condition that you would like to delete: ");
			System.out.print("> ");
			int index = reader.nextInt();
			
			if (index >= 0 && index < rules.size()) {
				rules.remove(index);
				System.out.println("Sucessfully removed the condition at index " + index);
				break;
			} else {
				System.out.println("Invalid Index! Please try again!");
			}
		}
	}
	
	
	public static void predictionOutcome(Rengine re) {
		
		String testcsv = "C:/Users/student/Desktop/Thesis/testcsv.csv";
		
		re.assign("test", testcsv);
		re.eval("testData <- read.csv(test)");
		re.eval("result <- predictData(testData, trainRules)");
		
		REXP result = re.eval("capture.output(result)");
		
		String[] output = result.asStringArray();
		
		System.out.println("Size of output: " + output.length);
		
		for (String s : output) {
			System.out.println(s);
		}
		
	}
	
	
	public static void loadFile(String filename, ArrayList<Data> dataset) throws FileNotFoundException, IOException, InvalidFormatException {
		
		File file = new File(filename);
		FileInputStream fis = new FileInputStream(file);
		XSSFWorkbook wb = new XSSFWorkbook(fis);
		XSSFSheet sheet = wb.getSheetAt(0);
		int totalRows = sheet.getPhysicalNumberOfRows();
		Iterator<Row> itr = sheet.iterator();
		System.out.println("Open\tHigh\tLow\tVolume\t\tCap\tClose\tTrend\t");
		while (itr.hasNext()) {
			
			Row row = itr.next();
			
			Iterator<Cell> cellIterator = row.cellIterator();
			String[] data = new String[7];
			int i = 0;
			while (cellIterator.hasNext()) {
				Cell cell = cellIterator.next();
				
				if (cell.getCellType() == CellType.NUMERIC) {
					long val = (long) cell.getNumericCellValue();
					data[i] = Long.toString(val);
					System.out.print(cell.getNumericCellValue() + "\t");
				} else {
					data[i] = cell.getStringCellValue();
					System.out.print(cell.getStringCellValue() + "\t");
				}
				i++;
			}
			
			addData(dataset, data);
			System.out.println();
		}
		
		System.out.println("\nAdded " + totalRows + " entries to the Dataset!");
		wb.close();
		fis.close();
	}
	
	
	
	public static void addData(ArrayList<Data> dataset, String[] data) {
		
		Data d = new Data();
		d.open = (double) Long.parseLong(data[0]);
		d.high = (double) Long.parseLong(data[1]);
		d.low = (double) Long.parseLong(data[2]);
		d.volume = Long.parseLong(data[3]);
		d.mCap = Long.parseLong(data[4]);
		d.close = (double) Long.parseLong(data[5]);
		if (data[6] == null)
			d.trend = null;
		
		dataset.add(d);

	}

}
