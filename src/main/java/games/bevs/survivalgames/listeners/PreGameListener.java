package games.bevs.survivalgames.listeners;

import games.bevs.survivalgames.SurvivalGames;
import games.bevs.survivalgames.game.Stage;
import games.bevs.survivalgames.game.games.Game;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class PreGameListener implements Listener
{
    @EventHandler
    public void onDamage(PlayerInteractEvent e)
    {
        Player player = e.getPlayer();
        Game game = SurvivalGames.get().getGame(player);
        if(game == null) return;
        if(game.getStage().isLive()) return;

        e.setCancelled(true);
    }
}
