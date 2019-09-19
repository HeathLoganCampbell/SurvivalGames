package games.bevs.survivalgames.lobby;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;

import games.bevs.survivalgames.commons.utils.PlayerUtils;

public class Lobby 
{
	public void spawn(Player player)
	{
		PlayerUtils.reset(player);
		
		player.setGameMode(GameMode.SURVIVAL);
		player.teleport(Bukkit.getWorlds().get(0).getSpawnLocation());
	}
}
