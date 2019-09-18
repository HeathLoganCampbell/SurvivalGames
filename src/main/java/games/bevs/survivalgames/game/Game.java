package games.bevs.survivalgames.game;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.java.JavaPlugin;

import games.bevs.survivalgames.componets.Componet;
import lombok.Getter;

public class Game 
{
	@Getter
	private HashMap<UUID, PlayState> playState = new HashMap<>();
	
	@Getter
	private ArrayList<Componet> componets = new ArrayList<>();
	
	@Getter
	private String gamemode;
	
	private JavaPlugin plugin;
	
	public Game(String gamemode, JavaPlugin plugin)
	{
		this.gamemode = gamemode;
		this.plugin = plugin;
	}
	
	public void spawn(Player player)
	{
		
		this.onSpawn(player);
	}
	
	public void start()
	{
		Bukkit.broadcastMessage("registed compinets");
		this.registerComponets();
		
		this.onStart();
	}
	
	public void champion(ChampionToken championToken)
	{
		this.onChampion(championToken);
	}
	
	public void finish()
	{
		this.unregisterComponets();
		
		this.onFinish();
	}
	
	public void addComponet(Componet componet)
	{
		this.componets.add(componet);
	}
	
	public void registerComponets()
	{
		this.componets.forEach(componet -> {
			Bukkit.getPluginManager().registerEvents(componet, this.plugin);
		});
	}
	
	public void unregisterComponets()
	{
		this.componets.forEach(componet -> {
			HandlerList.unregisterAll(componet);
		});
	}
	
	protected void onSpawn(Player player)
	{
		
	}
	
	protected void onStart()
	{
		
	}
	
	protected void onChampion(ChampionToken championToken)
	{
		
	}
	
	protected void onFinish()
	{
		
	}
	
	protected void onSeconds()
	{
		
	}
}
