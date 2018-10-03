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
		System.out.println("4. Modify Rules [NOTE: choose training dataset first]");
		System.out.println("5. Predict Test Dataset and Display Results [NOTE: choose training and testing datasets first]");
		System.out.println("6. EXIT\n\n");
		System.out.print("CHOOSE YOUR OPTION: ");

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
				System.out.println("\n\nNOT YET IMPLEMENTED!\n\n");
			}
			else if (choice == 5) {
				predictionOutcome(re);
			}
			else if (choice == 6) {
				System.out.println("\nExiting the Framework!");
				System.out.println("DONE.");
				reader.close();
				return;
			}
		}
	}
	
	@SuppressWarnings("resource")
	public static void configTrainingDataset(Scanner reader) throws FileNotFoundException, InvalidFormatException, IOException {
		
		System.out.print("Type in the filename of your Training Dataset: ");
		String path = "C:/Users/student/Desktop/Thesis/";
		reader = new Scanner(System.in);
		String filename = reader.next();
		path = path + filename + ".xlsx";
		
		while(true) {
			File file = new File(path);
			if (!file.exists()) {
				System.out.println("FILE NOT FOUND! TRY AGAIN!\n");
				System.out.println("Type in the path of your Training Dataset: ");
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
		
		System.out.print("Type in the filename of your Testing Dataset: ");
		String path = "C:/Users/student/Desktop/Thesis/";
		reader = new Scanner(System.in);
		String filename = reader.next();
		path = path + filename + ".xlsx";

		while(true) {
			File file = new File(path);
			if (!file.exists()) {
				System.out.println("FILE NOT FOUND! TRY AGAIN!\n");
				System.out.println("Type in the path of your Testing Dataset: ");
				path = reader.next();
			} else {
				break;
			}
		}
		
		System.out.println("\nLoading the Dataset: " + path + "\n");
		loadFile(path, testingDataset);
	}
	
	
	public static void generateRules(Rengine re) {
		
		String traincsv = "C:/Users/student/Desktop/Thesis/traincsv.csv";
		re.assign("train", traincsv);
		re.eval("trainData <- read.csv(train)");
		re.eval("vec <- c(2,2,2,1,1,2,0)");
		re.eval("trainRules <- createRules(trainData, vec)");
		REXP result = re.eval("capture.output(outputRules(trainRules))");
		String[] output = result.asStringArray();
		System.out.println("Size of output: " + output.length);
		for (String s : output) {
			System.out.println(s);
		}
		
	}
	
	
	public static void predictionOutcome(Rengine re) {
		
		
		
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
