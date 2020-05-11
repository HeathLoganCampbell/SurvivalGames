package games.bevs.survivalgames.listeners;

import games.bevs.survivalgames.SurvivalGames;
import games.bevs.survivalgames.commons.utils.CC;
import games.bevs.survivalgames.game.games.Game;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.HashMap;
import java.util.UUID;

public class CompassListener implements Listener
{
    private HashMap<UUID, Long> cooldownCommand = new HashMap<>();

    @EventHandler
    public void onDamage(PlayerInteractEvent e)
    {
        Player player = e.getPlayer();
        Game game = SurvivalGames.get().getGame(player);
        Long aLong = cooldownCommand.get(player.getUniqueId());
        if(aLong != null && aLong > System.currentTimeMillis())
        {
            return;
        }
        if(game == null) return;
        if(player.getItemInHand() == null) return;
        if(player.getItemInHand().getType() != Material.COMPASS) return;
        double closestDist = 1000000;
        Player targetPlayer = null;
        for (Player onlinePlayer : Bukkit.getOnlinePlayers())
        {
            if(onlinePlayer.getWorld() != player.getWorld()) continue;
            if(onlinePlayer == player) continue;;
            Game game1 = SurvivalGames.get().getGame(onlinePlayer);
            if(game1 == null) continue;
            if(game1.isAlive(onlinePlayer.getUniqueId()))
            {
                double dist = onlinePlayer.getLocation().distanceSquared(player.getLocation());
                if(targetPlayer == null || closestDist > dist)
                {
                    closestDist = dist;
                    targetPlayer = onlinePlayer;
                }
            }
        }

        cooldownCommand.put(player.getUniqueId(), System.currentTimeMillis() + 3 * 1000);

        if(targetPlayer == null)
        {
            player.sendMessage(CC.red + "No players found");
        }
        else
        {
            player.setCompassTarget(targetPlayer.getLocation());
            player.sendMessage(CC.green + "Compass pointing to " + targetPlayer.getName());
        }
    }
}
