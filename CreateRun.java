/*
*Author: Russ Santos
*Class: CreateRun
*Purpose: To read, display, and calculate varying aspects of my summer training
*/




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

		System.out.print("Enter a command (enter 'help' to see all commands): ");
		Scanner input = new Scanner(System.in);
		String instructions = input.nextLine();
		while (!instructions.equals("quit")) {
			if ((instructions.contains("average")) || (instructions.contains("usual"))){
				getAverage(runList, instructions);
			} else if (((instructions.contains("show")) || (instructions.contains("print"))) && (instructions.contains("all"))){
				printAllRuns(runList);
			} else if (instructions.contains("read")) {
				runList = scanFile(instructions);
			} else if (instructions.contains("help")) {
				printPossibleCommands();
			} else if ((instructions.contains("morning")) && (instructions.contains("pace")) && (instructions.contains("before"))) {
				paceBeforeGivenTime(runList, instructions);
			} else if ((instructions.contains("many")) && (instructions.contains("percent"))) {
				percentageOnThatDay(runList, instructions);
			} else if (instructions.contains("many")) {
				howManyOnThatDay(runList, instructions);
			} else if ((instructions.contains("start")) && (instructions.contains("morning"))) {
				averageStartTimeRouter(runList, instructions);
			} else if ((instructions.contains("start")) && ((instructions.contains("afternoon")) || (instructions.contains("evening")))) {
				secondAverageStartTimeRouter(runList, instructions);
			} else {
				System.out.println("That command is not recognized. Enter 'help' to see all commands" );
			}
			System.out.print("Enter a command: ");
			instructions = input.nextLine();
		}
		System.out.println("good-bye");

	}

	public static void printPossibleCommands() {
		System.out.println("");
		System.out.println("The possible commands are: ");
		System.out.println("");
		System.out.println("quit *ends program*");
		System.out.println("print (show) all *prints all run data*");
		System.out.println("NOT DEVELOPED YET morning pace before (enter time) *prints average pace before the entered time*");
		System.out.println("many Monday, Tuesday... *prints how many times you've ran on specific day*");
		System.out.println("many percent Monday, Tuesday... *prints percentage of time you've ran on specific day*");
		System.out.println("average (usual) pace *prints average pace over all runs*");
		System.out.println("average (usual) pace Monday, Tuesday... *prints average pace for that day of the week*");
		System.out.println("average (usual) distance *prints average distance over all runs*");
		System.out.println("average (usual) distance Monday, Tuesday... *prints average distance for that day of the week*");
		System.out.println("start morning *prints average time that you start your runs in the morning*");
		System.out.println("start morning Monday, Tuesday... *prints average time that you start your runs in the morning on specific day*");
		System.out.println("start afternoon (evening) *prints average time that you start your runs in the afternoon/evening*");
		System.out.println("start afternoon (evening) Monday, Tuesday... *prints average time that you start your runs in the afternoon/evening on specific day*");
		System.out.println("");
	}

	public static void printAllRuns (ArrayList<Run> runList) {
		for (int i = 0; i < runList.size(); i++) {
			System.out.println(runList.get(i).toString() + "\n");
		}
	}

	public static void getAverage (ArrayList<Run> runList, String instructions) {
		if (instructions.contains("pace")) {
			paceRouter(runList, instructions);
		} else if (instructions.contains("distance")) {
			distanceRouter(runList, instructions);
		} else if (instructions.contains("time")) {
			averageTime(runList);
		} else {
			System.out.println("I can't average that");
		}
	}

	public static void paceRouter (ArrayList<Run> runList, String instructions) {
		instructions = instructions.toLowerCase();
		if (instructions.contains("monday") || (instructions.contains("tuesday")) || (instructions.contains("wednesday")) || (instructions.contains("thursday")) || (instructions.contains("friday")) || (instructions.contains("saturday")) || (instructions.contains("sunday"))){
			averageSpecificDayPace(runList, instructions);
		} else {
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

			if (seconds < 10) {
				System.out.println("On average your pace is " + minutes + ":0" + seconds + " per mile");
			} else {
				System.out.println("On average your pace is " + minutes + ":" + seconds + " per mile");
			}
			
		}
	}

	public static void averageSpecificDayPace(ArrayList<Run> runList, String instructions) {
		String day = whichDay(instructions);
		int count = 0;
			int sumSeconds = 0;
			for (int i = 0; i < runList.size(); i++) {
				if (runList.get(i).getDay().equals(day)) {
					count ++;
					String[] hourMin = runList.get(i).getPace().split(":");
					sumSeconds += Integer.parseInt(hourMin[0]) * 60;
					if (hourMin[1].substring(0,1).equals("0")){
						sumSeconds += Integer.parseInt(hourMin[1].substring(1));
					} else {
						sumSeconds += Integer.parseInt(hourMin[1]);
					}
				}
				
			}

			int avgSeconds = sumSeconds / count;
			int minutes = avgSeconds/60;
			int seconds = avgSeconds%60;

			if (seconds < 10) {
				System.out.println("Your pace on " + day + "s is usually " + minutes + ":0" + seconds + " per mile");
			} else {
				System.out.println("Your pace on " + day + "s is usually " + minutes + ":" + seconds + " per mile");
			}

			

	}

	public static void averageTime (ArrayList<Run> runList) {
		System.out.println("times");
	}

	public static void distanceRouter (ArrayList<Run> runList, String instructions) {
		instructions = instructions.toLowerCase();
		if (instructions.contains("monday") || (instructions.contains("tuesday")) || (instructions.contains("wednesday")) || (instructions.contains("thursday")) || (instructions.contains("friday")) || (instructions.contains("saturday")) || (instructions.contains("sunday"))){
			averageSpecificDayDistance(runList, instructions);
		} else {
			double count = 0;
			double sum = 0;
			for (int i = 0; i < runList.size(); i++) {
				count ++;
				sum += Double.parseDouble(runList.get(i).getDistance());
			}
			double average = Math.round((sum / count) * 100.0) / 100.0;
			System.out.println("On average you run: " + average + " miles");
		}
	}

	public static void averageSpecificDayDistance (ArrayList<Run> runList, String instructions) {
		String day = whichDay(instructions);

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
			while ((console.hasNextLine()) && (!run.equals(",,,,,"))) {
				//System.out.println(run);
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

	public static String whichDay(String instructions) {
		instructions = instructions.toLowerCase();
		if (instructions.contains("monday")) {
			return "Monday";
		} else if (instructions.contains("tuesday")) {
			return "Tuesday";
		} else if (instructions.contains("wednesday")) {
			return "Wednesday";
		} else if (instructions.contains("thursday")) {
			return "Thursday";
		} else if (instructions.contains("friday")) {
			return "Friday";
		} else if (instructions.contains("saturday")) {
			return "Saturday";
		} else if (instructions.contains("sunday")) {
			return "Sunday";
		} else {
			return "";
		}
	}

	public static void howManyOnThatDay(ArrayList<Run> runList, String instructions) {
		String day = whichDay(instructions);
		
		int count = 0;
		for (int i = 0; i < runList.size(); i++) {
			if (runList.get(i).getDay().equals(day)) {
				count ++;
			}
		}
		if (count == 0) {
			System.out.println("You never run on " + day + "s ");
		} else {
			System.out.println("You've ran " + count + " times on " + day);
		}
	}

	public static void percentageOnThatDay(ArrayList<Run> runList, String instructions) {
		String day = whichDay(instructions);
		
		double dayCount = 0;
		double totalCount = 0;
		for (int i = 0; i < runList.size(); i++) {
			if (runList.get(i).getDay().equals(day)) {
				dayCount ++;
			}
			totalCount ++;
		}
		double percentage = Math.round((dayCount / totalCount) * 100);
		System.out.println("You run " + percentage + "% of your runs on " + day + "s");
	}

	public static void averageStartTimeRouter(ArrayList<Run> runList, String instructions) {
		instructions = instructions.toLowerCase();
		if ((instructions.contains("monday") || (instructions.contains("tuesday")) || (instructions.contains("wednesday")) || (instructions.contains("thursday")) || (instructions.contains("friday")) || (instructions.contains("saturday")) || (instructions.contains("sunday"))) && (instructions.contains("morning"))){
			averageStartTimeSpecificDayMorning(runList, instructions);
		} else {
			int count = 0;
			int sumSeconds = 0;
			for (int i = 0; i < runList.size(); i++) {
				if(!(runList.get(i).getTime().contains("PM"))) {
					count ++;
				String[] hourMin = runList.get(i).getTime().split(":");
				if (!(hourMin[0].equals("12"))) {
					sumSeconds += Integer.parseInt(hourMin[0]) * 60;
				}
				if (hourMin[1].substring(0,1).equals("0")){
					sumSeconds += Integer.parseInt(hourMin[1].substring(1,2));
				} else {
					sumSeconds += Integer.parseInt(hourMin[1].substring(0,2));
				}

				}
			}

			int avgSeconds = sumSeconds / count;
			int minutes = avgSeconds/60;
			int seconds = avgSeconds%60;

			if (seconds < 10) {
				System.out.println("You generally start your runs at " + minutes + ":0" + seconds + " AM");
			} else {
				System.out.println("You generally start your runs at " + minutes + ":" + seconds + " AM");
			}
		}
	}

	public static void secondAverageStartTimeRouter(ArrayList<Run> runList, String instructions) {
		instructions = instructions.toLowerCase();
		if ((instructions.contains("monday") || (instructions.contains("tuesday")) || (instructions.contains("wednesday")) || (instructions.contains("thursday")) || (instructions.contains("friday")) || (instructions.contains("saturday")) || (instructions.contains("sunday"))) && ((instructions.contains("afternoon")) || (instructions.contains("evening")))){
			averageStartTimeSpecificDayAfternoon(runList, instructions);
		} else {
			int count = 0;
			int sumSeconds = 0;
			for (int i = 0; i < runList.size(); i++) {
				if(!(runList.get(i).getTime().contains("AM"))) {
					count ++;
				String[] hourMin = runList.get(i).getTime().split(":");
				if (!(hourMin[0].equals("12"))) {
					sumSeconds += Integer.parseInt(hourMin[0]) * 60;
				}
				if (hourMin[1].substring(0,1).equals("0")){
					sumSeconds += Integer.parseInt(hourMin[1].substring(1,2));
				} else {
					sumSeconds += Integer.parseInt(hourMin[1].substring(0,2));
				}

				}
			}

			int avgSeconds = sumSeconds / count;
			int minutes = avgSeconds/60;
			int seconds = avgSeconds%60;

			if (seconds < 10) {
				System.out.println("You generally start your runs at " + minutes + ":0" + seconds + " PM");
			} else {
				System.out.println("You generally start your runs at " + minutes + ":" + seconds + " PM");
			}
		}
	}

	public static void averageStartTimeSpecificDayMorning(ArrayList<Run> runList, String instructions) {
		String day = whichDay(instructions);
		int count = 0;
		int sumSeconds = 0;
		for (int i = 0; i < runList.size(); i++) {
			if((!(runList.get(i).getTime().contains("PM"))) && (runList.get(i).getDay().equals(day))) {
				count ++;
			String[] hourMin = runList.get(i).getTime().split(":");
			if (!(hourMin[0].equals("12"))) {
				sumSeconds += Integer.parseInt(hourMin[0]) * 60;
			}
			if (hourMin[1].substring(0,1).equals("0")){
				sumSeconds += Integer.parseInt(hourMin[1].substring(1,2));
			} else {
				sumSeconds += Integer.parseInt(hourMin[1].substring(0,2));
			}

			}
		}

		int avgSeconds = sumSeconds / count;
		int minutes = avgSeconds/60;
		int seconds = avgSeconds%60;

		if (seconds < 10) {
			System.out.println("On " + day + "s you generally start your runs at " + minutes + ":0" + seconds + " AM");
		} else {
			System.out.println("On " + day + "s you generally start your runs at " + minutes + ":" + seconds + " AM");
		}
	}

	public static void averageStartTimeSpecificDayAfternoon(ArrayList<Run> runList, String instructions) {
		String day = whichDay(instructions);
		int count = 0;
		int sumSeconds = 0;
		for (int i = 0; i < runList.size(); i++) {
			if((!(runList.get(i).getTime().contains("AM"))) && (runList.get(i).getDay().equals(day))) {
				count ++;
			String[] hourMin = runList.get(i).getTime().split(":");
			if (!(hourMin[0].equals("12"))) {
				sumSeconds += Integer.parseInt(hourMin[0]) * 60;
			}
			if (hourMin[1].substring(0,1).equals("0")){
				sumSeconds += Integer.parseInt(hourMin[1].substring(1,2));
			} else {
				sumSeconds += Integer.parseInt(hourMin[1].substring(0,2));
			}

			}
		}

		int avgSeconds = sumSeconds / count;
		int minutes = avgSeconds/60;
		int seconds = avgSeconds%60;

		if (seconds < 10) {
			System.out.println("On " + day + "s you generally start your runs at " + minutes + ":0" + seconds + " PM");
		} else {
			System.out.println("On " + day + "s you generally start your runs at " + minutes + ":" + seconds + " PM");
		}
	}

	public static void paceBeforeGivenTime(ArrayList<Run> runList, String instructions) {
		int index = instructions.indexOf(":");
		String beforeTime = "";
		if (instructions.charAt(index-2) == ' '){
			index --;
			beforeTime = instructions.substring(index, index+4);
		} else {
			index = index - 2;
			beforeTime = instructions.substring(index, index+5);
		}

		int count = 0;
		int sumSeconds = 0;
		for (int i = 0; i < runList.size(); i++) {
			if (isBefore(beforeTime, runList.get(i).getTime())) {
				count ++;
				String[] hourMin = runList.get(i).getPace().split(":");
				sumSeconds += Integer.parseInt(hourMin[0]) * 60;
				if (hourMin[1].substring(0,1).equals("0")){
					sumSeconds += Integer.parseInt(hourMin[1].substring(1));
				} else {
					sumSeconds += Integer.parseInt(hourMin[1]);
				}
			}
				
		}

		int avgSeconds = sumSeconds / count;
		int minutes = avgSeconds/60;
		int seconds = avgSeconds%60;

		if (seconds < 10) {
			System.out.println("Before " + beforeTime + "AM your pace is usually " + minutes + ":0" + seconds + " per mile");
		} else {
			System.out.println("Before " + beforeTime + "AM you pace is usually " + minutes + ":" + seconds + " per mile");
		}

	}

	public static boolean isBefore(String beforeTime, String runTime){
		if (runTime.contains("AM")){
			int index = runTime.indexOf("AM");
			runTime = runTime.substring(0,index);
		}
		
		if (Integer.parseInt(beforeTime.substring(0,1)) <= Integer.parseInt(runTime.substring(0,1))) {
			return true;
		} else if ((Integer.parseInt(runTime.substring(0,1)) == Integer.parseInt(beforeTime.substring(0,1))) && (Integer.parseInt(beforeTime.substring(2)) <= Integer.parseInt(beforeTime.substring(2)))) {
			return true;
		} else {
			return false;
		}
	}

}










