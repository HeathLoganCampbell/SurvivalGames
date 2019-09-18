package games.bevs.survivalgames.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import games.bevs.survivalgames.game.GameManager;
import games.bevs.survivalgames.game.games.Game;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class PlayerListener implements Listener 
{
	@NonNull
	private GameManager gameManager;
	
	@EventHandler
	public void onJoin(PlayerJoinEvent e)
	{
		Player player = e.getPlayer();
		
		int lastGameId = this.gameManager.getLastId();
		Game game = this.gameManager.getGame(lastGameId);
		
		player.teleport(game.getMap().getWorld().getSpawnLocation());
		player.sendMessage("new world!");
	}
	
	@EventHandler
	public void onQuit(PlayerQuitEvent e)
	{
		
	}
}
