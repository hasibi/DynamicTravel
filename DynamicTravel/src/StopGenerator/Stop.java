package StopGenerator;


public class Stop {
	private String StopId;
	private String StopName;
	private String Region;
	private String Track;
	
	public void setStopId(String stopId)
	{	this.StopId = stopId;}
	public String getStopId()
	{	return this.StopId;}
	
	public void setStopName(String stopName)
	{	this.StopName = stopName;}
	public String getStation()
	{	return this.StopName;}
	
	public void setRegion(String region)
	{	this.Region = region;}
	public String getRegion()
	{	return this.Region;}
	
	public void setTrack(String track)
	{	this.Track = track;}
	public String getTrack()
	{	return this.Track;}
}
