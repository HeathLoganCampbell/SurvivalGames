package games.bevs.survivalgames.game.componets.chest;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Chest;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import games.bevs.survivalgames.commons.utils.ItemStackBuilder;
import games.bevs.survivalgames.commons.utils.MathUtils;
import games.bevs.survivalgames.game.componets.Component;
import games.bevs.survivalgames.game.games.Game;

public class ChestComponet extends Component 
{
	private static final BlockFace[]  CHEST_BLOCKS_DOUBLE_FACES = new BlockFace[] { BlockFace.NORTH, BlockFace.SOUTH, BlockFace.WEST, BlockFace.EAST}; 
	private static ArrayList<ItemStack> items = new ArrayList<>();
	
	private HashSet<Block> openedChests = new HashSet<>();
	
	public ChestComponet(Game game) 
	{
		super("Loot Chest", game);
		
		this.populate();
	}
	
	
	//TODO: Add potions and bow + arrows, melon slices
	public void populate()
	{
		//weapons
		items.add(new ItemStackBuilder(Material.WOOD_SWORD).build());
		items.add(new ItemStackBuilder(Material.STONE_SWORD).build());
		items.add(new ItemStackBuilder(Material.GOLD_SWORD).build());
		
		items.add(new ItemStackBuilder(Material.WOOD_AXE).build());
		items.add(new ItemStackBuilder(Material.STONE_AXE).build());
		items.add(new ItemStackBuilder(Material.GOLD_AXE).build());

		items.add(new ItemStackBuilder(Material.STONE_HOE).build());
		
		//
		items.add(new ItemStackBuilder(Material.FISHING_ROD).build());
		items.add(new ItemStackBuilder(Material.WEB).build());
		items.add(new ItemStackBuilder(Material.ARROW).amount(MathUtils.getRandom().nextInt(3) + 1).build());
		
		//Armor
		items.add(new ItemStackBuilder(Material.LEATHER_HELMET).build());
		items.add(new ItemStackBuilder(Material.LEATHER_CHESTPLATE).build());
		items.add(new ItemStackBuilder(Material.LEATHER_LEGGINGS).build());
		items.add(new ItemStackBuilder(Material.LEATHER_BOOTS).build());
		
		items.add(new ItemStackBuilder(Material.GOLD_HELMET).build());
		items.add(new ItemStackBuilder(Material.GOLD_CHESTPLATE).build());
		items.add(new ItemStackBuilder(Material.GOLD_LEGGINGS).build());
		items.add(new ItemStackBuilder(Material.GOLD_BOOTS).build());
		
		//food
		items.add(new ItemStackBuilder(Material.APPLE).amount(MathUtils.getRandom().nextInt(2) + 1).build());
		items.add(new ItemStackBuilder(Material.APPLE).amount(MathUtils.getRandom().nextInt(2) + 1).build());
		items.add(new ItemStackBuilder(Material.BREAD).amount(MathUtils.getRandom().nextInt(3) + 1).build());
		items.add(new ItemStackBuilder(Material.BREAD).amount(MathUtils.getRandom().nextInt(3) + 1).build());
		items.add(new ItemStackBuilder(Material.MUSHROOM_SOUP).build());
		items.add(new ItemStackBuilder(Material.CARROT).amount(MathUtils.getRandom().nextInt(3) + 1).build());
		
		items.add(new ItemStackBuilder(Material.COOKED_CHICKEN).amount(MathUtils.getRandom().nextInt(2) + 1).build());
		items.add(new ItemStackBuilder(Material.RAW_CHICKEN).amount(MathUtils.getRandom().nextInt(2) + 1).build());
		items.add(new ItemStackBuilder(Material.RAW_CHICKEN).amount(MathUtils.getRandom().nextInt(2) + 1).build());
		items.add(new ItemStackBuilder(Material.RAW_CHICKEN).amount(MathUtils.getRandom().nextInt(2) + 1).build());
		items.add(new ItemStackBuilder(Material.COOKED_BEEF).amount(MathUtils.getRandom().nextInt(2) + 1).build());
		items.add(new ItemStackBuilder(Material.COOKED_FISH).amount(MathUtils.getRandom().nextInt(2) + 1).build());
		items.add(new ItemStackBuilder(Material.GRILLED_PORK).amount(MathUtils.getRandom().nextInt(2) + 1).build());
		
		//craftables
		items.add(new ItemStackBuilder(Material.IRON_INGOT).build());
		items.add(new ItemStackBuilder(Material.GOLD_INGOT).build());
		items.add(new ItemStackBuilder(Material.DIAMOND).build());
		items.add(new ItemStackBuilder(Material.STICK).build());
		items.add(new ItemStackBuilder(Material.BOW).build());

		
		//misc
		items.add(new ItemStackBuilder(Material.BOAT).build());
	}
	
	public List<ItemStack> getItems(int tier)
	{
		ArrayList<ItemStack> items = new ArrayList<>();
		for(int i = 0; i < MathUtils.getRandom().nextInt(5) + 1; i++)
		{
			int index = MathUtils.getRandom().nextInt(ChestComponet.items.size());
			items.add(ChestComponet.items.get(index));
		}
		
		return items;
	}
	
	private void fillChest(Chest chest, Inventory inv)
	{
		List<ItemStack> items = this.getItems(1);
		for(ItemStack item : items)
		{
			int slot = MathUtils.getRandom().nextInt(inv.getSize());
			int misses = 0;
			while(inv.getItem(slot) != null && misses < 10)
			{
				slot = MathUtils.getRandom().nextInt(inv.getSize());
				misses++;
			}
			
			inv.setItem(slot, item);
		}
		chest.update();
	}
	
	//TODO: check if the world is the game world
	@EventHandler
	public void onInteract(PlayerInteractEvent e)
	{
		Block block = e.getClickedBlock();
		
		if(block == null)
			return;
		
		if(block.getWorld() != this.getGameWorld())
			return;

		if (block.getType() == Material.CHEST  && !this.openedChests.contains(block))
		{
			this.openedChests.add(block);
			
			Chest chest = (Chest) block.getState();
			Inventory inv = chest.getBlockInventory();

			 //add block to openChest
			 for(BlockFace blockface : CHEST_BLOCKS_DOUBLE_FACES)
			 {
				 Block otherBlock = block.getRelative(blockface);
				 if(otherBlock.getType() == Material.CHEST && !this.openedChests.contains(otherBlock))
				 {
					 this.openedChests.add(otherBlock);
					 Chest otherChest = (Chest) otherBlock.getState();
					 Inventory otherInv = otherChest.getBlockInventory();
					 
					 this.fillChest(otherChest, otherInv);
				 }
			 }			
			
			 this.fillChest(chest, inv);
		}
	}
}
