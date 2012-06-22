package GFWriter;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.List;

public class Customizer {
	private final static String Make = "gf -make ";
	
	public static void makePGF(List<String> baseGram, String pgfPath){
		try {
			String args = "";
			Iterator<String> it = baseGram.iterator();
			while(it.hasNext()){
				String name = it.next();
				args += name + " ";
			}
			Process p = Runtime.getRuntime().exec(Make + args);
			String gfOut = null;
			
			BufferedReader brInput = new BufferedReader(new InputStreamReader(p.getInputStream()));
			System.out.println("Writing PGF files ... ");
			while ((gfOut = brInput.readLine()) != null) {
                System.out.println(gfOut);
            }
			BufferedReader brError = new BufferedReader(new InputStreamReader(p.getErrorStream()));
			while ((gfOut = brError.readLine()) != null) {
                System.out.println(gfOut);
            }
			// pgfPath : "./res/Ext.pgf"
			String pgf = pgfPath.substring(pgfPath.lastIndexOf('/')+1);
			String path = pgfPath.substring(0, pgfPath.lastIndexOf('/'));
			runCommand("mv " + pgf + " " + path);
		} catch (IOException e) {
			System.out.println("runCommand Error: " +e.getMessage());
		}
	}
	
	public static void addLin(String filePath, Element elem){
		addElem(filePath, elem, "lin", "=");
	}

	public static void addFun(String filePath, Element elem){
		addElem(filePath, elem, "fun", ":");
	}
	
	public static void updateLin(String filePath, String[] module, Element elem){
		try{
			FileInputStream fstream = new FileInputStream(filePath);
			DataInputStream in = new DataInputStream(fstream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String strLine;
			boolean matchHead = false, newFun = false, haveLin = false;
			String txt = "";
			while ((strLine = br.readLine()) != null){
				// update head
				if(!matchHead && module.length>0 && strLine.matches("concrete.*=.*" + module[0] + ".*")){
					matchHead = true;
					String temp = strLine;
					for(int i=0;i<module.length;i++){
						if(strLine.matches(".*\\b" + module[i] + "\\b\\s?-\\s?\\[.*" + elem.getLHS() + ".*\\].*"))
							continue;
					    else if(strLine.matches(".*\\b" + module[i] + "\\b\\s?-\\s?\\[.*")){
					    	newFun= true;
							temp = temp.replaceAll(module[i] + "\\b\\s?-\\s?\\[", module[i]+"-[ " + elem.getLHS() + ",");
					    }
						else{
							newFun= true;
							temp = temp.replace(module[i] , module[i]+"-[ " + elem.getLHS() + " ]");
						}
					}
					txt += temp + "\n";
				}
				// update body
				else if(strLine.matches("\\s*lin\\s*")){
					haveLin = true;
					if(newFun)
						txt += "lin\n" + "  " + elem.getLHS() + " = " + elem.getRHS() + ";\n";
					else txt += strLine + "\n";
				}
				else if(haveLin && strLine.matches("\\s*" + elem.getLHS() + "\\s*=.*")){
					String update = "  " + elem.getLHS() + " = " + elem.getRHS() + " | ";
					txt += strLine.replaceAll("\\s*" + elem.getLHS() + "\\s*=", update) + "\n";
				}
				// if the is no lin 
				else if(!haveLin && strLine.matches("\\s*}\\s*"))
					txt += "lin\n  " + elem.getLHS() + " = " + elem.getRHS() + ";\n}" ;
				else 
					txt += strLine + "\n";
			} // reads all file
			in.close();
			File file = new File(filePath);
			GFWriter.GFGrammar.stringToFile(txt, file);
			}catch (Exception e){
				System.err.println("Error: " + e.getMessage());
			}
	} 
		
	private static void addElem(String filePath, Element elem, String section, String sep){
		try{
			FileInputStream fstream = new FileInputStream(filePath);
			DataInputStream in = new DataInputStream(fstream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String strLine;
			String txt = "";
			boolean haveSec = false, haveFun = false;
			while ((strLine = br.readLine()) != null){
				if(strLine.matches(section)){
					haveSec = true;
					txt += section + "\n  " + elem.getLHS() + " " + sep + " " + elem.getRHS() + ";\n";
				}
				else if(haveSec && !haveFun && strLine.matches("\\s*" + elem.getLHS() + "\\s*=.*")){
					haveFun = true;
					txt += elem.getLHS() + " " + sep + " " + elem.getRHS() + ";\n";
				}
				else if(!haveSec && !haveFun && strLine.matches("\\s*}\\s*"))
					txt += section + "\n  " + elem.getLHS() + " " + sep + " " + elem.getRHS() + ";\n}";
				else
					txt += strLine + "\n";
			} // reads all file
			in.close();
			File file = new File(filePath);
			GFWriter.GFGrammar.stringToFile(txt, file);
			}catch (Exception e){
				System.err.println("Error: " + e.getMessage());
			}
	}
	
	private static Process runCommand(String command){
		Process p = null;
		try {
			p = Runtime.getRuntime().exec(new String[]{"bash","-c",command});
		} catch (IOException e) {
			System.out.println("runCommand Error: " +e.getMessage());
			e.printStackTrace();
		}
		return p;
	}
}
