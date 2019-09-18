package games.bevs.survivalgames.commons.utils;

import java.util.UUID;

public class DataUtils 
{
	public static String uniqueIdToString(UUID uuid)
	{
		return uuid.toString().replaceAll("-", "");
	}
	
	public static UUID stringToUniqueId(String uuidStr)
	{
		String uuidWithDashse = uuidStr.replaceAll("(\\w{8})(\\w{4})(\\w{4})(\\w{4})(\\w{12})", "$1-$2-$3-$4-$5");
		return UUID.fromString(uuidWithDashse);
	}
}
