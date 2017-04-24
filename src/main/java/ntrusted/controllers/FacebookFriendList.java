package ntrusted.controllers;



import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;



public class FacebookFriendList {

	public List<String> getFriends(String id)
	{
	///// Getting facebook friends
		List<String> list = new ArrayList<String>();
		
		//Use id from input instead of hardcoded value
		String url = "https://graph.facebook.com/v2.9/"+id+"/friends?access_token=EAADsZB1QZCdXgBAJrqPyhJa7S9xFfXLxZBhkwL3rQtZCOSMKvXv5S5yQmYKcsyO6khAgcwhWOEcBYoEOZAIDxKiKBl7UlSaBFNdDLaFsH8G49oviPZCLLCMcxekFEtaQ1jx8oiSF9jb3D9ZANxUN1o5rzPi9zxA3GsZD";

		try {
			URL obj = new URL(url);
			HttpURLConnection con = (HttpURLConnection) obj.openConnection();

			// optional default is GET
			con.setRequestMethod("GET");

			int responseCode = con.getResponseCode();
			System.out.println("\nSending 'GET' request to URL : " + url);
			System.out.println("Response Code : " + responseCode);
			

			BufferedReader in = new BufferedReader(
			        new InputStreamReader(con.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();

			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();
		
			 try{
				 JSONObject jsonObject = new JSONObject(response.toString());
				 //JSONObject myResponse = jsonObject.getJSONObject("Data");
				 JSONArray arr = (JSONArray) jsonObject.get("data"); 
				 System.out.println("****Json Array is :"+ arr.toString());
				 for(int i=0; i < arr.length(); i++){
					    JSONObject ob = (JSONObject) arr.get(i);
					    String friendid = ob.getString("id");
					    list.add(friendid);
					}
				    
             } catch (JSONException e) {
                 e.printStackTrace();
             }

			//print result
			System.out.println("*******Response through Graph API is  "+response.toString());
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("List of ID's is :"+ list.toString());
			
		return list;
	}
}
