import java.util.List;


public class Trip {

	private List<Route> Routes;
	private int Changes ;
	private String Departure;
	private String Arrival;
	
	public Trip(List<Route> routes){
		this.Routes = routes;
		this.Changes = routes.size();
		this.Departure = routes.get(0).getDepartureTime();
		this.Arrival = routes.get(routes.size()-1).getArrivalTime();
	}
	
	public void setRoutes(List<Route> routes)
	{ 	this.Routes = routes;}
	public List<Route> getRoutes()
	{	return this.Routes;}
	
	public void setChanges(int changes)
	{ 	this.Changes = changes;}
	public int getChanges()
	{	return this.Changes;}
	
	public void setDeparture(String departure)
	{ 	this.Departure = departure;}
	public String getDeparture()
	{	return this.Departure;}
	
	
	public void setArrival(String arrival)
	{ 	this.Arrival = arrival;}
	public String getArrival()
	{	return this.Arrival;}
	
}
