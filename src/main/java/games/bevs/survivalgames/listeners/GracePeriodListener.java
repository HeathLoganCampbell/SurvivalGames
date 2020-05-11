package games.bevs.survivalgames.listeners;

import games.bevs.survivalgames.SurvivalGames;
import games.bevs.survivalgames.commons.utils.CC;
import games.bevs.survivalgames.events.StageChangeEvent;
import games.bevs.survivalgames.game.Stage;
import games.bevs.survivalgames.game.games.Game;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

public class GracePeriodListener implements Listener
{
    @EventHandler
    public void onDamage(EntityDamageEvent e)
    {
        Entity entity = e.getEntity();
        if(!(entity instanceof Player)) return;
        Player player = ((Player) e.getEntity());

        Game game = SurvivalGames.get().getGame(player);
        if(game == null) return;
        if(game.getStage() != Stage.LIVE)
        {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onDamage(StageChangeEvent e)
    {
        if (e.getNextStage() == Stage.GRACE_PERIOD) {
            Bukkit.broadcastMessage(CC.red + "You will not take damage for the next 30 seconds!");
        }

        if(e.getCurrentStage() == Stage.GRACE_PERIOD)
        {
            Bukkit.broadcastMessage(CC.red + "Grace period has ended, you no can PVP!");
        }
    }
}
