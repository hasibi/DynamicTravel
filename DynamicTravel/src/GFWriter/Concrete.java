package GFWriter;

import java.io.File;
import java.util.List;


public class Concrete extends GFGrammar{
	private String Abs;
	private List<Element> LinCat;
	private List<Element> Lin;
	
	/**
	 * writes given concrete syntax in specified file
	 * @param gfPath a path for GF module
	 * @param abs abstract module name for this concrete grammar
	 * @param extend list of modules for extension
	 * @param open list of modules for opening
	 * @param flags list of flags declarations
	 * @param linCat list of linearization type definitions
	 * @param lin list of linearization rules in concrete syntax
	 * @throws Exception
	 */

	public Concrete(String gfPath, String abs, List<ResInherit> extend, List<String> open, 
			List<Element> flags, List<Element> linCat, List<Element> lin) throws Exception{
		super(gfPath, extend, open, flags);
		this.Abs = abs;
		this.LinCat = linCat;
		this.Lin = lin;
	}
	public Concrete(String gfPath, String abs, List<Element> linCat, List<Element> lin) throws Exception{
		super(gfPath, null, null, null);
		this.Abs = abs;
		this.LinCat = linCat;
		this.Lin = lin;
	}
	public Concrete(File gfFile) throws Exception{
		super(gfFile);
		this.Abs = null;
		this.Abs = null;
		this.LinCat = null;
		this.Lin = null;
	}
	
	public void setAbs(String abs)
	{	this.Abs = abs;}
	public String getAbs()
	{	return this.Abs;}
	
	public void setLinCat(List<Element> linCat)
	{	this.LinCat = linCat;}
	public List<Element> getLinCat()
	{	return this.LinCat;}
	
	public void setFun(List<Element> Lin)
	{	this.Lin = Lin;}
	public List<Element> getLin()
	{	return this.Lin;}
	
	public void write(){
		String con = "concrete " + this.getName() + " of " + this.Abs + " = " + 
		             genHead() + "{\n";
		con += (this.getFlags() == null || this.getFlags().size() == 0) ? "" : "flags \n" + linElems(this.getFlags(), " = ");
		con += "lincat\n" + linElems(this.LinCat, " = ") + 
		             "lin\n" + linElems(this.Lin, " = ") + "}" ;
		stringToFile(con, this.getGfFile());
	}
}
