package games.bevs.survivalgames.game.componets.instabreak;

import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockDamageEvent;

import games.bevs.survivalgames.game.componets.Component;
import games.bevs.survivalgames.game.games.Game;

/**
 * Instantly break blocks
 * 
 * @author Sprock
 */
public class InstaBlockComponet extends Component 
{
	public InstaBlockComponet(Game game) 
	{
		super("Insta Break", game);
	}
	
	@EventHandler
	public void onBreak(BlockDamageEvent e)
	{
		Player player = e.getPlayer();
		if(player.getWorld() != this.getGameWorld())
			return;
		if(player.getGameMode() == GameMode.CREATIVE)
			return;
		
		if(e.getBlock().getType() == Material.BEDROCK)
			return;
		
		e.getBlock().setType(Material.AIR);
		e.setCancelled(true);
	}
	
}
