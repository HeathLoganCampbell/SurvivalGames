package games.bevs.survivalgames;

import java.lang.reflect.Field;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandMap;
import org.bukkit.plugin.java.JavaPlugin;

import games.bevs.survivalgames.commands.SurvivalGamesCommand;
import games.bevs.survivalgames.game.GameHandler;
import games.bevs.survivalgames.game.GameManager;
import games.bevs.survivalgames.game.games.Game;
import games.bevs.survivalgames.game.games.classic.ClassicGame;
import games.bevs.survivalgames.map.MapManager;

public class SurvivalGamesPlugin extends JavaPlugin
{
	private MapManager mapManager;
	private GameManager gameManager;
	
	@Override
	public void onEnable()
	{
		this.mapManager = new MapManager(this);
		this.gameManager = new GameManager(this, this.mapManager);
		
//		Game game = this.gameManager.createGame(ClassicGame.class);
		
		Bukkit.getPluginManager().registerEvents(new GameHandler(), this);
		
		registerCommands();
		
//		Game game = new ClassicGame(this);
//		GameClock gameClock = new GameClock(this,game);
//		GameClock clock = this.gameManager.addGame(new ClassicGame(this));
		
//		String mapTemplate = this.mapManager.selectGameMap();
//		this.mapManager.copyTo(mapTemplate, "cookie");
//		this.mapManager.loadWorld("cookie");
//		
	}
	
	@Override
	public void onDisable()
	{
		
	}
	
	private CommandMap getCommandMap()
	{
		try{
		    Field commandMapField = Bukkit.getServer().getClass().getDeclaredField("commandMap");
		    commandMapField.setAccessible(true);
		    return (CommandMap) commandMapField.get(Bukkit.getServer());
		}
		catch(Exception exception){
		    exception.printStackTrace();
		}
		 
		return null;
	}
	
	private void registerCommands()
	{
		CommandMap commandMap = this.getCommandMap();
		if(commandMap == null)
		{
			for(int i = 0; i < 20; i++)
				Bukkit.broadcastMessage("");
			
			for(int i = 0; i < 20; i++)
			{
				Bukkit.broadcastMessage(" FUCK, I can't get access to the commandMap");
				Bukkit.broadcastMessage("");
				Bukkit.broadcastMessage("");
			}
			return;
		}
		 
		SurvivalGamesCommand sgCmd = new SurvivalGamesCommand(this.gameManager);
		commandMap.register(sgCmd.getName(), sgCmd);
	}
}
