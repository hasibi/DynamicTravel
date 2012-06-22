package GFWriter;


public class Element {

	private String LHS;
	private String RHS;
	
	public Element(String lhs, String rhs){
		this.LHS = lhs;
		this.RHS = rhs;
	}
	
	public void setLHS(String lhs){
		this.LHS = lhs;
	}
	public String getLHS(){
		return this.LHS;
	}
	
	public void setRHS(String rhs){
		this.RHS = rhs;
	}
	public String getRHS(){
		return this.RHS;
	}
}
