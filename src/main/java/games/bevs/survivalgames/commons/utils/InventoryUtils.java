package games.bevs.survivalgames.commons.utils;

import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

public class InventoryUtils
{
	/**
	 * Removes all items from the player
	 * @param player
	 */
	public static void clearAllInventory(Player player)
	{
		player.getInventory().clear();
		InventoryUtils.clearArmor(player);
		InventoryUtils.clearInventoryCrafting(player);
		InventoryUtils.clearCursor(player);
	}
	
	/**
	 * removes the current armor the player is wearing
	 * @param player
	 */
	public static void clearArmor(Player player)
	{
		PlayerInventory inv = player.getInventory();
		inv.setArmorContents(new ItemStack[4]);
	}
	
	/**
	 * Clears crafting table inside inventory
	 * Note: when inventories are getting cleared, some people will put
	 * items in there to keep them.
	 * @param player
	 */
	public static void clearInventoryCrafting(Player player)
	{
		PlayerInventory inv = player.getInventory();
		for(int i = 0; i < 4; i++)
			inv.setItem(1 + i, null);
	}
	
	public static void clearCursor(Player player)
	{
		player.setItemOnCursor(null);
	}
	
	public static void addItem(Inventory inv, ItemStack item)
	{
		inv.addItem(item);
	}
}
