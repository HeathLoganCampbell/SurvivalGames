package games.bevs.survivalgames;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import games.bevs.survivalgames.game.Game;
import games.bevs.survivalgames.game.GameClock;
import games.bevs.survivalgames.game.GameHandler;
import games.bevs.survivalgames.game.games.classic.ClassicGame;

public class SurvivalGamesPlugin extends JavaPlugin
{
	@Override
	public void onEnable()
	{
		Game game = new ClassicGame(this);
		GameClock gameClock = new GameClock(this,game);
		
		Bukkit.getPluginManager().registerEvents(new GameHandler(), this);
	}
	
	@Override
	public void onDisable()
	{
		
	}
}
