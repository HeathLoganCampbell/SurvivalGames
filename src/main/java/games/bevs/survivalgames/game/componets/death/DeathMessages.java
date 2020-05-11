package games.bevs.survivalgames.game.componets.death;

import org.bukkit.Material;
import org.bukkit.entity.*;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.projectiles.ProjectileSource;

public class DeathMessages
{
    public String onDeath(Player victim, Entity killer, EntityDamageEvent.DamageCause lastCause)
    {
        String deathMessage = victim.getName() + " died";

        if(killer != null)
        {
            deathMessage = victim + " was slain by " + killer.getName();
        }

        if(lastCause == EntityDamageEvent.DamageCause.FALL)
        {
            deathMessage = victim + " fell from a high place";
        }

        if(lastCause == EntityDamageEvent.DamageCause.DROWNING)
        {
            deathMessage = victim + " couldn't swim";
        }

        if(lastCause == EntityDamageEvent.DamageCause.FIRE)
        {
            deathMessage = victim + " couldn't take the heat";
        }

        if(lastCause == EntityDamageEvent.DamageCause.LAVA)
        {
            deathMessage = victim + " got a bit too toasty";
        }

        if(lastCause == EntityDamageEvent.DamageCause.VOID)
        {
            deathMessage = victim + " fell out of the world";
        }

        if(lastCause == EntityDamageEvent.DamageCause.SUICIDE)
        {
            deathMessage = victim + " just couldn't take it anymore";
        }
        // Fall damage?
        // Void Damage?
        // Command?

        if(killer instanceof Projectile)
        {
            Projectile projectile = (Projectile) killer;
            deathMessage = victim.getName() + " was shoot to death";

            ProjectileSource shooter = projectile.getShooter();

            if(shooter instanceof Player)
            {
                Player killerPlayer = (Player) shooter;
                deathMessage = victim.getName() + " was shoot to death by " + killerPlayer.getName();

                double dist = victim.getLocation().distanceSquared(killerPlayer.getLocation());
                if(dist > (40 * 40))
                {
                    deathMessage += " from " + Math.sqrt(dist) + " blocks";
                }
            }
            else if(shooter instanceof Skeleton)
            {
                deathMessage = victim.getName() + " was shoot to death by a Skeleton";
            }
            else if(shooter instanceof Blaze)
            {
                deathMessage = victim.getName() + " was shoot to death by a Blaze";
            }
        }
        else if(killer instanceof Player)
        {
            Player killerPlayer = (Player) killer;
            ItemStack weapon = killerPlayer.getItemInHand();

            if(weapon == null &&  weapon.getType() == Material.AIR)
            {
                deathMessage = victim.getName() + " punched to death by " + killerPlayer.getName();
            }
            else
            {
                deathMessage = victim.getName() + " slain by " + killerPlayer.getName() + "'s " + weapon.getType().name().toLowerCase().replaceAll("_", " ");
            }
        }

        return deathMessage;
    }
}
