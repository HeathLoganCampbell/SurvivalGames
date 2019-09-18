package games.bevs.survivalgames.commons.utils;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Projectile;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;

public class EventUtils 
{
	public static LivingEntity getDamagerEntity(EntityDamageEvent event, boolean ranged)
	{
		if (!(event instanceof EntityDamageByEntityEvent)) return null;
		
		EntityDamageByEntityEvent eventEE = (EntityDamageByEntityEvent) event;

		if (eventEE.getDamager() instanceof LivingEntity) 
			return (LivingEntity) eventEE.getDamager();
		if (!ranged)  return null;
		if (!(eventEE.getDamager() instanceof Projectile)) return null;
		Projectile projectile = (Projectile) eventEE.getDamager();

		if (projectile.getShooter() == null) return null;
		if (!(projectile.getShooter() instanceof LivingEntity)) return null;
		
		return (LivingEntity) projectile.getShooter();
	}
	
}
