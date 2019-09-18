package games.bevs.survivalgames.game;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import games.bevs.survivalgames.events.StageChangeEvent;
import games.bevs.survivalgames.game.games.Game;

public class GameHandler implements Listener
{
	@EventHandler
	public void onStage(StageChangeEvent e)
	{
		Game game = e.getGame();
		if(e.getNextStage() == Stage.TELEPORTING)
		{
			//Spawn players
			game.spawn();
			
			e.setNextStage(Stage.FROZEN);
		}
		
		if(e.getNextStage() == Stage.GRACE_PERIOD)
		{
			game.start();
		}
		
		if(e.getNextStage() == Stage.CHAMPIONS)
		{
			game.champion(new ChampionToken("I don't know yet"));
		}
		
		if(e.getNextStage() == Stage.FINISHED)
		{
			game.finish();
		}
		
	}
}
