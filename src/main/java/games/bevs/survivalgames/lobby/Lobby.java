package games.bevs.survivalgames.lobby;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.World;
import org.bukkit.entity.Player;

import games.bevs.survivalgames.commons.utils.PlayerUtils;
import lombok.Getter;

public class Lobby 
{
	@Getter
	private World world;
	
	public Lobby(World world)
	{
		this.world = world;
	}

	public void spawn(Player player)
	{
		PlayerUtils.reset(player);
		
		player.setFlying(false);
		player.setAllowFlight(false);
		
		player.setGameMode(GameMode.SURVIVAL);
		player.teleport(this.getWorld().getSpawnLocation());
		player.sendMessage("Send Lobby");
	}
}
