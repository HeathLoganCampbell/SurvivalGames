package games.bevs.survivalgames.game.games;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.java.JavaPlugin;

import games.bevs.survivalgames.game.ChampionToken;
import games.bevs.survivalgames.game.PlayState;
import games.bevs.survivalgames.game.componets.Componet;
import games.bevs.survivalgames.map.Map;
import lombok.Getter;
import lombok.Setter;

public class Game 
{
	@Getter @Setter
	private int id;
	
	@Getter
	private HashMap<UUID, PlayState> playState = new HashMap<>();
	
	@Getter
	private ArrayList<Componet> componets = new ArrayList<>();
	
	@Getter
	private String gamemode;
	
	@Getter
	private JavaPlugin plugin;
	
	@Getter
	private Map map;
	
	public Game(String gamemode, JavaPlugin plugin, Map map)
	{
		this.gamemode = gamemode;
		this.plugin = plugin;
		this.map = map;
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
