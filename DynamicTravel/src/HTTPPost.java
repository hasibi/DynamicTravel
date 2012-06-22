import java.io.DataOutputStream;
import java.io.File;
//import java.io.FileOutputStream;

import java.io.IOException;
import java.io.InputStream;
//import java.io.OutputStream;

import java.io.Writer;
import java.io.StringWriter;
import java.io.Reader;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.BufferedWriter;
import java.io.FileWriter;

import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;


public class HTTPPost {
	static final String ContentType = "application/x-www-form-urlencoded";
	private String Url;
	
	public HTTPPost(String url)
	{
		Url= url;
	}
	
	public void send(String method, String params, File file) {
		// TODO Auto-generated method stub
		try{
			URL url;
			URLConnection urlConn;
			DataOutputStream printout;
			// URL of CGI-Bin script.
			url = new URL (this.Url + method);
			// URL connection channel.
			urlConn = url.openConnection();
			// Let the run-time system (RTS) know that we want input.
			urlConn.setDoInput (true);
			// Let the RTS know that we want to do output.
			urlConn.setDoOutput (true);
			// No caching, we want the real thing.
			urlConn.setUseCaches (false);
			// Specify the content type.
			urlConn.setRequestProperty("Content-Type", ContentType);
			// Send POST output.
			printout = new DataOutputStream (urlConn.getOutputStream ());
			printout.writeBytes (params);
			printout.flush ();
			printout.close ();
			
			InputStream is = urlConn.getInputStream ();
			// refine XML tags
			String response = streamToString(is);
			response = response.replaceAll("&lt;", "<");
			response = response.replaceAll("&gt;", ">");
			String redundant = "<\\?xml version=\"1.0\" encoding=\"utf-8\" standalone=\"yes\"\\?>";
			response = response.replaceAll(redundant, "");
			
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
	
	/*private void streamToFile(File file, InputStream is) {
		try {
			OutputStream out = new FileOutputStream(file);
    
			int read = 0;
			byte[] bytes = new byte[1024];
    
			while ((read = is.read(bytes)) != -1) {
				out.write(bytes, 0, read);
			}
			is.close();
			out.flush();
			out.close();
			System.out.println("New file created!");
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}*/
}

//Get response data.
//DataInputStream input = new DataInputStream (urlConn.getInputStream ());
//File file = new File("result1.xml");
//InputStream is = urlConn.getInputStream ();
//writeToFile(is, file);

	    
	    /*
	     TextArea textArea = new TextArea (25, 70);
	     String str;
	    
	    while (null != ((str = input.readLine())))
	    {
	    	System.out.println (str);
	    	textArea.appendText (str + "\n");
	    }
	    input.close ();*/
	 // Display response.
	    //add ("Center", textArea);

		/*Reader data = new StringReader("identifer= 734816b0-5363-4571-b28f-950b7a33337a");
		try
		{
			URL endpoint = new URL("http://vasttrafik.se/External_Services/TravelPlanner.asmx/");
			FileWriter output = new FileWriter ("results.html");
			postData(data, endpoint, output);
		} catch (Exception e)
		{}*/
	
		
	
	/*public static void writeToFile(InputStream is, File file) {
		try {
			DataOutputStream out = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(file)));
			InputStream is2 = is;
			boolean again = true;
			while(again) {
				if(is2.read() > -1) {
					out.writeByte(is.read());
				}
				else again = false;
			}
			is.close();
			out.close();
		}
		catch(IOException e) {
			System.err.println("Error Writing/Reading Streams.");
		}
	}*/




	/**
	* Reads data from the data reader and posts it to a server via POST request.
	* data - The data you want to send
	* endpoint - The server's address
	* output - writes the server's response to output
	* @throws Exception
	*/
	/*public static void postData(Reader data, URL endpoint, Writer output) throws Exception
	{
		HttpURLConnection urlc = null;
		try
		{
			urlc = (HttpURLConnection) endpoint.openConnection();
			try
			{
				urlc.setRequestMethod("POST");
			} catch (ProtocolException e)
			{
				throw new Exception("Shouldn't happen: HttpURLConnection doesn't support POST??", e);
			}
			urlc.setDoOutput(true);
			urlc.setDoInput(true);
			urlc.setUseCaches(false);
			urlc.setAllowUserInteraction(false);
			urlc.setRequestProperty("Content-type","application/x-www-form-urlencoded");// "text/xml; charset=" + "UTF-8");

			OutputStream out = urlc.getOutputStream();

			try
			{
				Writer writer = new OutputStreamWriter(out, "UTF-8");
				pipe(data, writer);
				writer.close();
			} catch (Exception e)
			{
				throw new Exception("IOException while posting data", e);
			} finally
			{
				if (out != null)
					out.close();
			}

			InputStream in = urlc.getInputStream();
			try
			{
				Reader reader = new InputStreamReader(in);
				pipe(reader, output);
				reader.close();
			} catch (IOException e)
			{
				throw new Exception("IOException while reading response", e);
			} finally
			{
				if (in != null)
					in.close();
			}

		} catch (IOException e)
		{
			throw new Exception("Connection error (is server running at " + endpoint + " ?): " + e);
		} finally
		{
			if (urlc != null)
				urlc.disconnect();
		}
	}*/

	/**
	* Pipes everything from the reader to the writer via a buffer
	*/
	/*private static void pipe(Reader reader, Writer writer) throws IOException
	{
		char[] buf = new char[1024];
		int read = 0;
		while ((read = reader.read(buf)) >= 0)
		{
			writer.write(buf, 0, read);
		}
		writer.flush();
		}

	*/



