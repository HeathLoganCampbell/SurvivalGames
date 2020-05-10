package games.bevs.survivalgames.game.games;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.function.Consumer;

import games.bevs.survivalgames.SurvivalGames;
import games.bevs.survivalgames.scorecard.Scorecard;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import games.bevs.survivalgames.commons.utils.CC;
import games.bevs.survivalgames.game.ChampionToken;
import games.bevs.survivalgames.game.GameClock;
import games.bevs.survivalgames.game.PlayState;
import games.bevs.survivalgames.game.Stage;
import games.bevs.survivalgames.game.componets.Component;
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
	private ArrayList<Component> components = new ArrayList<>();
	
	@Getter
	private String gamemode;
	
	@Getter
	private JavaPlugin plugin;
	
	@Getter
	private Map map;
	
	@Getter @Setter
	private GameClock gameClock;
	
	@Getter
	private GameSettings settings;
	@Getter
	private boolean championFired = false;

	@Getter
	private ChampionToken championToken;
	
	public Game(String gamemode, JavaPlugin plugin, Map map)
	{
		this.gamemode = gamemode;
		this.plugin = plugin;
		this.map = map;
		
		this.settings = this.onSettings(new GameSettings());
	}
	
	//Overriable
	public GameSettings onSettings(GameSettings settings)
	{
		return settings;
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
	
	public Stage getStage()
	{
		return this.getGameClock().getStage();
	}
	
	public void champion(ChampionToken championToken)
	{
		this.onChampion(championToken);
//		this.gameClock.setStage(Stage.CHAMPIONS);

		championFired = true;
	}
	
	public void finish()
	{
		this.unregisterComponets();
		this.onFinish();
	}
	
	/**
	 * Component registered on Stage#GRACE_PERIOD
	 * @param componet
	 */
	public void addComponet(Component component)
	{
		this.components.add(component);
	}
	
	public void registerComponets()
	{
		this.components.forEach(componet -> {
			Bukkit.getPluginManager().registerEvents(componet, this.plugin);
		});
	}
	
	public void unregisterComponets()
	{
		this.components.forEach(componet -> {
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
	
	private void dropInventory(Player player)
	{
		PlayerInventory inv = player.getInventory();
		for(ItemStack item : inv.getContents())
			if(item != null && item.getType() != Material.AIR)
				player.getWorld().dropItemNaturally(player.getLocation(), item);
		
		for(ItemStack item : inv.getArmorContents())
			if(item != null && item.getType() != Material.AIR)
				player.getWorld().dropItemNaturally(player.getLocation(), item);
		
		inv.clear();
		inv.setArmorContents(new ItemStack[4]);
	}
	
	private void deathLaunch(Player player)
	{
		Vector vect = player.getLocation().getDirection().multiply(-1);
		vect.setY(1.5);
		
		player.setVelocity(vect);
	}
	
	public void toSpectator(Player player)
	{
		player.setGameMode(GameMode.SURVIVAL);
		player.setAllowFlight(true);
		player.setFlying(true);
	}
	
	public void death(Player player, Player killer, DamageCause cause)
	{
		//remove player from the game
		this.playState.put(player.getUniqueId(), PlayState.DEAD);
		
		player.setHealth(20);
		this.dropInventory(player);
		player.setLevel(0);
		player.setExp(0);
		
		this.deathLaunch(player);
		this.toSpectator(player);
		
		player.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 1000000, 0, true, false), true);
		
		this.onDeath(player, killer, cause);
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
		this.components.forEach(Component::onSecond);
		
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

		if(!this.isChampionFired())
		{
			if(winner != null)
			{
				Scorecard scorecard = SurvivalGames.get().getScorecardManager().getScorecard(championToken.getPlayer());
				scorecard.addScoreEntry("Winning " + this.getId(), SurvivalGames.POINTS_PER_WIN);
			}
		}
	}
	
	protected void onFinish()
	{
		
	}
	
	protected void onDeath(Player player, Player killer, DamageCause cause)
	{
		this.broadcast(CC.gray + player.getDisplayName() + " has died! (" + this.getAlivePlayers().size() + " remaining)" );
	}
	
	protected void onSeconds()
	{
		List<Player> alivePlayers = this.getAlivePlayers();
		if(alivePlayers.size() <= 1)
		{
			this.championToken = new ChampionToken(alivePlayers.size() == 1 ? alivePlayers.get(0) : null);
			this.getGameClock().setStage(Stage.CHAMPIONS);
		}
	}
	
	public void onStateChange(Stage stage, Stage lastStage)
	{
		
	}

	public boolean isAlive(UUID uuid)
	{
		PlayState playState = this.playState.get(uuid);
		if(playState == null) return false;
		return playState == PlayState.ALIVE;
	}

	public boolean isWithin(UUID uuid)
	{
		PlayState playState = this.playState.get(uuid);
		return playState != null;
	}

	public int getPlayerCount()
	{
		return this.playState.size();
	}

	public int getAliveCount()
	{
		int aliveCount = 0;
		for (java.util.Map.Entry<UUID, PlayState> uuidPlayStateEntry : this.playState.entrySet()) {
			if(Bukkit.getPlayer(uuidPlayStateEntry.getKey()) == null) continue;
			if(uuidPlayStateEntry.getValue() != PlayState.ALIVE) continue;

			aliveCount++;
		}

		return aliveCount;
	}
	
	public void remove()
	{
		this.components.clear();
		this.playState.clear();
		this.plugin = null;
		Bukkit.unloadWorld(this.getWorld(), false);
	}
}
