package games.bevs.survivalgames.game.componets.death;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import games.bevs.survivalgames.game.Stage;
import games.bevs.survivalgames.game.componets.Component;
import games.bevs.survivalgames.game.games.Game;

public class DeathComponet extends Component 
{
	public DeathComponet(Game game) 
	{
		super("Basic Death", game);
	}
	
	private void leave(Player player)
	{
		Stage stage = this.getGame().getStage();
		
		if(!stage.isLive())
			return;
		
		if(!this.isInGame(player))
			return;
		
		this.onTriggerDeath(player, null, DamageCause.CUSTOM);
	}
	
	private Player getDamager(EntityDamageEvent damageEvent)
	{
		
		if(damageEvent instanceof EntityDamageByEntityEvent )
		{
			EntityDamageByEntityEvent eByEntity = (EntityDamageByEntityEvent) damageEvent;
			Entity damager = eByEntity.getDamager();
			
			if(damager instanceof Projectile)
			{
				Projectile projectile = (Projectile) damager;
				if(projectile.getShooter() instanceof Player)
					return (Player) projectile.getShooter();
			}
			
			if(damager instanceof Player)
			{
				return (Player) damager;
			}
		}
		
		return null;
	}
	
	/**
	 * @return cancel
	 */
	public boolean onTriggerDeath(Player player, Player killer, DamageCause cause)
	{
		this.getGame().death(player, killer, cause);
		return true;
	}
	
	@EventHandler(priority = EventPriority.HIGH)
	public void onDamage(EntityDamageEvent e)
	{
		if(!(e.getEntity() instanceof Player))
			return;
		Player player = (Player) e.getEntity();
		
		if(player.getWorld() != this.getGameWorld())
			return;
		
		double finalDamage = e.getFinalDamage();
		double health = player.getHealth();
		
		//Their health is greater then damage so they haven't died.. YET!
		if(finalDamage < health)
			return;
		
		Player killer = this.getDamager(e);
		
		if(this.onTriggerDeath(player, killer, e.getCause()))
			e.setCancelled(true);
	}
	
	
	@EventHandler
	public void onQuit(PlayerQuitEvent e)
	{
		Player player = e.getPlayer();
		this.leave(player);
	}
	
	@EventHandler
	public void onKick(PlayerKickEvent e)
	{
		Player player = e.getPlayer();
		this.leave(player);
	}
}
