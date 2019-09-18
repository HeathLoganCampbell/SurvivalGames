package games.bevs.survivalgames.commons.utils;

public class NumberUtils
{
	/**
	 * Check if a string contains a number
	 * @param numStr
	 * @return
	 */
	public static boolean isNumber(String numStr)
	{
		try
		{
			Integer.parseInt(numStr);
			return true;
		} catch(Exception e) {}
		return false;
	}
}
