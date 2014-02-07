package com.cs2340.buzzfunds;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.params.ConnManagerParams;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

/**
 * Your basic friendly HttpClient that can execute simple
 * gets and posts. Much of this was taken from various web
 * examples of java http clients
 * 
 * @author Austin
 * 
 */
public class BasicHttpClient {

	//Time it takes for client to timeout in milliseconds
	public static final int HTTP_TIMEOUT = 30 * 1000;
	//single HttpClient instance
	private static HttpClient mHttpClient;
	
	/**
	 * Create a basic instance of the HttpClient
	 * Basically just sets a bunch of timeout variables to our
	 * assigned timeout variable
	 * 
	 * @return a usable HttpClient
	 */
	private static HttpClient getHttpClient(){
		if(mHttpClient == null){
			mHttpClient = new DefaultHttpClient();
			final HttpParams params = mHttpClient.getParams();
			HttpConnectionParams.setConnectionTimeout(params, HTTP_TIMEOUT);
			HttpConnectionParams.setSoTimeout(params, HTTP_TIMEOUT);
			ConnManagerParams.setTimeout(params, HTTP_TIMEOUT);
		}
		return mHttpClient;
	}
	
	/**
	 * Executes an HTTP Post request to the specified url with the parameters given
	 * @param url The web address to post the request to
	 * @param postParameters The parameters to attach to the request
	 * @return The result given by the server based on the request
	 */
	public static String exePost(String url, ArrayList<NameValuePair> postParameters)
	{
		BufferedReader in = null;
		try
		{
			HttpClient client = getHttpClient();
			HttpPost request = new HttpPost(url);
			UrlEncodedFormEntity formEnt = new UrlEncodedFormEntity(postParameters);
			request.setEntity(formEnt);
			HttpResponse response = client.execute(request);
			
			in = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
			
			StringBuffer sb = new StringBuffer("");
			String line = "";
			String newLine = System.getProperty("line.separator"); //TODO switch to \n
			
			while((line = in.readLine()) != null)
			{
				sb.append(line + newLine);
			}
			in.close();
			
			String result = sb.toString();
			return result;
		} catch (IOException e) {
			e.printStackTrace();
		}
		finally
		{
			if (in != null)
			{
				try
				{
					in.close();
				}catch(IOException e){
					e.printStackTrace();
				}
			}
		}
		return ""; //Empty string denotes IOException that caused the post to be unsuccessful
	}
	
	
	/**
	 * Executes an HTTP GET request to the specified url.
	 * 
	 * @param url The web address to post the request to
	 * @return the result sent by the server based on the request
	 */
	public static String exeGet(String url)
	{
		BufferedReader in = null;
		try{
			HttpClient client = getHttpClient();
			HttpGet req = new HttpGet();
			req.setURI(new URI(url));
			HttpResponse res = client.execute(req);
			
			in = new BufferedReader(new InputStreamReader(res.getEntity().getContent()));
			
			StringBuffer stringBuff = new StringBuffer("");
			String line = "";
			String nL = System.getProperty("line.separator");
			
			while((line = in.readLine()) != null)
			{
				stringBuff.append(line + nL);
			}
			in.close();
			
			String result = stringBuff.toString();
			return result;
		} catch (IOException e) {
			e.printStackTrace();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		finally
		{
			if (in != null)
			{
				try{
					in.close();
				} catch(IOException e)
				{
					e.printStackTrace();
				}
			}
		}
		return ""; //Empty string denotes IOException that caused the post to be unsuccessful
	}
}
