package games.bevs.survivalgames;

import java.lang.reflect.Field;

import games.bevs.survivalgames.listeners.*;
import games.bevs.survivalgames.scoreboard.MainAssembleAdapter;
import io.github.thatkawaiisam.assemble.Assemble;
import io.github.thatkawaiisam.assemble.AssembleStyle;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandMap;
import org.bukkit.plugin.java.JavaPlugin;

import games.bevs.survivalgames.commands.SurvivalGamesCommand;
import games.bevs.survivalgames.game.GameHandler;
import games.bevs.survivalgames.game.GameManager;
import games.bevs.survivalgames.lobby.Lobby;
import games.bevs.survivalgames.map.MapManager;

/**
 * /sg dev create
 * /sg dev list
 * /sg dev join <GameId> 
 *
 * TODO:
 * - easy way to get players to join
 * - Get McMonday Map Lobby
 * - Create 4 maps
 * -
 * - fake players
 * - Regen soup
 * - TNT
 * - Surger Soup
 * - Player tracker
 *
 * - Scoreboards
 * 		- players left
 * 		- players watching
 * - Database
 * - Levels
 *
 * /sg dev join [GameId] - All players join the server who's not in creative
 * /sg dev spectate [GameId]
 *
 */
public class SurvivalGamesPlugin extends JavaPlugin
{
	private Assemble assemble;//scoreboard

	@Override
	public void onEnable()
	{
		new SurvivalGames(this);//init API

		this.assemble = new Assemble(this, new MainAssembleAdapter());
		this.assemble.setTicks(2);
		this.assemble.setAssembleStyle(AssembleStyle.KOHI);
		
//		Game game = this.gameManager.createGame(ClassicGame.class);
		Bukkit.getPluginManager().registerEvents(new GameHandler(), this);
		Bukkit.getPluginManager().registerEvents(new LobbyListener(SurvivalGames.get().getLobby()), this);
		Bukkit.getPluginManager().registerEvents(new PlayerListener(SurvivalGames.get().getGameManager()), this);
		Bukkit.getPluginManager().registerEvents(new SpectatorListener(), this);
		Bukkit.getPluginManager().registerEvents(new GracePeriodListener(), this);
		Bukkit.getPluginManager().registerEvents(new PreGameListener(), this);
		Bukkit.getPluginManager().registerEvents(new CompassListener(), this);
		//PlayerListener
		
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
		this.assemble.cleanup();
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
		 
		SurvivalGamesCommand sgCmd = new SurvivalGamesCommand(SurvivalGames.get().getGameManager());
		commandMap.register(sgCmd.getName(), sgCmd);
	}
}
