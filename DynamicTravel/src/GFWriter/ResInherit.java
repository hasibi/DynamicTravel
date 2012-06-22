package GFWriter;

import java.util.List;



public class ResInherit {
	private String Name;
	private InheritType Type;
	private List<String> Funs;
	
	/**
	 * 
	 * @param name module name for inheritance
	 * @param type Inheritance type defined by InheritType enum 
	 * @param funs Name of functions for restricted inheritance
	 */
	public ResInherit(String name, InheritType type, List<String> funs){
		this.Name = name;
		this.Type = type;
		this.Funs = funs;
	}
	public ResInherit(String name){
		this.Name = name;
		this.Type = InheritType.Simple;
		this.Funs = null;
	}
	
	public void setName(String name)
	{	this.Name = name;} 
	public String getName()
	{	return this.Name;}
	
	public void setType(InheritType type)
	{	this.Type = type;}
	public InheritType getType()
	{	return this.Type;}
	
	public void setFuns(List<String> funs)
	{	this.Funs = funs;}
	public List<String> getFuns()
	{	return this.Funs;}

	/*public static List<ResInherit> getResInherits(List<String> extend){
		
		List<ResInherit> ls = new LinkedList<ResInherit>();
		Iterator<String> exIt = extend.iterator();
		while(exIt.hasNext()){
			String str = exIt.next();
			ResInherit inh = new ResInherit(str);
			ls.add(inh);
		}
		return ls;
	}*/
}
