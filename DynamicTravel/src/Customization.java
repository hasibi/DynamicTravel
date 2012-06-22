/*import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

import org.grammaticalframework.PGF;
import org.grammaticalframework.Parser;
import org.grammaticalframework.UnknownLanguageException;
import org.grammaticalframework.Trees.PrettyPrinter;
import org.grammaticalframework.Trees.Absyn.Tree;
import org.grammaticalframework.parser.ParseState;*/


public class Customization {

	/*
	 public static String getFun(PGF pgf, String lang, String str){
		Parser p;
		try {
			p = new Parser(pgf, lang);
		} catch (UnknownLanguageException e) {
			throw new RuntimeException("Cannot create the Parser : language not found " + "Eng");
		}
		// *************** make change ********************
		//p.setStartcat("VhcTyp");
		ParseState ps = p.parse(str);
		Tree[] trees = ps.getTrees();
		if(trees.length != 0) return PrettyPrinter.print(trees[0]);
		else return null;
	}
	
	public static void searchHeader(String filePath, String module, String fun, String alter){
		try{
			FileInputStream fstream = new FileInputStream(filePath);
			DataInputStream in = new DataInputStream(fstream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String strLine;
			boolean matchHead = false, newFun = false;
			String txt = "";
			while ((strLine = br.readLine()) != null){
				System.out.println (strLine);
				// update head
				if(!matchHead && strLine.matches("concrete.*=.*" + module + ".*")){
					matchHead = true;
					String[] strs = strLine.split("\\b" + module + "\\b");
					if(strs.length==1){ // no customization is applied before
						txt += strs[0] + module;
						txt += " - [ " + fun + " ] ** {" + 
								"\nlin\n" + 
								"  " + fun + " = " + alter + " | " + module + "." + fun + " ;\n" + 
								"\n}";
						break;
					}
					else if(!strs[1].matches(".*\\b"+fun+"\\b.*")){
						newFun = true;
						txt += strLine.replace("[", "[ " + fun + ",") + "\n";
					}
					else 
						txt += strLine + "\n";
				}
				// update body
				else if(newFun && strLine.matches("lin"))
					txt += "lin\n" + 
							"  " + fun + " = " + alter + " | " + module + "." + fun + " ;\n";
				else if(strLine.matches("\\s*" + fun + "\\s*=.*")){
					String update = "  " + fun + " = " + alter + " | ";
					txt += strLine.replaceAll("\\s*" + fun + "\\s*=", update) + "\n";
				}
				else 
					txt += strLine + "\n";
			} // reads all file
			in.close();
			File file = new File(filePath);
			GFWriter.GFGrammar.stringToFile(txt, file);
			}catch (Exception e){
				System.err.println("Error: " + e.getMessage());
			}
	} */
}
