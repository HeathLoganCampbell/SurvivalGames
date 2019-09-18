package games.bevs.survivalgames.commons.utils;

import org.bukkit.entity.Player;

public class EffectUtils 
{
	/**
	 * Clears all active potion effects
	 * @param player
	 */
	public static void clearEffects(Player player)
	{
		player.getActivePotionEffects().forEach(effect -> player.removePotionEffect(effect.getType()));;
	}
}
