package games.bevs.survivalgames.commons.utils;

import org.bukkit.entity.Player;

public class PlayerUtils 
{
	/**
	 * resets all effects, items and restores health and hunger
	 */
	public static void reset(Player player)
	{
		InventoryUtils.clearAllInventory(player);
		EffectUtils.clearEffects(player);
		
		PlayerUtils.resetHealth(player);
		PlayerUtils.resetHunger(player);
		PlayerUtils.resetFire(player);
		PlayerUtils.resetFall(player);
		PlayerUtils.resetExp(player);
		PlayerUtils.resetFly(player);
	}
	
	public static void resetHunger(Player player)
	{
		player.setFoodLevel(20);
	}
	
	public static void resetHealth(Player player)
	{
		player.setMaxHealth(20);
		player.setHealth(20);
	}
	
	public static void resetFire(Player player)
	{
		player.setFireTicks(0);
	}
	
	public static void resetFall(Player player)
	{
		player.setFallDistance(0);
	}
	
	public static void resetFly(Player player)
	{
		player.setFlying(false);
		player.setAllowFlight(false);
	}
	
	public static void resetExp(Player player)
	{
		player.setExp(0);
		player.setLevel(0);
	}
}
