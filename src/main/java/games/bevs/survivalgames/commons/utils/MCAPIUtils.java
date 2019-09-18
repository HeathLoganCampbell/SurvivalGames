package games.bevs.survivalgames.commons.utils;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.UUID;

import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

public class MCAPIUtils 
{
	private static final String USERNAME_URL = "https://sessionserver.mojang.com/session/minecraft/profile/";
	private static final String UUID_URL = "https://api.mojang.com/users/profiles/minecraft/";
	
	public static UUID getUUID(String username) throws IOException, ParseException 
	{
		HttpURLConnection connection = (HttpURLConnection) (new URL(USERNAME_URL + username)).openConnection();
		JSONObject response = (JSONObject) JsonUtils.getJSONParser().parse(new InputStreamReader(connection.getInputStream()));
		
		String replyUsername = (String) response.get("name");
		if(replyUsername == null) return null;
		String uuid = (String) response.get("id");
		
		return DataUtils.stringToUniqueId(uuid);
	}
	
	public static String getUsername(UUID uuid) throws IOException, ParseException 
	{
		HttpURLConnection connection = (HttpURLConnection) (new URL(UUID_URL + uuid.toString().replace("-", ""))).openConnection();
		JSONObject response = (JSONObject) JsonUtils.getJSONParser().parse(new InputStreamReader(connection.getInputStream()));
		
		String name = (String) response.get("name");
		if (name == null) return null;
		String cause = (String) response.get("cause");
		String errorMessage = (String) response.get("errorMessage");
		if (cause != null && cause.length() > 0) throw new IllegalStateException(errorMessage);
		return name;
	}
}
