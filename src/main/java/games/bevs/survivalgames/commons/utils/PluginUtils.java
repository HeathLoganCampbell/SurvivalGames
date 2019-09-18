package games.bevs.survivalgames.commons.utils;

import org.bukkit.Bukkit;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

public class PluginUtils {
	public static void registerListener(Listener listener, JavaPlugin plugin) {
		Bukkit.getPluginManager().registerEvents(listener, plugin);
	}
	
	public static void unregisterListener(Listener listener) {
		HandlerList.unregisterAll(listener);
	}

	public static int repeat(JavaPlugin plugin, Runnable run, long deplay, long sprint) {

		return Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, run, deplay, sprint);
	}

	@SuppressWarnings("deprecation")
	public static void repeat(JavaPlugin plugin, BukkitRunnable run, long deplay, long sprint) {
		Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, run, deplay, sprint);
	}

	public static void later(JavaPlugin plugin, Runnable run, long deplay) {
		Bukkit.getScheduler().runTaskLater(plugin, run, deplay);
	}

}
