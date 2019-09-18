package games.bevs.survivalgames.game.games;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.function.Consumer;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;

import games.bevs.survivalgames.commons.utils.CC;
import games.bevs.survivalgames.game.ChampionToken;
import games.bevs.survivalgames.game.PlayState;
import games.bevs.survivalgames.game.Stage;
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
	
	@Getter @Setter
	private boolean championFound = false;
	
	public Game(String gamemode, JavaPlugin plugin, Map map)
	{
		this.gamemode = gamemode;
		this.plugin = plugin;
		this.map = map;
	}
	
	public void spawn()
	{
		this.applyToAll(player ->
		{ 
			this.playState.put(player.getUniqueId(), PlayState.ALIVE);
			
			this.onSpawn(player);
		});
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
		this.championFound = true;
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
	
	public void join(Player player)
	{
		this.getPlayState().put(player.getUniqueId(), PlayState.UNKNOWN);
		player.teleport(this.getMap().getWorld().getSpawnLocation());
		this.broadcast(CC.gray + player.getDisplayName() + " has joinned");
	}
	
	public void leave(Player player)
	{
		this.getPlayState().remove(player.getUniqueId());
		this.broadcast(CC.gray + player.getDisplayName() + " has left");
	}
	
	public List<Player> getAlivePlayers()
	{
		ArrayList<Player> alivePlayers = new ArrayList<>();
		Bukkit.getOnlinePlayers().forEach(player ->
		{
			if(this.getPlayState().containsKey(player.getUniqueId()))
				if(this.getPlayState().get(player.getUniqueId()) == PlayState.ALIVE)
					alivePlayers.add(player);
		});
		return alivePlayers;
	}
	
	public void applyToAll(Consumer<Player> consumer)
	{
		Bukkit.getOnlinePlayers().forEach(player ->
		{
			if(this.getPlayState().containsKey(player.getUniqueId()))
				consumer.accept(player);
		});
	}
	
	public void broadcast(String message)
	{
		applyToAll((player) -> player.sendMessage(message));
	}
	
	public World getWorld()
	{
		return this.getMap().getWorld();
	}
	
	public void second()
	{
		this.onSeconds();
	}
	
	protected void onSpawn(Player player)
	{
		player.teleport(this.getMap().getSpawn());
		player.setVelocity(new Vector(0, 0, 0));
	}
	
	protected void onStart()
	{
		
	}
	
	protected void onChampion(ChampionToken championToken)
	{
		String winner = "No one";
		if(championToken.getPlayer() != null)
			winner = championToken.getPlayer().getName();
		Bukkit.broadcastMessage(winner + " has won");
	}
	
	protected void onFinish()
	{
		
	}
	
	protected void onSeconds()
	{
		List<Player> alivePlayers = this.getAlivePlayers();
		if(alivePlayers.size() <= 1)
		{
			ChampionToken championToken = new ChampionToken(alivePlayers.size() == 1 ? alivePlayers.get(0) : null);
			this.champion(championToken);
		}
	}
	
	public void onStateChange(Stage stage, Stage lastStage)
	{
		
	}
	
	public void remove()
	{
		this.componets.clear();
		this.playState.clear();
		this.plugin = null;
		Bukkit.unloadWorld(this.getWorld(), false);
	}
}
