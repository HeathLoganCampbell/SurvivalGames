package games.bevs.survivalgames.commons.gui;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;

import games.bevs.survivalgames.commons.utils.Callback;
import games.bevs.survivalgames.commons.utils.ItemStackBuilder;
import lombok.Getter;

@Getter
public class Menu implements Listener
{
	private static final Sound SUCCESSFUL_CLICK_SOUND = Sound.CLICK;
	
	private HashMap<Integer, MenuIcon> icons = new HashMap<>();
	private String title;
	private int size;
	private Inventory inv;
	
	public Menu(String title, int size)
	{
		this.title = title;
		this.size = size;
		this.inv = Bukkit.createInventory(null, size, title);
	}
	
	public void setIcon(MenuIcon icon)
	{
		this.icons.put(icon.getSlot(), icon);
	}
	
	/**
	 * 
	 * @param slot
	 * @param itemStackbuilder
	 * @param runnable,
	 * 			ran when item is clicked
	 */
	public void setIcon(Integer slot, ItemStackBuilder itemStackbuilder, Callback<Player> run)
	{
		MenuIcon icon = new MenuIcon(itemStackbuilder, slot, run);
		this.setIcon(icon);
		this.inv.setItem(slot, itemStackbuilder.build());
	}
	
	/**
	 * No action happens when Item is clicked
	 * @param slot
	 * @param itemStackbuilder
	 */
	public void setIcon(Integer slot, ItemStackBuilder itemStackbuilder)
	{
		this.setIcon(slot, itemStackbuilder, (player) -> {});
	}
	
	@EventHandler
	public void onInventoryClick(InventoryClickEvent e)
	{
		Player player = (Player) e.getWhoClicked();
		Inventory  inv = e.getInventory();
		int slot = e.getSlot();
		
		if(inv == null) 
			return;
		
		if(!inv.getTitle().equalsIgnoreCase(title))
			return;
		MenuIcon icon = this.getIcons().get(slot);
		
		if(icon != null)
		{
			icon.getRunnable().run(player);
			player.playSound(player.getLocation(), SUCCESSFUL_CLICK_SOUND, 1f, 1f);
		}
		
		e.setCancelled(true);
	}
	
	@EventHandler
	public void onInvClose(InventoryCloseEvent e)
	{
		if(!inv.getTitle().equalsIgnoreCase(title))
			return;
		//Lets have a mermory leak idc
//		inv.getViewers().forEach(player -> {
//			if(e.getPlayer() != player) 
//				player.closeInventory();
//		});
//		PluginUtils.unregisterListener(this);
	}
	
	public void open(Player player)
	{
		player.openInventory(this.getInv());
	}
}
