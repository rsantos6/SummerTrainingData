import java.io.*;
import java.io.File;
import java.util.UUID;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;
import java.lang.*;
import java.util.Scanner;
import java.util.*;
import java.util.ArrayList;
import java.io.PrintWriter;
import java.lang.Math;

public class CreateRun {
	public static void main(String [] args) {
		ArrayList<Run> runList = scanFile("");

		System.out.print("Enter a command: ");
		Scanner input = new Scanner(System.in);
		String instructions = input.nextLine();
		while (!instructions.equals("quit")) {
			if ((instructions.contains("average")) || (instructions.contains("usual"))){
				getAverage(runList, instructions);
			} else if (((instructions.contains("show")) || (instructions.contains("print"))) && (instructions.contains("all"))){
				printAllRuns(runList);
				
			} else if (instructions.contains("read")) {
				runList = scanFile(instructions);
			} else {
				System.out.println("That command is not recognized...");
			}
			System.out.print("Enter a command: ");
			instructions = input.nextLine();
		}
		System.out.println("good-bye");

	}

	public static void printAllRuns (ArrayList<Run> runList) {
		for (int i = 0; i < runList.size(); i++) {
			System.out.println(runList.get(i).toString() + "\n");
		}
	}

	public static void getAverage (ArrayList<Run> runList, String instructions) {
		if (instructions.contains("pace")) {
			paceRouter(runList);
		} else if (instructions.contains("distance")) {
			distanceRouter(runList, instructions);
		} else if (instructions.contains("time")) {
			averageTime(runList);
		} else {
			System.out.println("I can't average that");
		}
	}

	public static void paceRouter (ArrayList<Run> runList) {
		int count = 0;
		int sumSeconds = 0;
		for (int i = 0; i < runList.size(); i++) {
			count ++;
			String[] hourMin = runList.get(i).getPace().split(":");
			sumSeconds += Integer.parseInt(hourMin[0]) * 60;
			if (hourMin[1].substring(0,1).equals("0")){
				sumSeconds += Integer.parseInt(hourMin[1].substring(1));
			} else {
				sumSeconds += Integer.parseInt(hourMin[1]);
			}
		}

		int avgSeconds = sumSeconds / count;
		int minutes = avgSeconds/60;
		int seconds = avgSeconds%60;

		System.out.println("Your average pace is " + minutes + ":" + seconds + " per mile");
	}

	public static void averageTime (ArrayList<Run> runList) {
		System.out.println("times");
	}

	public static void distanceRouter (ArrayList<Run> runList, String instructions) {
		if (instructions.contains("Monday") || (instructions.contains("Tuesday")) || (instructions.contains("Wednesday")) || (instructions.contains("Thursday")) || (instructions.contains("Friday")) || (instructions.contains("Saturday")) || (instructions.contains("Sunday"))){
			averageSpecificDayDistance(runList, instructions);
		} else {
			double count = 0;
			double sum = 0;
			for (int i = 0; i < runList.size(); i++) {
				count ++;
				sum += Double.parseDouble(runList.get(i).getDistance());
			}
			double average = Math.round((sum / count) * 100.0) / 100.0;
			System.out.println("Your average distance ran is: " + average + " miles");
		}
	}

	public static void averageSpecificDayDistance (ArrayList<Run> runList, String instructions) {
		String day = "";
		if (instructions.contains("Monday")) {
			day = "Monday";
		} else if (instructions.contains("Tuesday")) {
			day = "Tuesday";
		} else if (instructions.contains("Wednesday")) {
			day = "Wednesday";
		} else if (instructions.contains("Thursday")) {
			day = "Thursday";
		} else if (instructions.contains("Friday")) {
			day = "Friday";
		} else if (instructions.contains("Saturday")) {
			day = "Saturday";
		} else if (instructions.contains("Sunday")) {
			day = "Sunday";
		}

		double count = 0;
		double sum = 0;
		for (int i = 0; i < runList.size(); i++) {

			if (runList.get(i).getDay().equals(day)) {
				count ++;
				sum += Double.parseDouble(runList.get(i).getDistance());
			}
			
		}
		double average = Math.round((sum / count) * 100.0) / 100.0;
		System.out.println("Your average distance for each run on " + day + "s is: " + average + " miles");
	}

	public static ArrayList<Run> scanFile (String instructions) {
		ArrayList<Run> runList = new ArrayList<Run>();
		String path = "Running_AI.csv";
		if (instructions.contains("other")) {
			path = "Test_Data.csv";
			System.out.println("Switched to other file");
		} 
		

		try {
			Scanner console = new Scanner(new File(path));
			String previous = "something";
			String temp = console.nextLine();
			String run = console.nextLine();
			while ((console.hasNextLine()) && (!run.equals(",,,,"))) {
				String [] arr = run.split(",");
				Run newRun = new Run (arr[0], arr[1], arr[2], arr[3]);
				runList.add(newRun);
				run = console.nextLine();
			}
		} 
		catch (IOException e) {
   					// do something
		}

		System.out.println("File read");
		return runList;
	}

}










