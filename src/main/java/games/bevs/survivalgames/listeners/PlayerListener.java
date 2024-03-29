package games.bevs.survivalgames.listeners;

import games.bevs.survivalgames.SurvivalGames;
import games.bevs.survivalgames.commons.utils.CC;
import games.bevs.survivalgames.commons.utils.PlayerUtils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
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
		player.setPlayerListName(CC.yellow + e.getPlayer().getName());
		SurvivalGames.get().getScorecardManager().registerPlayer(player);
		PlayerUtils.reset(player);
		
//		int lastGameId = this.gameManager.getLastId();
//		Game game = this.gameManager.getGame(lastGameId);
//
//		player.teleport(game.getMap().getWorld().getSpawnLocation());
//		player.sendMessage("new world!");
	}

	
	@EventHandler
	public void onQuit(PlayerQuitEvent e)
	{
		Player player = e.getPlayer();
		Game game = SurvivalGames.get().getGame(player);
		if(game == null) return;
		SurvivalGames.get().getGameManager().leaveGame(player, game.getId());
	}

	@EventHandler
	public void onKick(PlayerKickEvent e)
	{
		Player player = e.getPlayer();
		Game game = SurvivalGames.get().getGame(player);
		SurvivalGames.get().getGameManager().leaveGame(player, game.getId());
	}
}
