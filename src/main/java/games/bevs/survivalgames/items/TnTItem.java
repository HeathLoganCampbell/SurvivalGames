package games.bevs.survivalgames.items;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockPlaceEvent;

//placing tnt makes it auto light
public class TnTItem extends SpecialItem
{
    @EventHandler
    public void onTnt(BlockPlaceEvent e)
    {
        Player player = e.getPlayer();
        Block block = e.getBlock();
        if(block.getType() != Material.TNT) return;

        TNTPrimed tnt = (TNTPrimed) player.getWorld().spawnEntity(block.getLocation().add(0.5, 0.5, 0.5), EntityType.PRIMED_TNT);
        tnt.setFuseTicks(200);
        e.setCancelled(true);
    }
}
