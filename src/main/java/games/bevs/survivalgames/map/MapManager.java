package games.bevs.survivalgames.map;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.plugin.java.JavaPlugin;

import games.bevs.survivalgames.commons.Console;
import games.bevs.survivalgames.commons.EmptyChunkGenerator;
import games.bevs.survivalgames.commons.io.Decompress;
import games.bevs.survivalgames.commons.utils.MathUtils;

public class MapManager 
{
	public static final String MAP_FOLDER = "maps";
	public static final String BUILD_MAP_FOLDER = "buildMaps";
	public static final String ACTIVE_GAME_MAP_FOLDER = "activeGames";
	
	private JavaPlugin plugin;
	
	
	public MapManager(JavaPlugin plugin)
	{
		this.plugin = plugin;
		
		clearActiveMaps();
	}
	
	private void clearActiveMaps()
	{
		File file = new File(ACTIVE_GAME_MAP_FOLDER);
		try {
			FileUtils.deleteDirectory(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		if(!file.exists())
			file.mkdir();
	}
	
	public List<String> listGameMaps()
	{
		if(!this.plugin.getDataFolder().exists()) this.plugin.getDataFolder().mkdirs();
		File gameMapFilder = new File(this.plugin.getDataFolder(), MAP_FOLDER);
		Console.log("MapManager", "searching map...");
		Console.log("MapManager", gameMapFilder.toString());
		if(!gameMapFilder.exists()) gameMapFilder.mkdir();
		
		Console.log("MapManager (This should be null)", gameMapFilder.toString());
		
		List<String> maps = new ArrayList<>();
		
		for(File file : gameMapFilder.listFiles())
		{
			System.out.println("vvvvv");
			System.out.println(file.toString());
		}
		
		for(String mapFile : gameMapFilder.list())
		{
			if(!mapFile.endsWith(".zip"))
				continue;
			
			maps.add(gameMapFilder.getAbsolutePath() + File.separator + mapFile);
		}
		
		return maps;
	}
	
	/**
	 * @param gameId
	 * @return a randomly selected map
	 */
	public String selectGameMap()
	{
		List<String> maps = this.listGameMaps();
		String mapName = maps.get(MathUtils.getRandom().nextInt(maps.size()));
		return mapName;
	}
	
	public void copyTo(String worldZipDirectory, String gameSessionId)
	{
		Decompress decompression = new Decompress(worldZipDirectory, gameSessionId);
		try {
			decompression.unzipSpeed();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public World loadWorld(String worldName)
	{
		WorldCreator worldCreator = new WorldCreator(worldName);
		worldCreator.generator(new EmptyChunkGenerator());
		return Bukkit.getServer().createWorld(worldCreator);
	}
	
	
	// building a world
	
	public void loadBuildWorld(String worldName)
	{
		World world = this.loadWorld(BUILD_MAP_FOLDER + File.separator + worldName);
		world.setTime(0);
		world.setGameRuleValue("DoDayNightCycles", "false");
	}
	
	public List<String> listBuildWorld(String gameId)
	{
		File gameMapFilder = new File(BUILD_MAP_FOLDER);
		Console.log("MapManager", "searching map...");
		Console.log("MapManager", gameMapFilder.toString());
		if(!gameMapFilder.exists()) gameMapFilder.mkdir();
		
		List<String> maps = new ArrayList<>();
		
		for(String mapFile : gameMapFilder.list())
		{
			maps.add(mapFile);
		}
		
		return maps;
	}
	
	/**
	 * unloads the world and says if it was successful
	 */
	public boolean unloadBuildWorld(String worldName)
	{
		String realWorldName = BUILD_MAP_FOLDER + File.separator + worldName;
		World world = Bukkit.getWorld(realWorldName);
		
		if(world == null) return false;
		
		world.getPlayers().forEach(player -> player.teleport(Bukkit.getWorlds().get(0).getSpawnLocation()));
		
		
		Bukkit.unloadWorld(this.plugin.getDataFolder().getAbsolutePath() + File.separator + BUILD_MAP_FOLDER + File.separator + worldName, true);
		return true;
	}
	
}