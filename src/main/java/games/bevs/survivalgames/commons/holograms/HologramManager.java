package games.bevs.survivalgames.commons.holograms;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.event.server.PluginDisableEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class HologramManager
{
    private HologramList hologramlist;

    public HologramManager(JavaPlugin plugin)
    {
//        HologramList hologramsLoaded = this.loadHolograms();
//        if(hologramsLoaded != null) {
//            this.hologramlist = hologramsLoaded;
//            System.out.println("Set loaded holograms");
//            this.hologramlist.getHolograms().forEach(holo -> this.log(holo.getId()));
//        } else{
            this.hologramlist = new HologramList();
//        }

//        this.registerCommand(new HologramCommand(this));
    }

    public List<Hologram> getHolograms()
    {
        return this.hologramlist.getHolograms();
    }

    public void registerHologram(Hologram hologram)
    {
        getHolograms().add(hologram);
        hologram.spawn(hologram.getLocation().getWorld().getPlayers());
    }

    public void unregisterHologram(Hologram hologram)
    {
        hologramlist.getHolograms().remove(hologram);
    }

    public Hologram getHologram(String id)
    {
        for (Hologram hologram : this.getHolograms())
        {
            if(hologram.getId().equalsIgnoreCase(id))
                return hologram;
        }
        return null;
    }

//    public void saveHolograms(HologramList hologramsToSave)
//    {
//        String gsons = GsonUtil.getPrettyGson().toJson(hologramsToSave);
//        FileUtils.writeFile("plugins" + File.separator + "Library" + File.separator + "hologram.json", gsons);
//    }

//    public HologramList loadHolograms()
//    {
//        String jsonHologram = FileUtils.readFile("plugins" + File.separator + "Library" + File.separator + "hologram.json");
//        if(jsonHologram == null) return null;
//        HologramList list = GsonUtil.getPrettyGson().fromJson(jsonHologram, HologramList.class);
//        System.out.println(list + "   " + list.getHolograms().size());
//        list.getHolograms().forEach(holo -> System.out.println(holo.getId() + " <<< --"));
//        return list;
//    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e)
    {
        Player player = e.getPlayer();
        for (Hologram hologram : this.getHolograms())
        {
            if(hologram.getLocation().getWorld() == player.getWorld())
            {
                hologram.spawn(Arrays.asList(player));
            }
        }
        //spawn mobs
    }

    @EventHandler
    public void onTeleport(PlayerTeleportEvent e)
    {
        Player player = e.getPlayer();
        for (Hologram hologram : this.getHolograms())
        {

            if(hologram.getLocation().getWorld() == player.getWorld())
            {
                hologram.spawn(Arrays.asList(player));
            }
        }

//        this.hologramlist.getHolograms().forEach(holo -> this.log(holo.getId()));
    }

    @EventHandler
    public void onDisable(PluginDisableEvent e)
    {
//        this.saveHolograms(this.hologramlist);
    }
}
