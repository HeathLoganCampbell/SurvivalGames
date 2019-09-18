package games.bevs.survivalgames.game;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
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
			
			game.start();
		}
		
		
		if(e.getNextStage() == Stage.CHAMPIONS)
		{
			//player with the mode kills or health wins
			double mostHealth = -1;
			Player mostHealthPlayer = null;
			for(Player player : game.getAlivePlayers())
			{
				if(mostHealthPlayer == null || mostHealth < player.getHealth())
				{
					mostHealth = player.getHealth();
					mostHealthPlayer = player;
				}	
			}
			
			game.champion(new ChampionToken(mostHealthPlayer));
		}
		
		if(e.getNextStage() == Stage.FINISHED)
		{
			game.finish();
		}
		
	}
}
