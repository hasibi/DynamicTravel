import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import StopGenerator.Stop;
import StopGenerator.XmlStop;


public class Evaluate {
	
	public static double calcAccuracy(String path1, String path2){
		List<String> text = readFile(path1);
		List<String> speech = readFile(path2);
		int sum = 0;
		for(int i = 0; i< text.size(); i++){
			if(compareAccuracy(text.get(i), speech.get(i)))
				sum ++;
		}
		return sum*100/ text.size();
	}
	
	private static boolean compareAccuracy(String text, String speech){
		String[] textArr = text.split(" ");
		String[] speechArr = speech.split(" ");
		int len = Math.min(textArr.length, speechArr.length);
		boolean match = true;
		for(int i=0; i<len; i++){
			if(textArr[i].equals(speechArr[i]))
				match = false;
		}
		return match;
	}

	public static double calc(String path1, String path2){
		List<String> text = readFile(path1);
		List<String> speech = readFile(path2);
		int sum = 0;
		for(int i = 0; i< text.size(); i++){
			sum += compare(text.get(i), speech.get(i));
		}
		return sum/ text.size();
	}
	
	private static double compare(String text, String speech){
		String[] textArr = text.split(" ");
		String[] speechArr = speech.split(" ");
		int len = Math.min(textArr.length, speechArr.length);
		int match = 0;
		for(int i=0; i<len; i++){
			if(textArr[i].equals(speechArr[i]))
				match ++;
		}
		return 100 * match / textArr.length;
	}
	
	private static List<String> readFile(String path){
		List<String> res = new ArrayList<String>();
		try {
			FileInputStream fstream = new FileInputStream(path);
			DataInputStream in = new DataInputStream(fstream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String strLine;
			while ((strLine = br.readLine()) != null){
				res.add(strLine);
			}
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
		return res;
	}
	
	public static void genRand(int n, String path1, String path2){
		normalQueries(n, path1);
		adaptQueries(n, path2);
	}

	private static void normalQueries(int n, String path) {
		Random r = new Random();
		
		String fName = "./res/allStops.xml";
		// parse xml file to extract a list of stops
		System.out.println("Extracting data from " + fName);
		List<Stop> stops = XmlStop.parseXml(fName);
		int range = stops.size();
		
		String res = "";
		for (int i = 0; i < n; i++) {
		  String from = stops.get(r.nextInt(range)).getStation();
		  String to = stops.get(r.nextInt(range)).getStation();
		  String day = getDay();
		  int hour = r.nextInt(24);
		  int min = r.nextInt(60);
		  res += "I want to go from " + from + " to " + to + " " + day + " at " + hour + ":" + min + "\n";
		}
		File file = new File(path);
		stringToFile(res,file);
	}

	private static void adaptQueries(int n, String path) {
		Random r = new Random();
		String[] places = { "home", "work", "bar", "office", "university", 
							"swimming pool", "gym", "my brother's birthday", "park", "museum",
							"bank", "hospital","shopping mall", "library", "concert hall",
							"cinema", "lake", "beach", "library","theater",
							"restaurant", "coffee shop", "pub", "lake", "laboratory",
							"meeting", "market", "drugstore", "doctor", "sauna",
							"gallery", "airport"};
		int n1 = n/3;
		int n2 = (n-n1)/2;
		int n3 = n - n1 -n2;
		String res = "";
		int range = places.length;
		for(int i=0; i< n1; i++){
			String from = places[r.nextInt(range)];
			String to = places[r.nextInt(range)];
			res += "I want to go from " + from + " to " + to + "\n";
		}
		for(int i=0; i< n2; i++){
			String from = places[r.nextInt(range)];
			String to = places[r.nextInt(range)];
			int hour = r.nextInt(24);
			int min = r.nextInt(60);
			res += "I want to go from " + from + " to " + to +  " at " + hour + ":" + min + "\n";
		}
		for(int i=0; i< n3; i++){
			String from = places[r.nextInt(range)];
			String to = places[r.nextInt(range)];
			int hour = r.nextInt(24);
			int min = r.nextInt(60);
			res += "I want to go from " + from + " to " + to + " " + getDay() + " at " + hour + ":" + min + "\n";
		}
		File file = new File(path);
		stringToFile(res,file);
	}
	
	private static String getDay(){
		Random r = new Random();
		int i = r.nextInt(9);
		String day = "";
		switch(i){
		case 0 : {
			day = "on Sunday";
			break;
		}
		case 1 : {
			day = "on Monday";
			break;
		}
		case 2 : {
			day = "on Tuesday";
			break;
		}
		case 3 : {
			day = "on Wednesday";
			break;
		}
		case 4 : {
			day = "on Thursday";
			break;
		}
		case 5 : {
			day = "on Friday";
			break;
		}
		case 6 : {
			day = "on Saturday";
			break;
		}
		case 7 : {
			day = "today";
			break;
		}
		case 8 : {
			day = "tomorrow";
			break;
		}
		}
		return day;
	}
	
	private static void stringToFile(String str, File file){
		try {
			String fName = file.getPath();
			System.out.println("Writing file: " + fName);
			BufferedWriter out = new BufferedWriter(new FileWriter(fName));
			out.write(str);
			out.close();
		}
		catch (IOException e){
			System.out.println("Module " + file.getName()+ " can not be created. Make sure the path is correct.");
		}
	}
}
