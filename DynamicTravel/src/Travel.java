import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import GFWriter.Customizer;
import GFWriter.Element;
import StopGenerator.StopWriter;


public class Travel {
	static final String Key ="authKey=734816b0-5363-4571-b28f-950b7a33337a";
	static final String VastTrafik = "http://api.vasttrafik.se/bin/rest.exe/v1/";
	static final String AllStops = "location.allstops";
	static final String Routing = "trip";
	static String Lang;
	
	private static void setLang(String lang) throws Exception{
		if(lang.equalsIgnoreCase("eng"))
			Lang = "Eng";			
		else if(lang.equalsIgnoreCase("swe"))
			Lang = "Swe";
		else if(lang.equalsIgnoreCase("quit"))
			System.exit(0);
		else
			throw new Exception(lang + " is not recognized.");
	}
	
	public static void main(String[] args) {
		boolean hasLang = false;
		while(!hasLang){
		System.out.println("Please choose your language: \n English(Eng) Swedish(Swe)");
		BufferedReader r = new BufferedReader(new InputStreamReader(System.in));
		String lang = "";
		try {
			lang = r.readLine();
			try {
				setLang(lang);
				hasLang = true;
			} catch (Exception e) {System.out.println(e.getMessage());
			}
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
		}
		System.out.println("To update Stops enter: mkst");
		System.out.println("To update grammar enter: u");
		System.out.println("To change the language enter: chlang <language>");
		System.out.println("To get your travel plan enter: <your query>");
		System.out.println("To quit the program enter: quit");
		//Translator trans = null;
		List<String> travelBase = new LinkedList<String>();
		travelBase.add("./res/ExtEng.gf");
		travelBase.add("./res/ExtSwe.gf");
		travelBase.add("./res/ExtHttp.gf");
		String input = "";
		do{
			System.out.println("What do you want to do now?");
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			try {
				input = br.readLine();
				if (input.equals("mkst")){
					StopWriter stopWriter = new StopWriter(VastTrafik, AllStops, Key, "./res");
					stopWriter.genVTStops();
					Customizer.makePGF(travelBase, "./res/Ext.pgf");
					//trans.setPgfFile("./res/Ext.pgf");
				}
				else if(input.matches("eval\\s.*")){
					//String[] arr = input.split(" ");
					//int i = Integer.parseInt(arr[1]);
					//Evaluate.genRand(i, "./res/test1.txt", "./res/test2.txt");
					double resAdapt = Evaluate.calc("./res/Adapt.txt", "./res/Adapt-sp.txt");
					System.out.println(resAdapt);
					double resNormal = Evaluate.calc("./res/Normal.txt", "./res/Normal-sp.txt");
					System.out.println(resNormal);
					
					double resAccuracy = Evaluate.calc("./res/Adapt.txt", "./res/Adapt-sp.txt");
					System.out.println(resNormal);
				}
				else if(input.equals("u")){
					Customizer.makePGF(travelBase, "./res/Ext.pgf");
					//trans.setPgfFile("./res/Ext.pgf");
				}
				else if(input.matches("chlang\\s.*")){
					String[] arr = input.split(" ");
					try {
						setLang(arr[1]);
					} catch (Exception e) {
						System.out.println(e.getMessage());
					}
				}
				else if(input.equals("quit")) {
					System.out.println("Good bye");
					break;
				}

				else {
					long startT = new Date().getTime();
					
					Translator trans = new Translator("./res/Ext.pgf");
					//if(trans == null)
					//	trans = new Translator("./res/Ext.pgf");
					
					//trans.setPgfFile("./res/Ext.pgf");
					//trans.setParser("ExtEng");
					String[] trees = null;
					try {
						trees = trans.incParse("Ext"+Lang, input);
					} catch (Exception e1) {
						System.out.println(e1.getMessage());
						continue;
					}
					
					System.out.println("Parse tree: " + trees[0]);
					
					String temp = trees[0];
					temp = temp.replace("(", "");
					temp = temp.replace(")", "");
					String[] funs = temp.split(" ");
					if(funs[0].equals("Ask")){
						String engAns = "";
						try {
							// translating the query from Eng to Http
							String httpQuery = trans.linearize("ExtHttp", trees[0]);
							System.out.println("- Translation: " + httpQuery);
							TravelPlan tPlan = new TravelPlan(VastTrafik, Routing, Key,"./res/route.xml",trans, travelBase);
							engAns = tPlan.GetPlan(httpQuery, "ExtHttp", "Ext"+Lang);
						} catch (Exception e) {
							System.out.println(e.getMessage());
						}
						String espeak= "espeak --stdout '"+ engAns +"' | aplay";
						runCommand(espeak);
						long endT = new Date().getTime(); 
						System.out.println(endT-startT + "msec");
					}
					else if(funs[0].equals("Customize")){
						try {
							customize(input, funs);
						} catch (Exception e) {
							System.out.println(e.getMessage());
						}
						Customizer.makePGF(travelBase, "./res/Ext.pgf");
						//trans.setPgfFile("./res/Ext.pgf");
					}
				}
			} catch (IOException e) {
				System.out.println(e.getMessage());
				System.exit(0);
			}	
		}while(true);
	}
	
	private static void customize(String input, String[] funs) throws Exception{
		String[] langs = {"Eng", "Swe"};
		
		if(funs[1].equals("DefPlace")){
			for(int i = 0; i<langs.length; i++){
				String gfModule = "Travel"+langs[i];
				String[] modules = { gfModule};
				Element elem = new Element(funs[3], "toStop " + gfModule + "." + funs[2] + " " + gfModule + "." + funs[3]);
				Customizer.updateLin("./res/Ext"+langs[i]+".gf", modules, elem);
			}
		}
		else if(funs[1].equals("DefDay")){
			for(int i = 0; i<langs.length; i++){
				String gfModule = "Travel"+langs[i];
				String[] modules = { gfModule};
				Element elem = new Element(funs[3], "toWeekDay " + gfModule + "." + funs[2] + " " + gfModule + "." + funs[3]);
				Customizer.updateLin("./res/Ext"+langs[i]+".gf", modules, elem);
			}
		}
		else if(funs[1].equals("DefPlaceDay")){
			Element elemAbs = new Element(funs[2] + "StopDay", "StopDay");
			Customizer.addFun("./res/Ext.gf", elemAbs);
			
			Element elemHttp = new Element(funs[2] + "StopDay", 
					"toStopDay TravelHttp." + funs[3] + " TravelHttp." + funs[4]);
			Customizer.addLin("./res/ExtHttp.gf", elemHttp);
			for(int i = 0; i<langs.length; i++){
				String gfModule = "Travel"+langs[i];
				Element elemLang = new Element(funs[2] + "StopDay", 
						"toStopDay " + gfModule + "." + funs[2] +
						" " + gfModule + "." + funs[3] + " " + gfModule +"." + funs[4]);
				Customizer.addLin("./res/Ext" + langs[i] + ".gf", elemLang);
			}
		}
		else if(funs[1].equals("DefPlaceDayTime")){
			char[] inArr = input.toCharArray();
			String t = "";
			for(int i = inArr.length-1; i >= 0; i--){
				if((inArr[i]-48 >= 0 && inArr[i]-48<=9) || inArr[i]==':')
					t = inArr[i] + t;
				else if(inArr[i] == ' ') continue;
				else break;
			}
			String[] hourMin = t.split(":");
			if(Integer.parseInt(hourMin[0])<0 || Integer.parseInt(hourMin[0])>24)
				throw new Exception("Hour is not valid");
			if(Integer.parseInt(hourMin[1])<0 || Integer.parseInt(hourMin[1])>60)
				throw new Exception("Minute is not valid");
			char[] timeArr = t.toCharArray();
			String time = Character.toString(timeArr[0]);
			for(int i = 1; i<timeArr.length; i++)
				time += " " + timeArr[i];
			
			Element elemAbs = new Element(funs[2] + "StopDayTime", "StopDayTime");
			Customizer.addFun("./res/Ext.gf", elemAbs);
			Element elemHttp = new Element(funs[2] + "StopDayTime",
					"toStopDayTime TravelHttp." + funs[3] +
					" TravelHttp." + funs[4] + " \"" + time + "\"" );
			Customizer.addLin("./res/ExtHttp.gf", elemHttp);
			for(int i = 0; i<langs.length; i++){
				String gfModule = "Travel"+langs[i];
				Element elemLang = new Element(funs[2] + "StopDayTime", 
						"toStopDayTime " + gfModule + "." + funs[2] +
						" " + gfModule + "." + funs[3] + " " + gfModule + "." + funs[4] +  " \"" + time + "\"");
				Customizer.addLin("./res/Ext"+langs[i]+".gf", elemLang);
			
			}
		}
		
	}
	private static Process runCommand(String command){
		Process p = null;
		try {
			p = Runtime.getRuntime().exec(new String[]{"bash","-c",command});
		} catch (IOException e) {
			System.out.println("runCommand Error: " +e.getMessage());
			e.printStackTrace();
		}
		return p;
	}
}