package games.bevs.survivalgames.commons.io;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import lombok.Getter;

public class Config {
	private final @Getter FileConfiguration config;
	private final @Getter File configFile;
	
	public Config(String path, String name, JavaPlugin plugin) 
	{
		this(path + File.pathSeparatorChar + name, plugin);
	}

	public Config(String name, JavaPlugin plugin) 
	{
		this.configFile = new File(plugin.getDataFolder().toString() + File.separatorChar + name + ".yml");
		boolean isNew = false;
		
		if (!this.configFile.exists()) 
		{
			isNew = true;
			try 
			{
				this.configFile.getParentFile().mkdirs();
				this.configFile.createNewFile();
			} 
			catch (IOException e) 
			{
				e.printStackTrace();
			}
		}
		
		this.config = YamlConfiguration.loadConfiguration(this.configFile);
		if(isNew)
		{
			onCreate(this.config);
			this.save();
		}
	}

	public void save() 
	{
		try 
		{
			this.config.save(this.configFile);
		}
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}
	
	public void onCreate(FileConfiguration configFile)
	{
		
	}
}