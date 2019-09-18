package games.bevs.survivalgames.commons.utils;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

import org.apache.commons.io.IOUtils;

public class ZipUtils 
{
	public static void zip(File sourceDir, File zipFile) 
	{
		try {
			FileOutputStream fout = new FileOutputStream(zipFile);

			ZipOutputStream zout = new ZipOutputStream(fout);
			addDirectory(zout, sourceDir, sourceDir);

			zout.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Add the directory recursively into a zip file
	 * @param zout
	 * @param fileSource
	 * @param sourceDir
	 */
	private static void addDirectory(ZipOutputStream zout, File fileSource, File sourceDir) {

		// get sub-folder/files list
		File[] files = fileSource.listFiles();

		for (int i = 0; i < files.length; i++) {
			try {
				String name = files[i].getAbsolutePath();
				name = name.substring((int) sourceDir.getAbsolutePath().length());
				// if the file is directory, call the function recursively
				if (files[i].isDirectory()) {
					addDirectory(zout, files[i], sourceDir);
					continue;
				}

				/*
				 * we are here means, its file and not directory, so
				 * add it to the zip file
				 */


				// create object of FileInputStream
				FileInputStream fin = new FileInputStream(files[i]);

				zout.putNextEntry(new ZipEntry(name));

				IOUtils.copy(fin, zout);
				
				zout.closeEntry();

				// close the InputStream
				fin.close();

			} catch (IOException ioe) {
				ioe.printStackTrace();
			}
		}

	}

	/**
	 * Unzip a zipFile into a directory
	 * @param targetDir
	 * @param zipFile
	 * @throws ZipException
	 * @throws IOException
	 */
	public static void unzip(File targetDir,File zipFile) throws ZipException, IOException{
		ZipFile zip = new ZipFile(zipFile);
		
		@SuppressWarnings("unchecked")
		Enumeration<ZipEntry> z = (Enumeration<ZipEntry>) zip.entries();
		while(z.hasMoreElements()){
			ZipEntry entry = z.nextElement();
			File f = new File(targetDir, entry.getName());
			if(f.isDirectory()){
				if(!f.exists())
					f.mkdirs();
			}else{
				f.getParentFile().mkdirs();
				InputStream in = zip.getInputStream(entry);
				if(f.getPath().contains("__MACOSX")) continue;
				BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(f));
				IOUtils.copy(in, out);
				in.close();
				out.flush();
				out.close();
			}
		}
		zip.close();
	}
}
