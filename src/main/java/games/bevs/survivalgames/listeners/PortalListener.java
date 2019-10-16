package games.bevs.survivalgames.listeners;

import java.util.Iterator;
import java.util.Map.Entry;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.World;
import org.bukkit.entity.Player;

import games.bevs.survivalgames.game.GameClock;
import games.bevs.survivalgames.game.GameManager;
import games.bevs.survivalgames.game.Stage;
import games.bevs.survivalgames.lobby.Lobby;
import lombok.AccessLevel;
import lombok.Getter;

@Getter(AccessLevel.PRIVATE)
public class PortalListener implements Runnable
{
	private Lobby lobby;
	private GameManager gameManager;
	
	public PortalListener(Lobby lobby, GameManager gameManager)
	{
		this.lobby = lobby;
		this.gameManager = gameManager;
		
		Bukkit.getScheduler().scheduleSyncRepeatingTask(gameManager.getPlugin(), this, 10, 10l);
	}

	//Cheap queue
	@Override
	public void run() 
	{
		World lobbyWorld = this.lobby.getWorld();
		for(Player player : lobbyWorld.getPlayers())
		{
			if(player.isDead()) 
				continue;
			if(player.getGameMode() != GameMode.SURVIVAL)
				continue;
			
			int gameId = -1;
			Iterator<Entry<Integer, GameClock>> entryIt = this.gameManager.getGames().entrySet().iterator();
			while(entryIt.hasNext())
			{
				Entry<Integer, GameClock> entry = entryIt.next();
				GameClock gameClock = entry.getValue();
				
				if(gameClock.getStage() == Stage.WAITING_PLAYERS || gameClock.getStage() == Stage.COUNTDOWN)
					gameId = entry.getKey();
				
				//create new game if not found
			}
			this.gameManager.joinGame(player, gameId);
		}
	}
}
