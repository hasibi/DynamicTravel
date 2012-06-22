import org.grammaticalframework.PGF;
import org.grammaticalframework.PGFBuilder;
import org.grammaticalframework.Parser;
import org.grammaticalframework.Linearizer;
import org.grammaticalframework.UnknownLanguageException;
import org.grammaticalframework.Trees.PrettyPrinter;
import org.grammaticalframework.Trees.Yylex;
import org.grammaticalframework.Trees.Absyn.Tree;
import org.grammaticalframework.parser.ParseState;
import org.grammaticalframework.Trees.parser;


import org.grammaticalframework.reader.*;

import java.io.FileNotFoundException;
import java.io.StringReader;
import java.lang.RuntimeException;

class Translator {
	private String Path;
	private PGF PgfFile;              // The pgf object to use
	private Parser Parser;            // The Parser object to use
	private Linearizer Linearizer;    // The Linearizer object to use

   Translator(String pgfName, String sLang, String tLang) {
	   this(pgfName);
	   try{
		   this.Parser = new Parser(this.PgfFile, sLang);
		   try {
			   this.Linearizer = new Linearizer(this.PgfFile, this.PgfFile.concrete(tLang));
		   } catch (Exception e) {
			   throw new RuntimeException("Cannot create the linearizer : " + e);
		   }
	   } catch (UnknownLanguageException e) {
			   throw new RuntimeException("Cannot create the Parser : language not found " + sLang);
	   }
   }

   Translator(String pgfName) {
	   try {
		   PGF pgf = PGFBuilder.fromFile(pgfName);
		   this.Path = pgfName;
		   this.PgfFile = pgf;
		   this.Parser = null;
		   this.Linearizer = null;
	   } catch (FileNotFoundException e) {
		   throw new RuntimeException("Cannot find the PGF file : " + pgfName);
	   } catch (Exception e) {
		   throw new RuntimeException("Cannot create the PGF file : " + e);
	   }
   }
   
   public String getPath()
   {	return this.Path;}
   
   public void setPgfFile(String pgfName){
	   try {
		   //this.PgfFile = null;
		   PGF pgf = PGFBuilder.fromFile(pgfName);
		   this.PgfFile = pgf;
		   } catch (FileNotFoundException e) {
		   throw new RuntimeException("Cannot find the PGF file : " + pgfName);
		   } catch (Exception e) {
		   throw new RuntimeException("Cannot create the PGF file : " + e);
		   }
	   }
    
   public void setParser(String lang){
	   Parser p;
		try {
			p = new Parser(this.PgfFile, lang);
			this.Parser = p;
		} catch (UnknownLanguageException e) {
			throw new RuntimeException("Cannot create the Parser : language not found " + "Eng");
		}
   }
   
	public String[] parse(String lang, String str){
		Parser p;
		try {
			p = new Parser(this.PgfFile, lang);
			this.Parser = p;
		} catch (UnknownLanguageException e) {
			throw new RuntimeException("Cannot create the Parser : language not found " + "Eng");
		}
		ParseState ps = p.parse(str);
		Tree[] trees = ps.getTrees();
		String[] printTrees = new String[trees.length];
		for(int i=0; i < printTrees.length; i++)
			printTrees[i] = PrettyPrinter.print(trees[i]);
		return printTrees;
	}
	
	public String[] parse(String lang, String startcat, String str){
		Parser p;
		try {
			p = new Parser(this.PgfFile, lang);
			p.setStartcat(startcat);
			this.Parser = p;
		} catch (UnknownLanguageException e) {
			throw new RuntimeException("Cannot create the Parser : language not found " + "Eng");
		}
		ParseState ps = p.parse(str);
		Tree[] trees = ps.getTrees();
		String[] printTrees = new String[trees.length];
		for(int i=0; i < printTrees.length; i++)
			printTrees[i] = PrettyPrinter.print(trees[i]);
		return printTrees;
	}
	
	public String[] incParse(String lang, String input) throws Exception{
    	String[] tokens = input.split(" ");
        ParseState ps = null;
		try {
			ps = new ParseState(this.PgfFile.concrete(lang), this.PgfFile.getAbstract().startcat());
		} catch (UnknownLanguageException e) {
			System.out.println(e.getMessage());
		}
        for (String t : tokens)
            if (!ps.scan(t))
                throw new Exception("The parser fail at token \"" + t + "\"");
        Tree[] trees = ps.getTrees();
		String[] printTrees = new String[trees.length];
		for(int i=0; i < printTrees.length; i++)
			printTrees[i] = PrettyPrinter.print(trees[i]);
		return printTrees;
    }

	public String linearize(String lang, String tree){
		Linearizer l;
		try {
			l = new Linearizer(this.PgfFile, this.PgfFile.concrete(lang));
			this.Linearizer = l;
		} catch (Exception e) {
			throw new RuntimeException("Cannot create the linearizer : " + e);
		}
		Tree pTree = parseTree(tree);
		String lin;
		try {
			lin = l.linearizeString(pTree);
		} catch (java.lang.Exception e) {
			   return "Error during linearization";
		   }
		return lin;
	}

	public String translate(String txt) {
		   String[] tokens = txt.split(" ");
		   Tree[] trees = this.Parser.parse(tokens).getTrees();
		   if (trees.length < 1){
			   return null;
		   }
		   try {
			   return this.Linearizer.linearizeString(trees[0]);
		   } catch (java.lang.Exception e) {
			   return "Error during linearization";
		   }
	   	}
    
    private Tree parseTree(String s) {
	 Yylex l = new Yylex(new StringReader(s));
	 parser p = new parser(l);
	 try {
	     Tree parse_tree = p.pTree();
	     return parse_tree;
	 } catch(Exception e) {
	     return null;
	 }
    }
}