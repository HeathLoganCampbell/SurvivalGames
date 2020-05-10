package games.bevs.survivalgames;

import java.lang.reflect.Field;

import games.bevs.survivalgames.listeners.LobbyListener;
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
 * - Regen soup
 * - TNT
 * - Surger Soup
 * - Player tracker
 * - Point Tracker
 *
 * - Maybe ENChant books
 * - Scoreboards
 * 		- players left
 * 		- players watching
 * - Database
 * - Levels
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
