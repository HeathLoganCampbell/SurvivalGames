package games.bevs.survivalgames.game.componets.worldblocks;

import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;

import games.bevs.survivalgames.game.Stage;
import games.bevs.survivalgames.game.componets.Component;
import games.bevs.survivalgames.game.games.Game;

/**
 * Stop you from breaking all but a select amount of blocks
 * @author heathlogancampbell
 *
 */
public class WorldBlockComponet extends Component 
{
	private Material[] ALLOW_BLOCKS = new Material[] { Material.LEAVES, Material.LEAVES_2, Material.WEB, Material.GLASS, Material.STAINED_GLASS_PANE, Material.THIN_GLASS };
	
	public WorldBlockComponet(Game game) 
	{
		super("World Blocks", game);
	}
	
	@EventHandler
	public void onPlace(BlockPlaceEvent e)
	{
		Player player = e.getPlayer();
		if(player.getWorld() != this.getGameWorld())
			return;
		
		if(player.getGameMode() == GameMode.CREATIVE)
			return;
		
		for(Material mat : ALLOW_BLOCKS)
			if(e.getBlock().getType() == mat)
				return;
		
		e.setCancelled(true);
	}
	
	@EventHandler
	public void onBreak(BlockBreakEvent e)
	{
		Player player = e.getPlayer();
		if(player.getWorld() != this.getGameWorld())
			return;
		if(player.getGameMode() == GameMode.CREATIVE)
			return;
		
		if(this.getGame().getStage() == Stage.LIVE || this.getGame().getStage() == Stage.GRACE_PERIOD)
			for(Material mat : ALLOW_BLOCKS)
				if(e.getBlock().getType() == mat)
					return;
		
		e.setCancelled(true);
	}
	
}
