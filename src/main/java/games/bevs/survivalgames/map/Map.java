package games.bevs.survivalgames.map;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import games.bevs.survivalgames.commons.io.Config;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class Map {
	@Getter
	private JavaPlugin plugin;

	@Getter
	private World world;

	@Getter
	private List<Location> spawns = new ArrayList<>();

	private int spawnIndex = 0;

	public Map(JavaPlugin plugin, World world) {
		this.plugin = plugin;
		this.world = world;

		loadConfig();
	}

	public void loadConfig() 
	{
		System.out.println("config file of world: " + this.getWorld().getName() + File.separator + "config");
		Config configFile = new Config(".." + File.separator + ".." + File.separator + this.getWorld().getName() + File.separator + "config", this.getPlugin());

		FileConfiguration config = configFile.getConfig();

		System.out.println(">>> " + config.toString());
		System.out.println("X > " + config.getString("Game.Spawns.1.X"));
		ConfigurationSection baseConfSec = config.getConfigurationSection("Game.Spawns");
		System.out.println("I guess it's the keys");
		Set<String> spawnKeys = baseConfSec.getKeys(false);
		System.out.println("Or the iterator?");
		Iterator<String> itSpawn = spawnKeys.iterator();

		while (itSpawn.hasNext()) {
			String spawnKey = itSpawn.next();
			ConfigurationSection configSec = config.getConfigurationSection("Game.Spawns." + spawnKey);
			Location newSpawn = this.getLocation(configSec);
			this.spawns.add(newSpawn);
		}

	}

	public Location getLocation(ConfigurationSection configSec) {
		double x = configSec.getDouble("X");
		double y = configSec.getDouble("Y");
		double z = configSec.getDouble("Z");

		float yaw = (float) configSec.getDouble("Yaw");
		float pitch = (float) configSec.getDouble("Pitch");

		return new Location(this.world, x, y, z, yaw, pitch);
	}

	public Location getSpawn() {
		Location location = this.spawns.get(spawnIndex % this.spawns.size());
		spawnIndex++;
		return location;
	}
}
