import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;


public class HTTPGet {
	private String Url;
	
	public HTTPGet(String url)
	{
		if (url.startsWith("http://"))
			Url= url;
		else
			Url = "http://"+url;
	}
	
	public void send(String method, String params, File file) {
		try{
			URL url = null;
			if (params != null && params.length() > 0)
			{
				 url = new URL(this.Url + method + "?" + params);
			}
			URLConnection urlConn = url.openConnection();
			// Get the response
			InputStream is = urlConn.getInputStream ();
			// refine XML tags
			String response = streamToString(is);
			//String redundant = "<\\?xml version=\"1.0\" encoding=\"utf-8\" standalone=\"yes\"\\?>";
			//response = response.replaceAll(redundant, "");
			
			stringToFile(response,file);
	    }
		catch (MalformedURLException me)
		{
		    System.err.println("MalformedURLException: " + me);
		}
		catch (IOException ioe)
		{
		    System.err.println("IOException: " + ioe.getMessage());
		}
	}

	private String streamToString(InputStream is){
		// write the inputStream to a String for modifying tags
		Reader reader;
		Writer writer = new StringWriter();
		try {
			reader = new BufferedReader( new InputStreamReader(is, "UTF-8"));
			int read = 0;
			char[] buffer = new char[1024];
	    
			while ((read = reader.read(buffer)) != -1) {
				writer.write(buffer, 0, read);
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return writer.toString();
	}
	
	private void stringToFile(String str, File file){
		try {
			String fName = file.getPath();
			BufferedWriter out = new BufferedWriter(new FileWriter(fName));
			out.write(str);
			out.close();
		}
		catch (IOException e){
			System.out.println(e.getMessage());
		}
	}
}
