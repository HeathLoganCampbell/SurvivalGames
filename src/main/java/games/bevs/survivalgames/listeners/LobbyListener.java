package games.bevs.survivalgames.listeners;

import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;

import games.bevs.survivalgames.lobby.Lobby;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter(AccessLevel.PRIVATE)
@AllArgsConstructor
public class LobbyListener implements Listener
{
	private Lobby lobby;
	
	@EventHandler
	public void onBreak(BlockBreakEvent e)
	{
		Player player = e.getPlayer();
		if(player.getGameMode() == GameMode.CREATIVE)
			return;
		if(this.getLobby().getWorld() != player.getWorld())
			return;
		e.setCancelled(true);
	}
	
	@EventHandler
	public void onPlace(BlockPlaceEvent e)
	{
		Player player = e.getPlayer();
		if(player.getGameMode() == GameMode.CREATIVE)
			return;
		if(this.getLobby().getWorld() != player.getWorld())
			return;
		e.setCancelled(true);
	}
}
