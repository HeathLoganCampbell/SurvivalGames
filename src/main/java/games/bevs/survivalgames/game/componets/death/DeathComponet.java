package games.bevs.survivalgames.game.componets.death;

import games.bevs.survivalgames.SurvivalGames;
import games.bevs.survivalgames.scorecard.Scorecard;
import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.Sound;
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
	private DeathMessages deathMessages = new DeathMessages();

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
		
		this.onTriggerDeath(player, null, null, DamageCause.CUSTOM);
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
	public boolean onTriggerDeath(Player player, Player killer, Entity entityKiller, DamageCause cause)
	{
		this.getGame().death(player, killer, cause);
		this.deathMessages.onDeath(player, entityKiller, cause);
		if(killer != null)
		{
			Scorecard scorecard = SurvivalGames.get().getScorecardManager().getScorecard(killer);
			scorecard.addScoreEntry("killing " + player.getName() + " in " + this.getGame().getId(), SurvivalGames.POINTS_PER_KILL);
		}
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

		Entity killerEntity = null;
		if(e instanceof EntityDamageByEntityEvent )
		{
			killerEntity = ((EntityDamageByEntityEvent) e).getDamager();
		}

		Player killer = this.getDamager(e);
		
		if(killer != null)
			killer.playSound(killer.getLocation(), Sound.BLAZE_DEATH, 1f, 1f);

		for(int i = 0; i < 2; i++) {
			player.getWorld().playEffect(player.getLocation(), Effect.STEP_SOUND, Material.REDSTONE_BLOCK);
			player.getWorld().playEffect(player.getEyeLocation(), Effect.STEP_SOUND, Material.REDSTONE_BLOCK);
		}
		if (player.getVehicle() != null)
			player.getVehicle().eject();
		player.eject();



		if(this.onTriggerDeath(player, killer, killerEntity, e.getCause()))
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
