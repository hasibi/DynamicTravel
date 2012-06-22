
public class Route {
	private String FromId;
	private String ToId;
	private String Line;  // "Buss 42" or "Spårvagn 6"
	private String DepartureTime;
	private String ArrivalTime;
	private String Track;
	
	public void setFromId(String fromId)
	{ 	this.FromId = fromId;}
	public String getFromId()
	{	return this.FromId;}
	
	public void setLine(String line)
	{	this.Line = line;}
	public String getLine()
	{	return this.Line;}
	
	public void setToId(String toId)
	{	this.ToId = toId;}
	public String getToId()
	{	return this.ToId;}
	
	public void setDepartureTime(String departureTime)
	{	this.DepartureTime = departureTime;}
	public String getDepartureTime()
	{	return this.DepartureTime;}
	
	public void setArrivalTime(String arrivalTime)
	{	this.ArrivalTime = arrivalTime;}
	public String getArrivalTime()
	{	return this.ArrivalTime;}
	
	public void setTrack(String track)
	{	this.Track = track;}
	public String getTrack()
	{	return this.Track;}
	
	/*public static String toVtrFormat(List<Route> routes){
		String res = "";
		Iterator<Route> routeIt = routes.iterator();
		while(routeIt.hasNext()){
			Route route = routeIt.next();
			// insert spaces to match the numeral of gfGrammer
			res += "line = " + insertSpace(route.getLine()); 
			res += " & fromId = " + route.getFromId();
			res += " & toId = " + route.getToId();
			res += " & trafficIsland = " + route.getTrack();
			res += " & departureTime = " + getTime(route.getDepartureTime()) + " # ";
		}
		return res;
	}*/
	
	/*private static String insertSpace(String s){ // ex. s= "18,B|"   s = "GRÖN,B|"
		String res = "";
		char[] cs = s.toCharArray();
		int splitIndex = cs.length-3;
		String lbl = s.substring(0, splitIndex); // ex. s= "18,B|"  -> lbl= "18"
		String rem = s.substring(splitIndex, cs.length);
		try{
			Integer.parseInt(lbl);  // check if s is integer 
			for(int i=0; i<splitIndex; i++)
				res+=cs[i] + " ";
		}catch(Exception e){
			res = lbl+ " ";
		}
		return res + rem;
	}*/
	
	/*private static String getTime(String s){
		String[] time = s.split("T")[1].split(":"); // 2011-11-07T07:51:00 -> [07, 51, 00]
		String res = "";
		for(int i=0; i<2; i++){ // [07, 51, 00] -> "7 - 5 1"
			char[] cs = time[i].toCharArray();
			res += (cs[0] == '0') ? cs[1] : (cs[0] + " " + cs[1]);
			if (i == 0) res += " - " ;
		}
		return res ;
	}*/
}
