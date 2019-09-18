package games.bevs.survivalgames.commons.utils;

import org.json.simple.parser.JSONParser;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

public class JsonUtils
{
	private static final JSONParser JSON_PARSER = new JSONParser();
	private static final JsonParser GSON_PARSER = new JsonParser();
	private static final Gson GSON = new Gson();
	
	public static JSONParser getJSONParser()
	{
		return JSON_PARSER;
	}
	
	public static JsonElement toJsonFromString(String jsonStr)
	{
		return GSON_PARSER.parse(jsonStr);
	}
	
	public static String toJson(Object obj)
	{
		return GSON.toJson(obj);
	}
	
	public static <T> T fromJson(String obj, Class<T> clazz)
	{
		return GSON.fromJson(obj, clazz);
	}
}
