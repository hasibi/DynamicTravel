package GFWriter;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;


public abstract class GFGrammar {

	String Name;
	File GfFile;
	List<ResInherit> Extend;
	List<String> Open;
	List<Element> Flags;
	/**
	 * 
	 * @param gfPath
	 * @param extend
	 * @param open
	 * @param flags
	 * @throws Exception
	 */
	public GFGrammar(String gfPath, List<ResInherit> extend, List<String> open, List<Element> flags) throws Exception{
		if(!getFormat(gfPath).equals("gf")) throw new Exception("File format must be .gf");
		if(getName(gfPath).equals("")) throw new Exception("Please specify a name for your GF file.");
		this.Name = getName(gfPath);
		this.GfFile = new File(gfPath);
		this.Extend = extend;
		this.Open = open;
		this.Flags = flags;
	}
	public GFGrammar(File gfFile) throws Exception{
		String path = this.GfFile.getName();
		if(getFormat(path)!= "gf") throw new Exception("File format must be .gf");
		this.Name = getName(path);
		this.GfFile = gfFile;
		this.Extend = null;
		this.Open = null;
	}
	
	public String getName(){
		return this.Name;
	}
	
	public void setGFFile(File gfFile)
	{	this.GfFile = gfFile;}
	public File getGfFile()
	{	return this.GfFile;}
	
	public void setExtend(List<ResInherit> extend)
	{	this.Extend = extend;}
	public List<ResInherit> getExtend()
	{	return this.Extend;}
	
	public void setOpen(List<String> open)
	{	this.Open = open;}
	public List<String> getOpen()
	{	return this.Open;}
	
	public void setFlags(List<Element> flags)
	{	this.Flags = flags;}
	public List<Element> getFlags()
	{	return this.Flags;}
	
	abstract void write();
	
	private String getName(String path){
	    int dot = path.lastIndexOf('.');
	    int sep = path.lastIndexOf('/');
	    return path.substring(sep + 1, dot);
	}
	private String getFormat(String path){
	    int dot = path.lastIndexOf('.');
	    return path.substring(dot +1);
	}
	
	String genHead(){
		String res = "";
		// add extends
		if(this.Extend!=null && !this.Extend.isEmpty()){
			Iterator<ResInherit> extIt = this.Extend.iterator();
			while(extIt.hasNext()){
				ResInherit inh = extIt.next();
				if(inh.getType() != InheritType.Simple && inh.getFuns() != null && !inh.getFuns().isEmpty()){
					Iterator<String> funsIt = inh.getFuns().iterator();
					String funs = "[" + funsIt.next();
					while(funsIt.hasNext())
						funs +=  ", " + funsIt.next();
					funs += "]";
					res += (inh.getType() == InheritType.Include) ? 
							inh.getName() + " " + funs + ", " :  // include -> exam [fun1, fun2]
							inh.getName() + " - " + funs + ", "; // exclude -> exam - [fun1, fun2]
				}
				else
				res += inh.getName() + ", ";
			}
			res  = res.substring(0, res.length()-2) + " ** "; // remove the last ", " from string
		}
		// add opens
		if(this.Open != null && !this.Open.isEmpty()){
			Iterator<String> opnIt = this.Open.iterator();
			res += "open " + opnIt.next();
			while(opnIt.hasNext())
				res += ", " + opnIt.next();
			res += " in ";
		}
		return res;
	}
	/**
	 * linearizes a list of elements. 
	 * Each element is linearized in this format: 
	 * <LHS + symbol + RHS + ";\n">
	 * @param elems list of element instances
	 * @param symbol a symbol that assigns RHs to LHS 
	 * @return a linearized string 
	 */
	String linElems(List<Element> elems, String symbol){
		String res = "" ;
		if(elems != null){
			Iterator<Element> elemIt = elems.iterator();
			while(elemIt.hasNext()){
				Element elem = elemIt.next();
				res += "  " + elem.getLHS() + symbol + elem.getRHS() + ";\n";
			}
		}
		return res;
	}
	 
	public static void stringToFile(String str, File file){
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
