package GFWriter;

import java.io.File;
import java.util.Iterator;
import java.util.List;


public class Abstract extends GFGrammar {
	
	private List<String> Cat;
	private List<Element> Fun;
	
	/**
	 * writes given abstract syntax in specified file
	 * @param gfFile a path for GF module
	 * @param extend list of modules for extension
	 * @param open list of modules for opening
	 * @param cat list of categories declarations
	 * @param fun list of function declarations
	 * @throws Exception 
	 */
	public Abstract (String gfPath, List<ResInherit> extend, List<String> open, List<Element> flags, List<String> cat, List<Element> fun) throws Exception{
		super(gfPath, extend, open, flags);
		this.Cat = cat;
		this.Fun = fun;
	}
	public Abstract (String gfPath, List<String> cat, List<Element> fun) throws Exception{
		super(gfPath, null, null, null);
		this.Cat = cat;
		this.Fun = fun;
	}
	public Abstract (File gfFile) throws Exception{
		super(gfFile);
		this.Cat = null;
		this.Fun = null;
	}
	public void setCat(List<String> cat)
	{	this.Cat = cat;}
	public List<String> getCat()
	{	return this.Cat;}
	
	public void setFun(List<Element> fun)
	{	this.Fun = fun;}
	public List<Element> getFun()
	{	return this.Fun;}
	
	public void write(){
		String s = "abstract " + this.getName() + " = " + genHead() + "{\n" ;
		s += (this.getFlags()==null || this.getFlags().size()==0) ? "" : "flags \n" + linElems(this.getFlags(), " = ");
		s += "cat\n" + genCat() + "fun\n" + linElems (this.Fun, " : ") + "}" ;
		stringToFile(s, this.getGfFile());
	}

	
	private String genCat(){
		String res = "" ;
		Iterator<String> extIt = this.Cat.iterator();
		while(extIt.hasNext())
			res += "  " + extIt.next() + ";\n";
		return res;
	}
}
