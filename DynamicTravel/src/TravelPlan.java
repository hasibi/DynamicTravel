import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

import GFWriter.Customizer;
import GFWriter.Element;


public class TravelPlan {
	private String WebService;
	private String Method;
	private String Ident;
	private String Path;
	private Translator Trans;
	private List<String> ConModules;
	
	public TravelPlan(String webService, String method, String ident, String path, Translator trans, List<String> conModules){
		this.WebService = webService;
		this.Method = method;
		this.Ident = ident;
		this.Path = path;
		this.Trans = trans;
		this.ConModules = conModules;
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
		
	public void setTrans(Translator trans)
	{	this.Trans = trans;}
	public Translator getTrans()
	{	return this.Trans;}
	
	public void setConModules(List<String> conModules)
	{	this.ConModules = conModules;}
	public List<String> getConModules()
	{	return this.ConModules;}
	public String GetPlan(String httpQuery, String httpLang, String targetLang) throws Exception{
		System.out.println("- Refining translation to valid request for Vasttrafik ... ");
		String routeRequest = Ident+"&" + refine(httpQuery.replace(" ", ""));
		System.out.println("- Refined request:" + routeRequest);
				
		// Sending the query to Web service
		System.out.println("- Questing the vastTrafik to get the route ...");
		HTTPGet vt = new HTTPGet(this.WebService);
		File file = new File(this.Path);
		vt.send(this.Method, routeRequest, file); 
				
		// Parsing XML file
		System.out.println("- Extracting data from VastTrafik response ...");
		List<Trip> trips = XmlRoute.parseXml(this.Path);
		Iterator<Trip> tripIt = trips.iterator();
		List<Route> routes = tripIt.next().getRoutes();
		
		// presenting results to user in English
		System.out.println("- creating Parse Tree ...");
		Iterator<Route> routeIt = routes.iterator();
		int routesLen = routes.size();
		String[] trees = new String[routesLen];
		int count = 0;
		boolean isChanged = false;
		while(routeIt.hasNext()){
			Route route = routeIt.next();
			if(route.getLine().equals("Walk")) // line is "Name" attribute in xml file
				continue;
			String[] typLine = route.getLine().split(" "); // Buss 9 -> [Buss, 9]
			// generating vehicle Type
			String vehicle = "";
			if(typLine[0].equals("Buss")) 
				vehicle = "((Vhc Buss) (Lbl " + digitTree(typLine[1]) + "))";
			else if (typLine[0].equals("Spårvagn"))
				vehicle = "((Vhc Tram) (Lbl " + digitTree(typLine[1]) + "))";
			else{
				String[] vhc = Trans.parse(targetLang, "Label", route.getLine());
				if(vhc.length==0){
					String newLbl = addLabel(route.getLine(), targetLang);
					Customizer.makePGF(this.ConModules, this.Trans.getPath());
					isChanged = true;
					vehicle = "((Vhc Buss) " + newLbl + ")";
				}
				else 
					vehicle = "((Vhc Buss) " + vhc[0] + ")";
				
			}
			String[] hourMin= route.getDepartureTime().split(":");
			// (Reply ((((Routing ((Vhc Buss) Lbl_20120430_2332)) St_1605) St_1622) ((HourMin ((Nums N1) (Num N1))) ((Nums N3) (Num N0)))))
			String fromFun = this.Trans.parse(httpLang, "Stop", route.getFromId())[0];
			String toFun = this.Trans.parse(httpLang, "Stop", route.getToId())[0];
			// (Reply ((((Routing ((Vhc Tram) (Lbl (Num N7)))) St_1605) St_1634) ((HourMin ((Nums N1) (Num N1))) ((Nums N4) (Num N1)))))
			trees[count] = "((((Routing " + vehicle + ") " +
					fromFun + ") " + toFun + ") ((HourMin " + 
					digitTree(hourMin[0]) + ") " +  digitTree(hourMin[1]) + "))";
			count++;
		}
		String tree = trees[count-1];
		for(int i=count-2; i>=0; i--)
			tree = "((Change " + trees[i] + ") " + tree + ")";
		tree = "(Reply " + tree + ")";
		System.out.println(tree);
		if(isChanged)
			this.Trans.setPgfFile("./res/Ext.pgf");
		String res = this.Trans.linearize(targetLang, tree);
		System.out.println(res);		
		return res;
	}
	
	private static String refine (String request)
	{
		String[] parts = request.split("&"); //[date=8, time=11-50, originId=9021014001960000, destId=9021014007220000]
		// refine "date"
		String date = parts[0].split("=")[1]; // date=8 -> 8
		String d = getDate(date);
		// refine "time"
		String[] time = parts[1].split("=")[1].split(":");  // time = 11-50 -> [11, 50]
		if(Integer.parseInt(time[0]) < 0 || Integer.parseInt(time[0]) > 24 ||
				Integer.parseInt(time[1]) < 0 || Integer.parseInt(time[1]) > 60) {
			System.out.println("Travelling time is invalid.");
			System.exit(0);
		}
		String hour = (time[0].length()<2) ? ("0"+time[0]) : time[0];
		String min =(time[1].length()<2) ? ("0"+time[1]) : time[1];	
		String t = hour + ":" + min;
		
		// substitute new values in request
		parts[0] = "date=" + d;
		parts[1] = "time=" + t ;
		String res = parts[0];
		for(int i=1; i<parts.length;i++)
			res += "&"+parts[i];
		return res;
	}
	
	private static String getDate (String date)
	{
		//get current day
		Calendar cal = Calendar.getInstance();
		//calculate traveling day
		int dif, iDay = Integer.parseInt(date);
		if (iDay > 7) dif = iDay-8; // today = 8 tomorrow = 9  
		else dif = (iDay - cal.get(Calendar.DAY_OF_WEEK) + 7) % 7 ;
		cal.add(Calendar.DATE, dif); // adds "dif" to current date
		//generate xml format
		String year = Integer.toString(cal.get(Calendar.YEAR));
		String month = Integer.toString(cal.get(Calendar.MONTH) + 1);
		String day = Integer.toString(cal.get(Calendar.DATE));
		
		return (year+"-"+month+"-"+day);
	}
	
	// 14 -> ((Nums N1) (Num N4))
	private static String digitTree(String number){
		String res = "";
		char[] digits = number.toCharArray();
		int len = digits.length;
		if(len==0) return res;
		else res = "(Num N" + digits[len-1] + ")";
		for(int i= len-2; i>=0; i--){
			res = "((Nums N" + digits[i] + ") "+ res + ")";
		}
		return res;
	}

	private String addLabel(String lbl, String lang){
		// A unique Id for new label is created by current date & time
		DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd_HHmm");
		Calendar cal = Calendar.getInstance();
	    String funId = "Lbl_" + dateFormat.format(cal.getTime());

		String absPath = this.Trans.getPath().replace(".pgf", ".gf");
		Element funElem = new Element(funId, "Label");
		Customizer.addFun(absPath, funElem);
		
		Iterator<String> it = this.ConModules.iterator();
		Element linElem = new Element(funId, "toLabel \"" + lbl + "\"");
		while(it.hasNext()){
			String conPath = it.next();
			Customizer.addLin(conPath, linElem);
		}
		return funId;
	}
}
