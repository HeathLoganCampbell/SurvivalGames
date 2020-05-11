package games.bevs.survivalgames.listeners;

import games.bevs.survivalgames.SurvivalGames;
import games.bevs.survivalgames.game.games.Game;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryPickupItemEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;

public class SpectatorListener implements Listener
{
    @EventHandler
    public void onDamage(EntityDamageByEntityEvent e)
    {
        Entity entity = e.getEntity();
        if((entity instanceof Player) && (e.getDamager() instanceof Player)) {
            Player player = ((Player) e.getEntity());

            Game game = SurvivalGames.get().getGame(player);
            if (game == null) return;
            if (game.isAlive(player.getUniqueId()) && game.isAlive(e.getDamager().getUniqueId())) return;

            Bukkit.broadcastMessage("Cancleed");
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onPickUp(PlayerPickupItemEvent e)
    {
        Player player = e.getPlayer();
        Game game = SurvivalGames.get().getGame(player);
        if(game == null) return;
        if(player.getGameMode() == GameMode.CREATIVE) return;
        if(game.isAlive(player.getUniqueId())) return;

        e.setCancelled(true);
    }

    @EventHandler
    public void onInv(InventoryClickEvent e)
    {
        Player player = (Player) e.getWhoClicked();
        Game game = SurvivalGames.get().getGame(player);
        if(game == null) return;
        if(player.getGameMode() == GameMode.CREATIVE) return;
        if(game.isAlive(player.getUniqueId())) return;

        e.setCancelled(true);
    }
}
