package StopGenerator;

import java.io.File;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import GFWriter.*;


public class StopWriter {
	
	private static final String Stop = "Stop";
	
	private String WebService;
	private String Method;
	private String Ident;
	private String Path;
	
	
	public StopWriter(String webService, String method, String ident, String path){
		this.WebService = webService;
		this.Method = method;
		this.Ident = ident;
		this.Path = path;
	}
	
	public void setWebService(String webService)
	{	this.WebService = webService;}
	public String getWebService()
	{	return this.WebService;}
	
	public void setMethod(String method)
	{	this.Method = method;}
	public String getMethod()
	{	return this.Method;}
	
	public void setIdent(String ident)
	{	this.Ident = ident;}
	public String getIdent()
	{	return this.Ident;}
	
	public void setPath(String path)
	{	this.Path = path;}
	public String getPath()
	{	return this.Path;}
	
	public List<GFGrammar> genVTStops() {
		System.out.println("Please wait ...");
		long startT = new Date().getTime();
		
		String fName = this.Path + "/allStops.xml";
		File file = new File(fName);
		
		// send a reguest to VastTrafik to get all stops
		//HTTPGet vt = new HTTPGet(this.WebService);
		//System.out.println("Sending query to VastTrafik");
		//vt.send(this.Method, Ident, file);
		
		// parse xml file to extract a list of stops
		System.out.println("Extracting data from " + fName);
		List<Stop> stops = XmlStop.parseXml(fName);
		
		List<Element> fun = new LinkedList<Element>();
		Abstract abs = writeAbs(stops, fun);
		
		List<Element> linHttp = new LinkedList<Element>();
		Concrete conHttp = writeHttpCon(stops, "ResStopHttp", "utf8", linHttp);
		
		List<Element> linEng = new LinkedList<Element>();
		Concrete conEng = writeCon(stops, "Eng", "ResStop", "utf8", linEng, "track");

		List<Element> linSwe = new LinkedList<Element>();
		Concrete conSwe = writeCon(stops, "Swe", "ResStop", "utf8", linSwe, "läge");
		
		List<GFGrammar> grams = new LinkedList<GFGrammar>();
		grams.add(abs);
		grams.add(conHttp);
		grams.add(conEng);
		grams.add(conSwe);
		
		long endT = new Date().getTime();
		
		long executeT = (long) ((endT - startT) / 1000);
		System.out.println("GF files are created in "+ executeT+ " seconds.");
		
		return grams;
	}
	
	
	private Abstract writeAbs(List<Stop> stops, List<Element> fun){
		List<String> cat = genCat();
		for(int i=0; i<stops.size(); i++){
			fun.add(new Element("St_"+i, Stop));
		}
		Abstract abs = null;
		try {
			abs = new Abstract(Path + "/"+Stop+".gf", cat, fun);
			abs.write();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return abs;
	}
	private Concrete writeHttpCon (List<Stop> stops, String resModule, String coding, List<Element> lin){
		int i = 0;
		Iterator<Stop> stopsIt = stops.iterator();
		while(stopsIt.hasNext()){
			Stop stop = stopsIt.next();
			String stopId = "\"" + stop.getStopId() + "\"";
			String rhs = "mkStop " + stopId ;
			lin.add(new Element("St_" +i, rhs));
			i++;
		}
		List<Element> lincat = new LinkedList<Element>();
		lincat.add(new Element (Stop, resModule + ".TStop"));
		Concrete con = createCon(stops, "Http", resModule, coding, lincat, lin);
		con.write();
		return con;
	}
	
	private Concrete writeCon(List<Stop> stops, String lang, String resModule, String coding, List<Element> lin, String trackName){
		int i = 0;
		Iterator<Stop> stopsIt = stops.iterator();
		while(stopsIt.hasNext()){
			Stop stop = stopsIt.next();
			String station = "\"" + stop.getStation() + "\"";
			String region = (stop.getRegion() == "") ? "" : "\"" + stop.getRegion() + "\"" ;
			String track = (stop.getTrack() == null) ? "\"\"" : "\"" + trackName + " " + stop.getTrack() + "\"";
			String rhs = "mkStop " + station + " " + region + " " + track ;
			lin.add(new Element("St_" +i, rhs));
			i++;
		}
		List<Element> lincat = new LinkedList<Element>();
		lincat.add(new Element(Stop, resModule + ".TStop"));
		Concrete con = createCon(stops, lang, resModule, coding, lincat, lin);
		con.write();
		return con;
	}
	
	private Concrete createCon(List<Stop> stops, String lang, String resModule, String coding, List<Element> lincat, List<Element> lin){
		String path = this.Path+"/"+Stop+lang+".gf";
		List<String> open = new LinkedList<String>();
		open.add(resModule);
		List<Element> flags = new LinkedList<Element>();
		flags.add(new Element("coding", coding));
		Concrete con = null;
		try {
			con = new Concrete(path, Stop, null, open, flags, lincat, lin);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return con;
	}

	private List<String> genCat() {
		List<String> cat = new LinkedList<String>();
		cat.add(Stop);
		return cat;
	}
	
	/*static String Swe2Eng(String s){
		String res = "";
		char[] cs = s.toCharArray();
		for(int i=0; i<cs.length; i++){
			if(cs[i]=='Ö') cs[i] = 'O';
			else if(cs[i]=='ö') cs[i] = 'o';
			else if(cs[i]=='Ä') cs[i] = 'A';
			else if(cs[i]=='ä') cs[i] = 'a';
			else if(cs[i]=='Å') cs[i] = 'A';
			else if(cs[i]=='å') cs[i] = 'a';
			res += cs[i];
		}
		return res;
	}*/
}
