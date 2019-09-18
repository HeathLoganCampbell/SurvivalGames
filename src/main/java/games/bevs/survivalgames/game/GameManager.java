package games.bevs.survivalgames.game;

import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;

import org.bukkit.World;
import org.bukkit.plugin.java.JavaPlugin;

import games.bevs.survivalgames.game.games.Game;
import games.bevs.survivalgames.map.Map;
import games.bevs.survivalgames.map.MapManager;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

/**
 * 
 * This class will create the games
 * 
 * @author Sprock
 *
 */
@RequiredArgsConstructor
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
		Map map = new Map(world);
		
		Game game = null;
		try {
			Constructor<?> constr = gameClazz.getConstructor(JavaPlugin.class, Map.class);
			game = (Game) constr.newInstance(this.getPlugin(), map);
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
}
