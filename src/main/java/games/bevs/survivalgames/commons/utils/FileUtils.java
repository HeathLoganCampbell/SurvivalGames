package games.bevs.survivalgames.commons.utils;

import java.io.File;
import java.io.IOException;

public class FileUtils
{
	public static void deleteFolder(File file)
	{
		try {
			org.apache.commons.io.FileUtils.deleteDirectory(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
