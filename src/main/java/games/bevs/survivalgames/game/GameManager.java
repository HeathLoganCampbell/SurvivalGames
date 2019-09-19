package games.bevs.survivalgames.game;

import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import games.bevs.survivalgames.game.games.Game;
import games.bevs.survivalgames.lobby.Lobby;
import games.bevs.survivalgames.map.Map;
import games.bevs.survivalgames.map.MapManager;
import lombok.Getter;
import lombok.NonNull;

/**
 * 
 * This class will create the games
 * 
 * @author Sprock
 *
 */
public class GameManager 
{
	@Getter
	private HashMap<Integer, GameClock> games = new HashMap<>();
	
	@Getter
	private int lastId = 0;
	
	@Getter @NonNull
	private JavaPlugin plugin;
	
	@Getter @NonNull
	private MapManager mapManager;
	
	@Getter
	private Lobby lobby;
	
	public GameManager(JavaPlugin plugin,  MapManager mapManager, Lobby lobby)
	{
		this.plugin = plugin;
		this.mapManager = mapManager;
		this.lobby = lobby;
		
		Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, () ->  {
			Iterator<Entry<Integer, GameClock>> gameIt = games.entrySet().iterator();
			while(gameIt.hasNext())
			{
				Entry<Integer, GameClock> gameclockEntry = gameIt.next();
				GameClock clock =  gameclockEntry.getValue();
				if(clock.getStage() == Stage.DELETING)
				{
					clock.remove();
					this.games.remove(gameclockEntry.getKey());
				}
					
			}
		}, 200l, 200l);
	}
	
	/**
	 * loads in the world and generates a game instance
	 * @return
	 */
	public Game createGame(Class<?> gameClazz)
	{
		
		this.lastId++;
		
		String name = "survivalgames" + File.separator + "sg_" + this.lastId;
		
		String mapTemplate = this.mapManager.selectGameMap();
		this.mapManager.copyTo(mapTemplate, name);
		World world = this.mapManager.loadWorld(name);
		Map map = new Map(this.getPlugin(), world);
		
		Game game = null;
		try {
			Constructor<?> constr = gameClazz.getConstructor(JavaPlugin.class, Map.class);
			game = (Game) constr.newInstance(this.getPlugin(), map);
			game.setId(lastId);
			this.addGame(game);
			
		} catch (NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
		}
		
		return game;
	}
	
	private GameClock addGame(Game game)
	{
		
		GameClock clock = new GameClock(this.getPlugin(), game);
		this.games.put(game.getId(), clock);
		
		clock.start();
		
		return clock;
	}
	
	public GameClock getGameClock(int id)
	{
		return this.games.get(id);
	}
	
	public Game getGame(int id)
	{
		return this.games.get(id).getGame();
	}
	
	/**
	 * 
	 * @param player
	 * @param gameId
	 * @return successfully joined
	 */
	public boolean joinGame(Player player, int gameId)
	{
		Game game = this.getGame(gameId);
		if(game == null) return false;
		game.join(player);
		return true;
	}
	
	public boolean leaveGame(Player player, int gameId)
	{
		Game game = this.getGame(gameId);
		if(game == null) return false;
		game.leave(player);
		this.lobby.spawn(player);
		return true;
	}
}
