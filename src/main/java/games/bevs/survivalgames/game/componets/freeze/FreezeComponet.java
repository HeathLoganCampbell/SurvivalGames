package games.bevs.survivalgames.game.componets.freeze;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.util.Vector;

import games.bevs.survivalgames.game.Stage;
import games.bevs.survivalgames.game.componets.Component;
import games.bevs.survivalgames.game.games.Game;

public class FreezeComponet extends Component 
{
	private static final Vector ZERO_VECTOR = new Vector(0, 0, 0);
	
	public FreezeComponet(Game game) 
	{
		super("Freeze", game);
		
	}
	
	@EventHandler
	public void onMove(PlayerMoveEvent e)
	{
		Player player = e.getPlayer();
		Stage stage = this.getGame().getStage();
		
		Location to = e.getTo(),
				 from = e.getFrom();
		
		if(to.getBlockX() == from.getBlockX()
				&& to.getBlockZ() == from.getBlockZ())
			return;
		
		if(stage == Stage.FROZEN ||stage == Stage.TELEPORTING)
		{
			e.setTo(e.getFrom());
			player.setVelocity(ZERO_VECTOR);
		}
	}
}
